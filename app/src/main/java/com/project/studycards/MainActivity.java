package com.project.studycards;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.project.studycards.model.Deck;
import com.project.studycards.fragments.DeckNameDialogFragment;
import com.project.studycards.model.UserDecks;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DeckNameDialogFragment.DeckNameDialogListener{

    private FloatingActionButton btnAddNewDeck;
    private TableLayout mainTableLayout;
    private UserDecks userDecks;
    public static final String key = "deck";

    private View.OnClickListener addNewDeckOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewDeckBtnClicked();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
        btnAddNewDeck = (FloatingActionButton) findViewById(R.id.btnAddNewDeck);
        addDecksDir();
        btnAddNewDeck.setOnClickListener(addNewDeckOnClickListener);
        final AssetManager assetManager = getAssets();
        this.userDecks = new UserDecks();
        userDecks.readDecksFromFiles(assetManager, this.getApplicationContext()); //Fill UserDecks list with decks from files
        createDecksButtons();//take list of decks from UserDecks and create buttons with deck name on main screen
    }
    
    private void addDecksDir() {
        File decksDir = new File(this.getApplicationContext().getFilesDir()+"/decks");

        if(!decksDir.exists() && !decksDir.isDirectory()) {
            // create empty directory
            if (decksDir.mkdirs()) {
                Log.i("CreateDir","Decks dir created");
            } else {
                Log.w("CreateDir","Unable to create decks dir!");
            }
        } else {
            Log.i("CreateDir","Decks dir already exists");
        }
    }
    //show dialog for typing deck name
    private void addNewDeckBtnClicked() {
        DialogFragment deckNameDialog = new DeckNameDialogFragment();
        deckNameDialog.show(getFragmentManager(), "missiles");
    }

    //create new button on main screen with deck name
    @Override
    public void applyDeckname(String deckName) {
        if (!deckName.isEmpty()) {
            Deck newDeck = new Deck(deckName);
            userDecks.add(newDeck);
            createBtn(newDeck);
        }
    }

    //create buttons with existing decks  on main screen
    private void createDecksButtons () {
        if (userDecks != null) {
            for (Deck deck : userDecks.getDecks())
                createBtn(deck);
        }
    }

    //create new button with deck name
    private void createBtn (Deck deck) {
       // Button btnNew = new Button(this);
        View btnView=getLayoutInflater().inflate(R.layout.studydeckbutton,null);
        Button btnNew=btnView.findViewById(R.id.decknamebtn);
        btnNew.setText(deck.getName());
        btnNew.setOnClickListener(new DeckClickListener(deck));
        mainTableLayout.addView(btnView);
    }

    //OnClickListener for starting new activity with given deck
    public class DeckClickListener implements View.OnClickListener {
        private Deck deck;

        public DeckClickListener (Deck deck) {
            this.deck = deck;
        }

        public void onClick(View view) {
            //open new screen with deck modes
            Log.w("Deck ", deck.getName());
            Intent intent = new Intent(MainActivity.this, DeckModes.class);
            intent.putExtra(MainActivity.key, deck);//send clicked deck to DeckModes Activity
            startActivity(intent);
        }
    }
}