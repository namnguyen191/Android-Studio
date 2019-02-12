package com.example.namnam.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWEREDINDEX = "answeredIndex";
    private static final String KEY_CHEATEDINDEX = "cheatedIndex";
    private static final String KEY_GRADE = "grade";
    private static final String KEY_CHEATER = "cheater";
    private static final int REQUEST_CODE_CHEAT = 0;

    private boolean mIsCheater;
    private Button mRestartButton;
    private Button mCheatButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private List<Integer> mAnsweredIndex = new ArrayList<Integer>();
    private List<Integer> mCheatedIndex = new ArrayList<Integer>();
    private int mGrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate(Bundle) called");
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredIndex = savedInstanceState.getIntegerArrayList(KEY_ANSWEREDINDEX);
            mCheatedIndex = savedInstanceState.getIntegerArrayList(KEY_CHEATEDINDEX);
            mGrade = savedInstanceState.getInt(KEY_GRADE);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mRestartButton = (Button) findViewById(R.id.restart_button);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);
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
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                mIsCheater = false;
                updateQuestion();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        updateQuestion();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
        if(mIsCheater) mCheatedIndex.add(mCurrentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putIntegerArrayList(KEY_ANSWEREDINDEX, (ArrayList<Integer>) mAnsweredIndex);
        savedInstanceState.putIntegerArrayList(KEY_CHEATEDINDEX, (ArrayList<Integer>) mCheatedIndex);
        savedInstanceState.putInt(KEY_GRADE, mGrade);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    private void updateQuestion() {
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if(mAnsweredIndex.contains(mCurrentIndex)){
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        }
        if(mCheatedIndex.contains(mCurrentIndex)){
            mIsCheater = true;
        }
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
            mAnsweredIndex.add(mCurrentIndex);
            mCheatedIndex.add(mCurrentIndex);
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mGrade += 1;
                mAnsweredIndex.add(mCurrentIndex);
            } else {
                messageResId = R.string.incorrect_toast;
                mAnsweredIndex.add(mCurrentIndex);
            }
        }
        if(mAnsweredIndex.size() == mQuestionBank.length){
            Toast.makeText(this, "Your Score is: "+mGrade+"/"+mAnsweredIndex.size(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                    .show();
        }
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
    }
}
