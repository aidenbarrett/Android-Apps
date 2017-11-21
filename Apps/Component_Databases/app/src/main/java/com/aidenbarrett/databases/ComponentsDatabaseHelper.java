package com.aidenbarrett.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ComponentsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database

    ComponentsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE COMPONENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertComponent(db, "Capacitors", "A Capacitor is used to store electrical energy.", R.drawable.capacitor);
            insertComponent(db, "Diodes", "A Diode is used to conduct current in one direction but not the other.", R.drawable.diode);
            insertComponent(db, "Transistors", "A Transistor is used to switch electronic signals and electrical power.", R.drawable.transistor);
            insertComponent(db, "LEDs", "An LED emits light when powered.", R.drawable.led);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE COMPONENTS ADD COLUMN FAV NUMERIC;");
        }
    }

    private static void insertComponent(SQLiteDatabase db, String name,
                                        String description, int resourceId) {
        ContentValues componentValues = new ContentValues();
        componentValues.put("NAME", name);
        componentValues.put("DESCRIPTION", description);
        componentValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("COMPONENTS", null, componentValues);
    }
}
