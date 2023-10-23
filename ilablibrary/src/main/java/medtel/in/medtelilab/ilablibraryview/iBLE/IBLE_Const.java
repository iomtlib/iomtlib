package medtel.in.medtelilab.ilablibraryview.iBLE;


import java.util.UUID;

public class IBLE_Const {
    public static final double GlucoseUnitConversionMultiplier = 18.016D;
    public static final double KetoneMultiplier = 10.0D;
    public static final UUID BLE_SERVICE_GLUCOSE = UUID.fromString("00001808-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_SERVICE_DEVICE_INFO = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_SERVICE_CUSTOM_TIME = UUID.fromString("0000FFF0-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_SERVICE_CUSTOM_TIME_NEW = UUID.fromString("C4DEA010-5A9D-11E9-8647-D663BD873D93");
    public static final UUID BLE_SERVICE_CURRENT_TIME = UUID.fromString("00001805-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_GLUCOSE_MEASUREMENT = UUID.fromString("00002A18-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_GLUCOSE_CONTEXT = UUID.fromString("00002A34-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_DEVICE_INFO_SERIALNO = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_DEVICE_INFO_SOFTWARE_REVISION = UUID.fromString("00002A28-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_RACP = UUID.fromString("00002A52-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_CUSTOM_TIME = UUID.fromString("0000FFF1-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_CUSTOM_TIME_NEW = UUID.fromString("C4DEA3BC-5A9D-11E9-8647-D663BD873D93");
    public static final UUID BLE_CHAR_CURRENT_TIME = UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_CHAR_LOCAL_TIME_INFO = UUID.fromString("00002a0f-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    public IBLE_Const() {
    }
}
