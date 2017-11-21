package com.aidenbarrett.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String QUESTION_INDEX = "q_index";
    private static final String CORRECT_INDEX = "cor_index";
    private static final String INCORRECT_INDEX = "incor_index";
    private static final String SKIPSLEFT_INDEX = "skips_index";
    private static final String STOP_TIME = "stop_time";



    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private TextView mSkipsLeft;
    private Chronometer chronometer;

    // R.String links to questions located in the string file.
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, false),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, true)
    };

    private int mAnswers = 0;
    public int mCorrect = 0;
    public int mIncorrect = 0;
    public int time = 0;
    private int skips = 0;
    private int x = 3;
    private int skipsleft = 0;

    public long stopTime = 0;


    private int mCurrentIndex = 0;

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCorrect++;
        } else {
            messageResId = R.string.incorrect_toast;
            mIncorrect++;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    // Method that the OnClick button calls.
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // Everytime the screen rotates, this gets called and current states are saved.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnswers = savedInstanceState.getInt(QUESTION_INDEX, 0);
            mCorrect = savedInstanceState.getInt(CORRECT_INDEX, 0);
            mIncorrect = savedInstanceState.getInt(INCORRECT_INDEX, 0);
            skips = savedInstanceState.getInt(SKIPSLEFT_INDEX, 0);
            stopTime = savedInstanceState.getLong(STOP_TIME, 0);
        }

        // setup for max 3 skips
        skipsleft = x - skips;
        // tells android to focus on specific text view box.
        mSkipsLeft = (TextView) findViewById(R.id.skips_num);
        // converts int value of skips left and converts to a string for text view.
        String skips_string = String.valueOf(skipsleft);
        // takes new string and inserts into text view.
        mSkipsLeft.setText(skips_string);

        // This chronometer variable is linked with the XML layout through the R file.
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        // tells android to focus on the question text view in the layout
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if(skips<3) {
                    // The below section could be put in a separate method and called when needed.
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                    skips++;
                    skipsleft = x - skips;
                    // Focus on the linked textview and do as told below.
                    mSkipsLeft = (TextView) findViewById(R.id.skips_num);
                    String skips_string = String.valueOf(skipsleft);
                    mSkipsLeft.setText(skips_string);
                }
            }
        });

        //  findViewByID - this declares a button mTrueButton and links it to true button activity home in XML
        //  OnClickListener - upon clicking of referenced true button, it will call the onClick method (intents).
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mAnswers++;

                if (mAnswers == 1){
                    // if answers equal 1, start clock
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }

                updateQuestion();

                if (mAnswers == (7)){
                    // takes base time and subtracts from elapsed time. (unix/epoch time)
                    stopTime = SystemClock.elapsedRealtime() - chronometer.getBase();

                    // convert time from milliseconds to seconds
                    time = (int) (stopTime/ 1000) % 60 ;
                    chronometer.stop();

                    // creates a new intent and passes extra values to the new activity.
                    Intent intent = new Intent(QuizActivity.this,ResultsActivity.class);
                    intent.putExtra("right", mCorrect);
                    intent.putExtra("time", time);
                    intent.putExtra("wrong", mIncorrect);
                    startActivity(intent);
                }

            }

        });

        //  findViewByID - this declares a button mFalseButton and links it to false button activity home in XML
        //  OnClickListener - upon clicking of referenced false button, it will call the onClick method (intents).
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mAnswers++;

                if (mAnswers == 1){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }

                updateQuestion();

                if (mAnswers == (7)){
                    // takes base time and subtracts from elapsed time. (unix/epoch time)
                    stopTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                    // convert time from milliseconds to seconds
                    time = (int) (stopTime/ 1000) % 60 ;
                    chronometer.stop();

                    // creates a new intent and passes extra values to the new activity.
                    Intent intent = new Intent(QuizActivity.this,ResultsActivity.class);
                    intent.putExtra("right", mCorrect);
                    intent.putExtra("wrong", mIncorrect);
                    intent.putExtra("time", time);
                    startActivity(intent);
                }

            }
        });


        //  findViewByID - this declares a button mNextButton and links it to next button activity home in XML
        //  OnClickListener - upon clicking of referenced next button, it will call the onClick method (intents).
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skips < 3) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                    skips++;
                    skipsleft = x - skips;
                    mSkipsLeft = (TextView) findViewById(R.id.skips_num);
                    String skips_string = String.valueOf(skipsleft);
                    mSkipsLeft.setText(skips_string);
                }
            }
        });

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        // Saves values when called.
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(QUESTION_INDEX, mAnswers);
        savedInstanceState.putInt(CORRECT_INDEX, mCorrect);
        savedInstanceState.putInt(INCORRECT_INDEX, mIncorrect);
        savedInstanceState.putInt(SKIPSLEFT_INDEX, skips);
        savedInstanceState.putLong(STOP_TIME, stopTime);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        // dont care because not using these functions for this app
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        stopTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        if (mAnswers > 0) {
            chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
            chronometer.start();
        }

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

}


