package medtel.in.medtelilab.ilablibrary.FHR;


import android.bluetooth.BluetoothDevice;

public interface GattClientActionListenerfhr {

    void logfhr(String message);

    void logErrorfhr(String message);

    void setConnectedfhr(boolean connected, BluetoothDevice device);

    void initializeTimefhr();

    void initializeEchofhr();

    void disconnectGattServerfhr();

    void showToastfhr(String msg);



}