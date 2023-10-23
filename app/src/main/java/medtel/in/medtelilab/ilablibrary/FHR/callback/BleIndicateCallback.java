package medtel.in.medtelilab.ilablibrary.FHR.callback;


import medtel.in.medtelilab.ilablibrary.FHR.exception.BleException;

public abstract class BleIndicateCallback extends BleBaseCallback{

    public abstract void onIndicateSuccess();

    public abstract void onIndicateFailure(BleException exception);

    public abstract void onCharacteristicChanged(byte[] data);
}
