package com.scottcolas.android.flashcard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scottcolas.android.flashcard.Model.Card;
import com.scottcolas.android.flashcard.View.CardEditViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamDetail extends AppCompatActivity {

    private static final int NEW_EXAM_Detail_ACTIVITY_REQUEST_CODE = 3;
    private String Examname;
    private CardEditViewModel mCardEditViewModel;
    List<Card> cardList = new ArrayList<Card>();
    @BindView(R.id.cardTxt)
    TextView viewQA;
    @BindView(R.id.cardCountTxt)
    TextView cardCount;
    int listLocation = 0;
    public static int intentExamId;
    String intentExamName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if ((savedInstanceState != null)
                     && (savedInstanceState.getSerializable("eID") != null)) {
                     int idd = (int) savedInstanceState.getSerializable("eID");
                }

        if (extras != null) {
            if(extras.containsKey(Intent.EXTRA_TEXT)){
                intentExamName = extras.getString(Intent.EXTRA_TEXT);
            }

            if(extras.containsKey("ExamID")){
                intentExamId = extras.getInt("ExamID");
            }
        }else{
            return;
        }

        mCardEditViewModel = ViewModelProviders.of(this).get(CardEditViewModel.class);

        mCardEditViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                if(cardList != null){
                    cardList.clear();
                }
               //update set text
                for( Card c : cards ){
                    String cname = c.getExamname();
                    int cID = c.getExamID();
                  //  if(cname.equals(intentExamName)){
  //                      cardList.add(c);
                    //}
                    if(cID == intentExamId){
                        cardList.add(c);
                    }
                }

                if(cardList != null && (cardList.size()>0)){
                    viewQA.setText(cardList.get(listLocation).getQuestion());
                    cardCount.setText("1 Of "+cardList.size());
                }


            }


        });

    }

    @OnClick(R.id.cardTxt)
    public void question_answer(){
        if(cardList.isEmpty()){
            Intent intent = new Intent(ExamDetail.this, CardEditActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT,Examname);
            intent.putExtra("ID",intentExamId);
            startActivityForResult(intent, NEW_EXAM_Detail_ACTIVITY_REQUEST_CODE);
        }

        if(!cardList.isEmpty()) {
            String currentvalue = viewQA.getText().toString();
            String answer = cardList.get(listLocation).getAnswer();
            String question = cardList.get(listLocation).getQuestion();


            if (question == currentvalue) {
                viewQA.setText(answer);
            } else if (answer == currentvalue) {
                viewQA.setText(question);
            }
        }
    }


    @OnClick(R.id.nextButton)
    public void nextBtn(View view){
        if(cardList != null && cardList.size()>0) {
            if (listLocation == (cardList.size()-1)) {
                listLocation = 0;
                viewQA.setText(cardList.get(listLocation).getQuestion());
            } else {
                listLocation++;
                viewQA.setText(cardList.get(listLocation).getQuestion());
            }
            setlocationText();
        }

    }

    @OnClick(R.id.preButton)
    public void prevBtn(View view){
        if(cardList != null && cardList.size()>0) {
            if (listLocation == 0) {
                listLocation = cardList.size() - 1;
                viewQA.setText(cardList.get(cardList.size() - 1).getQuestion());

            } else {
                listLocation--;
                viewQA.setText(cardList.get(listLocation).getQuestion());
            }
            setlocationText();
        }

    }

    public void setlocationText(){
        cardCount.setText((listLocation+1)+" Of "+cardList.size());
    }

    @OnClick(R.id.editQuestion)
    public void editCards(View view){
        Intent intent = new Intent(ExamDetail.this, CardEditActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,Examname);
        intent.putExtra("ID",intentExamId);
        startActivityForResult(intent, NEW_EXAM_Detail_ACTIVITY_REQUEST_CODE);
    }


}
