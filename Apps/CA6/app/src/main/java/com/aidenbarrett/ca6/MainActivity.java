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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        X_Value = (TextView) findViewById(R.id.Xreadings);
        Y_Value = (TextView) findViewById(R.id.Yreadings);
        Z_Value = (TextView) findViewById(R.id.Zreadings);

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

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = event.timestamp;

        if (accelerationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }

            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT)
                    .show();

            X_Value.setText(Float.toString(x));
            Y_Value.setText(Float.toString(y));
            Z_Value.setText(Float.toString(z));
        }
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
        // Listener for the orientation and accelerometer sensors
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
