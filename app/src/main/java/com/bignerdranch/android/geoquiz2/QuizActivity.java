package com.bignerdranch.android.geoquiz2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private final static String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Question[] mQuestionBank = {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex=0;

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"============ onStop =========== ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"============ onDestroy =========== ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"============ onPause =========== ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"============ onResume =========== ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"============ onStart =========== ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_INDEX,mCurrentIndex);
        super.onSaveInstanceState(outState);
        Log.d(TAG,"============ onSaveInstanceState =========== ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG,"============ onCreate =========== ");

        if (savedInstanceState!=null){
            Log.d(TAG,"============  savedInstanceState!=null =========== ");
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        updateQuestion();


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });



        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(QuizActivity.this,CheatActivity.class);
               startActivity(i);
            }
        });

    }





    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){

        if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()){
            Toast.makeText(QuizActivity.this,R.string.toast_correct,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(QuizActivity.this,R.string.toast_incorrect,Toast.LENGTH_SHORT).show();
        }
    }

}
