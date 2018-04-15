package com.project.studycards.model;


import android.content.res.AssetManager;
import android.util.Log;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDecks {

    private List<Deck> decks;

    public UserDecks () {
        this.decks = new ArrayList<>();
    }

    //get list of files from deck folder
    public String [] readDecksFromFiles (AssetManager assetManager) {
        try {
            // for assets folder add empty string
            String[] filelist = assetManager.list("decks");
            if (filelist == null) {
                // dir does not exist or is not a directory
            } else {
                for (int i=0; i<filelist.length; i++) {
                    // Get filename of file or directory
                    String filename = filelist[i];
                    //fromFileToList(filename);
                    Log.w("File name ", filename);
                }
            }
            return filelist;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //save decs from file to List
    private void fromFileToList  (String filename)throws IOException {
        Deck newDeck = new Deck(filename.replaceFirst("[.][^.]+$", ""));
        decks.add(newDeck);
        ICsvBeanReader csvBeanReader = new CsvBeanReader(new FileReader(filename), CsvPreference.STANDARD_PREFERENCE);

        String [] mapping = new String[]{"question", "answer", "priority", "count"};

        CellProcessor[] procs = getProcessors();
        Card card;
        while((card = csvBeanReader.read(Card.class, mapping, procs)) != null) {
            //newDeck.addCard(card);
        }
        Log.i("Deck", newDeck.toString());
    }


    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new UniqueHashCode(),
                new NotNull(),
                new Optional(),
                new NotNull()
        };
    }
}
