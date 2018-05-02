package com.project.studycards.model;

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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserDecks {

    private List<Deck> decks;
    private AssetManager assetManager;

    public UserDecks () {
        this.decks = new ArrayList<>();
    }

    //get list of files from deck folder
    public void readDecksFromFiles (AssetManager assetManager) {
        this.assetManager = assetManager;
        try {
            // for assets folder add empty string
            String[] filelist = assetManager.list("decks");
            if (filelist == null) {
                // dir does not exist or is not a directory
            } else {
                for (int i=0; i<filelist.length; i++) {
                    // Get filename of file or directory
                    String filename = filelist[i];
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

    public void add(Deck newDeck) {
        this.decks.add(newDeck);
    }

    public void writeDecksToFiles(AssetManager assetManager) {
        this.assetManager = assetManager;

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
                        ICsvBeanWriter beanWriter = null;
                        try {
                            beanWriter = new CsvBeanWriter(new FileWriter("decks/" + newFileName),
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
