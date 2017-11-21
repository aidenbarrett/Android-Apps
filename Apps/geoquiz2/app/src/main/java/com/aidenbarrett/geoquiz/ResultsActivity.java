package com.aidenbarrett.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private Button mRestart;
    private TextView mRight;
    private TextView mWrong;
    private Button mReturn;
    private Button mShare;
    private TextView mTime;

    //  onCreate launches code on creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this tells this activity to focus on the activity results layout XML
        setContentView(R.layout.activity_results);

        //  findViewByID - this declares a button mRestart and links it to restart button activity home in XML
        //  OnClickListener - upon clicking of referenced start button, it will call the onClick method (intents).
        mRestart = (Button) findViewById(R.id.restart);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates new intent of type Intent, that signals that 'this' activity launches the quiz activity
                Intent intent = new Intent(ResultsActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

        mReturn = (Button) findViewById(R.id.return_home1);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates explicit intent, this is a specific intent.
                Intent intent = new Intent(ResultsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        // this calls the getIntent method, which is used to receive the extra values that were sent with previous intents.
        Intent intent = getIntent();
        // get these values and give them the variable names as assigned.
        int correct = intent.getIntExtra("right", 0);
        int incorrect = intent.getIntExtra("wrong", 0);
        int time = intent.getIntExtra("time", 0);

        // conversion of int's to strings
        String correct_text =String.valueOf(correct);
        String incorrect_text =String.valueOf(incorrect);
        String stopTime =String.valueOf(time);

        // focus on referenced text views and inject text into it.
        mRight = (TextView) findViewById(R.id.correct_values);
        mRight.setText(" " + correct_text);

        mWrong = (TextView) findViewById(R.id.incorrect_values);
        mWrong.setText(" " + incorrect_text);

        mTime = (TextView) findViewById(R.id.time_taken);
        mTime.setText(" " + stopTime);

        // creates a combined string that is use to send in an intent.
        String share_text = ("I took The Donegal Quiz™. Can you beat my score? \nCorrect Answers: " + correct_text + "\nIncorrect Answers: " + incorrect_text + "\nTime in Seconds: " + stopTime);
        final String finalShare_text = share_text;

        mShare = (Button) findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates new intent of type Intent, that creates an Implicit intent.
                // This just defines a type of action to take and lets android determine
                // which application can handle this action.
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, finalShare_text);
                startActivity(intent);
            }
        });

    }

}
