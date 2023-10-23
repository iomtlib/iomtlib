package medtel.in.medtelilab.ilablibrary;


import android.bluetooth.BluetoothDevice;

import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public class ReadDevices {

    String device_id,device_name,devicetype;
    BleDevice bleDevice;
    BluetoothDevice bluetoothDevice;

    public ReadDevices(String device_id,String device_name,String devicetype) {
        this.device_id=device_id;
        this.device_name=device_name;
        this.devicetype=devicetype;
    }

    public String getDevice_id()
    {
        return device_id;
    }

    public void setDevice_id(String device_id)
    {
        this.device_id = device_id;
    }

    public String getDevice_name()
    {
        return device_name;
    }

    public void setDevice_name(String device_name)
    {
        this.device_name = device_name;
    }
    public String getDevicetype()
    {
        return devicetype;
    }
    public void setDevicetype(String devicetype)
    {
        this.devicetype=devicetype;
    }

    public BleDevice getBleDevice()
    {
        return bleDevice;
    }
    public void setBleDevice(BleDevice bleDevice)
    {
        this.bleDevice=bleDevice;
    }
    public BluetoothDevice getBluetoothDevice()
    {
        return  bluetoothDevice;
    }
    public void setBluetoothDevice(BluetoothDevice bluetoothDevice)
    {
        this.bluetoothDevice=bluetoothDevice;
    }


}
