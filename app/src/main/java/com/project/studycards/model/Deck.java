package com.project.studycards.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Deck implements Parcelable {
    private String name;
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public Deck (String name) {
        this.name = name;
        this.cards = new LinkedList<>();
    }

    public void addCard (Card card) {
        cards.add(card);
    }

    public void deleteCard (Card card) {
        cards.remove(card);
    }

    public String toString () {
        StringBuilder str = new StringBuilder();
        for (Card card : cards)
            str.append(card + "\n");
        return str.toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(cards);
    }
    private Deck(Parcel in) {
        name = in.readString();
        cards = new LinkedList<>();
        in.readList(cards, null);
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel source) {
            return new Deck(source);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };
}
