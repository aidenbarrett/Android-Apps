package com.aidenbarrett.component;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrderActivity extends Activity {

    private EditText addComponentEditText;
    private EditText addComponentDetailEditText;
    private Button addComponentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addComponentEditText = (EditText) findViewById(R.id.add_component_edit_text);
        addComponentDetailEditText = (EditText) findViewById(R.id.add_component_detail_edit_text);
        addComponentButton = (Button) findViewById(R.id.add_component_button);

        addComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String componentText = addComponentEditText.getText().toString();
                String componentDetailText = addComponentDetailEditText.getText().toString();

                if (componentText.isEmpty()) {
                    Toast.makeText(OrderActivity.this, R.string.toast_no_component, Toast.LENGTH_SHORT).show();
                }else if (componentDetailText.isEmpty()) {
                    Toast.makeText(OrderActivity.this, R.string.toast_no_component_detail, Toast.LENGTH_SHORT).show();
                }else {
                    Component.addPart(componentText, componentDetailText);
                    Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
