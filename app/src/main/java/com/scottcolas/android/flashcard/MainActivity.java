package com.scottcolas.android.flashcard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scottcolas.android.flashcard.Model.Exam;
import com.scottcolas.android.flashcard.View.ExamViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {
    private ExamViewModel mExamViewModel;
    public static final int NEW_EXAM_ACTIVITY_REQUEST_CODE = 1;
    RecyclerView recyclerView;
    ExamListAdapter adapter;
    public static final String RECIPE_LIST = "recipe_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_recipelist);
        ButterKnife.bind(this);

        mExamViewModel = ViewModelProviders.of(this).get(ExamViewModel.class);

        //recyclerView = findViewById(R.id.recyclerview);
        recyclerView = findViewById(R.id.recipeRecyclerView);
        //adapter = new ExamListAdapter(this, this);
        adapter = new ExamListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExamViewModel.getAllExams().observe(this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable List<Exam> exams) {
                adapter.setExams(exams);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_EXAM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Exam word = new Exam(data.getStringExtra(NewExamActivity.EXTRA_REPLY));
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            word.setDate(date.toString());
            mExamViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.add_exam_btn)
    public void addBtn(View view){
        Intent intent = new Intent(MainActivity.this, NewExamActivity.class);
        startActivityForResult(intent, NEW_EXAM_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void onListItemDelete(int clickedItemIndex){
        LiveData<List<Exam>> exams =  mExamViewModel.getAllExams();
        List<Exam> exams1 = exams.getValue();
        Exam clickedExam = exams1.get(clickedItemIndex);
        Toast.makeText(
                getApplicationContext(),
                "onListItemDelete exam name "+clickedExam.getExamName(),
                Toast.LENGTH_LONG).show();

        mExamViewModel.delete(clickedExam);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        LiveData<List<Exam>> exams =  mExamViewModel.getAllExams();
        List<Exam> exams1 = exams.getValue();
        Exam clickedExam = exams1.get(clickedItemIndex);
        Log.i("TAG",""+clickedItemIndex+" or"+clickedExam.getExamName()+"");
        Intent intent = new Intent(MainActivity.this,ExamDetail.class);
        intent.putExtra(Intent.EXTRA_TEXT,clickedExam.getExamName());
        intent.putExtra("ExamID",clickedExam.getId());
        startActivity(intent);
    }
}
