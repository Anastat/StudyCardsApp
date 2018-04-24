package com.project.studycards.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.studycards.R;

public class LearningModeFragment extends Fragment {

    private int pageNumber;

    public static LearningModeFragment newInstance(int n, String question, String answer) {
        LearningModeFragment fragment = new LearningModeFragment();
        Bundle args=new Bundle();
        args.putInt("num", n);
        args.putString("question", question);
        args.putString("answer", answer);
        fragment.setArguments(args);
        return fragment;
    }

    public LearningModeFragment () {

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.learning_mode_layout, container, false);
        TextView pageHeader=(TextView)result.findViewById(R.id.viewQuestion);
        String header = String.format("Page %d", pageNumber+1);
        pageHeader.setText(header);
        return result;
    }
}
