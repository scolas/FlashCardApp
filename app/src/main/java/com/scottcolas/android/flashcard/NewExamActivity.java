package com.scottcolas.android.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewExamActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.examListsql.REPLY";

    private EditText mEditExamView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exam);
        mEditExamView = findViewById(R.id.edit_word);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditExamView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String exam = mEditExamView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, exam);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
