package medtel.in.medtelilab.ilablibrary;


import java.util.UUID;

public class Constants {

    // public static String SERVICE_STRING = "7D2EA28A-F7BD-485A-BD9D-92AD6ECFE93E";
    //my device
    public static String SERVICE_STRING  = "D973F2E0-B19E-11E2-9E96-0800200C9A66";

    public static UUID SERVICE_UUID = UUID.fromString(SERVICE_STRING);
    //public static String CHARACTERISTIC_ECHO_STRING = "7D2EBAAD-F7BD-485A-BD9D-92AD6ECFE93E";
    //my device rx
    public static String CHARACTERISTIC_ECHO_STRING = "D973F2E2-B19E-11E2-9E96-0800200C9A66";
    public static UUID CHARACTERISTIC_ECHO_UUID = UUID.fromString(CHARACTERISTIC_ECHO_STRING);

    //public static String CHARACTERISTIC_TIME_STRING = "7D2EDEAD-F7BD-485A-BD9D-92AD6ECFE93E";
    //my device tx
    public static String CHARACTERISTIC_TIME_STRING = "D973F2E1-B19E-11E2-9E96-0800200C9A66";
    public static UUID CHARACTERISTIC_TIME_UUID = UUID.fromString(CHARACTERISTIC_TIME_STRING);

    public static String CLIENT_CONFIGURATION_DESCRIPTOR_STRING = "00002902-0000-1000-8000-00805f9b34fb";
    public static UUID CLIENT_CONFIGURATION_DESCRIPTOR_UUID = UUID.fromString(CLIENT_CONFIGURATION_DESCRIPTOR_STRING);

    public static final String CLIENT_CONFIGURATION_DESCRIPTOR_SHORT_ID = "2902";

    public static final long SCAN_PERIOD = 5000;

    public final static String READ_CHARACTERISTICS_UUID = "0000fff4-0000-1000-8000-00805f9b34fb";
    public final static String WRITE_CHARACTERISTICS_UUID = "0000fff5-0000-1000-8000-00805f9b34fb";
    public final static String NOTIFICATION_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";

    public final static String BLE_COMMAND_BT09 = "BT:9";
    public final static String BLE_COMMAND_BT00 = "BT:0";
    public final static String BLE_COMMAND_BT01 = "BT:1";
    public final static String BLE_COMMAND_BT02 = "BT:2";
    public final static String BLE_COMMAND_TIME_SYNC = "TIME_SYNC";
    public final static String BLE_COMMAND_CLEAR = "CLEAR";

    public final static String BLE_HEADING_DATA = "FA5AF1F2FA5AF3F4";
    public final static String BLE_ENDING_DATA = "F5A5F5F6F5A5F7F8";
    public final static String BLE_EMPTY_DATA = "FFFFFFFFFFFFFFFF";

    public final static String TYPE_BPM = "BPM";
    public final static String TYPE_BGM = "BGM";

    public final static String BLOOD_PRESSURE_DEVICE_ID = "HL158HC";
    public final static String BLOOD_PRESSURE_DEVICE_ID_NEW = "SFBPBLE";
    public final static String BLOOD_GLUCOSE_DEVICE_ID = "HL568HC";
    public final static String BLOOD_GLUCOSE_DEVICE_ID_NEW = "SFBGBLE";
    public final static String BLOOD_GLUCOSE_SYNC_DEVICE_ID = "Sync";
    public final static String ECG_DEVICE_ID = "Checkme";

    public final static String BLOOD_PRESSURE_DEVICE_ID_FULL = "HL158HCBLE";
    public final static String BLOOD_GLUCOSE_DEVICE_ID_FULL = "HL568HCBLE";

    public final static String BLE_STATUS_NONE = "None";
    public final static String BLE_STATUS_CONNECTED = "Connected";

    public final static long TIME_OUT_TIME = 3000;
    public final static long SCAN_TIME_OUT_TIME = 5000;
    public final static String DATE_TIME_FORMAT_LOCAL = "dd-MM-yyyy hh:mm a";

    public static int REQUEST_CHECK_LOCATION_SETTINGS = 999;
    public static final UUID  UUID_SERVICE_DATA                 = UUID.fromString("49535343-fe7d-4ae5-8fa9-9fafd205e455");
    public static final UUID       UUID_CHARACTER_RECEIVE       = UUID.fromString("49535343-1e4d-4bd9-ba61-23c647249616");
    public static final UUID       UUID_MODIFY_BT_NAME          = UUID.fromString("00005343-0000-1000-8000-00805F9B34FB");

    public static final UUID UUID_CLIENT_CHARACTER_CONFIG       = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
  //public static final String DEVICEURL="https://5f5be514-8387-4c91-9a2f-868ee4744c94.mock.pstmn.io/medteldevicelist";

    public static final String scanfinalvalues="10202331";
    public static final String fhrfinalvalue="10202331";
    public static final String bpfinalvalue="01202430";
    public static final String hgfinalvalue="01202430";
    public static final String weightfinalvalue="01202430";
    public static final String glucosefinalvalue="01202430";
    public static final String valuesid=null;

}
