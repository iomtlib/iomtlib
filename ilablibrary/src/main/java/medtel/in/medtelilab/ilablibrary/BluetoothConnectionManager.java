package medtel.in.medtelilab.ilablibrary;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.reflect.Method;

public class BluetoothConnectionManager implements BluetoothConnectionListener {
    private boolean bpconnectionstatus = false;
    private BluetoothA2dpServiceListener bluetoothA2dpServiceListener;
    private Context context;

    public BluetoothConnectionManager(Context context) {
        this.context = context;
        bluetoothA2dpServiceListener = new BluetoothA2dpServiceListener();
    }

    public void checkBluetoothConnectionState(String deviceAddress) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);

        // Register for broadcasts when a device is connected or disconnected
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        context.registerReceiver(bluetoothReceiver, filter);

        // Get the A2DP profile
        BluetoothA2dp bluetoothA2dp = getBluetoothA2dp();

        if (bluetoothA2dp != null) {
            // Get the connection state
            int connectionState = bluetoothA2dp.getConnectionState(device);

            switch (connectionState) {
                case BluetoothProfile.STATE_CONNECTED:
                    onBluetoothConnected();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    onBluetoothDisconnected();
                    break;
                default:
                    break;
            }
        } else {
            // A2DP profile not available
            onBluetoothDisconnected();
        }
    }

    public boolean isBluetoothConnected(Activity activity) {
        return bpconnectionstatus;
    }

    @Override
    public void onBluetoothConnected() {
        bpconnectionstatus = true;
        // You can add additional logic or callbacks here
    }

    @Override
    public void onBluetoothDisconnected() {
        bpconnectionstatus = false;
        // You can add additional logic or callbacks here
    }

    private BluetoothA2dp getBluetoothA2dp() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Method getProfileProxyMethod = BluetoothAdapter.class.getMethod("getProfileProxy", Context.class, BluetoothProfile.ServiceListener.class, int.class);
            if (getProfileProxyMethod.invoke(bluetoothAdapter, context, bluetoothA2dpServiceListener, BluetoothProfile.A2DP) != null) {
                return bluetoothA2dpServiceListener.getBluetoothA2dp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // BroadcastReceiver to listen for Bluetooth device connection events
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                onBluetoothConnected();
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                onBluetoothDisconnected();
            }
        }
    };
}