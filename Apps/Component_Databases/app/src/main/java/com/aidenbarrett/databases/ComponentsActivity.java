package com.aidenbarrett.databases;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.CheckBox;
import android.content.ContentValues;
import android.os.AsyncTask;

public class ComponentsActivity extends Activity {

    public static final String EXTRA_COMPONENT_NUM = "componentNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int componentNo = (Integer)getIntent().getExtras().get(EXTRA_COMPONENT_NUM);

        //Create a cursor
        try {
            SQLiteOpenHelper componentsDatabaseHelper = new ComponentsDatabaseHelper(this);
            SQLiteDatabase db = componentsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("COMPONENTS",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAV"},
                    "_id = ?",
                    new String[] {Integer.toString(componentNo)},
                    null, null,null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {

                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFav = (cursor.getInt(3) == 1);

                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                CheckBox used = (CheckBox)findViewById(R.id.favorite);
                used.setChecked(isFav);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Update the database when the checkbox is clicked
    public void onUsedClicked(View view){
        int componentNo = (Integer)getIntent().getExtras().get("componentNumber");
        new UpdateComponentTask().execute(componentNo);
    }

    private class UpdateComponentTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues componentValues;
        protected void onPreExecute() {
            CheckBox fav = (CheckBox)findViewById(R.id.favorite);
            componentValues = new ContentValues(); componentValues.put("FAV", fav.isChecked());
        }
        protected Boolean doInBackground(Integer... components) {
            int componentNo = components[0];
            SQLiteOpenHelper componentsDatabaseHelper = new ComponentsDatabaseHelper(ComponentsActivity.this);
            try {
                SQLiteDatabase db = componentsDatabaseHelper.getWritableDatabase();
                db.update("COMPONENTS", componentValues,
                        "_id = ?", new String[] {Integer.toString(componentNo)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(ComponentsActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


}
