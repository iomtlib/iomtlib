
package medtel.in.medtelilab.ilablibraryview.Urion.urionclass;

import android.content.IntentFilter;

import medtel.in.medtelilab.ilablibraryview.Urion.urionservice.BluetoothLeService;

public class BleServiceHelper {

	public BleServiceHelper() {
	}


	// 注册广播的IntentFilter
	public  static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_WRITE_SUCCESS);
		return intentFilter;
	}


}
