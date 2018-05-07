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
import java.util.Collections;

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
    private int guessedCards;
    private boolean end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_deck_layout);

        currentCard = 0;
        guessedCards = 0;
        end = false;
        testQuestion = (TextView) findViewById(R.id.testQuestion);
        viewTestAnswer = (TextView) findViewById(R.id.viewTestAnswer);
        cardStat = (TextView) findViewById(R.id.cardStat);
        testAnswer = (EditText) findViewById(R.id.testAnswer);
        btnShowAnswer = (Button) findViewById(R.id.btnShowAnswer);
        btnNextCard = (Button) findViewById(R.id.btnNextCard);
        textInputLayout4 = (TextInputLayout) findViewById(R.id.textInputLayout4);

        Intent intent = getIntent();
        deck = (Deck) intent.getParcelableExtra(MainActivity.key);
        Collections.sort(deck.getCards()); //Sort cards by priority
        updateQuestion(currentCard);
        setTitle(deck.getName());
        //set listener for 'show answer' and 'next card' buttons
        btnShowAnswer.setOnClickListener(showAnswer);
        btnNextCard.setOnClickListener(nextCard);

        //set listener for EditText field, check answer, when user press done button
        testAnswer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    userAnswer = testAnswer.getText().toString();
                    if (userAnswer.toLowerCase().equals(deck.getCards().get(currentCard).getAnswer().toLowerCase())) {
                        //if the answer is correct increment right answers count
                        deck.getCards().get(currentCard).incrementCount();
                        guessedCards++;
                        setCardPriority(5);
                        alertView("Right answer!", "");
                        isNextCard();
                    } else {
                        setCardPriority(1);
                        alertView("Wrong answer!", "");
                        showRightAnswer();
                    }
                    return true;
                }
                return false;
            }
        });
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
            isNextCard();
        }
    };
    //show current card question
    private void updateQuestion (int cardNum) {
        testQuestion.setText(deck.getCards().get(cardNum).getQuestion());
        cardStat.setText("This card guessed " + deck.getCards().get(cardNum).getCount() + " times");
    }

    private void alertView( String title, String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle( title )
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        if (end)
                            endSession();
                        else {
                            updateQuestion(currentCard);
                            testAnswer.setText("");
                        }
                    }
                }).show();
    }

    //show next card if exist
    private void isNextCard() {
        if (++currentCard<deck.getCards().size() && currentCard<10) {
            updateQuestion(currentCard);
        } else {
            end = true;
            showResult();
        }
    }

    //show how much cards guessed and finnish session
    private void showResult () {
        String message = "You guessed " + guessedCards + "/" + currentCard;
        if (currentCard-guessedCards <= 2)
            alertView("Well done!", message);
        else if (currentCard-guessedCards <= 4)
            alertView("Good job", message);
        else
            alertView("You can do better", message);
    }

    //change card priority if card new priority 0, if card guessed +5, else +1
    private void setCardPriority (int num) {
        int cardPriority = deck.getCards().get(currentCard).getPriority();
        deck.getCards().get(currentCard).setPriority(cardPriority+num);
    }

    private void endSession() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.key, deck);
        setResult(RESULT_OK, intent);
        finish();
    }
}
