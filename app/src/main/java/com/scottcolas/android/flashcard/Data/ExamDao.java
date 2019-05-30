package com.scottcolas.android.flashcard.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.scottcolas.android.flashcard.Model.Exam;

import java.util.List;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exams")
    LiveData<List<Exam>> getAllExams();

    @Insert
    void insert(Exam exam);

    @Delete
    void delete(Exam exam);

    @Query("DELETE FROM exams")
    void deleteAll();

}
