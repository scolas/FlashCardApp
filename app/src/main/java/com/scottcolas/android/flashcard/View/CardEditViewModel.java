package com.scottcolas.android.flashcard.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.scottcolas.android.flashcard.Data.CardRepository;
import com.scottcolas.android.flashcard.Data.ExamRepository;
import com.scottcolas.android.flashcard.Model.Card;
import com.scottcolas.android.flashcard.Model.Exam;

import java.util.List;

public class CardEditViewModel  extends AndroidViewModel {
    private CardRepository mRepository;
    private LiveData<List<Card>> mAllCards;

    public CardEditViewModel (Application application) {
        super(application);
        mRepository = new CardRepository(application);
        mAllCards = mRepository.getmAllExams();
    }

    public LiveData<List<Card>> getAllCards(){ return mAllCards;}
    public void insert(Card card){mRepository.insert(card);}
    public void delete(Card card){mRepository.delete(card);}

    public void updatec(Card card){
        mRepository.updatec(card,card.getId(),card.getQuestion(),card.getAnswer());
    }


}
