package com.ossf.www.bletagregister.Xbee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.ossf.www.bletagregister.R;

public class XbeeConnectActivity extends AppCompatActivity {

    private boolean connecting = false;
    private XBeeDevice myXBeeDevice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xbee_connect);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Avoid accesses while connecting.
        if (connecting)
            return;

        // Instantiate the XBeeDevice object.
        if (myXBeeDevice == null)
            myXBeeDevice = new XBeeDevice(this, 9600);

        // Create the connection thread.
        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                connecting = true;
                try {
                    // Check connection status.
                    if (myXBeeDevice.isOpen())
                        myXBeeDevice.close();
                    // Open device connection
                    myXBeeDevice.open();
                    showToastMessage("Device open: " + myXBeeDevice.toString());
                } catch (XBeeException e) {
                    showToastMessage("Could not open device: " + e.getMessage());
                }
                connecting = false;
            }
        });
        connectThread.start();
    }

    /**
     * Displays the given message.
     *
     * @param message The message to show.
     */
    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(XbeeConnectActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


}