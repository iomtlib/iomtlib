package medtel.in.medtelilab.ilablibrary.FHR.callback;

import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public interface BleScanPresenterImp {

    void onScanStarted(boolean success);

    void onScanning(BleDevice bleDevice);

}
