package com.hfad.weatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.Locale;


public class WeatherActivity extends Fragment {

    TextView cityLocation;
    TextView CurrentConditions;
    TextView Temperature;

    Handler handler;

    public WeatherActivity(){
        handler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateWeather(new Location(getActivity()).getCity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_weather, container, false);
        cityLocation = (TextView)rootView.findViewById(R.id.city_Location);
        CurrentConditions = (TextView)rootView.findViewById(R.id.Current_Conditions);
        Temperature = (TextView)rootView.findViewById(R.id.current_temperature);

        return rootView;
    }
    private void updateWeather(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = GetWeather.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }
    private void renderWeather(JSONObject json){
        try {
            cityLocation.setText(json.getString("name").toUpperCase(Locale.UK) + ", " + json.getJSONObject("sys").getString("country"));

            JSONObject weather = json.getJSONArray("weather").getJSONObject(0);

            JSONObject main = json.getJSONObject("main");
            CurrentConditions.setText(weather.getString("main").toUpperCase(Locale.UK) +
                    "\n" + "Humidity =  " + main.getString("humidity") + "%" +
                    "\n" + "Pressure =  " + main.getString("pressure") + " HPA");

            Temperature.setText(String.format("Current temp is " + "%.0f", main.getDouble("temp") - 273.15) + " Degrees Celcius");

        }catch(Exception e){
            Log.e("OpenWeather", "Error with JSON data");
        }
    }

    public void changeCity(String city){
        updateWeather(city);
    }
}