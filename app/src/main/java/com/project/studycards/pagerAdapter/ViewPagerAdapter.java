package com.project.studycards.pagerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.studycards.R;
import com.project.studycards.model.Card;
import com.project.studycards.model.Deck;

import java.util.List;
//Adapter for learning mode activity
public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    private Deck deckCards;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, Deck deckCards) {
        this.context = context;
        this.deckCards = deckCards;
    }

    @Override
    public int getCount() {
        return deckCards.getCards().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView viewQuestion;
        TextView viewAnswer;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.learning_mode_layout, container, false);

        viewQuestion = (TextView)cardView.findViewById(R.id.viewQuestion);
        viewAnswer = (TextView) cardView.findViewById(R.id.viewAnswer);

        viewQuestion.setText(deckCards.getCards().get(position).getQuestion());
        viewAnswer.setText(deckCards.getCards().get(position).getAnswer());
        ((ViewPager)container).addView(cardView);

        return cardView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Remove learning_mode_layout.xml from ViewPager
        ((ViewPager)container).removeView((RelativeLayout)object);
    }
}
