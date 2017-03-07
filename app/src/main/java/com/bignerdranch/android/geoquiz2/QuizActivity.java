package com.bignerdranch.android.geoquiz2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private final static String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX2 = "index2";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Question[] mQuestionBank = {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex=0;
    private boolean mIsCheater =false;

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
        outState.putBoolean(KEY_INDEX2,mIsCheater);
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
            mIsCheater=savedInstanceState.getBoolean(KEY_INDEX2,false);
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        updateQuestion();


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater=false;
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
                Intent i=CheatActivity.newIntent(QuizActivity.this,mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_CHEAT){
            if (resultCode==RESULT_OK){
                mIsCheater =CheatActivity.wasAnswerShown(data);
            }
            else if (resultCode==RESULT_CANCELED){
                mIsCheater =false;
            }
        }
    }


    /* Branched for challenge */

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){

        if (mIsCheater){
            Toast.makeText(QuizActivity.this,R.string.judgment_toast,Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()){
            Toast.makeText(QuizActivity.this,R.string.toast_correct,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(QuizActivity.this,R.string.toast_incorrect,Toast.LENGTH_SHORT).show();
        }
    }

}
