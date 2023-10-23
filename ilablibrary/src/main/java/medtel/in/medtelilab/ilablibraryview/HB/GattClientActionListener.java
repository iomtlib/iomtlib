package medtel.in.medtelilab.ilablibraryview.HB;


import android.bluetooth.BluetoothDevice;

public interface GattClientActionListener {

    void log(String message);

    void logError(String message);

    void setConnected(boolean connected, BluetoothDevice device);

    void initializeTime();

    void initializeEcho();

    void disconnectGattServer();

    void showToast(String msg);
}
