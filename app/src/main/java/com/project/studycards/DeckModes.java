package com.project.studycards;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeckModes extends AppCompatActivity {

    private FloatingActionButton btnAddNewCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckmodes);
        btnAddNewCard = (FloatingActionButton) findViewById(R.id.btnAddNewCard);
        btnAddNewCard.setOnClickListener(openAddCard);
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
