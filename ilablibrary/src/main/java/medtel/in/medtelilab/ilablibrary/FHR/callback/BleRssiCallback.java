package medtel.in.medtelilab.ilablibrary.FHR.callback;


import medtel.in.medtelilab.ilablibrary.FHR.exception.BleException;

public abstract class BleRssiCallback extends BleBaseCallback{

    public abstract void onRssiFailure(BleException exception);

    public abstract void onRssiSuccess(int rssi);

}