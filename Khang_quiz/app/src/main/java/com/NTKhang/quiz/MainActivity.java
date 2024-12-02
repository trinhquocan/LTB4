package com.NTKhang.quiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton imageButtonNext;
    private ImageButton imageButtonPrevious;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private Button mButtonCheat;
    private TextView mTextViewQuestion;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.Question1, true),
            new Question(R.string.Question2, false),
            new Question(R.string.Question3, false),
            new Question(R.string.Question4, true),
            new Question(R.string.Question5, false)
    };
    private int mCurrentIndex = 0;
    private boolean misCheater;

    private void UpdateQuestion() {
        mTextViewQuestion.setText(mQuestionBank[mCurrentIndex].getmTextResId());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();

        int messageResID = 0;

        if (misCheater)
            messageResID = R.string.toast_cheating;
        else {
            if (userPressedTrue == answerIsTrue)
                messageResID = R.string.correct_toast;
            else
                messageResID = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> startActivity4Result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o != null && o.getResultCode() == RESULT_OK)
                        if (o.getData() != null)
                            misCheater = CheatActivity.getAnswerShow(o.getData());
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewQuestion = findViewById(R.id.textview_question);

        mButtonTrue = findViewById(R.id.button_True);
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mQuestionBank[mCurrentIndex].ismAnswerTrue())
//                    Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mButtonFalse = findViewById(R.id.button_False);
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!mQuestionBank[mCurrentIndex].ismAnswerTrue())
//                    Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mButtonNext = findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                UpdateQuestion();
            }
        });

        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex <= 0)
                    mCurrentIndex = mQuestionBank.length - 1;
                else
                    mCurrentIndex = (mCurrentIndex - 1);
                UpdateQuestion();
            }
        });


        mButtonCheat = findViewById(R.id.button_cheat);
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainActivity.this, CheatActivity.class);
                boolean answer = mQuestionBank[mCurrentIndex].ismAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, answer);
                //startActivity(i);
                startActivity4Result.launch(i);
            }
        });


        UpdateQuestion();
    }

}