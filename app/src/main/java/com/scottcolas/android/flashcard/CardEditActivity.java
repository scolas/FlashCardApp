package com.scottcolas.android.flashcard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.scottcolas.android.flashcard.Model.Card;
import com.scottcolas.android.flashcard.Model.Exam;
import com.scottcolas.android.flashcard.View.CardEditViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardEditActivity extends AppCompatActivity implements ListItemClickListener{

    private CardEditViewModel mCardEditViewModel;
    public static final int NEW_Card_ACTIVITY_REQUEST_CODE = 2;
    RecyclerView recyclerView;
    cardsEditAdapter adapter;
    private String Examname;
    public static int ExamID;
    List<Card> subjectCards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_main);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            if(extras.containsKey(Intent.EXTRA_TEXT)){
                Examname = extras.getString(Intent.EXTRA_TEXT);
            }

            if(extras.containsKey("ID")){
                ExamID = extras.getInt("ID");
            }
        }else if(ExamID > -1){

        }else{
            return;
        }
        mCardEditViewModel = ViewModelProviders.of(this).get(CardEditViewModel.class);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new cardsEditAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCardEditViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                List<Card> subjectCardsCopy =  new ArrayList<Card>();


                if(!subjectCards.isEmpty()) {
                    subjectCards = new ArrayList<Card>();
                }

                for( Card c : cards ){
                    String cname = c.getExamname();

                    int cID = c.getExamID();

                    if(cID == ExamID){

                        subjectCards.add(c);
                        //HAD TO USE EXTRA ARRAY DEFINED IN ONCHANGED BCUS WE ARE USING ON ACTIVITY
                        // REUSLT THIS ACTIVITY IS NEVER DESTROYED SO THE DATA IS STORED IN subjectCards so we
                        //are appending to an array that already has values
                        subjectCardsCopy.add(c);

                    }
                }

                adapter.setCards(subjectCards);
            }
        });
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Card clickedCard = subjectCards.get(clickedItemIndex);
       // Card clickedCard = s.get(clickedItemIndex);
       // Log.i("TAG",""+clickedItemIndex+" or"+clickedCard.getCardname()+"");
        Intent intent = new Intent(CardEditActivity.this,InputCardActivity.class);
        intent.putExtra("quest",clickedCard.getQuestion());
        intent.putExtra("answ",clickedCard.getAnswer());
        intent.putExtra("cardID",clickedCard.getId());

        startActivity(intent);
       // Log.i("We here","its on sight");
    }


    @Override
    public void onListItemDelete(int clickedItemIndex){



        Card clickedCard = subjectCards.get(clickedItemIndex);

        mCardEditViewModel.delete(clickedCard);


    }



    //ADD NEW FLASH CARD
    @OnClick(R.id.add_card_btn)
    public void addCard(){
        Intent intent = new Intent(CardEditActivity.this, InputCardActivity.class);
        intent.putExtra("examname", Examname);
        intent.putExtra("ExamID", ExamID);
        startActivityForResult(intent, NEW_Card_ACTIVITY_REQUEST_CODE);
//        adapter.notifyDataSetChanged();

    }


    //RESULT AFTER ADDCARD CALLED resultCode comes from inputcardactivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RESULT_EDIT_EXISTING_CARD = 1;


       if (requestCode == NEW_Card_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Exam word = new Exam(data.getStringExtra(NewExamActivity.EXTRA_REPLY));
            String answer = data.getStringExtra("inputa");
            String question = data.getStringExtra("inputq");
            String examn = data.getStringExtra("exname");
            int examID = data.getIntExtra("ID",999);
            Card card = null;
            card = new Card(question,answer,examn,examID);
            mCardEditViewModel.insert(card);
               // adapter.notifyDataSetChanged();

        } else {

                   // R.string.empty_not_saved
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(CardEditActivity.this, InputCardActivity.class);
        intent.putExtra("examname", Examname);
        intent.putExtra("ExamID", ExamID);

    }

// fix for back button that was creating a new activity when trying ro go back
    public boolean onOptionsItemSelected(MenuItem item){

        ExamDetail.intentExamId = ExamID;
        //startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
@Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("eID", ExamID);
    }

}
