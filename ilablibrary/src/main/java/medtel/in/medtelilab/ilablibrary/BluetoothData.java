package medtel.in.medtelilab.ilablibrary;

import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public class BluetoothData {

    public String address,devicename,rssi;
   public BleDevice bleDevice;
   String devicetype="";


    public BluetoothData(String devicename,String address,String rssi,BleDevice bleDevice,String devicetype) {
      this.devicename=devicename;
      this.address=address;
      this.rssi=rssi;
      this.bleDevice=bleDevice;
      this.devicetype=devicetype;


    }

    public String getAddress()
    {
        return  address;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public String getDevicename()
    {
        return  devicename;
    }
    public void setDevicename(String devicename)
    {
        this.devicename=devicename;

    }
    public String getRssi()
    {
        return rssi;
    }
    public void setRssi(String rssi)
    {
        this.rssi=rssi;
    }
    public BleDevice getBleDevice()
    {
        return bleDevice;
    }
    public void setBleDevice(BleDevice bleDevice)
    {
        this.bleDevice=bleDevice;
    }
    public String getDevicetype()
    {
        return devicetype;
    }
    public void setDevicetype(String devicetype)
    {
        this.devicetype=devicetype;
    }

}
