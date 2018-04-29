package com.project.studycards.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.project.studycards.DeckModes;

import java.util.jar.Attributes;

@SuppressLint("ValidFragment")
public abstract class DefaultFragment extends Fragment implements FragmentManager.OnBackStackChangedListener{

    private static String LOG_TAG = DefaultFragment.class.getCanonicalName();
    private DeckModes deckModes;

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle saveInstanceState) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(this).commit();
        }
        super.onInflate(activity, attrs, saveInstanceState);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public abstract void onViewCreated(View view, Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        deckModes = (DeckModes) getActivity();
        getFragmentManager().addOnBackStackChangedListener(this);

        setRetainInstance(true);
        shouldDisplayHomeUp();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (shouldDisplayHomeUp()) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    deckModes.onBackPressed();
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean shouldDisplayHomeUp() {
        boolean back = false;
        try {
            back = getFragmentManager().getBackStackEntryCount() >0;
        } catch (Exception e) {

        };
        /*if (back) {
            deckModes.getDrawerToggle().setDrawerIndicatorEnable(false);
        } else {
            deckModes.getDrawerToggle().setDrawerIndicatorEnable(true);
        }*/
        return back;
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }
}
