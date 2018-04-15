package com.project.studycards;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;

import com.project.studycards.model.Deck;
import com.project.studycards.model.DeckNameDialogFragment;

public class MainActivity extends AppCompatActivity implements DeckNameDialogFragment.DeckNameDialogListener{

    private FloatingActionButton btnAddNewDeck;
    private TableLayout mainTableLayout;
    private String deckName;
    //private GridLayout mainGridLayout;
    private View.OnClickListener addNewDeckOnClickistener = new View.OnClickListener() {
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
        btnAddNewDeck.setOnClickListener(addNewDeckOnClickistener);
    }


    //create new button on main screen with deck name
    private void addNewDeckBtnClicked() {
        deckName = "";
        //Deck newDeck = new Deck("New deck");
        DialogFragment deckNameDialog = new DeckNameDialogFragment();
        deckNameDialog.show(getFragmentManager(), "missiles");
    }

    //open new screen with deck modes
    private void openDeckMenu () {
        Intent intent = new Intent(this, DeckModes.class);
        startActivity(intent);
    }

    @Override
    public void applyDeckname(String deckName) {
        this.deckName = deckName;
        if (!deckName.isEmpty()) {
            //create new button with deck name
            Button btnNew = new Button(this);
            btnNew.setText(deckName);
            btnNew.setOnClickListener(openDeckModes);
            mainTableLayout.addView(btnNew);
        }
    }
}