package medtel.in.medtelilab.ilablibraryview.Urion.urionbean;

import android.os.Parcel;

public class Pressure extends IBean {

	private int PressureH;
	private int PressureL;

	public Pressure() {
		super();
	}

	public int getPressure() {
		return PressureH * 256 + PressureL;
	}

	public int getPressureHL() {
		return PressureH + PressureL;
	}

	public int getPressureH() {
		return PressureH;
	}

	public void setPressureH(int pressureH) {
		PressureH = pressureH;
	}

	public int getPressureL() {
		return PressureL;
	}

	public void setPressureL(int pressureL) {
		PressureL = pressureL;
	}

	public void analysis(int[] f) {
		PressureH = f[3];
		PressureL = f[4];
		System.out
				.println("PressureH:" + PressureH + " PressureL:" + PressureL);
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(PressureH);
		dest.writeFloat(PressureL);
	}

	public static final Creator<Pressure> CREATOR = new Creator<Pressure>() {
		public Pressure createFromParcel(Parcel in) {
			return new Pressure(in);
		}

		public Pressure[] newArray(int size) {
			return new Pressure[size];
		}
	};

	private Pressure(Parcel in) {
		PressureH = in.readInt();
		PressureL = in.readInt();
	}
}
