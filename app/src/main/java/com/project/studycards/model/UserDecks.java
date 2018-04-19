package com.project.studycards.model;

import android.content.res.AssetManager;
import android.util.Log;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
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
    public String [] readDecksFromFiles (AssetManager assetManager) {
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
                    fromFileToList("decks/" + filename);
                }
            }
            return filelist;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //save deck from file to List
    private void fromFileToList  (String filename)throws IOException {
        //create deck based on file name
        Deck newDeck = new Deck(filename.replaceFirst("[.][^.]+$", ""));
        decks.add(newDeck);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(filename));
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
}
