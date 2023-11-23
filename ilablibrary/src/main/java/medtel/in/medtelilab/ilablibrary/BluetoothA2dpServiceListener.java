package medtel.in.medtelilab.ilablibrary;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothProfile;

public class BluetoothA2dpServiceListener implements BluetoothProfile.ServiceListener {
    private BluetoothA2dp bluetoothA2dp;

    @Override
    public void onServiceConnected(int profile, BluetoothProfile proxy) {
        if (profile == BluetoothProfile.A2DP) {
            bluetoothA2dp = (BluetoothA2dp) proxy;
        }
    }

    @Override
    public void onServiceDisconnected(int profile) {
        if (profile == BluetoothProfile.A2DP) {
            bluetoothA2dp = null;
        }
    }

    public BluetoothA2dp getBluetoothA2dp() {
        return bluetoothA2dp;
    }
}
