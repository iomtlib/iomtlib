package medtel.in.medtelilab.ilablibrary.iBLE;


import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.UUID;

public class IBLE_GattCallback extends BluetoothGattCallback {
    private Context mContext;
    private Handler mHandler;
    private BluetoothGatt mBluetoothGatt;
    private String mDeviceAddress;
    private BluetoothDevice mBluetoothDevice;
    private IBLE_Callback mIBLECallback;
    public static final SparseArray<IBLE_GlucoseRecord> mRecords = new SparseArray();
    private static final int SOFTWARE_REVISION_BASE = 1;
    private static final int SOFTWARE_REVISION_1 = 1;
    private static final int SOFTWARE_REVISION_2 = 0;
    private static final int SOFTWARE_REVISION_CTS_2 = 5;
    private static final int SOFTWARE_REVISION_CTS_3 = 4;
    private boolean mIsDownloadFinished = false;
    private int mSoftwareVersion1;
    private int mSoftwareVersion2;
    private int mSoftwareVersion3;
    private String mSerialNum;
    private String mSoftwareVersion;
    private String mDeviceName;
    private boolean mCTSSupport = false;
    private BluetoothGattCharacteristic mGlucoseMeasurementCharacteristic;
    private BluetoothGattCharacteristic mGlucoseContextCharacteristic;
    private BluetoothGattCharacteristic mRACPCharacteristic;
    private BluetoothGattCharacteristic mDeviceSerialCharacteristic;
    private BluetoothGattCharacteristic mDeviceSoftwareRevisionCharacteristic;
    private BluetoothGattCharacteristic mCustomTimeCharacteristic;
    private BluetoothGattCharacteristic mCurrentTimeCharacteristic;
    private BluetoothGattCharacteristic mLocalTimeInfoCharacteristic;
    private static final int RESPONSE_SUCCESS = 1;
    private static final int RESPONSE_OP_CODE_NOT_SUPPORTED = 2;
    private static final int RESPONSE_NO_RECORDS_FOUND = 6;
    private static final int RESPONSE_ABORT_UNSUCCESSFUL = 7;
    private static final int RESPONSE_PROCEDURE_NOT_COMPLETED = 8;
    private static final int OPERATOR_ALL_RECORDS = 1;
    private static final int OPERATOR_GREATER_OR_EQUAL_RECORDS = 3;
    private static final int FILTER_TYPE_SEQUENCE_NUMBER = 1;
    private static final int OP_CODE_REPORT_STORED_RECORDS = 1;
    private static final int OP_CODE_REPORT_NUMBER_OF_RECORDS = 4;
    private static final int OP_CODE_NUMBER_OF_STORED_RECORDS_RESPONSE = 5;
    private static final int OP_CODE_RESPONSE_CODE = 6;
    private static final int OP_CODE_SET_FLAG = 225;
    private static final int OP_CODE_GET_FLAG = 224;
    private static final int COMPLETE_RESULT_FROM_METER = 192;
    private static final int RESPONSE_SET_FLAG_INFO = 2;
    private static final int RESPONSE_SET_TARGET_RANGE = 5;
    private BroadcastReceiver mBondingBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(final Context context, final Intent intent) {
            BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int bondState = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
            if (device != null && IBLE_GattCallback.this.mBluetoothGatt != null) {
                if (device.getAddress().equals(IBLE_GattCallback.this.mBluetoothGatt.getDevice().getAddress())) {
                    if (bondState == 12) {
                        IBLE_GattCallback.this.enableRecordAccessControlPointIndication(IBLE_GattCallback.this.mBluetoothGatt);
                    } else if (bondState == 10) {
                        IBLE_GattCallback.this.mIBLECallback.CallbackError(IBLE_Error.ERROR_CONNECT_FAIL);
                    }

                }
            }
        }
    };

    public IBLE_GattCallback(Context context, IBLE_Callback ible_callback) {
        this.mContext = context;
        this.mIBLECallback = ible_callback;
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        context.registerReceiver(this.mBondingBroadcastReceiver, filter);
    }

    public void destroy() {
        try {
            this.mContext.unregisterReceiver(this.mBondingBroadcastReceiver);
            this.close();
        } catch (Exception var2) {
        }

    }

    public void initBluetoothVariables() {
        if (this.mBluetoothGatt != null) {
            try {
                this.mBluetoothGatt.disconnect();
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
            } catch (Exception var2) {
            }
        }

        this.mDeviceAddress = null;
    }

    public void setCallback(IBLE_Callback callback) {
        this.mIBLECallback = callback;
    }

    public void setBluetoothGatt(BluetoothGatt gatt, BluetoothDevice device) {
        this.mBluetoothDevice = device;
        this.mBluetoothGatt = gatt;
    }

    public void disconnect() {
        if (this.mBluetoothGatt != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    Thread.sleep(200L);
                    this.mBluetoothGatt.disconnect();
                } catch (InterruptedException var3) {
                    var3.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(200L);
                    this.mBluetoothGatt.writeCharacteristic(this.mRACPCharacteristic);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }

        }
    }

    public void close() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
        }

        if (mRecords != null) {
            mRecords.clear();
        }

    }

    public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
        Log.d("", "onConnectionStateChange status: " + status + " // newState: " + newState);
        if (status != 133 && status != 129) {
            if (newState == 2) {
                this.mDeviceName = gatt.getDevice().getName();
                this.mIBLECallback.CallbackConnectedDevice();
                this.mBluetoothGatt = gatt;
                gatt.discoverServices();
                Log.d("", "onConnectionStateChange status: " + this.mDeviceName+"||"+this.mIBLECallback+"||"+this.mBluetoothGatt);
            } else if (newState == 0) {
                this.mIBLECallback.CallbackDisconnectedDevice();
                if (gatt.getDevice().getBondState() == 12 && !this.mIsDownloadFinished) {
                    this.mIsDownloadFinished = true;
                    this.mIBLECallback.CallbackRequestRecordsComplete(mRecords);
                }

                this.disconnect();
            }

        }
    }

    private void initCharacteristics() {
        this.mGlucoseMeasurementCharacteristic = null;
        this.mGlucoseContextCharacteristic = null;
        this.mRACPCharacteristic = null;
        this.mDeviceSerialCharacteristic = null;
        this.mDeviceSoftwareRevisionCharacteristic = null;
        this.mCustomTimeCharacteristic = null;
        this.mCurrentTimeCharacteristic = null;
        this.mLocalTimeInfoCharacteristic = null;
    }

    public void initVariables() {
        mRecords.clear();
        this.mIsDownloadFinished = false;
        this.mSerialNum = "";
        this.mSoftwareVersion = "";
        this.mDeviceName = "";
        this.mCTSSupport = false;
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.d("", "onServicesDiscovered");
        if (status != 0) {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_DISCOVERY_SERVICE);
        } else {
            this.initCharacteristics();
            this.initVariables();
            Iterator var3 = gatt.getServices().iterator();

            while(true) {
                if (!var3.hasNext()) {
                    if (this.mGlucoseMeasurementCharacteristic == null || this.mRACPCharacteristic == null) {
                        this.mIBLECallback.CallbackError(IBLE_Error.ERROR_DEVICE_NOT_SUPPORTED);
                        return;
                    }

                    if (this.mDeviceSoftwareRevisionCharacteristic != null) {
                        this.readDeviceSoftwareRevision(gatt);
                    }
                    break;
                }

                BluetoothGattService service = (BluetoothGattService)var3.next();
                if (IBLE_Const.BLE_SERVICE_GLUCOSE.equals(service.getUuid())) {
                    this.mGlucoseMeasurementCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_GLUCOSE_MEASUREMENT);
                    this.mGlucoseContextCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_GLUCOSE_CONTEXT);
                    this.mRACPCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_RACP);
                } else if (IBLE_Const.BLE_SERVICE_DEVICE_INFO.equals(service.getUuid())) {
                    this.mDeviceSerialCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_DEVICE_INFO_SERIALNO);
                    this.mDeviceSoftwareRevisionCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_DEVICE_INFO_SOFTWARE_REVISION);
                } else if (IBLE_Const.BLE_SERVICE_CUSTOM_TIME.equals(service.getUuid())) {
                    this.mCustomTimeCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_CUSTOM_TIME);
                    if (this.mCustomTimeCharacteristic != null) {
                        gatt.setCharacteristicNotification(this.mCustomTimeCharacteristic, true);
                    }
                } else if (IBLE_Const.BLE_SERVICE_CUSTOM_TIME_NEW.equals(service.getUuid())) {
                    this.mCustomTimeCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_CUSTOM_TIME_NEW);
                    if (this.mCustomTimeCharacteristic != null) {
                        gatt.setCharacteristicNotification(this.mCustomTimeCharacteristic, true);
                    }
                } else if (IBLE_Const.BLE_SERVICE_CURRENT_TIME.equals(service.getUuid())) {
                    this.mCurrentTimeCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_CURRENT_TIME);
                    this.mLocalTimeInfoCharacteristic = service.getCharacteristic(IBLE_Const.BLE_CHAR_LOCAL_TIME_INFO);
                }
            }
        }

    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d("", "onCharacteristicRead uuid : " + characteristic.getUuid());
        if (status == 0) {
            if (IBLE_Const.BLE_CHAR_DEVICE_INFO_SOFTWARE_REVISION.equals(characteristic.getUuid())) {
                this.mSoftwareVersion = characteristic.getStringValue(0);
                String[] softwareVersions = this.mSoftwareVersion.split("\\.");
                if (softwareVersions.length < 3) {
                    this.mIBLECallback.CallbackError(IBLE_Error.ERROR_INVALID_SOFTWARE_VERSION);
                    gatt.disconnect();
                    return;
                }

                this.mSoftwareVersion1 = Integer.parseInt(softwareVersions[0]);
                this.mSoftwareVersion2 = Integer.parseInt(softwareVersions[1]);
                this.mSoftwareVersion3 = Integer.parseInt(softwareVersions[2]);
                if (this.mSoftwareVersion1 > 1) {
                    this.mIBLECallback.CallbackError(IBLE_Error.ERROR_INVALID_SOFTWARE_VERSION);
                    gatt.disconnect();
                    return;
                }

                if (this.mSoftwareVersion1 >= 1 && this.mSoftwareVersion1 == 1 && this.mCustomTimeCharacteristic == null) {
                    this.mIBLECallback.CallbackError(IBLE_Error.ERROR_INVALID_SOFTWARE_VERSION);
                    gatt.disconnect();
                    return;
                }

                if (this.mSoftwareVersion2 >= 5 && this.mSoftwareVersion3 >= 4) {
                    this.mCTSSupport = true;
                }

                if (this.mDeviceSerialCharacteristic != null) {
                    this.readDeviceSerial(gatt);
                }
            } else if (IBLE_Const.BLE_CHAR_DEVICE_INFO_SERIALNO.equals(characteristic.getUuid())) {
                this.mSerialNum = characteristic.getStringValue(0);
                if (this.mBluetoothGatt.getDevice().getBondState() != 12) {
                    this.mBluetoothGatt.getDevice().createBond();
                } else {
                    this.enableRecordAccessControlPointIndication(gatt);
                }
            }
        }

    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        if (status == 0) {
            if (IBLE_Const.BLE_CHAR_GLUCOSE_MEASUREMENT.equals(descriptor.getCharacteristic().getUuid())) {
                this.enableGlucoseContextNotification(gatt);
            }

            if (IBLE_Const.BLE_CHAR_GLUCOSE_CONTEXT.equals(descriptor.getCharacteristic().getUuid())) {
                if (this.mCustomTimeCharacteristic != null) {
                    if (this.mCTSSupport) {
                        this.enableCurrentTimeSyncNotification(gatt);
                    } else {
                        this.enableCustomTimeSyncNotification(gatt);
                    }
                } else {
                    this.requestTimeSyncForOldMeter();
                }
            }

            if (IBLE_Const.BLE_CHAR_RACP.equals(descriptor.getCharacteristic().getUuid())) {
                this.enableGlucoseMeasurementNotification(gatt);
            }

            if (IBLE_Const.BLE_CHAR_CUSTOM_TIME.equals(descriptor.getCharacteristic().getUuid()) || IBLE_Const.BLE_CHAR_CUSTOM_TIME_NEW.equals(descriptor.getCharacteristic().getUuid())) {
                this.requestTotalCount();
            }

            if (IBLE_Const.BLE_CHAR_CURRENT_TIME.equals(descriptor.getCharacteristic().getUuid())) {
                this.enableCustomTimeSyncNotification(gatt);
            }
        } else if (status == 5 && gatt.getDevice().getBondState() != 10) {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_AUTH_ERROR_WHILE_BONDED);
        }

    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        UUID uuid = characteristic.getUuid();
        Log.d("", "onCharacteristicChanged uuid : " + uuid);

        int opCode;
        int responseCode;
        int offset;
        if (!IBLE_Const.BLE_CHAR_CUSTOM_TIME.equals(uuid) && !IBLE_Const.BLE_CHAR_CUSTOM_TIME_NEW.equals(uuid) && !IBLE_Const.BLE_CHAR_LOCAL_TIME_INFO.equals(uuid)) {
            if (IBLE_Const.BLE_CHAR_CURRENT_TIME.equals(uuid)) {
                this.setLocalTimeInfo();
                offset = 0;
                opCode = characteristic.getIntValue(17, offset);
                offset = offset + 2;
                Log.d("", "---BLE_CHAR_CURRENT_TIME");
                this.mIBLECallback.CallbackRequestTimeSync();
            } else {
                boolean moreFlagsPresent;
                boolean carbohydratePresent;
                boolean mealPresent;
                if (IBLE_Const.BLE_CHAR_GLUCOSE_MEASUREMENT.equals(uuid)) {
                    offset = 0;
                    opCode = characteristic.getIntValue(17, offset);
                    offset = offset + 1;
                    carbohydratePresent = (opCode & 1) > 0;
                    mealPresent = (opCode & 2) > 0;
                    moreFlagsPresent = (opCode & 4) > 0;
                    boolean sensorStatusAnnunciationPresent = (opCode & 8) > 0;
                    boolean contextInfoFollows = (opCode & 16) > 0;
                    int sequenceNumber = characteristic.getIntValue(18, offset);
                    boolean isSavedData = true;
                    IBLE_GlucoseRecord record = (IBLE_GlucoseRecord)mRecords.get(sequenceNumber);
                    if (record == null) {
                        record = new IBLE_GlucoseRecord();
                        isSavedData = false;
                    }

                    record.sequenceNumber = sequenceNumber;
                    record.flag_context = 0;
                    offset += 2;
                    int year = characteristic.getIntValue(18, offset + 0);
                    int month = characteristic.getIntValue(17, offset + 2);
                    int day = characteristic.getIntValue(17, offset + 3);
                    int hours = characteristic.getIntValue(17, offset + 4);
                    int minutes = characteristic.getIntValue(17, offset + 5);
                    int seconds = characteristic.getIntValue(17, offset + 6);
                    offset += 7;
                    if (month >= 13) {
                        month -= 12;
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month - 1, day, hours, minutes, seconds);
                    record.time = calendar.getTimeInMillis() / 1000L;
                    if (carbohydratePresent) {
                        record.timeoffset = characteristic.getIntValue(34, offset);
                        record.time += (long)(record.timeoffset * 60);
                        offset += 2;
                    }

                    if (mealPresent) {
                        byte[] value = characteristic.getValue();
                        int glucoseValue = (int)this.bytesToFloat(value[offset], value[offset + 1]);
                        record.glucoseData = (double)glucoseValue;
                        int typeAndLocation = characteristic.getIntValue(17, offset + 2);
                        int type = (typeAndLocation & 240) >> 4;
                        record.flag_cs = type == 10 ? 1 : 0;
                        offset += 3;
                    }

                    if (sensorStatusAnnunciationPresent) {
                        int hilow = characteristic.getIntValue(18, offset);
                        if (hilow == 64) {
                            record.flag_hilow = -1;
                        }

                        if (hilow == 32) {
                            record.flag_hilow = 1;
                        }

                        offset += 2;
                    }

                    record.flag_hilow = record.flag_ketone == 1 && record.flag_hilow == -1 && this.mSoftwareVersion2 >= 4 ? -2 : record.flag_hilow;
                    if (!contextInfoFollows) {
                        record.flag_context = 1;
                    }

                    try {
                        if (!isSavedData) {
                            mRecords.put(record.sequenceNumber, record);
                        }
                    } catch (Exception var26) {
                    }
                } else if (IBLE_Const.BLE_CHAR_GLUCOSE_CONTEXT.equals(uuid)) {
                    offset = 0;
                    opCode = characteristic.getIntValue(17, offset);
                    offset = offset + 1;
                    carbohydratePresent = (opCode & 1) > 0;
                    mealPresent = (opCode & 2) > 0;
                    moreFlagsPresent = (opCode & 128) > 0;
                    int sequenceNumber = characteristic.getIntValue(18, offset);
                    offset += 2;
                    if (moreFlagsPresent) {
                        ++offset;
                    }

                    if (carbohydratePresent) {
                        offset += 3;
                    }

                    int meal = mealPresent ? characteristic.getIntValue(17, offset) : -1;
                    boolean isSavedData = true;
                    IBLE_GlucoseRecord record = (IBLE_GlucoseRecord)mRecords.get(sequenceNumber);
                    if (record == null) {
                        record = new IBLE_GlucoseRecord();
                        isSavedData = false;
                    }

                    if (record == null || !mealPresent) {
                        return;
                    }

                    record.sequenceNumber = sequenceNumber;
                    record.flag_context = 1;
                    switch(meal) {
                        case 0:
                            if (record.flag_cs == 0) {
                                record.flag_nomark = 1;
                            }
                            break;
                        case 1:
                            record.flag_meal = -1;
                            break;
                        case 2:
                            record.flag_meal = 1;
                            break;
                        case 3:
                            record.flag_fasting = 1;
                        case 4:
                        case 5:
                        default:
                            break;
                        case 6:
                            record.flag_ketone = 1;
                    }

                    try {
                        if (!isSavedData) {
                            mRecords.put(record.sequenceNumber, record);
                        }
                    } catch (Exception var25) {
                    }
                } else if (IBLE_Const.BLE_CHAR_RACP.equals(uuid)) {
                    offset = 0;
                    opCode = characteristic.getIntValue(17, offset);
                    offset = offset + 2;
                    if (opCode == 192) {
                        responseCode = characteristic.getIntValue(17, offset - 1);
                        switch(responseCode) {
                            case 1:
                                this.mIBLECallback.CallbackRequestRecordsComplete(mRecords);
                            case 2:
                        }
                    } else if (opCode == 5) {
                        if (this.mBluetoothGatt == null || this.mRACPCharacteristic == null) {
                            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
                            return;
                        }

                        responseCode = characteristic.getIntValue(18, offset);

                        IBLE_Device device = new IBLE_Device();
                        device.mSerialNum = this.mSerialNum;
                        device.mTotalCount = responseCode;
                        device.mSoftwareVersion = this.mSoftwareVersion;

                        if (this.mDeviceName == null || this.mDeviceName.equals("")) {
                            this.mDeviceName = gatt.getDevice().getName();
                        }
                        Log.d("", "---BLE_CHAR_RACP totalCnt : " + responseCode+"||"+this.mDeviceName);
                        device.mDeviceName = this.mDeviceName;
                        this.mIBLECallback.CallbackReadDeviceInfo(device);
                    } else if (opCode == 6) {
                        responseCode = characteristic.getIntValue(17, offset + 1);
                        offset += 2;
                        switch(responseCode) {
                            case 1:
                            case 6:
                                if (this.mIsDownloadFinished) {
                                    return;
                                }

                                this.mIsDownloadFinished = true;
                                this.mIBLECallback.CallbackRequestRecordsComplete(mRecords);
                                break;
                            case 2:
                                this.mIBLECallback.CallbackError(IBLE_Error.ERROR_OPERATE_NOT_SUPPORTED);
                            case 3:
                            case 4:
                            case 5:
                            case 7:
                            case 8:
                        }
                    }
                }
            }
        } else {
            offset = 0;
            opCode = characteristic.getIntValue(17, offset);
            offset = offset + 2;
            if (opCode == 5) {
                Log.d("", "---requestCustomTimeSync");
                this.mIBLECallback.CallbackRequestTimeSync();
            } else if (opCode == 192) {
                responseCode = characteristic.getIntValue(17, offset - 1);
                switch(responseCode) {
                    case 2:
                        if (this.mBluetoothGatt == null || this.mRACPCharacteristic == null) {
                            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
                            return;
                        }
                }
            }
        }

    }

    private void readDeviceSoftwareRevision(final BluetoothGatt gatt) {
        gatt.readCharacteristic(this.mDeviceSoftwareRevisionCharacteristic);
    }

    private void readDeviceSerial(final BluetoothGatt gatt) {
        gatt.readCharacteristic(this.mDeviceSerialCharacteristic);
    }

    private void enableGlucoseMeasurementNotification(final BluetoothGatt gatt) {
        if (this.mGlucoseMeasurementCharacteristic != null) {
            gatt.setCharacteristicNotification(this.mGlucoseMeasurementCharacteristic, true);
            BluetoothGattDescriptor descriptor = this.mGlucoseMeasurementCharacteristic.getDescriptor(IBLE_Const.BLE_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
    }

    private void enableGlucoseContextNotification(final BluetoothGatt gatt) {
        if (this.mGlucoseContextCharacteristic != null) {
            gatt.setCharacteristicNotification(this.mGlucoseContextCharacteristic, true);
            BluetoothGattDescriptor descriptor = this.mGlucoseContextCharacteristic.getDescriptor(IBLE_Const.BLE_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
    }

    private void enableRecordAccessControlPointIndication(final BluetoothGatt gatt) {
        if (this.mRACPCharacteristic != null) {
            gatt.setCharacteristicNotification(this.mRACPCharacteristic, true);
            BluetoothGattDescriptor descriptor = this.mRACPCharacteristic.getDescriptor(IBLE_Const.BLE_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
    }

    private void enableCustomTimeSyncNotification(final BluetoothGatt gatt) {
        if (this.mCustomTimeCharacteristic != null) {
            gatt.setCharacteristicNotification(this.mCustomTimeCharacteristic, true);
            BluetoothGattDescriptor descriptor = this.mCustomTimeCharacteristic.getDescriptor(IBLE_Const.BLE_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
    }

    private void enableCurrentTimeSyncNotification(final BluetoothGatt gatt) {
        if (this.mCurrentTimeCharacteristic != null) {
            gatt.setCharacteristicNotification(this.mCurrentTimeCharacteristic, true);
            BluetoothGattDescriptor descriptor = this.mCurrentTimeCharacteristic.getDescriptor(IBLE_Const.BLE_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
    }

    public boolean requestTimeSyncForOldMeter() {
        if (this.mBluetoothGatt != null && this.mRACPCharacteristic != null) {
            this.setTimeSyncForOldMeter(this.mRACPCharacteristic);
            return this.mBluetoothGatt.writeCharacteristic(this.mRACPCharacteristic);
        } else {
            return false;
        }
    }

    public void setTimeSyncForOldMeter(final BluetoothGattCharacteristic characteristic) {
        Calendar currCal = new GregorianCalendar();
        byte bCurrYear1 = (byte)(currCal.get(1) & 255);
        byte bCurrYear2 = (byte)(currCal.get(1) >> 8 & 255);
        byte bCurrMonth = (byte)(currCal.get(2) + 1 & 255);
        byte bCurrDay = (byte)currCal.get(5);
        byte bCurrHour = (byte)(currCal.get(11) & 255);
        byte bCurrMin = (byte)(currCal.get(12) & 255);
        byte bCurrSec = (byte)(currCal.get(13) & 255);
        byte[] data = new byte[]{4, 1, 1, 0, bCurrYear1, bCurrYear2, bCurrMonth, bCurrDay, bCurrHour, bCurrMin, bCurrSec};
        characteristic.setValue(new byte[data.length]);

        for(int i = 0; i < data.length; ++i) {
            characteristic.setValue(data);
        }

    }

    private void requestTotalCount() {
        Log.d("", "---requestTotalCount");
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!IBLE_GattCallback.this.getTotalDataCnt()) {
                    try {
                        Thread.sleep(500L);
                        IBLE_GattCallback.this.getTotalDataCnt();
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }

            }
        });
    }

    public boolean getTotalDataCnt() {
        if (this.mBluetoothGatt != null && this.mRACPCharacteristic != null) {
            Log.d("", "---getTotalDataCnt");
            this.setOpCode(this.mRACPCharacteristic, 4, 1);
            return this.mBluetoothGatt.writeCharacteristic(this.mRACPCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private boolean getAllRecords() {
        if (this.mBluetoothGatt != null && this.mRACPCharacteristic != null) {
            Log.d("", "---getAllRecords");
            this.setOpCode(this.mRACPCharacteristic, 1, 1);
            return this.mBluetoothGatt.writeCharacteristic(this.mRACPCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    public void requestBleAll() {
        this.mIsDownloadFinished = false;
        mRecords.clear();
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!IBLE_GattCallback.this.getAllRecords()) {
                    try {
                        Thread.sleep(500L);
                        IBLE_GattCallback.this.getAllRecords();
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }

            }
        });
    }

    public void requestBleMoreEqual(final int sequence) {
        this.mIsDownloadFinished = false;
        mRecords.clear();
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!IBLE_GattCallback.this.getRecordsGreaterOrEqual(sequence + 1)) {
                    try {
                        Thread.sleep(500L);
                        IBLE_GattCallback.this.getRecordsGreaterOrEqual(sequence + 1);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }

            }
        });
    }

    private boolean getRecordsGreaterOrEqual(int sequence) {
        if (this.mBluetoothGatt != null && this.mRACPCharacteristic != null) {
            if (this.mCustomTimeCharacteristic == null) {
                this.setOpCode(this.mRACPCharacteristic, 4, 3, sequence);
            } else {
                this.setOpCode(this.mRACPCharacteristic, 1, 3, sequence);
            }

            return this.mBluetoothGatt.writeCharacteristic(this.mRACPCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private void setOpCode(final BluetoothGattCharacteristic characteristic, final int opCode, final int operator, final Integer... params) {
        if (characteristic != null) {
            int size = 2 + (params.length > 0 ? 1 : 0) + params.length * 2;
            characteristic.setValue(new byte[size]);
            int offset = 0;
            characteristic.setValue(opCode, 17, offset);
             offset = offset + 1;
            characteristic.setValue(operator, 17, offset);
            ++offset;
            if (params.length > 0) {
                characteristic.setValue(1, 17, offset);
                ++offset;
                Integer[] var7 = params;
                int var8 = params.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    Integer i = var7[var9];
                    characteristic.setValue(i, 18, offset);
                    offset += 2;
                }
            }

        }
    }

    public void setTimeSync() {
        Log.d("", "---setTimeSync");
        if (this.mCustomTimeCharacteristic == null && this.mCurrentTimeCharacteristic == null) {
            this.requestTimeSyncForOldMeter();
        } else {
            this.requestTimeSync();
        }

    }

    public void requestTimeSync() {
        if (this.mCTSSupport) {
            if (!this.getCurrentTimeSync()) {
                try {
                    Thread.sleep(300L);
                    this.getCurrentTimeSync();
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
        } else {
            this.requestCustomTimeSync();
        }

    }

    private boolean requestCustomTimeSync() {
        if (this.mBluetoothGatt != null && this.mCustomTimeCharacteristic != null) {
            this.setCustomTimeSync(this.mCustomTimeCharacteristic, new GregorianCalendar());
            return this.mBluetoothGatt.writeCharacteristic(this.mCustomTimeCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private void setCustomTimeSync(final BluetoothGattCharacteristic characteristic, Calendar currCal) {
        if (characteristic != null) {
            byte bCurrYear1 = (byte)(currCal.get(1) & 255);
            byte bCurrYear2 = (byte)(currCal.get(1) >> 8 & 255);
            byte bCurrMonth = (byte)(currCal.get(2) + 1 & 255);
            byte bCurrDay = (byte)(currCal.get(5) & 255);
            byte bCurrHour = (byte)(currCal.get(11) & 255);
            byte bCurrMin = (byte)(currCal.get(12) & 255);
            byte bCurrSec = (byte)(currCal.get(13) & 255);
            byte op_code_1 = -64;
            byte[] data = new byte[]{op_code_1, 3, 1, 0, bCurrYear1, bCurrYear2, bCurrMonth, bCurrDay, bCurrHour, bCurrMin, bCurrSec};
            characteristic.setValue(new byte[data.length]);

            for(int i = 0; i < data.length; ++i) {
                characteristic.setValue(data);
            }

        }
    }

    public boolean setCustomFlag(int default_flag, boolean premeal, boolean postmeal, boolean fasting) {
        Log.d("", "---setCustomFlag");
        boolean result = false;
        if (this.mBluetoothGatt != null && this.mCustomTimeCharacteristic != null) {
            byte op_code_1 = -64;
            byte op_code_2 = -31;
            byte pre_byte = 0;
            byte post_byte = 0;
            byte fasting_byte = 0;
            if (premeal) {
                pre_byte = 1;
            }

            if (postmeal) {
                post_byte = 1;
            }

            if (fasting) {
                fasting_byte = 1;
            }

            byte[] data = new byte[]{op_code_1, 2, op_code_2, 1, 5, (byte)default_flag, 1, pre_byte, post_byte, fasting_byte, 0};

            try {
                this.mCustomTimeCharacteristic.setValue(new byte[data.length]);

                for(int i = 0; i < data.length; ++i) {
                    this.mCustomTimeCharacteristic.setValue(data);
                }

                result = this.mBluetoothGatt.writeCharacteristic(this.mCustomTimeCharacteristic);
            } catch (Exception var13) {
                var13.getMessage();
            }

            return result;
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private boolean getCurrentTimeSync() {
        if (this.mBluetoothGatt != null && this.mCurrentTimeCharacteristic != null) {
            this.setCurrentTimeSync(this.mCurrentTimeCharacteristic);
            return this.mBluetoothGatt.writeCharacteristic(this.mCurrentTimeCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private void setCurrentTimeSync(final BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {
            Calendar currCal = new GregorianCalendar();
            byte bCurrYear1 = (byte)(currCal.get(1) & 255);
            byte bCurrYear2 = (byte)(currCal.get(1) >> 8 & 255);
            byte bCurrMonth = (byte)(currCal.get(2) + 1 & 255);
            byte bCurrDay = (byte)(currCal.get(5) & 255);
            byte bCurrHour = (byte)(currCal.get(11) & 255);
            byte bCurrMin = (byte)(currCal.get(12) & 255);
            byte bCurrSec = (byte)(currCal.get(13) & 255);
            byte[] data = new byte[]{bCurrYear1, bCurrYear2, bCurrMonth, bCurrDay, bCurrHour, bCurrMin, bCurrSec, 3, 1, 4};
            characteristic.setValue(new byte[data.length]);

            for(int i = 0; i < data.length; ++i) {
                characteristic.setValue(data);
            }

        }
    }

    private boolean setLocalTimeInfo() {
        if (this.mBluetoothGatt != null && this.mLocalTimeInfoCharacteristic != null) {
            this.setLocalTimeInfoSync(this.mLocalTimeInfoCharacteristic);
            return this.mBluetoothGatt.writeCharacteristic(this.mLocalTimeInfoCharacteristic);
        } else {
            this.mIBLECallback.CallbackError(IBLE_Error.ERROR_GATT_NULL);
            return false;
        }
    }

    private void setLocalTimeInfoSync(final BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {
            Calendar currCal = new GregorianCalendar();
            TimeZone zone = currCal.getTimeZone();
            long offset = (long)(zone.getRawOffset() / 1000 / 60 / 15);
            byte bTimeOffset = (byte)((int)(offset & 255L));
            long dstOffset = (long)(zone.getDSTSavings() / 1000 / 60 / 15);
            byte bDstTimeOffset = (byte)((int)(dstOffset & 255L));
            byte[] data = new byte[]{bTimeOffset, bDstTimeOffset};
            characteristic.setValue(new byte[data.length]);

            for(int i = 0; i < data.length; ++i) {
                characteristic.setValue(data);
            }

        }
    }

    private float bytesToFloat(byte b0, byte b1) {
        return (float)this.unsignedByteToInt(b0) + (float)((this.unsignedByteToInt(b1) & 15) << 8);
    }

    private int unsignedByteToInt(byte b) {
        return b & 255;
    }
}
