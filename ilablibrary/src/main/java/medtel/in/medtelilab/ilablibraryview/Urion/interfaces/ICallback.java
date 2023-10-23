package medtel.in.medtelilab.ilablibraryview.Urion.interfaces;


import medtel.in.medtelilab.ilablibraryview.Urion.urionbean.IBean;
import medtel.in.medtelilab.ilablibraryview.Urion.urionbean.Msg;

public interface ICallback {
	public void onReceive(IBean bean);

	public void onMessage(Msg message);

	public void onError(Error error);
}
