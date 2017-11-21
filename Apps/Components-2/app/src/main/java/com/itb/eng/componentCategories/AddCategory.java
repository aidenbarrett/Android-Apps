package com.itb.eng.componentCategories;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itb.eng.components.R;

public class AddCategory extends Activity {

    private EditText addCategoryEditText;
    private EditText addCategoryDetailEditText;
    private Button addCategoryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addCategoryEditText = (EditText) findViewById(R.id.add_category_edit_text);
        addCategoryDetailEditText = (EditText) findViewById(R.id.add_category_detail_edit_text);
        addCategoryButton = (Button) findViewById(R.id.add_category_button);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryText = addCategoryEditText.getText().toString();
                String categoryDetailText = addCategoryDetailEditText.getText().toString();

                if (categoryText.isEmpty()) {
                    Toast.makeText(AddCategory.this, R.string.toast_no_category, Toast.LENGTH_SHORT).show();
                }else if (categoryDetailText.isEmpty()) {
                    Toast.makeText(AddCategory.this, R.string.toast_no_category_detail, Toast.LENGTH_SHORT).show();
                }else {
                    ComponentCategory.addPart(categoryText, categoryDetailText);
                    Intent intent = new Intent(AddCategory.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
