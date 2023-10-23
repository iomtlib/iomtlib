package medtel.in.medtelilab.ilablibraryview.FHR.callback;


import medtel.in.medtelilab.ilablibraryview.FHR.data.BleDevice;

public abstract class BleScanAndConnectCallback extends BleGattCallback implements BleScanPresenterImp {

    public abstract void onScanFinished(BleDevice scanResult);

    public void onLeScan(BleDevice bleDevice) {
    }

}
