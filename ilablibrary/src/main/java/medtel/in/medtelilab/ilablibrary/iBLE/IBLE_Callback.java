package medtel.in.medtelilab.ilablibrary.iBLE;


import android.util.SparseArray;

public interface IBLE_Callback {
    void CallbackInitSDK(int version);

    void CallbackConnectedDevice();

    void CallbackDisconnectedDevice();

    void CallbackRequestTimeSync();

    void CallbackRequestRecordsComplete(SparseArray<IBLE_GlucoseRecord> records);

    void CallbackReadDeviceInfo(IBLE_Device device);

    void CallbackError(IBLE_Error error);
}
