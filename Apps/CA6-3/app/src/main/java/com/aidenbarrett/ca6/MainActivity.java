package com.aidenbarrett.ca6;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends Activity implements SensorEventListener {


    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    // Well known SPP UUID
    public static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Button button;

    private BluetoothSocket btSocket = null;

    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private TextView X_Value, Y_Value, Z_Value;
    private long lastUpdate;
    float x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        X_Value = (TextView) findViewById(R.id.Xreadings);
        Y_Value = (TextView) findViewById(R.id.Yreadings);
        Z_Value = (TextView) findViewById(R.id.Zreadings);

        if (null != savedInstanceState) {
            x = savedInstanceState.getFloat("x",x);
            y = savedInstanceState.getFloat("y",y);
            z = savedInstanceState.getFloat("z",z);
            lastUpdate = savedInstanceState.getLong("lastUpdate",lastUpdate);

            // Set Values
            X_Value.setText(Float.toString(x));
            Y_Value.setText(Float.toString(y));
            Z_Value.setText(Float.toString(z));
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        if (btAdapter.isEnabled()) {

            // Set up a pointer to the remote node using it's address.
            // Make sure address format uses colons (:), not dashes (-) or app will crash
            BluetoothDevice device = btAdapter.getRemoteDevice("60:C5:47:89:95:05"); //MBP Bluetooth address.

            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                btSocket.connect();

                Log.d("Bluetooth-CA6-1", "...Connection established and data link opened...");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putFloat("x",x);
        outState.putFloat("y",y);
        outState.putFloat("z",z);
        outState.putLong("lastUpdate",lastUpdate);
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // get the Movement data
        x = values[0];
        y = values[1];
        z = values[2];

        // get event change time in milisecond
        long actualTime = (new Date()).getTime()
                + (event.timestamp - System.nanoTime()) / 1000000L;

        //Check that time difference is greater then 500 milisecond
        if (actualTime - lastUpdate < 500) {
            // If less then then donot update textview
            return;
        }

        // Reset last update time with actual time
        lastUpdate = actualTime;

        // Set Values
        X_Value.setText(Float.toString(x));
        Y_Value.setText(Float.toString(y));
        Z_Value.setText(Float.toString(z));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        getAccelerometer(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start Listening
        // Listener for the orientation and accelerometer sensors
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister Sensor listner when app is pause
        sensorManager.unregisterListener(this);
    }

    public void buttonClick(View view) {
        try {
            btSocket.getOutputStream().write('a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
