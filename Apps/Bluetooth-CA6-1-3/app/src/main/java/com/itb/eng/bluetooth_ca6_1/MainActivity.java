package com.itb.eng.bluetooth_ca6_1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends Activity {


    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    // Well known SPP UUID
    public static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Button button;

    private BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);



        if(btAdapter.isEnabled()) {


            // Set up a pointer to the remote node using it's address.
            BluetoothDevice device = btAdapter.getRemoteDevice("60:C5:47:89:95:05"); // Change this address to your own PC bluetooth address

            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

                btSocket.connect();

                Log.d("Bluetooth-CA6-1", "...Connection established and data link opened...");

            } catch (IOException e) {
            }
        }
    }

    public void buttonClick(View view)
    {
        try {
            btSocket.getOutputStream().write('a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
