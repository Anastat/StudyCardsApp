package com.project.studycards;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.studycards.fragments.AddCardDialogFragment;
import com.project.studycards.fragments.DeckNameDialogFragment;
import com.project.studycards.model.Deck;
import com.project.studycards.model.Card;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeckModes extends AppCompatActivity implements AddCardDialogFragment.AddCardDialogListener{

    private FloatingActionButton btnAddNewCard;
    private Button btnLearningMode;
    private Button btnTestMode;
    private Deck currentDeck;
    private AssetManager assetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckmodes);


        //button for adding new card in deck
        btnAddNewCard = (FloatingActionButton) findViewById(R.id.btnAddNewCard);
        btnAddNewCard.setOnClickListener(addNewCard);

        //button for start learning mode
        btnLearningMode = (Button) findViewById(R.id.btnLearningMode);
        btnLearningMode.setOnClickListener(startLearningMode);

        //button for start test mode
        btnTestMode = (Button) findViewById(R.id.btnTestMode);
        btnTestMode.setOnClickListener(startTestMode);

        //set clicked deck from MainActivity to current deck
        Intent intent = getIntent();
        currentDeck = (Deck) intent.getParcelableExtra(MainActivity.key);
        assetManager = getAssets();
        Log.w("You clicked deck", currentDeck.toString());
        setTitle(currentDeck.getName());
    }

    private View.OnClickListener startLearningMode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentDeck.getCards().isEmpty())
                startLearningMode();
            else alertView("The deck is empty!");
        }
    };


    private View.OnClickListener startTestMode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentDeck.getCards().isEmpty())
                startTestMode();
            else alertView("There are no cards in the deck!");
        }
    };



    private void startLearningMode () {
        Intent intent = new Intent (this, LearningModeActivity.class);
        intent.putExtra(MainActivity.key, currentDeck);
        startActivity(intent);
    }


    private void startTestMode() {
        Intent intent = new Intent(this, TestModeActivity.class);
        intent.putExtra(MainActivity.key, currentDeck);
        startActivityForResult(intent, 1);
    }
    private View.OnClickListener addNewCard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewCardBtnClicked();
        }
    };
    //show dialog for adding a new card
    private void addNewCardBtnClicked() {
        DialogFragment addCardDialog = new AddCardDialogFragment();
        addCardDialog.show(getFragmentManager(), "add card");
    }

    public void addCard(String question, String answer) {
        if (!question.isEmpty() && !answer.isEmpty()) {
            Card newCard = new Card(question, answer);
            currentDeck.addCard(newCard);
        }

    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle( message )
                //.setMessage()
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            this.currentDeck = (Deck) data.getParcelableExtra(MainActivity.key);
        }
    }

}
