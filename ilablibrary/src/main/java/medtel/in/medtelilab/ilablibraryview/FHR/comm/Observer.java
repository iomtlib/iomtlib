package medtel.in.medtelilab.ilablibraryview.FHR.comm;


import medtel.in.medtelilab.ilablibraryview.FHR.data.BleDevice;

public interface Observer {

    void disConnected(BleDevice bleDevice);
}
