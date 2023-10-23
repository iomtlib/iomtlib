package medtel.in.medtelilab.ilablibrary.FHR.comm;


import medtel.in.medtelilab.ilablibrary.FHR.data.BleDevice;

public interface Observable {

    void addObserver(Observer obj);

    void deleteObserver(Observer obj);

    void notifyObserver(BleDevice bleDevice);
}
