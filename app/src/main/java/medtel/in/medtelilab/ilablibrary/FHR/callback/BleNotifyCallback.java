package medtel.in.medtelilab.ilablibrary.FHR.callback;


import medtel.in.medtelilab.ilablibrary.FHR.exception.BleException;

public abstract class BleNotifyCallback extends BleBaseCallback {

    public abstract void onNotifySuccess();

    public abstract void onNotifyFailure(BleException exception);

    public abstract void onCharacteristicChanged(byte[] data);

}
