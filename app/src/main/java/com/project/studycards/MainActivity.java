package com.project.studycards;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;

import com.project.studycards.model.Deck;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewDeck;
    private TableLayout mainTableLayout;
    //private GridLayout mainGridLayout;
    private View.OnClickListener addNewDeckOnClickistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewDeckBtnClicked();
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

        Deck newDeck = new Deck("New deck");
        Button btnNew = new Button(this);
        btnNew.setText("New deck");
        mainTableLayout.addView(btnNew);
    }
}