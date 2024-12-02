package com.NTKhang.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.internal.EdgeToEdgeUtils;


public class CheatActivity extends AppCompatActivity {

    private boolean mAnswer;
    private TextView mTextViewAnswer;
    private Button mButtonShowAnswer;

    private static final String EXTRA_ANSWER = "nt-khang.quiz.anwser";
    private static final String EXTRA_ANSWER_RESULT = "nt-khang.quiz.anwser_result";



    public static Intent newIntent(Context packageContext, boolean answer) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER, answer);
        return i;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, false);
        mTextViewAnswer = findViewById(R.id.textview_answer);

        mButtonShowAnswer = findViewById(R.id.button_show);
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer)
                    mTextViewAnswer.setText("True");
                else
                    mTextViewAnswer.setText("False");
                setAnswerResult(true);
            }
        });

    }

    private void setAnswerResult(boolean isAnswerShow) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_RESULT, isAnswerShow);
        setResult(RESULT_OK, data);
    }

    public static boolean getAnswerShow(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_RESULT, false);
    }



}