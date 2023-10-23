package medtel.in.medtelilab.ilablibrary.FHR.callback;


import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public abstract class BleScanAndConnectCallback extends BleGattCallback implements BleScanPresenterImp {

    public abstract void onScanFinished(BleDevice scanResult);

    public void onLeScan(BleDevice bleDevice) {
    }

}
