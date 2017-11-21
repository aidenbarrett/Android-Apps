package com.hfad.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;

public class Location {
    SharedPreferences prefs;

    public Location(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    String getCity(){
        return prefs.getString("city", "Dublin, IE");
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}
