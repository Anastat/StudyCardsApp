package com.project.studycards;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.studycards.model.Deck;
import com.project.studycards.adapters.ViewPagerAdapter;

import java.util.Collections;

public class LearningModeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentviewer_layout);
        Intent intent = getIntent();
        deck = (Deck) intent.getParcelableExtra(MainActivity.key);
        Collections.sort(deck.getCards());
        setTitle(deck.getName());
        viewPager = (ViewPager)findViewById(R.id.pager);
        //pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(LearningModeActivity.this, deck);
        viewPager.setAdapter(adapter);
    }
}
