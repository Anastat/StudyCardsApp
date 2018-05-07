package com.project.studycards.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserDecks {

    private List<Deck> decks;
    private AssetManager assetManager;
    private Context context;

    public UserDecks () {
        this.decks = new ArrayList<>();
    }

    //get list of files from deck folder
    public void readDecksFromFiles (AssetManager assetManager, Context context) {
        this.assetManager = assetManager;
        this.context = context;

        try {
            // for assets folder add empty string
            String[] filelist = assetManager.list("decks");
            // get users own files
            File decksDir = new File(context.getFilesDir()+"/decks");
            String[] userfiles = decksDir.list();
            if (filelist == null) {
                // dir does not exist or is not a directory
            } else {
                for (int i=0; i<filelist.length; i++) {
                    // Get filename of file or directory
                    String filename = filelist[i];
                    fromFileToList( filename, assetManager.open("decks/" + filename));
                }
            }

            if (userfiles != null) {
                for (int i=0; i<userfiles.length; i++) {
                    // Get filename of file or directory
                    String filename = userfiles[i];
                    File file = new File(context.getFilesDir()+"/decks", filename);
                    FileInputStream in = new FileInputStream(file);
                    fromFileToList( filename, in);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public List<Deck> getDecks() {
        return decks;
    }

    //save deck from file to List
    private void fromFileToList  (String filename, InputStream in)throws IOException {
        //create deck based on file name
        Deck newDeck = new Deck(filename.replaceFirst("[.][^.]+$", ""));
        decks.add(newDeck);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            ICsvBeanReader csvBeanReader = new CsvBeanReader(inputStreamReader, CsvPreference.STANDARD_PREFERENCE);
            //String [] mapping = new String[]{"question", "answer", "priority", "count"};
            final String[] header = csvBeanReader.getHeader(true);
            final CellProcessor[] processors = new CellProcessor[]{new NotNull(),new NotNull(), new ParseInt(), new ParseInt()};
            Card card;
            //read rows from file and add card to deck list
            while((card = csvBeanReader.read(Card.class, header, processors)) != null) {
                newDeck.addCard(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(filename, newDeck.toString());
    }

    public void add(Deck newDeck){
        this.decks.add(newDeck);
        this.writeDeck(newDeck);
    }

    public void writeDeck(Deck deck){
        ICsvBeanWriter beanWriter = null;
        Log.i("writeDeck", "Save the deck in dir: " + context.getFilesDir());
        try {
            String newFileName = deck.getName() + ".csv";
            String path = context.getFilesDir() + "/decks";
            File file = new File(path, newFileName);
            beanWriter = new CsvBeanWriter(new FileWriter(file),
                    CsvPreference.STANDARD_PREFERENCE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
