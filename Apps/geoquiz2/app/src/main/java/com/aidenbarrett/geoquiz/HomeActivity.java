package com.aidenbarrett.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button mStart;
    private Button mRules;

    //  onCreate launches code on creation of activity
    //  setContentView focuses the activity on the reference layout - links to XML
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //  findViewByID - this declares a button mStart and links it to start button activity home in XML
        //  OnClickListener - upon clicking of referenced start button, it will call the onClick method (intents).
        mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

        //  findViewByID - this declares a button mRules and links it to rules button activity home in XML
        //  OnClickListener - upon clicking of referenced Rules button, it will call the onClick method (intents).
        mRules = (Button) findViewById(R.id.rules);
        mRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RulesActivity.class);
                startActivity(intent);
            }
        });
    }

}
