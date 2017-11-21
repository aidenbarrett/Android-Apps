package com.aidenbarrett.component;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OrderActivity extends Activity {

    EditText Edit_Component_Name;
    EditText Edit_Component_Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // Attempted to use the template for Messaging App to get this to work
    public void onAddComponent(View view) {
        EditText messageView = (EditText)findViewById(R.id.New_component_name);
        //String messageText = messageView.getText().toString();

        EditText messageView_desc = (EditText)findViewById(R.id.New_component_description);
        //String messageText = messageView.getText().toString();

        //Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_TEXT, messageText);
        String chooserTitle = getString(R.string.Add_component);

/*        String name = Edit_Component_Name.getText().toString();
        // Create new Component object and add into existing Arraylist
        Component.components.add(new Component(name, ""));
        Toast.makeText(OrderActivity.this, "Component is added", Toast.LENGTH_LONG).show();
        // Start new MainActivity to refresh Component list in navigation drawer
        Intent i = new Intent(OrderActivity.this,MainActivity.class);
        */

        //Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
       // startActivity(chosenIntent);
    }
}
