package medtel.in.medtelilab.ilablibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import medtel.in.medtelilab.ilablibrary.FHR.BleManager;
import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MedtelScan extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    public static Boolean scanmethod = false;

    public static ArrayList<String> medteldevicelist = new ArrayList<>();
    public static ArrayList<String> deviceidlist = new ArrayList<>();
    public static ArrayList<String> devicenamelist = new ArrayList<>();
    public static ArrayList<String> devicerssilist = new ArrayList<>();
    public static ArrayList<String> devicetypelist = new ArrayList<>();
    public static ArrayList<BluetoothData> mMacIdList = new ArrayList<>();
    public static ArrayList<BluetoothData> MedtelmMacIdList = new ArrayList<>();
    public static BleDevice bledevice;
    public static boolean isinternet = false;
    public static String jsonstringlist = "";
    public static String bluetoothdeviceaddress = "";
    public static String libversion="";
    private static final int PERMISSION_REQUEST_CODE = 123;
    private LocationManager locationManager;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String json="",fhrdevicename="",fhrdeviceaddress="",bpdevicename="",bpdeviceaddress="",hbdevicename="",hbdeviceaddress="",weightdeviceaddress="",weightdevicename="",glucosedeviceaddres="",glucosedevicename="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    public static ArrayList<String> selectdevicenamelist = new ArrayList<>();
    public static ArrayList<String> selectdeviceaddresslist = new ArrayList<>();
    public static Context mContext;
    RelativeLayout customtoast;
    LinearLayout devicelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_name);

        if (isNetworkConnected(this)) {
            mContext=this;
            isinternet = true;
        } else {
            mContext=this;
            isinternet = false;
        }

        selectdevicenamelist.clear();
        selectdeviceaddresslist.clear();

    }
    public void initalize(Activity activity)
    {
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
       jsonArray=new JSONArray();
        jsonObject=new JSONObject();
        // Check for location permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation("","");
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
    public void devicelist(Activity activity) {

        String packageName = getPackageName();
       Log.d("Package",packageName);
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("arr_version", "2");


        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(Constants.DEVICEURL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle request failure
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    System.out.println("jsonstring" + jsonString);
                    medteldevicelist.clear();
                    MedtelmMacIdList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray jsonArrayfhr = jsonObject.getJSONArray("fhr_devices");
                        JSONArray jsonArraybp = jsonObject.getJSONArray("bp_devices");
                        JSONArray jsonArrayhg = jsonObject.getJSONArray("hemo_devices");
                        JSONArray jsonArrayweight = jsonObject.getJSONArray("weight_devices");
                        JSONArray jsonArrayglucose = jsonObject.getJSONArray("glucose_devices");
                        for (int i = 0; i < jsonArrayfhr.length(); ++i) {

                            JSONObject itemObj = jsonArrayfhr.getJSONObject(i);

                            String deviceid = itemObj.getString("device_id");
                            String devicename = itemObj.getString("device_name");
                            System.out.println("deviceid" + deviceid + "||" + devicename);
                            if (!medteldevicelist.contains(deviceid)) {
                                medteldevicelist.add(deviceid);
                                if (devicename.contains("Keyar Mini"))
                                {
                                    MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "6"));
                                }else {
                                    MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "5"));
                                }
                            }

                        }
                        for (int i = 0; i < jsonArraybp.length(); ++i) {

                            JSONObject itemObj = jsonArraybp.getJSONObject(i);

                            String deviceid = itemObj.getString("device_id");
                            String devicename = itemObj.getString("device_name");
                            System.out.println("deviceid" + deviceid);
                            if (!medteldevicelist.contains(deviceid)) {
                                medteldevicelist.add(deviceid);
                                MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "2"));

                            }

                        }
                        for (int i = 0; i < jsonArrayhg.length(); ++i) {

                            JSONObject itemObj = jsonArrayhg.getJSONObject(i);

                            String deviceid = itemObj.getString("device_id");
                            String devicename = itemObj.getString("device_name");
                            System.out.println("deviceid" + deviceid);
                            if (!medteldevicelist.contains(deviceid)) {
                                medteldevicelist.add(deviceid);
                                MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "3"));

                            }

                        }
                        for (int i = 0; i < jsonArrayweight.length(); ++i) {

                            JSONObject itemObj = jsonArrayweight.getJSONObject(i);

                            String deviceid = itemObj.getString("device_id");
                            String devicename = itemObj.getString("device_name");
                            System.out.println("deviceid" + deviceid);
                            if (!medteldevicelist.contains(deviceid)) {
                                medteldevicelist.add(deviceid);
                                MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "1"));

                            }

                        }
                        for (int i = 0; i < jsonArrayglucose.length(); ++i) {

                            JSONObject itemObj = jsonArrayglucose.getJSONObject(i);

                            String deviceid = itemObj.getString("device_id");
                            String devicename = itemObj.getString("device_name");
                            System.out.println("glucosedeviceid" + deviceid);

                            if (!medteldevicelist.contains(deviceid)) {
                                medteldevicelist.add(deviceid);
                                MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "4"));


                            }

                        }

                        System.out.println("medteldevicelist" + medteldevicelist.size());
                        System.out.println("devicelistsmac" + medteldevicelist.size() + "||fhr" + jsonArrayfhr.length() + "||" + "bp" + jsonArraybp.length() + "||" + "hg" + jsonArrayhg.length() + "||" + "weight" + jsonArrayweight.length());
                        //  startScan();

                        // Access the values from the JSONObject
                        // Handle the retrieved JSON data
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"Please upgrade library",Toast.LENGTH_SHORT).show();

                        }
                    });
                        // Handle request failure
                }
            }
        });

    }

    public static void printlist() {
        System.out.println("jsonstring" + jsonstringlist);
    }

    public void scan() {

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);

    }

    public void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifyTitle)
                            .setMessage(R.string.gpsNotifyMsg)
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
                    //   setScanRule();
                    startScan();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void startScanmethod(Boolean isinternet, Activity activity) {

        if (isinternet == true) {

            devicelist(activity);
        } else {
            medteldevicelist.clear();
            MedtelmMacIdList.clear();
            String fileName = "devicelist.json";
            String jsonString = loadJsonFromAsset(activity, fileName);
            System.out.println("devicelist" + jsonString);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArrayfhr = jsonObject.getJSONArray("fhr_devices");
                JSONArray jsonArraybp = jsonObject.getJSONArray("bp_devices");
                JSONArray jsonArrayhg = jsonObject.getJSONArray("hemo_devices");
                JSONArray jsonArrayweight = jsonObject.getJSONArray("weight_devices");
                JSONArray jsonArrayglucose = jsonObject.getJSONArray("glucose_devices");
                for (int i = 0; i < jsonArrayfhr.length(); ++i) {

                    JSONObject itemObj = jsonArrayfhr.getJSONObject(i);

                    String deviceid = itemObj.getString("device_id");
                    String devicename = itemObj.getString("device_name");
                    System.out.println("deviceid" + deviceid + "||" + devicename);
                    if (!medteldevicelist.contains(deviceid)) {
                        medteldevicelist.add(deviceid);
                        MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "5"));

                    }
                }
                for (int i = 0; i < jsonArraybp.length(); ++i) {

                    JSONObject itemObj = jsonArraybp.getJSONObject(i);

                    String deviceid = itemObj.getString("device_id");
                    String devicename = itemObj.getString("device_name");
                    System.out.println("deviceid" + deviceid);
                    if (!medteldevicelist.contains(deviceid)) {
                        medteldevicelist.add(deviceid);
                        MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "2"));

                    }

                }
                for (int i = 0; i < jsonArrayhg.length(); ++i) {

                    JSONObject itemObj = jsonArrayhg.getJSONObject(i);

                    String deviceid = itemObj.getString("device_id");
                    String devicename = itemObj.getString("device_name");
                    System.out.println("deviceid" + deviceid);
                    if (!medteldevicelist.contains(deviceid)) {
                        medteldevicelist.add(deviceid);
                        MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "3"));

                    }

                }
                for (int i = 0; i < jsonArrayweight.length(); ++i) {

                    JSONObject itemObj = jsonArrayweight.getJSONObject(i);

                    String deviceid = itemObj.getString("device_id");
                    String devicename = itemObj.getString("device_name");
                    System.out.println("deviceid" + deviceid);
                    if (!medteldevicelist.contains(deviceid)) {
                        medteldevicelist.add(deviceid);
                        MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "1"));

                    }

                }
                for (int i = 0; i < jsonArrayglucose.length(); ++i) {

                    JSONObject itemObj = jsonArrayglucose.getJSONObject(i);

                    String deviceid = itemObj.getString("device_id");
                    String devicename = itemObj.getString("device_name");
                    System.out.println("deviceid" + deviceid);

                    if (!medteldevicelist.contains(deviceid)) {
                        medteldevicelist.add(deviceid);
                        MedtelmMacIdList.add(new BluetoothData(devicename, deviceid, "", bledevice, "4"));


                    }

                }
                System.out.println("medteldevicelist" + medteldevicelist.size());
                System.out.println("devicelistsmac" + medteldevicelist.size() + "||fhr" + jsonArrayfhr.length() + "||" + "bp" + jsonArraybp.length() + "||" + "hg" + jsonArrayhg.length() + "||" + "weight" + jsonArrayweight.length());
                //  startScan();

                // Access the values from the JSONObject
                // Handle the retrieved JSON data
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing error
            }
        }
    }

    public  void startScan() {
        deviceidlist.clear();
        devicenamelist.clear();
        devicerssilist.clear();
        devicetypelist.clear();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();


        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                // Extract the relevant information from the scan result
                String deviceName = result.getDevice().getName();
                String deviceAddress = result.getDevice().getAddress();
                int rssi = result.getRssi();
                BluetoothDevice devicetypes = bluetoothAdapter.getRemoteDevice(deviceAddress);

                int deviceType = devicetypes.getBluetoothClass().getDeviceClass();
                System.out.println("devicetypes" + deviceType + "||" + deviceName + "||" + deviceAddress + "||" + medteldevicelist.size());

                jsonstringlist = String.valueOf(deviceType);
                if (medteldevicelist.contains(result.getDevice().getAddress())) {

                    if (result.getDevice().getName() != null) {
                        if (result.getDevice().getName().contains("VCOMIN")) {

                            if (!deviceidlist.contains(result.getDevice().getAddress())) {
                                deviceidlist.add(result.getDevice().getAddress());
                                devicenamelist.add(result.getDevice().getName());
                                devicerssilist.add(String.valueOf(rssi));
                                devicetypelist.add("5");
                                mMacIdList.add(new BluetoothData(result.getDevice().getName(), result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "5"));
                            }
                        }

                        if (result.getDevice().getName().contains("MD1"))
                        {
                            if (!deviceidlist.contains(result.getDevice().getAddress())) {
                                deviceidlist.add(result.getDevice().getAddress());
                                devicenamelist.add("FHR-AD51");
                                devicerssilist.add(String.valueOf(rssi));
                                devicetypelist.add("6");
                                mMacIdList.add(new BluetoothData("FHR-AD51", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "6"));
                            }
                        }
                        if (result.getDevice().getName().contains("Bluetooth BP") || result.getDevice().getName().contains("Wileless BP") || result.getDevice().getName().contains("Urion BP") || result.getDevice().getName().contains("BLE to UART_2") || result.getDevice().getName().contains("Bluetooth BP")) {

                            if (!deviceidlist.contains(result.getDevice().getAddress())) {
                                deviceidlist.add(result.getDevice().getAddress());
                                devicenamelist.add(result.getDevice().getName());
                                devicerssilist.add(String.valueOf(rssi));
                                devicetypelist.add("2");
                                mMacIdList.add(new BluetoothData(result.getDevice().getName(), result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "2"));
                            }
                        }
                        if (result.getDevice().getName().contains("THB_")) {
                            if (!deviceidlist.contains(result.getDevice().getAddress())) {
                                deviceidlist.add(result.getDevice().getAddress());
                                devicenamelist.add(result.getDevice().getName());
                                devicerssilist.add(String.valueOf(rssi));
                                devicetypelist.add("3");
                                mMacIdList.add(new BluetoothData(result.getDevice().getName(), result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "3"));
                            }
                        }

                        if (result.getDevice().getName().contains("CareSens")) {
                            if (!deviceidlist.contains(result.getDevice().getAddress())) {
                                deviceidlist.add(result.getDevice().getAddress());
                                devicenamelist.add(result.getDevice().getName());
                                devicerssilist.add(String.valueOf(rssi));
                                devicetypelist.add("4");
                                mMacIdList.add(new BluetoothData(result.getDevice().getName(), result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "4"));
                            }
                        }



                    } else {

                        if (!deviceidlist.contains(result.getDevice().getAddress())) {


                            for (BluetoothData person : MedtelmMacIdList) {
                                if (person.getAddress().equals(result.getDevice().getAddress())) {
                                    System.out.println("devicetype" + person.getDevicetype());
                                    if (person.getDevicetype().toString().equals("1")) {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("scale");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("1");
                                        mMacIdList.add(new BluetoothData("scale", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "1"));

                                    } else if (person.getDevicetype().toString().equals("2")) {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("Bluetooth BP");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("2");
                                        mMacIdList.add(new BluetoothData("Bluetooth BP", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "2"));
                                    } else if (person.getDevicetype().toString().equals("3")) {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("THB");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("3");
                                        mMacIdList.add(new BluetoothData("THB", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "3"));
                                    } else if (person.getDevicetype().toString().equals("5")) {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("VCOMIN");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("5");
                                        mMacIdList.add(new BluetoothData("VCOMIN", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "5"));
                                    }else if (person.getDevicetype().toString().equals("6")) {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("Keyar Mini");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("6");
                                        mMacIdList.add(new BluetoothData("Keyar Mini", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "5"));
                                    }else if (person.getDevicetype().toString().equals("4"))
                                    {
                                        deviceidlist.add(result.getDevice().getAddress());
                                        devicenamelist.add("CareSens");
                                        devicerssilist.add(String.valueOf(rssi));
                                        devicetypelist.add("4");
                                        mMacIdList.add(new BluetoothData("CareSens", result.getDevice().getAddress(), String.valueOf(rssi), bledevice, "4"));
                                    }
                                    break;
                                }
                            }

                        }
                    }

                }


            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                // Handle scan failure, if needed
                Log.e("ScanFailed", "Error code: " + errorCode);
            }
        };

        // Start scanning
        bluetoothLeScanner.startScan(scanCallback);
        long scanDuration =10000; // 10 seconds
        Handler scanHandler = new Handler();
        BluetoothLeScanner finalBluetoothLeScanner = bluetoothLeScanner;
        scanHandler.postDelayed(() -> {
            if (finalBluetoothLeScanner != null && scanCallback != null) {
                finalBluetoothLeScanner.stopScan(scanCallback);
                scanmethod=true;
            }
        }, scanDuration);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                //   setScanRule();
                startScan();
            }
        }
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String loadJsonFromAsset(Context context, String fileName) {
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void Glucosedeviceperformrefresh(String devicename,String deviceaddress)

    {


        getLocation(devicename,deviceaddress);

    }

    public void getLocation(String devicename, String deviceaddress) {
        if (locationManager != null) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (lastKnownLocation != null) {
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();
                Log.d("Location",String.valueOf(latitude)+String.valueOf(longitude));
                jsonObject=new JSONObject();
                jsonArray=new JSONArray();
                try {

                    jsonObject.put("device_type",devicename);
                    jsonObject.put("device_address",deviceaddress);
                    jsonObject.put("latitude", String.valueOf(latitude));
                    jsonObject.put("longitude", String.valueOf(longitude));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!deviceaddress.isEmpty() || !deviceaddress.equals("")) {
                    jsonArray.put(jsonObject);
                }
                devicelistsend(latitude, longitude);

                // Use latitude and longitude
                // Do something with latitude and longitude values
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation("","");
            } else {
                // Handle permission denied
            }
        }
    }

    public void devicelistsend(double latitude, double longitude) {

        OkHttpClient client = new OkHttpClient();
        Log.d("JsonObject",jsonArray.toString());
        if (jsonArray.length()!=0) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            formBuilder.add("raw", jsonArray.toString());


            RequestBody requestBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(Constants.DEVICEURLSUCCESS)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    // Handle request failure
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        String jsonString = response.body().string();
                        System.out.println("jsonstring" + jsonString);
                        Log.d("Success3", jsonString + "||" + requestBody.toString());

                    } else {
                        // Handle request failure
                    }
                }
            });
        }
    }



}
