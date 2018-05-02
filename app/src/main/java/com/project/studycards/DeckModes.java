package com.project.studycards;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.studycards.model.Deck;

public class DeckModes extends AppCompatActivity {

    private FloatingActionButton btnAddNewCard;
    private Button btnLearningMode;
    private Button btnTestMode;
    private Deck currentDeck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckmodes);

        //button for adding new card in deck
        btnAddNewCard = (FloatingActionButton) findViewById(R.id.btnAddNewCard);
        btnAddNewCard.setOnClickListener(openAddCard);

        //button for start learning mode
        btnLearningMode = (Button) findViewById(R.id.btnLearningMode);
        btnLearningMode.setOnClickListener(startLearningMode);

        //button for start test mode
        btnTestMode = (Button) findViewById(R.id.btnTestMode);
        btnTestMode.setOnClickListener(startTestMode);

        //set clicked deck from MainActivity to current deck
        Intent intent = getIntent();
        currentDeck = (Deck) intent.getParcelableExtra("deck");
        Log.w("You clicked deck", currentDeck.toString());

    }

    private View.OnClickListener openAddCard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddCardView();
        }
    };

    private View.OnClickListener startLearningMode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLearningMode();
        }
    };

    private View.OnClickListener startTestMode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startTestMode();
        }
    };

    private void openAddCardView() {
        Intent intent = new Intent(this, AddCard.class);
        startActivity(intent);
    }

    private void startLearningMode () {
        Intent intent = new Intent (this, LearningModeActivity.class);
        intent.putExtra("currentDeck", currentDeck);
        startActivity(intent);
    }

    private void startTestMode() {
        Intent intent = new Intent (this, TestModeActivity.class);
        intent.putExtra("currentDeck", currentDeck);
        startActivity(intent);
    }
}
