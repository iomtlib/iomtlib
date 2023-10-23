package medtel.in.medtelilab.ilablibrary.FHR.comm;


import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public interface Observer {

    void disConnected(BleDevice bleDevice);
}
