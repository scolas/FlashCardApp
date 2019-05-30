package com.scottcolas.android.flashcard.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.scottcolas.android.flashcard.Model.Exam;

import java.util.List;

public class ExamRepository {
    private ExamDao mExamDao;
    private LiveData<List<Exam>> mAllExams;

    public ExamRepository(Application application) {
        AppDatabase db = AppDatabase.getsInstance(application);
        mExamDao = db.examRepoDao();
        mAllExams = mExamDao.getAllExams();
    }

    public LiveData<List<Exam>> getmAllExams(){
        return mAllExams;
    }

    public void insert (Exam exam) {

        new insertAsyncTask(mExamDao).execute(exam);
    }

    public void delete (Exam exam){
        new deleteAsyncTask(mExamDao).execute(exam);
    }

    private static class insertAsyncTask extends AsyncTask<Exam, Void, Void> {

        private ExamDao mAsyncTaskDao;

        insertAsyncTask(ExamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Exam... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Exam, Void, Void> {

        private ExamDao mAsyncTaskDao;

        deleteAsyncTask(ExamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Exam... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
