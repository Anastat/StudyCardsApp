package com.project.studycards;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.studycards.fragments.AddCardDialogFragment;
import com.project.studycards.model.Deck;
import com.project.studycards.model.Card;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeckModes extends AppCompatActivity implements AddCardDialogFragment.AddCardDialogListener{

    private FloatingActionButton btnAddNewCard;
    private Button btnLearningMode;
    private Deck currentDeck;


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


        //set clicked deck from MainActivity to current deck
        Intent intent = getIntent();
        currentDeck = (Deck) intent.getParcelableExtra("deck");
        Log.w("You clicked deck", currentDeck.toString());

    }

    private View.OnClickListener startLearningMode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLearningMode();
        }
    };

    private void startLearningMode () {
        Intent intent = new Intent (this, LearningModeActivity.class);
        intent.putExtra("currentDeck", currentDeck);
        startActivity(intent);
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
            this.writeCard(newCard);
        }
    }

    public void writeCard(Card card) {
        ICsvBeanWriter beanWriter = null;
        Log.i("writeCard", "Save the deck in dir: " + this.getApplicationContext().getFilesDir());
        try {
            String fileName = currentDeck.getName() + ".csv";
            String path = this.getApplicationContext().getFilesDir() + "/decks";
            File file = new File(path, fileName);
            beanWriter = new CsvBeanWriter(new FileWriter(file, true),
                    CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the bean values to each column (names must match)
            final String[] header = new String[] {"question", "answer", "priority", "count"};
            final CellProcessor[] processors = new CellProcessor[]{new NotNull(),new NotNull(), new ParseInt(), new ParseInt()};

            if (currentDeck.getCards().size() == 1) {
                // write the header
                beanWriter.writeHeader(header);
            }

            // write the beans
            beanWriter.write(card, header, processors);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
