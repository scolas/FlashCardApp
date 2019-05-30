package com.scottcolas.android.flashcard.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "exams")
public class Exam {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int mId;

    @ColumnInfo(name = "examName")
    private String mExamName;


    @ColumnInfo(name = "date")
    private String mDate;

    public Exam(){}

    public Exam(String name){
        this.mExamName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getExamName() {
        return mExamName;
    }

    public void setExamName(String mExamName) {
        this.mExamName = mExamName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

}
