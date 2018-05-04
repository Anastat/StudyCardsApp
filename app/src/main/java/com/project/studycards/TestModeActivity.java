package com.project.studycards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.studycards.model.Deck;

public class TestModeActivity extends AppCompatActivity {

    private Deck deck;
    private TextView testQuestion;
    private TextView viewTestAnswer;
    private TextView cardStat;
    private EditText testAnswer;
    private Button btnShowAnswer;
    private Button btnNextCard;
    private TextInputLayout textInputLayout4;
    private String userAnswer;
    private int currentCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_deck_layout);

        currentCard = 0;
        testQuestion = (TextView) findViewById(R.id.testQuestion);
        viewTestAnswer = (TextView) findViewById(R.id.viewTestAnswer);
        cardStat = (TextView) findViewById(R.id.cardStat);
        testAnswer = (EditText) findViewById(R.id.testAnswer);
        btnShowAnswer = (Button) findViewById(R.id.btnShowAnswer);
        btnNextCard = (Button) findViewById(R.id.btnNextCard);
        textInputLayout4 = (TextInputLayout) findViewById(R.id.textInputLayout4);

        //set listener for 'show answer' and 'next card' buttons
        btnShowAnswer.setOnClickListener(showAnswer);
        btnNextCard.setOnClickListener(nextCard);

        //set listener for EditText field, check answer, when user press done button
        testAnswer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    userAnswer = testAnswer.getText().toString();
                    if (userAnswer.equals(deck.getCards().get(currentCard).getAnswer())) {
                        //if the answer is correct increment right answers count
                        deck.getCards().get(currentCard).incrementCount();
                        currentCard++;
                        alertView("Right answer!");
                    } else {
                        alertView("Wrong answer!");
                        showRightAnswer();
                    }
                    return true;
                }
                return false;
            }
        });

        Intent intent = getIntent();
        deck = (Deck) intent.getParcelableExtra("currentDeck");

        updateQuestion(currentCard);

    }

    //if answer is wrong or pressed button 'show answer'
    private void showRightAnswer() {
        //set invisible edit text field and visible button 'next card' and TextView with answer
        testAnswer.setVisibility(View.INVISIBLE);
        textInputLayout4.setVisibility(View.INVISIBLE);
        viewTestAnswer.setVisibility(View.VISIBLE);
        btnShowAnswer.setVisibility(View.GONE);
        btnNextCard.setVisibility(View.VISIBLE);
        viewTestAnswer.setText(deck.getCards().get(currentCard).getAnswer());
    }

    private View.OnClickListener showAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showRightAnswer();
        }
    };
    private View.OnClickListener nextCard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            testAnswer.setVisibility(View.VISIBLE);
            textInputLayout4.setVisibility(View.VISIBLE);
            viewTestAnswer.setVisibility(View.GONE);
            btnShowAnswer.setVisibility(View.VISIBLE);
            btnNextCard.setVisibility(View.GONE);
            updateQuestion(++currentCard);
        }
    };
    //show current card question
    private void updateQuestion (int cardNum) {
        testQuestion.setText(deck.getCards().get(cardNum).getQuestion());
        cardStat.setText("This card guessed " + deck.getCards().get(cardNum).getCount() + " times");
    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle( message )
                //.setMessage()
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        updateQuestion(currentCard);
                        testAnswer.setText("");
                    }
                }).show();
    }
}
