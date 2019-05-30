package com.scottcolas.android.flashcard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scottcolas.android.flashcard.Model.Card;
import com.scottcolas.android.flashcard.Model.Exam;
import com.scottcolas.android.flashcard.View.CardEditViewModel;
import com.scottcolas.android.flashcard.View.ExamViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputCardActivity extends AppCompatActivity implements ListItemClickListener{
    private CardEditViewModel mCardEditViewModel;
    cardsEditAdapter adapter;
    List<Card> cardsList;
    @BindView(R.id.input_edit_question)
    TextView inputEditQuestion;
    @BindView(R.id.input_edit_answer)
    TextView inputEditAnswer;
    String Examname;
    int intentExamID = -1;
    public static int ExamID;
    String qCard;
    String aCard;
    int intentCardID = -1;
    int RESULT_EDIT_EXISTING_CARD = 0;
    int CardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_card);
        ButterKnife.bind(this);

        mCardEditViewModel = ViewModelProviders.of(this).get(CardEditViewModel.class);
        adapter = new cardsEditAdapter(this,this);

        mCardEditViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
             //   adapter.setCards(cards);
                cardsList = cards;
            }


        });



        Bundle extras = getIntent().getExtras();
        String intentExamName = null;
        String intentQuestion = null;
        String intentAnswer = null;



        if (extras != null) {
            if(extras.containsKey("examname")){
                intentExamName = extras.getString("examname");
                Examname = intentExamName;
            }

            if(extras.containsKey("ExamID")){
                intentExamID = extras.getInt("ExamID");
                ExamID = intentExamID;
            }
            if(extras.containsKey("quest")){
                intentQuestion = extras.getString("quest");
            }
            if(extras.containsKey("answ")){
                intentAnswer = extras.getString("answ");
            }
            if(extras.containsKey("cardID")){
                intentCardID = extras.getInt("cardID");
                RESULT_EDIT_EXISTING_CARD  = 1;
            }
        }else{
            return;
        }

        //NEW CARD NOT EXISTING
        if(intentQuestion != null && intentAnswer != null && intentCardID != -1){
            qCard = intentQuestion;
            aCard = intentAnswer;
            fillOutInputs(qCard,aCard);
        }


    }


   @OnClick(R.id.save_card)
    public void savecard(){


               Intent replyIntent = new Intent();
               if (TextUtils.isEmpty(inputEditQuestion.getText()) && TextUtils.isEmpty(inputEditAnswer.getText())) {
                   setResult(RESULT_CANCELED, replyIntent);
               }
               if (RESULT_EDIT_EXISTING_CARD == 0){
                    // ADD NEW CARD
                   String question = inputEditQuestion.getText().toString();
                   String answer = inputEditAnswer.getText().toString();


                   replyIntent.putExtra("inputq", question);
                   replyIntent.putExtra("inputa", answer);
                   replyIntent.putExtra("exname",Examname);
                   replyIntent.putExtra("ID",ExamID);
                   setResult(RESULT_OK, replyIntent);
               }else if(RESULT_EDIT_EXISTING_CARD == 1){
                   String question = inputEditQuestion.getText().toString();
                   String answer = inputEditAnswer.getText().toString();


                   replyIntent.putExtra("inputq", question);
                   replyIntent.putExtra("inputa", answer);
                   replyIntent.putExtra("exname",Examname);
                   replyIntent.putExtra("ID",ExamID);
                   replyIntent.putExtra("cardID",intentCardID);

                   Card card = new Card(intentCardID,question,answer);

                    mCardEditViewModel.updatec(card);
                    //finish();
                   //setResult(RESULT_OK, replyIntent);
               }
              // adapter.notifyDataSetChanged();
              finish();



   }

   @OnClick(R.id.delete_card)
   public void deleteCard(){
        for(Card dcard: cardsList){
            if(dcard.getId() == intentCardID ){
                mCardEditViewModel.delete(dcard);
                adapter.notifyDataSetChanged();

            }
        }

        finish();

   }

    // fix for back button that was creating a new activity when trying ro go back
    public boolean onOptionsItemSelected(MenuItem item){
        CardEditActivity.ExamID = ExamID;
        finish();
        return true;
    }


   public void fillOutInputs(String q, String a){
        inputEditAnswer.setText(a);
        inputEditQuestion.setText(q);
   }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.i("TAG","why not insert card");
    }

    @Override
    public void onListItemDelete(int clickedItemIndex) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
