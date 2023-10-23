package medtel.in.medtelilab.ilablibraryview.FHR.callback;

import medtel.in.medtelilab.ilablibraryview.FHR.data.BleDevice;

public interface BleScanPresenterImp {

    void onScanStarted(boolean success);

    void onScanning(BleDevice bleDevice);

}
