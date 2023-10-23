package medtel.in.medtelilab.ilablibraryview.iBLE;


import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class IBLE_ScannerServiceParser {
    public static final UUID BLE_SERVICE_GLUCOSE = UUID.fromString("00001808-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_SERVICE_CUSTOM_TIME_MC = UUID.fromString("11223344-5566-7788-9900-AABBCCDDEEFF");
    private static final int FLAGS_BIT = 1;
    private static final int SERVICES_MORE_AVAILABLE_16_BIT = 2;
    private static final int SERVICES_COMPLETE_LIST_16_BIT = 3;
    private static final int SERVICES_MORE_AVAILABLE_32_BIT = 4;
    private static final int SERVICES_COMPLETE_LIST_32_BIT = 5;
    private static final int SERVICES_MORE_AVAILABLE_128_BIT = 6;
    private static final int SERVICES_COMPLETE_LIST_128_BIT = 7;
    private static final int SHORTENED_LOCAL_NAME = 8;
    private static final int COMPLETE_LOCAL_NAME = 9;
    private static final byte LE_LIMITED_DISCOVERABLE_MODE = 1;
    private static final byte LE_GENERAL_DISCOVERABLE_MODE = 2;
    private static IBLE_ScannerServiceParser mParserInstance;
    private int packetLength = 0;
    private boolean mIsValidSensor = false;
    private String mRequiredUUID;

    public IBLE_ScannerServiceParser() {
    }

    public static synchronized IBLE_ScannerServiceParser getParser() {
        if (mParserInstance == null) {
            mParserInstance = new IBLE_ScannerServiceParser();
        }

        return mParserInstance;
    }

    public static boolean decodeDeviceAdvData(byte[] data) {
        UUID[] filterUUID = new UUID[]{BLE_SERVICE_CUSTOM_TIME_MC, BLE_SERVICE_GLUCOSE};
        if (data == null) {
            return false;
        } else {
            boolean connectible = false;
            boolean valid = false;
            if (connectible && valid) {
                return true;
            } else {
                int packetLength = data.length;

                for(int index = 0; index < packetLength; ++index) {
                    int fieldLength = data[index];
                    if (fieldLength == 0) {
                        return connectible && valid;
                    }

                    ++index;
                    int fieldName = data[index];

                    for(int uuidCount = 0; uuidCount < filterUUID.length; ++uuidCount) {
                        String uuid = filterUUID != null ? filterUUID[uuidCount].toString() : null;
                        if (uuid != null) {
                            int i;
                            if (fieldName != 2 && fieldName != 3) {
                                if (fieldName != 4 && fieldName != 5) {
                                    if (fieldName == 6 || fieldName == 7) {
                                        for(i = index + 1; i < index + fieldLength - 1; i += 16) {
                                            valid = valid || decodeService128BitUUID(uuid, data, i, 16);
                                        }
                                    }
                                } else {
                                    for(i = index + 1; i < index + fieldLength - 1; i += 4) {
                                        valid = valid || decodeService32BitUUID(uuid, data, i, 4);
                                    }
                                }
                            } else {
                                for(i = index + 1; i < index + fieldLength - 1; i += 2) {
                                    valid = valid || decodeService16BitUUID(uuid, data, i, 2);
                                }
                            }
                        }
                    }

                    if (!connectible && fieldName == 1) {
                        int flags = data[index + 1];
                        connectible = (flags & 3) > 0;
                    }

                    index += fieldLength - 1;
                }

                return connectible && valid;
            }
        }
    }

    public static String decodeDeviceName(byte[] data) {
        String name = null;
        int packetLength = data.length;

        for(int index = 0; index < packetLength; ++index) {
            int fieldLength = data[index];
            if (fieldLength == 0) {
                break;
            }

            ++index;
            int fieldName = data[index];
            if (fieldName == 9 || fieldName == 8) {
                name = decodeLocalName(data, index + 1, fieldLength - 1);
                break;
            }

            index += fieldLength - 1;
        }

        return name;
    }

    public static String decodeLocalName(byte[] data, int start, int length) {
        try {
            return new String(data, start, length, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            return null;
        } catch (IndexOutOfBoundsException var5) {
            return null;
        }
    }

    private static boolean decodeService16BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        String serviceUUID = Integer.toHexString(decodeUuid16(data, startPosition));
        String requiredUUID = uuid.substring(4, 8);
        return serviceUUID.equals(requiredUUID);
    }

    private static boolean decodeService32BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        String serviceUUID = Integer.toHexString(decodeUuid16(data, startPosition + serviceDataLength - 4));
        String requiredUUID = uuid.substring(4, 8);
        return serviceUUID.equals(requiredUUID);
    }

    private static boolean decodeService128BitUUID(String uuid, byte[] data, int startPosition, int serviceDataLength) {
        String serviceUUID = Integer.toHexString(decodeUuid16(data, startPosition + serviceDataLength - 4));
        String requiredUUID = uuid.substring(4, 8);
        return serviceUUID.equals(requiredUUID);
    }

    private static int decodeUuid16(final byte[] data, final int start) {
        int b1 = data[start] & 255;
        int b2 = data[start + 1] & 255;
        return b2 << 8 | b1;
    }

    public boolean isValidSensor() {
        return this.mIsValidSensor;
    }
}
