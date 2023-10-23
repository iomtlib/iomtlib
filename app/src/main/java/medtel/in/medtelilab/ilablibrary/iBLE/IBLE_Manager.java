package medtel.in.medtelilab.ilablibrary.iBLE;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

public class IBLE_Manager {
    public static int VERSION = 7;
    public static IBLE_Manager mIBleManager;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private IBLE_GattCallback mGattCallback;
    private Context mContext;
    private String mConnectDeviceAddress;
    private IBLE_Callback mIBLECallback;

    IBLE_Manager() {
    }

    public static IBLE_Manager getInstance() {
        if (mIBleManager == null) {
            mIBleManager = new IBLE_Manager();
        }

        return mIBleManager;
    }

    public void InitSDK(Context context) {
        this.mContext = context;
        boolean isBleAvailable = this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        if (isBleAvailable) {
            this.mBluetoothManager = (BluetoothManager)this.mContext.getSystemService("bluetooth");
            this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        }

        this.mGattCallback = new IBLE_GattCallback(this.mContext, this.mIBLECallback);
        this.mIBLECallback.CallbackInitSDK(VERSION);
    }

    public void SetCallback(IBLE_Callback callback) {
        this.mIBLECallback = callback;
        if (this.mGattCallback != null) {
            this.mGattCallback.setCallback(callback);
        }

    }

    public void RequestTimeSync() {
        this.mGattCallback.setTimeSync();
    }

    public void RequestAllRecords() {
        this.mGattCallback.requestBleAll();
    }

    public void RequestRecordAfterSequence(int sequence) {
        this.mGattCallback.requestBleMoreEqual(sequence);
    }

    public synchronized void ConnectDevice(String address) {

        if (this.mBluetoothAdapter == null || address == null) {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
        }

        BluetoothDevice bluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(address);
        if (bluetoothDevice == null) {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_BLUETOOTH_DEVICE_NULL);
        }

        if (this.mBluetoothManager == null || this.mBluetoothManager.getConnectionState(bluetoothDevice, 7) != 2) {
            if (this.mConnectDeviceAddress != null && address.equals(this.mConnectDeviceAddress) && this.mBluetoothGatt != null) {
                if (this.mBluetoothGatt.connect()) {
                    return;
                }

                this.mGattCallback.initBluetoothVariables();
            }

            if (android.os.Build.VERSION.SDK_INT >= 23) {
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, true, this.mGattCallback, 2);
            } else {
                this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, true, this.mGattCallback);
            }

            this.refreshDeviceCache(this.mBluetoothGatt);
            this.mGattCallback.setBluetoothGatt(this.mBluetoothGatt, bluetoothDevice);
            this.mConnectDeviceAddress = address;
        }
    }

    private boolean refreshDeviceCache(BluetoothGatt gatt) {
        try {
            Method localMethod = gatt.getClass().getMethod("refresh");
            if (localMethod != null) {
                boolean bool = (Boolean)localMethod.invoke(gatt);
                return bool;
            }
        } catch (Exception var5) {
            var5.getMessage();
        }

        return false;
    }

    public void DisconnectDevice() {
        if (this.mGattCallback != null) {
            this.mGattCallback.disconnect();
        }
    }

    public void UnPairDevice(String address) {
        Set<BluetoothDevice> pairedDevices = ((BluetoothManager)this.mContext.getSystemService("bluetooth")).getAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            Iterator var3 = pairedDevices.iterator();

            while(var3.hasNext()) {
                BluetoothDevice device = (BluetoothDevice)var3.next();

                try {
                    if (device.getAddress().equals(address)) {
                        Method m = device.getClass().getMethod("removeBond", (Class[])null);
                        m.invoke(device, (Object[])null);
                    }
                } catch (Exception var6) {
                    var6.getMessage();
                }
            }
        }

    }

    public void DestroySDK() {
        this.mConnectDeviceAddress = null;
        if (this.mGattCallback != null) {
            this.mGattCallback.destroy();
        }

    }
}