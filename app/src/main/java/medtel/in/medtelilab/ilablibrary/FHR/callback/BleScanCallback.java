package medtel.in.medtelilab.ilablibrary.FHR.callback;


import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

import java.util.List;

public abstract class BleScanCallback implements BleScanPresenterImp {

    public abstract void onScanFinished(List<BleDevice> scanResultList);

    public void onLeScan(BleDevice bleDevice) {
    }
}
