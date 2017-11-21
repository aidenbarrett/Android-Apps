package com.aidenbarrett.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mRestart = (Button) findViewById(R.id.restart);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ResultsActivity.this,QuizActivity.class);
                    startActivity(intent);
            }
        });

        mReturn = (Button) findViewById(R.id.return_home);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        int correct = intent.getIntExtra("right", 0);
        int incorrect = intent.getIntExtra("wrong", 0);
        long time = intent.getLongExtra("time_taken", 0);

        String correct_text =String.valueOf(correct);
        String incorrect_text =String.valueOf(incorrect);
        String stopTime =String.valueOf(time);

        mRight = (TextView) findViewById(R.id.correct_values);
        mRight.setText(" " + correct_text);

        mWrong = (TextView) findViewById(R.id.incorrect_values);
        mWrong.setText(" " + incorrect_text);

        mTime = (TextView) findViewById(R.id.time_taken);
        mTime.setText(" " + stopTime);


        String share_text = ("I took The Donegal Quiz™. Can you beat my score? \nCorrect Answers: " + correct_text + "\nIncorrect Answers: " + incorrect_text);
        final String finalShare_text = share_text;

        mShare = (Button) findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, finalShare_text);
                startActivity(intent);
            }
        });

    }

}
