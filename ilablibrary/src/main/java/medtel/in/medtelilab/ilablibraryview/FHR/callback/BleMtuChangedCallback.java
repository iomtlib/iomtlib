package medtel.in.medtelilab.ilablibraryview.FHR.callback;


import medtel.in.medtelilab.ilablibraryview.FHR.exception.BleException;

public abstract class BleMtuChangedCallback extends BleBaseCallback {

    public abstract void onSetMTUFailure(BleException exception);

    public abstract void onMtuChanged(int mtu);

}
