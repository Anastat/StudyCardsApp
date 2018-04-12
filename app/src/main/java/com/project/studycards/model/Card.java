package com.project.studycards.model;

import android.support.annotation.NonNull;

public class Card implements Comparable<Card>{
    private String question;
    private String answer;
    private int priority;
    private int count;

    public Card (String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.priority = 1;
        this.count = 0;
    }


    @Override
    public int compareTo(@NonNull Card o) {
        return this.priority - o.priority;
    }

    public void rightAnswersCount () {
        count++;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPriority() {
        return priority;
    }

    public int getCount() {
        return count;
    }
}
