package medtel.in.medtelilab.ilablibrary.iBLE;


public class IBLE_GlucoseRecord {
    public int sequenceNumber = 0;
    public long time = 0L;
    public double glucoseData = 0.0D;
    public int flag_cs = 0;
    public int flag_hilow = 0;
    public int flag_context = 0;
    public int flag_meal = 0;
    public int flag_fasting = 0;
    public int flag_ketone = 0;
    public int flag_nomark = 0;
    public int timeoffset = 0;

    public IBLE_GlucoseRecord() {
    }
}
