package com.scottcolas.android.flashcard.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.scottcolas.android.flashcard.Model.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards")
    LiveData<List<Card>> getAllCards();

    @Insert
    void insert(Card cards);

    @Delete
    void delete(Card cards);

    @Query("DELETE FROM cards")
    void deleteAll();

    @Update
    public void updateCard(Card... cards);

    //@Query("UPDATE cards SET question=:qInput, answer=:aInput WHERE ID = :cID")
   // void updatec(List<String> c);

    @Query("UPDATE cards SET question=:question, answer=:answers WHERE ID = :id")
    void updatec(String question,String answers, int id);


}
