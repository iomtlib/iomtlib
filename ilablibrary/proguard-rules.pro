# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.CodeFormat {public *;}
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.CodeFormat { *; }
-keep class medtel.in.medtelilab.ilablibrary.DeviceTable { *; }
-keep class medtel.in.medtelilab.ilablibrary.MedtelScan { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.BleFragmentActivity { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.DeviceListActivity { *; }
-keep class medtel.in.medtelilab.ilablibrary.BMI.Config { *; }
-keep class medtel.in.medtelilab.ilablibrary.BMI.User { *; }
-keep class medtel.in.medtelilab.ilablibrary.BMI.UserConst { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.BleManager { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.BleBluetooth { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.BleConnector { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.MultipleBluetoothController { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.SplitWriter { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleBaseCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleGattCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleIndicateCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleMtuChangedCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleNotifyCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleReadCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleRssiCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanAndConnectCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanPresenterImp { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.callback.BleWriteCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.comm.Observable { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.comm.Observer { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.comm.ObserverManager { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.data.BleMsg { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.data.BleScanState { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.data.BleWriteState { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.exception.BleException { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.exception.ConnectException { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.exception.GattException { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.exception.OtherException { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.exception.TimeoutException { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanner { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanPresenter { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanRuleConfig { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.utils.BleLog { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.utils.BleLruHashMap { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.utils.HexUtil { *; }
-keep class medtel.in.medtelilab.ilablibrary.FHR.BleManager { *; }
-keep class medtel.in.medtelilab.ilablibrary.HB.BluetoothUtils { *; }
-keep class medtel.in.medtelilab.ilablibrary.HB.GattClientActionListener { *; }
-keep class medtel.in.medtelilab.ilablibrary.HB.GattClientCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Manager { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Callback { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Const { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Device { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Error { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_GattCallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_GlucoseRecord { *; }
-keep class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_ScannerServiceParser { *; }
-keep class medtel.in.medtelilab.ilablibrary.BluetoothData { *; }
-keep class medtel.in.medtelilab.ilablibrary.BluetoothLeService { *; }
-keep class medtel.in.medtelilab.ilablibrary.Constants { *; }
-keep class medtel.in.medtelilab.ilablibrary.StringUtils { *; }
-keep class medtel.in.medtelilab.ilablibrary.ReadDevices { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.interfaces.ICallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Data { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Error { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Head { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.IBean { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Msg { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Pressure { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionbean.User { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.Average { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.BleServiceHelper { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.BluetoothConnectActivityReceiver { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.ClsUtils { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.First { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.L { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.MySpinnerButton { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionclass.SampleGattAttributes { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.uriondb.DBOpenHelper { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionrxt.RbxtApp { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionservice.BluetoothLeService { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionservice.ICallback { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.ByteUtil { *; }
-keep class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.CodeFormat { *; }
-keep class medtel.in.medtelilab.ilablibrary.BuildConfig { *; }

-keep public class * extends java.lang.Exception
-dontobfuscate
-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }
-keepclassmembers class **.R$* {
public static <fields>;}
-dontwarn org.**
-dontwarn com.**
-dontwarn java.**
-dontwarn javax.**
-dontwarn sun.**
-keep class android.** { *; }
-keep class org.** { *; }
-keep class java.** { *; }
-keep class javax.** { *; }
-keep class sun.** { *; }
-keep class de.mindpipe.** { *; }
-keep class com.j256.** { *; }
-keep class org.json.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.BleFragmentActivity
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.DeviceListActivity
-keepclassmembers class medtel.in.medtelilab.ilablibrary.BMI.Config
-keepclassmembers class medtel.in.medtelilab.ilablibrary.BMI.User
-keepclassmembers class medtel.in.medtelilab.ilablibrary.BMI.UserConst
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.BleManager
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.BleBluetooth
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.BleConnector
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.MultipleBluetoothController
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.bluetooth.SplitWriter
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleBaseCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleGattCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleIndicateCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleMtuChangedCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleNotifyCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleReadCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleRssiCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanAndConnectCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleScanPresenterImp
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.callback.BleWriteCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.comm.Observable
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.comm.Observer
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.comm.ObserverManager
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.data.BleMsg
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.data.BleScanState
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.data.BleWriteState
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.exception.BleException
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.exception.ConnectException
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.exception.GattException
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.exception.OtherException
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.exception.TimeoutException
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanner
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanPresenter
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.scan.BleScanRuleConfig
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.utils.BleLog
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.utils.BleLruHashMap
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.utils.HexUtil
-keepclassmembers class medtel.in.medtelilab.ilablibrary.FHR.BleManager
-keepclassmembers class medtel.in.medtelilab.ilablibrary.HB.BluetoothUtils
-keepclassmembers class medtel.in.medtelilab.ilablibrary.HB.GattClientActionListener
-keepclassmembers class medtel.in.medtelilab.ilablibrary.HB.GattClientCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Manager
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Callback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Const
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Device
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Error
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_GattCallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_GlucoseRecord
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_Manager
-keepclassmembers class medtel.in.medtelilab.ilablibrary.iBLE.IBLE_ScannerServiceParser
-keepclassmembers class medtel.in.medtelilab.ilablibrary.BluetoothData
-keepclassmembers class medtel.in.medtelilab.ilablibrary.BluetoothLeService
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Constants
-keepclassmembers class medtel.in.medtelilab.ilablibrary.DeviceTable
-keepclassmembers class medtel.in.medtelilab.ilablibrary.ReadDevices
-keepclassmembers class medtel.in.medtelilab.ilablibrary.MedtelScan
-keepclassmembers class medtel.in.medtelilab.ilablibrary.StringUtils
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.DeviceListActivity
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.BleFragmentActivity
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.interfaces.ICallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Data
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Error
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Head
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.IBean
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Msg
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.Pressure
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionbean.User
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.Average
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.BleServiceHelper
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.BluetoothConnectActivityReceiver
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.ClsUtils
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.First
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.L
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.MySpinnerButton
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionclass.SampleGattAttributes
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.uriondb.DBOpenHelper
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionrxt.RbxtApp
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionservice.BluetoothLeService
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionservice.ICallback
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.ByteUtil
-keepclassmembers class medtel.in.medtelilab.ilablibrary.Urion.urionuntil.CodeFormat
-keep class medtel.in.medtelilab.ilablibrary.** {
    *;
}
-keep public class medtel.in.medtelilab.ilablibrary.Constants {
    public protected *;
}
-keep public class medtel.in.medtelilab.ilablibrary.MedtelScan {
    public protected *;
}
-keep public class medtel.in.medtelilab.ilablibrary.DeviceTable {
    public protected *;
}







