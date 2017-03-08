package com.bignerdranch.android.geoquiz2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import static android.os.Build.*;

public class CheatActivity extends AppCompatActivity {

    private final static String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz2.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz2.answer_shown";
    private static final String KEY_INDEX = "index";


    private boolean mAnswerIsTrue;
    private boolean mAnswerWasShown=false;
    private Button mShowAnswer;
    private Button mShowAPI;
    private TextView mAnswerTextView;
    private TextView mAPITextView;







    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
       return (result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false));
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState!=null){
            mAnswerWasShown=savedInstanceState.getBoolean(KEY_INDEX);
            setAnswerShownResult(mAnswerWasShown);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAPITextView = (TextView) findViewById(R.id.api_text_view);

        mShowAPI = (Button) findViewById(R.id.show_api_button);
        mShowAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int version = VERSION.SDK_INT;
                mAPITextView.setText("API Level "+Integer.toString(version));
            }
        });

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mAnswerTextView.setText(R.string.false_button);
                }

                mAnswerWasShown=true;
                setAnswerShownResult(mAnswerWasShown);

                if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }


            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"============ CheatActivity onSaveInstanceState ===================");
        outState.putBoolean(KEY_INDEX,mAnswerWasShown);
        super.onSaveInstanceState(outState);
    }




    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);

    }


}
