package com.scottcolas.android.flashcard.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "answer")
    private String answer;
    @ColumnInfo(name = "examname")
    private String examname;

    @ColumnInfo(name = "examID")
    private int mExamID;

    @ColumnInfo(name = "cardname")
    private String cardname;



    public Card(){

    }

    public Card(String q,String a,String en,int i){
        this.setQuestion(q);
        this.setAnswer(a);
        this.setExamname(en);
        this.setExamID(i);
    }
    public Card(int cID, String q,String a){
        this.setId(cID);
        this.setQuestion(q);
        this.setAnswer(a);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }


    public int getExamID() {
        return mExamID;
    }

    public void setExamID(int mExamID) {
        this.mExamID = mExamID;
    }
}
