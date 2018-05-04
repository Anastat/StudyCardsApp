package com.project.studycards.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable{
    private String question;
    private String answer;
    private int priority;
    private int count;

    public Card (String question, String answer, int priority, int count) {
        this.question = question;
        this.answer = answer;
        this.priority = priority;
        this.count = count;
    }

    public Card (String question, String answer) {
        this(question, answer, 1, 0);
    }

    public Card () {
        //this constructor needs for reading from file (SuperCSV doesn't works without empty constructor)
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount () { count++;}

    public String toString () {
        return "Question: " + this.question + " Answer: " + this.answer;
    }
}
