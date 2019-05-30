package com.scottcolas.android.flashcard.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.scottcolas.android.flashcard.Data.ExamRepository;
import com.scottcolas.android.flashcard.Model.Exam;

import java.util.List;

public class ExamViewModel extends AndroidViewModel {
    private ExamRepository mRepository;
    private LiveData<List<Exam>> mAllExams;

    public ExamViewModel (Application application) {
        super(application);
        mRepository = new ExamRepository(application);
        mAllExams = mRepository.getmAllExams();
    }

    public LiveData<List<Exam>> getAllExams(){ return mAllExams;}
    public void insert(Exam exam){mRepository.insert(exam);}
    public void delete(Exam exam){mRepository.delete(exam);}
}
