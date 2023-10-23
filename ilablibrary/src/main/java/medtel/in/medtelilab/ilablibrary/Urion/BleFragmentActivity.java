package medtel.in.medtelilab.ilablibrary.Urion;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;


import medtel.in.medtelilab.ilablibrary.Urion.urionclass.BleServiceHelper;
import medtel.in.medtelilab.ilablibrary.Urion.urionclass.L;
import medtel.in.medtelilab.ilablibrary.Urion.urionclass.SampleGattAttributes;
import medtel.in.medtelilab.ilablibrary.Urion.urionservice.BluetoothLeService;

public abstract class BleFragmentActivity extends FragmentActivity {



    public final static int ble_scaning = 0;
    public final static int ble_connecting = 1;
    public final static int ble_connected = 3;
    public final static int ble_on = 2;
    public final static int ble_off = -1;
    public final static int ble_disConnected = -2;


    public int bleState = ble_off;

    private boolean isBindServise;
    public Handler handler = new Handler(Looper.getMainLooper());


    protected ArrayList<BluetoothDevice> mLeDevices = new ArrayList<BluetoothDevice>();
    // 连接GATT Serveice
    protected BluetoothLeService mBluetoothLeService;
    protected BluetoothAdapter mBluetoothAdapter;
    protected BluetoothDevice mDevice;

    private BleBroadCastRecever myBleRecever;
    private boolean isBleseviceRegiste;
    private int reTryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothManager bluetoothManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }


        myBleRecever = new BleBroadCastRecever();
        registerReceiver(myBleRecever, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        startService("");

        initBlue();


        if(mBluetoothAdapter.isEnabled()){
            startScan();
        }else{
//            showBleDialog();
            mBluetoothAdapter.enable();
//            Toast.makeText(getA, "bluetooth is now ON, please click on start", Toast.LENGTH_SHORT).show();

        }

        //L.d("-------------------->"+mBluetoothAdapter);


    }



    public BleFragmentActivity() {
    }




    public void initBlue() {
        IntentFilter filter =  BleServiceHelper.makeGattUpdateIntentFilter();
        filter.addAction(SampleGattAttributes.DISCONNECTEDBLE);
        registerReceiver(getBroadCastReceiver(), filter);
        isBleseviceRegiste = true;

        // 此时开始搜索蓝牙
        // FIXME 开启动画
        //getTipText().setText(R.string.ble_scan_);

        L.d("-------------------->"+mBluetoothAdapter);
        L.d("-------------------->"+mLeScanCallback);
    }

    public void startScan(){
        // Toast.makeText(getApplicationContext(), "Scanning", Toast.LENGTH_SHORT).show();
        mDevice = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
        bleState = ble_scaning;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                stopScan();
            }
        }, 15000);
    }



    public void stopScan() {
        // mScanning = false;
        L.d("-------------------->"+mBluetoothAdapter);
        L.d("-------------------->"+mLeScanCallback);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //scanlayout1.setVisibility(View.GONE);
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        // center_button.setText("停止");
        if(mDevice == null){
            bleState = ble_scaning;
            // start.setVisibility(View.VISIBLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        }
    }



    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mLeDevices.contains(device)) {
                        mLeDevices.add(device);
                        L.d("device-->" + device.getName());
                        if (device.getName() != null) {
                            String deviceName = device.getName();
                            // System.out.println("devicename"+deviceName);
                            if (getDeviceName().equals(deviceName) || "Wileless BP".equals(deviceName) || "Urion BP".equals(deviceName)
                                    || "BLE to UART_2".equals(deviceName) || "Bluetooth BP".equals(deviceName)) {

                                bleState = ble_connecting;
                                mDevice = device;
                                final String address = device.getAddress();
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        startService(address);
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    };




    protected void startService(String address) {
        if(!isBindServise ){
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            // 启动services
            isBindServise = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        }else if(!TextUtils.isEmpty(address)){
            if(mBluetoothLeService != null){
                mBluetoothLeService.connect(address);// 根据地址通过后台services去连接BLE蓝牙
            }

        }
    }

    // 后台services,通过ServiceConnection找到IBinder-->service
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            // 获取services对象
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(mDevice==null){
                        return;
                    }
                    boolean isOk = mBluetoothLeService.connect(mDevice.getAddress());// 根据地址通过后台services去连接BLE蓝牙
                    L.d("first  -----connect ok  ? >"+ isOk);
                    if(!isOk){
                        reTryConected();
                    }
                }
            });

        }

        // service因异常而断开连接的时候，这个方法才会用到。
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }

    };
//	private DialogFragment bleDialog;


    private void reTryConected(){
        reTryCount++;
        if(reTryCount < 7){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    boolean isOk = mBluetoothLeService.connect(getDeviceName());// 根据地址通过后台services去连接BLE蓝牙
                    L.d(reTryCount+" -----connect ok  ? >"+ isOk);
                    if(!isOk){
                        reTryConected();
                    }
                }
            }, 1000);
        }
    }







    class BleBroadCastRecever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    break;
                case BluetoothAdapter.STATE_ON:
//				getTipText().setText("已开启蓝牙");
//				getTipText().setOnClickListener(null);
//				initBlue();
                    bleState = ble_on;
                    startScan();
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    bleState = ble_off;
                    break;
                case BluetoothAdapter.STATE_OFF:
                    bleState = ble_off;
                    break;

            }
        }
    }



    @Override
    protected void onDestroy() {
        if(isBleseviceRegiste){
            unregisterReceiver(getBroadCastReceiver());
        }
        if (isBindServise) {
            this.unbindService(mServiceConnection);
        }
        unregisterReceiver(myBleRecever);
        super.onDestroy();

    }

    public abstract BroadcastReceiver getBroadCastReceiver();

    public abstract TextView getTipText();

    public abstract String getDeviceName();

    public abstract String getUUID();

}
