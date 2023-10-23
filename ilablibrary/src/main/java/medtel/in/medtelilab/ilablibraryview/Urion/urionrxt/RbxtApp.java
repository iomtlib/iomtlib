package medtel.in.medtelilab.ilablibraryview.Urion.urionrxt;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import medtel.in.medtelilab.ilablibraryview.Urion.interfaces.ICallback;


public class RbxtApp extends Application {

	public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;

	private ICallback call;

	public void onCreate() {
		super.onCreate();

		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			builder.detectFileUriExposure();
		}
	}


	public ICallback getCall() {
		return call;
	}

	public void setCall(ICallback call) {
		this.call = call;
	}
}
