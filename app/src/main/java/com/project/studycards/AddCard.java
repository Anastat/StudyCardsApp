package com.project.studycards;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AddCard extends AppCompatActivity {
    private Button btnAddCard;
    private TextInputEditText inputQuestion;
    private TextInputEditText inputAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        btnAddCard = (Button) findViewById(R.id.btnAddCard);
        btnAddCard.setOnClickListener(addCardOnClickListener);
        inputQuestion = (TextInputEditText) findViewById(R.id.inputQuestion);
        inputAnswer = (TextInputEditText) findViewById(R.id.inputAnswer);
    }

    private View.OnClickListener addCardOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCardBtnClicked();
        }
    };

    private void addCardBtnClicked() {

    }
}
