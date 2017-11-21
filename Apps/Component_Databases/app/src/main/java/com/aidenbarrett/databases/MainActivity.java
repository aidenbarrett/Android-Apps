package com.aidenbarrett.databases;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor Cursor;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id) {
                        if (position == 0) {
                            Intent intent = new Intent(MainActivity.this,
                                    CategoriesActivity.class);
                            startActivity(intent);
                        }
                    }
                };
        //Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        //Populate the list_favorites ListView from a cursor
        ListView listUsed = (ListView)findViewById(R.id.list_used);
        try {
            SQLiteOpenHelper componentsDatabaseHelper = new ComponentsDatabaseHelper(this);
            db = componentsDatabaseHelper.getReadableDatabase();
            Cursor = db.query("COMPONENTS",
                    new String[] { "_id", "NAME"}, "FAV = 1",
                    null, null, null, null);
            CursorAdapter usedAdapter =
                    new SimpleCursorAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            Cursor,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listUsed.setAdapter(usedAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listUsed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ComponentsActivity.class);
                intent.putExtra(ComponentsActivity.EXTRA_COMPONENT_NUM, (int)id);
                startActivity(intent);
            }
        });
    }

    //Close the cursor and database in the onDestroy() method
    @Override
    public void onDestroy(){
        super.onDestroy();
        Cursor.close();
        db.close();
    }

    public void onRestart() {
        super.onRestart();
        try {
            ComponentsDatabaseHelper componentsDatabaseHelper = new ComponentsDatabaseHelper(this);
            db = componentsDatabaseHelper.getReadableDatabase();
            Cursor newCursor = db.query("COMPONENTS",
                    new String[] { "_id", "NAME"},
                    "FAV = 1",
                    null, null, null, null);
            ListView listUsed = (ListView)findViewById(R.id.list_used);
            CursorAdapter adapter = (CursorAdapter) listUsed.getAdapter();
            adapter.changeCursor(newCursor);
            Cursor = newCursor;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text for sharing info");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }
}
