package medtel.in.medtelilab.ilablibraryview.FHR.comm;


import medtel.in.medtelilab.ilablibraryview.FHR.data.BleDevice;

public interface Observable {

    void addObserver(Observer obj);

    void deleteObserver(Observer obj);

    void notifyObserver(BleDevice bleDevice);
}
