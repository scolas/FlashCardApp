package com.scottcolas.android.flashcard.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.scottcolas.android.flashcard.Model.Card;

import java.util.List;

public class CardRepository {

    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    public CardRepository(Application application) {
        AppDatabase db = AppDatabase.getsInstance(application);
        mCardDao = db.cardsRepoDao();
        mAllCards = mCardDao.getAllCards();
    }

    public LiveData<List<Card>> getmAllExams(){
        return mAllCards;
    }

    public void insert (Card card) {

        new CardRepository.insertAsyncTask(mCardDao).execute(card);
    }
    public void delete (Card card) {

        new CardRepository.DeleteAsyncTask(mCardDao).execute(card);
    }

    public void updateCard (Card card) {

        new CardRepository.insertAsyncTask(mCardDao).execute(card);
    }
    public void updatec(Card card) {

      //  new CardRepository.UpdateAsyncTask(mCardDao).execute(card);
    }
    public void updatec(Card card, int cID,String qInputard,String aInput) {

        new CardRepository.UpdateAsyncTask(mCardDao, qInputard, aInput, cID).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao mAsyncTaskDao;

        insertAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao mAsyncTaskDao;
        private String question;
        private String  answer;
        private int idd;

        UpdateAsyncTask(CardDao dao, String q, String a, int id) {
            mAsyncTaskDao = dao;
            question = q;
            answer = a;
            idd = id;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            //mAsyncTaskDao.update(params[0]);
            //mAsyncTaskDao.updatec(params[0]);
            mAsyncTaskDao.updatec(this.question, this.answer, this.idd);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao mAsyncTaskDao;

        DeleteAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
