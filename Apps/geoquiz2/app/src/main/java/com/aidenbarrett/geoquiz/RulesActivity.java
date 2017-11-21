package com.aidenbarrett.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {

    private Button mReturn;

    //  onCreate launches code on creation of activity
    //  setContentView focuses the activity on the reference layout - links to XML
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        //  findViewByID - this declares a button mReturn and links it to return button activity home in XML
        //  OnClickListener - upon clicking of referenced return button, it will call the onClick method (intents).
        mReturn = (Button) findViewById(R.id.return_home);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

