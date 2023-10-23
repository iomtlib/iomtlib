package medtel.in.medtelilab.ilablibrary.Urion.interfaces;


import medtel.in.medtelilab.ilablibrary.Urion.urionbean.IBean;
import medtel.in.medtelilab.ilablibrary.Urion.urionbean.Msg;

public interface ICallback {
	public void onReceive(IBean bean);

	public void onMessage(Msg message);

	public void onError(Error error);
}
