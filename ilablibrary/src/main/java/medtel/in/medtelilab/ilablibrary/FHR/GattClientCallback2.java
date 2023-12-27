package medtel.in.medtelilab.ilablibrary.FHR;


import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import medtel.in.medtelilab.ilablibrary.StringUtils;

public class GattClientCallback2 extends BluetoothGattCallback {

    private GattClientActionListenerfhr mClientActionListener;

    public GattClientCallback2(GattClientActionListenerfhr clientActionListener) {
        mClientActionListener = clientActionListener;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        mClientActionListener.logfhr("onConnectionStateChange newState: " + newState);

        if (status == BluetoothGatt.GATT_FAILURE) {
            mClientActionListener.logErrorfhr("Connection Gatt failure status " + status);
            mClientActionListener.disconnectGattServerfhr();
            return;
        } else if (status != BluetoothGatt.GATT_SUCCESS) {
            // handle anything not SUCCESS as failure
            mClientActionListener.logErrorfhr(String.valueOf(status));
            mClientActionListener.disconnectGattServerfhr();
            return;
        }

        if (newState == BluetoothProfile.STATE_CONNECTED) {
            mClientActionListener.logfhr("Connected to device " + gatt.getDevice().getAddress());
            mClientActionListener.setConnectedfhr(true, gatt.getDevice());

            // Discover services
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            mClientActionListener.logfhr("8");
            mClientActionListener.disconnectGattServerfhr();
        }
    }
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);

        // Inside onServicesDiscovered method
        if (status != BluetoothGatt.GATT_SUCCESS) {
            mClientActionListener.logfhr("Device service discovery unsuccessful, status " + status);
            return;
        }

        // Find the Write Characteristic
        BluetoothGattCharacteristic writeCharacteristic =
                BluetoothUtils1.findCharacteristic(gatt, "0000FFF2-0000-1000-8000-00805F9B34FB");

        if (writeCharacteristic != null) {
            mClientActionListener.logfhr("Write Characteristic UUID: " + writeCharacteristic.getUuid());
            byte[] startCommand = { (byte) 0xFF, (byte) 0xFD, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFC };

            // Define the start command structure
           /* byte[] startCommand = new byte[12];
            startCommand[0] = (byte) 0xFF;  // header
            startCommand[1] = (byte) 0xFD;  // header

            // Serial (8 bytes with initial values set to 0x00)
            for (int i = 2; i < 10; i++) {
                startCommand[i] = 0x00;
            }

            // Calculate and set checksum using CRC8Calculator
            startCommand[10] = CRC8Calculator.calculateCRC8(startCommand, 10);

            */

            // Set write type
            writeCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);

            // Write the start command to the characteristic
            writeCharacteristic.setValue(startCommand);

            // Write the characteristic to the device
            boolean writeResult = gatt.writeCharacteristic(writeCharacteristic);

            if (writeResult) {
                mClientActionListener.logfhr("Write command successfully sent.");
            } else {
                mClientActionListener.logErrorfhr("Failed to write command.");
            }

            // Enable notifications for the Notify Characteristic
            BluetoothGattCharacteristic notifyCharacteristic =
                    BluetoothUtils1.findCharacteristic(gatt, "0000FFE4-0000-1000-8000-00805F9B34FB");

            if (notifyCharacteristic != null) {
                enableCharacteristicNotification(gatt, notifyCharacteristic);
            } else {
                mClientActionListener.logErrorfhr("Notify Characteristic not found");
                mClientActionListener.disconnectGattServerfhr();
            }
        } else {
            mClientActionListener.logErrorfhr("Write Characteristic not found");
            mClientActionListener.disconnectGattServerfhr();
        }
    }

 /*   @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
// Inside onServicesDiscovered method


        if (status != BluetoothGatt.GATT_SUCCESS) {
            mClientActionListener.log("Device service discovery unsuccessful, status " + status);
            return;
        }

        // Find the Write Characteristic
        BluetoothGattCharacteristic writeCharacteristic =
                BluetoothUtils1.findCharacteristic(gatt, "00005501-D102-11E1-9B23-00025B00A5A5");
        if (writeCharacteristic != null) {
            mClientActionListener.log("Write Characteristic UUID: " + writeCharacteristic.getUuid());
        }
        if (writeCharacteristic != null) {
            // Set write type
            writeCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);

            // Enable notifications for the Notify Characteristic
            BluetoothGattCharacteristic notifyCharacteristic =
                    BluetoothUtils1.findCharacteristic(gatt, "00005502-D102-11E1-9B23-00025B00A5A5");

            if (notifyCharacteristic != null) {
                enableCharacteristicNotification(gatt, notifyCharacteristic);
            } else {
                mClientActionListener.logError("Notify Characteristic not found");
                mClientActionListener.disconnectGattServer();
            }
        } else {
            mClientActionListener.logError("Write Characteristic not found");
            mClientActionListener.disconnectGattServer();
        }
    }*/

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        mClientActionListener.logfhr("Characteristic changed, " + characteristic.getUuid().toString());
        readCharacteristic(characteristic);
    }

    // Add other necessary methods...

    private void enableCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        boolean characteristicWriteSuccess = gatt.setCharacteristicNotification(characteristic, true);
        if (characteristicWriteSuccess) {
            mClientActionListener.logfhr("Characteristic notification set successfully for " + characteristic.getUuid().toString());
        } else {
            mClientActionListener.logErrorfhr("Characteristic notification set failure for " + characteristic.getUuid().toString());
            mClientActionListener.disconnectGattServerfhr();
        }
    }

    private void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        byte[] messageBytes = characteristic.getValue();
        mClientActionListener.logfhr("Read: " + StringUtils.byteArrayInHexFormat(messageBytes));
        String message = StringUtils.stringFromBytes(messageBytes);
        bytesToHex(messageBytes);
        if (message != null) {
            mClientActionListener.logfhr("Received message: " + message);
            byte[] byteArray = new byte[0];
            try {
                byteArray = message.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : byteArray) {
                hexStringBuilder.append(String.format("%02X ", b));
            }
            Log.d("Tag", "Hex representation: " + hexStringBuilder.toString());
            //mClientActionListener.showToast(hexStringBuilder.toString());
        } else {
            mClientActionListener.logErrorfhr("Unable to convert bytes to string");
        }
    }

    private  String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }

        mClientActionListener.showToastfhr(hexStringBuilder.toString());
        return hexStringBuilder.toString();
    }
}
