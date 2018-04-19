package com.project.studycards;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import com.project.studycards.model.DeckNameDialogFragment;
import com.project.studycards.model.UserDecks;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements DeckNameDialogFragment.DeckNameDialogListener{

    private FloatingActionButton btnAddNewDeck;
    private TableLayout mainTableLayout;
    private String deckName;
    private UserDecks userDecks;

    private View.OnClickListener addNewDeckOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewDeckBtnClicked();
        }
    };

    private View.OnClickListener openDeckModes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDeckMenu();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
        btnAddNewDeck = (FloatingActionButton) findViewById(R.id.btnAddNewDeck);
        btnAddNewDeck.setOnClickListener(addNewDeckOnClickListener);
        final AssetManager assetManager = getAssets();
        this.userDecks = new UserDecks();
        createDecksButtons(userDecks.readDecksFromFiles(assetManager));//take file list from assets/deck folder and create buttons with deck name on main screen
    }


    //show dialog for typing deck name
    private void addNewDeckBtnClicked() {
        this.deckName = "";

        DialogFragment deckNameDialog = new DeckNameDialogFragment();
        deckNameDialog.show(getFragmentManager(), "missiles");
    }

    //open new screen with deck modes
    private void openDeckMenu () {
        Intent intent = new Intent(this, DeckModes.class);
        startActivity(intent);
    }

    //create new button on main screen with deck name
    @Override
    public void applyDeckname(String deckName) {
        this.deckName = deckName;
        if (!deckName.isEmpty()) {
            createBtn(deckName);
            //Deck newDeck = new Deck("New deck");
        }
    }

    //create buttons with existing decks  on main screen
    private void createDecksButtons (String [] filelist) {
        if (filelist != null) {
            for (String filename : filelist)
                createBtn(filename.replaceFirst("[.][^.]+$", ""));
        }
    }

    //create new button with deck name
    private void createBtn (String name) {
        Button btnNew = new Button(this);
        btnNew.setText(name);
        btnNew.setOnClickListener(openDeckModes);
        mainTableLayout.addView(btnNew);
    }

}