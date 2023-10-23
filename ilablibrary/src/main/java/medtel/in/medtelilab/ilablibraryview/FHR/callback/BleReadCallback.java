package medtel.in.medtelilab.ilablibraryview.FHR.callback;


import medtel.in.medtelilab.ilablibraryview.FHR.exception.BleException;

public abstract class BleReadCallback extends BleBaseCallback {

    public abstract void onReadSuccess(byte[] data);

    public abstract void onReadFailure(BleException exception);

}
