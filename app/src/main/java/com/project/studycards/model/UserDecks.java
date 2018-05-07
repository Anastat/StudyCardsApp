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
import java.io.FileWriter;
import java.io.IOException;
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
                    fromFileToList( filename);
                }
            }

            if (userfiles != null) {
                for (int i=0; i<userfiles.length; i++) {
                    // Get filename of file or directory
                    String filename = userfiles[i];
                    fromFileToList( filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public List<Deck> getDecks() {
        return decks;
    }

    public boolean uniqueName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return false;
            }
        }
        return true;
    }

    //save deck from file to List
    private void fromFileToList  (String filename)throws IOException {
        //create deck based on file name
        Deck newDeck = new Deck(filename.replaceFirst("[.][^.]+$", ""));
        decks.add(newDeck);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("decks/" + filename));
            ICsvBeanReader csvBeanReader = new CsvBeanReader(inputStreamReader, CsvPreference.STANDARD_PREFERENCE);
            String [] mapping = new String[]{"question", "answer", "priority", "count"};
            final CellProcessor[] processors = new CellProcessor[]{new NotNull(),new NotNull(), new ParseInt(), new ParseInt()};
            Card card;
            //read rows from file and add card to deck list
            while((card = csvBeanReader.read(Card.class, mapping, processors)) != null) {
                newDeck.addCard(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(filename, newDeck.toString());
    }

    public void add(Deck newDeck){
        this.decks.add(newDeck);
        //this.writeDeck(newDeck);
    }
/*
    public void writeDeck(Deck deck){
        ICsvBeanWriter beanWriter = null;
        Log.i("writeDeck", "Save the deck in dir: " + context.getFilesDir());
        try {

            File file = new File((context.getFilesDir().getName() + "/decks"), (deck.getName() + ".csv"));
            beanWriter = new CsvBeanWriter(new FileWriter(file),
                    CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the bean values to each column (names must match)
            final String[] header = new String[] {"question", "answer", "priority", "count"};
            final CellProcessor[] processors = new CellProcessor[]{new NotNull(),new NotNull(), new ParseInt(), new ParseInt()};

            // write the header
            beanWriter.writeHeader(header);

            // write the beans
            for (final Card card : deck.getCards()) {
                beanWriter.write(card, header, processors);
            }

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
    }*/

    public void writeDecksToFiles(Context context) {
        Log.i("writeDecksToFiles", "In writeDecksToFiles method");
        try {
            String[] filelist = assetManager.list("decks");
            if (filelist == null) {
                // dir does not exist or is not a directory
            } else {

                for (Deck deck : decks) {
                    //find out if file already exists
                    String newFileName = deck.getName() + ".csv";
                    boolean fileExists = false;

                    for (int i=0; i<filelist.length; i++) {
                        String filename = filelist[i];
                        if (newFileName.equals(filename)) {
                            fileExists = true;
                        }
                    }

                    if (fileExists) {
                        //if filename exists, go through cards, save new ones

                    } else {
                        //save deck and its cards

                        Log.i("writeDecksToFiles", "Save the decks and its cards. Directory: " + context.getFilesDir());
                        ICsvBeanWriter beanWriter = null;
                        try {

                            File file = new File(context.getFilesDir().getName() + "/decks", newFileName);
                            beanWriter = new CsvBeanWriter(new FileWriter(file),
                                    CsvPreference.STANDARD_PREFERENCE);

                            // the header elements are used to map the bean values to each column (names must match)
                            final String[] header = new String[] {"question", "answer", "priority", "count"};
                            final CellProcessor[] processors = new CellProcessor[]{new NotNull(),new NotNull(), new ParseInt(), new ParseInt()};

                            // write the header
                            beanWriter.writeHeader(header);

                            // write the beans
                            for (final Card card : deck.getCards()) {
                                beanWriter.write(card, header, processors);
                            }

                        } finally {
                            if(beanWriter != null) {
                                beanWriter.close();
                            }
                        }
                    }

                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
