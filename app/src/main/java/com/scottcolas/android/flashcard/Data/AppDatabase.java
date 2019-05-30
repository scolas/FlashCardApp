package com.scottcolas.android.flashcard.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.scottcolas.android.flashcard.Model.Card;
import com.scottcolas.android.flashcard.Model.Exam;

@Database(entities = {Exam.class, Card.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "FlashCards";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }


    public abstract ExamDao examRepoDao();
    public abstract CardDao cardsRepoDao();
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };



    /*private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ExamDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.examRepoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();
            Exam exam = new Exam("Hello");
            mDao.insert(exam);
            exam = new Exam("World");
            mDao.insert(exam);
            return null;
        }
    }*/
}
