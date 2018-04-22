package com.project.studycards;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.studycards.model.Deck;

public class DeckModes extends AppCompatActivity {

    private FloatingActionButton btnAddNewCard;
    private Deck currentDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckmodes);
        btnAddNewCard = (FloatingActionButton) findViewById(R.id.btnAddNewCard);
        btnAddNewCard.setOnClickListener(openAddCard);

        //get clicked deck from MainActivity
        Intent intent = getIntent();
        currentDeck = (Deck) intent.getParcelableExtra("deck");
        Log.w("You clicked deck", currentDeck.getName());
    }

    private View.OnClickListener openAddCard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddCardView();
        }
    };

    private void openAddCardView() {
        Intent intent = new Intent(this, AddCard.class);
        startActivity(intent);
    }
}
