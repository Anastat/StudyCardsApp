package com.project.studycards.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.project.studycards.R;


public class AddCardDialogFragment extends DialogFragment {

    private AddCardDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText question = new EditText(getActivity());
        question.setHint("Question");
        layout.addView(question);

        final EditText answer = new EditText(getActivity());
        answer.setHint("Answer");
        layout.addView(answer);
        
        builder.setMessage("Add card")
                .setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Add card
                        listener.addCard(question.getText().toString(), answer.getText().toString());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        AddCardDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddCardDialogFragment.AddCardDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement deckNameDialogListener");
        }
    }

    public interface AddCardDialogListener {
        void addCard(String quest, String answ);
    }

}
