package medtel.in.medtelilab.ilablibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DeviceTable extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "devicetable.db";
    public static final String TABLE_NAMEFHR = "fhr_devicelist";
    public static final String TABLE_NAMEBP = "bp_devicelist";
    public static final String TABLE_NAMEHG = "hg_devicelist";
    public static final String TABLE_NAMEWEIGHT = "weight_devicelist";
    public static final String TABLE_NAMEGLUCOSE="glucose_devicelist";
    public static final String DEVICE_NAME="DEVICE_NAME";
    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String  RSSI="RSSI";
    public static final String  DEVICETYPE="DEVICETYPE";
    public static final String DEVICESTATUS="DEVICESTATUS";
    public static final String DEVICEMETHOD="DEVICEMETHOD";
    public static final String DEVICEDATE="DEVICEDATE";


    public  static final String MEDTELFHRTABLE="medtelfhrtable";
    public static final  String MEDTELBPTABLE="medtelbptable";
    public static final  String MEDTELHGTABLE="medtelhgtable";
    public static final  String MEDTELWEIGHTTABLE="medtelweighttable";
    public static  final String MEDTELGLUCOSETABLE="medtelglucosetable";

    public static final String  SCANTABLE="scantable";
    public DeviceTable(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAMEFHR + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT,DEVICESTATUS TEXT,DEVICEMETHOD TEXT,DEVICEDATE TEXT)");
        db.execSQL("create table " + TABLE_NAMEBP + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT,DEVICESTATUS TEXT,DEVICEMETHOD TEXT,DEVICEDATE TEXT)");
        db.execSQL("create table " + TABLE_NAMEHG + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT,DEVICESTATUS TEXT,DEVICEMETHOD TEXT,DEVICEDATE TEXT)");
        db.execSQL("create table " + TABLE_NAMEWEIGHT + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT,DEVICESTATUS TEXT,DEVICEMETHOD TEXT,DEVICEDATE TEXT)");
        db.execSQL("create table " + TABLE_NAMEGLUCOSE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT,DEVICESTATUS TEXT,DEVICEMETHOD TEXT,DEVICEDATE TEXT)");


        db.execSQL("create table " + MEDTELFHRTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT)");
        db.execSQL("create table " + MEDTELBPTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT)");
        db.execSQL("create table " + MEDTELHGTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT)");
        db.execSQL("create table " + MEDTELWEIGHTTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT)");
        db.execSQL("create table " + MEDTELGLUCOSETABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT, DEVICE_ID TEXT)");

        db.execSQL("create table " + SCANTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DEVICE_NAME TEXT,DEVICE_ID TEXT,RSSI TEXT,DEVICETYPE TEXT)");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Updating table from " + oldVersion + " to " + newVersion);
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEFHR);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEBP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEHG);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEWEIGHT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEGLUCOSE);
            db.execSQL("DROP TABLE IF EXISTS " + MEDTELFHRTABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEDTELBPTABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEDTELHGTABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEDTELWEIGHTTABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEDTELGLUCOSETABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SCANTABLE);


        }


        onCreate(db);
    }



    public boolean insertfhr(String devicename, String deviceid,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(DEVICESTATUS,devicestatus);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
       /* if (devicemethod.equals("2")) {
            LocalDate currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDate = LocalDate.now();
                LocalDate dateIn40Days = currentDate.plusDays(45);

                contentValues.put(DEVICEDATE, dateIn40Days.toString());
            }

// Add 40 days to the current date

        }else
        {
            contentValues.put(DEVICEDATE, "");
        }*/

        long result = db.insert(TABLE_NAMEFHR, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertbp(String devicename, String deviceid,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(DEVICESTATUS,devicestatus);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        long result = db.insert(TABLE_NAMEBP, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean inserthg(String devicename, String deviceid,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(DEVICESTATUS,devicestatus);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        long result = db.insert(TABLE_NAMEHG, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertweight(String devicename, String deviceid,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(DEVICESTATUS,devicestatus);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        long result = db.insert(TABLE_NAMEWEIGHT, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertglucose(String devicename, String deviceid,String  devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(DEVICESTATUS,devicestatus);
        contentValues.put(DEVICEMETHOD,devicemethod);

        contentValues.put(DEVICEDATE,"2024-01-30");

        long result = db.insert(TABLE_NAMEGLUCOSE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public boolean updatefhr(String deviceid,String devicename,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);
        contentValues.put(DEVICE_NAME,devicename);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");

        db.update(TABLE_NAMEFHR, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updatebp(String deviceid,String devicename,String devicestatus,String  devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);
        contentValues.put(DEVICE_NAME,devicename);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");

        db.update(TABLE_NAMEBP, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updateHG(String deviceid,String devicename,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);
        contentValues.put(DEVICE_NAME,devicename);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        db.update(TABLE_NAMEHG, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updateweight(String deviceid,String devicename,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);
        contentValues.put(DEVICE_NAME,devicename);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        db.update(TABLE_NAMEWEIGHT, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updateglucose(String deviceid,String devicename,String devicestatus,String devicemethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);
        contentValues.put(DEVICE_NAME,devicename);
        contentValues.put(DEVICEMETHOD,devicemethod);
        contentValues.put(DEVICEDATE,"2024-01-30");
        db.update(TABLE_NAMEGLUCOSE, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }



    public boolean updatefhraddress(String deviceid,String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);



        db.update(TABLE_NAMEFHR, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updatebpaddress(String deviceid,String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);



        db.update(TABLE_NAMEBP, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updatehgaddress(String deviceid,String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);



        db.update(TABLE_NAMEHG, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }
    public boolean updateweightaddress(String deviceid,String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);



        db.update(TABLE_NAMEWEIGHT, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }

    public boolean updateglucoseaddress(String deviceid,String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(ID, id);

        contentValues.put(DEVICE_ID, deviceid);



        db.update(TABLE_NAMEGLUCOSE, contentValues, "DEVICESTATUS = ?", new String[]{devicestatus});
        return true;
    }
    public boolean insertmedtelfhr(String devicename, String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);

        long result = db.insert(MEDTELFHRTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertmetelbp(String devicename, String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);

        long result = db.insert(MEDTELBPTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertmedtelhg(String devicename, String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);

        long result = db.insert(MEDTELHGTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertmedtelweight(String devicename, String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);

        long result = db.insert(MEDTELWEIGHTTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertmedtelglucose(String devicename, String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);

        long result = db.insert(MEDTELGLUCOSETABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertscandevices(String devicename, String deviceid,String rssi,String devicetype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEVICE_NAME, devicename);
        contentValues.put(DEVICE_ID,deviceid);
        contentValues.put(RSSI,rssi);
        contentValues.put(DEVICETYPE,devicetype);

        long result = db.insert(SCANTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataFhrlist(String deviceid,String devicestatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'fhr_devicelist' Where DEVICEID=" + '"' + deviceid + '"' + " and DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }

    public Cursor getAllDataBplist(String deviceid,String devicestatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'bp_devicelist' Where DEVICEID=" + '"' + deviceid + '"' + " and DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }
    public Cursor getAllDataHglist(String deviceid,String devicestatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'hg_devicelist' Where DEVICEID=" + '"' + deviceid + '"' + " and DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }
    public Cursor getAllDataWeightlist(String deviceid,String devicestatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'weight_devicelist' Where DEVICEID=" + '"' + deviceid + '"' + " and DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }
    public Cursor getAllDataGlucoselist(String deviceid,String devicestatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'glucose_devicelist' Where DEVICEID=" + '"' + deviceid + '"' + " and DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }





    public Cursor getAllDataFHR(String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'fhr_devicelist' Where DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
     //   Cursor res = db.rawQuery("select * from " + TABLE_NAMEFHR, null);
        return res;
    }


    public Cursor getAllDataBP(String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'bp_devicelist' Where DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }
    public Cursor getAllDataHG(String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'hg_devicelist' Where DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }
    public Cursor getAllDataWeight(String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'weight_devicelist' Where DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }

    public Cursor getAllDataGlucose(String devicestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'glucose_devicelist' Where DEVICESTATUS=" + '"' + devicestatus + '"' + "", null);
        return res;
    }


    public Cursor getAllDataFHRDeviceid(String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'fhr_devicelist' Where DEVICE_ID=" + '"' + deviceid + '"' + "", null);
        //   Cursor res = db.rawQuery("select * from " + TABLE_NAMEFHR, null);
        return res;
    }


    public Cursor getAllDataBPDeviceid(String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'bp_devicelist' Where DEVICE_ID=" + '"' + deviceid + '"' + "", null);
        return res;
    }
    public Cursor getAllDataHgDeviceid(String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'hg_devicelist' Where DEVICE_ID=" + '"' + deviceid + '"' + "", null);
        return res;
    }
    public Cursor getAllDataWeightDeviceid(String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'weight_devicelist' Where DEVICE_ID=" + '"' + deviceid + '"' + "", null);
        return res;
    }

    public Cursor getAllDataGlucoseDeviceid(String deviceid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM 'glucose_devicelist' Where DEVICE_ID=" + '"' + deviceid + '"' + "", null);
        return res;
    }

    public Cursor getAllDataFHR() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAMEFHR, null);
        return res;
    }


    public Cursor getAllDataBP() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAMEBP, null);
        return res;
    }
    public Cursor getAllDataHG() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAMEHG, null);
        return res;
    }
    public Cursor getAllDataWeight() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAMEWEIGHT, null);
        return res;
    }


    public Cursor getAllDataGlucose() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAMEGLUCOSE, null);
        return res;
    }

    public Cursor getAllDataMedtelFHR() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MEDTELFHRTABLE, null);
        return res;
    }


    public Cursor getAllDataMedtelBP() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MEDTELBPTABLE, null);
        return res;
    }
    public Cursor getAllMedtelDataHG() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MEDTELHGTABLE, null);
        return res;
    }
    public Cursor getAllMedtelDataWeight() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MEDTELWEIGHTTABLE, null);
        return res;
    }


    public Cursor getAllMedtelDataGlucose() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MEDTELGLUCOSETABLE, null);
        return res;
    }
    public void deletetable(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, null, null);
        //   db.execSQL("create table " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_ID TEXT, USER_MODE TEXT,ILAB_CLIENTLOGO TEXT,ILAB_CLIENTBACKGROUND TEXT,ILAB_WELCOMETEXT TEXT,ILAB_PRIVACYPOLICY TEXT,OFFLINEPATIENTSTRING TEXT)");
        db.close();
    }

    public void deleteMedtelFHR() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELFHRTABLE, null, null);
        db.close();
    }
    public void deleteMedtelBpTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELBPTABLE, null, null);
        db.close();
    }
    public void deleteMedtelHGTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELHGTABLE, null, null);
        db.close();
    }
    public void deleteMedtelweightTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELWEIGHTTABLE, null, null);
        db.close();
    }
    public void deleteMedtelglucoseTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELGLUCOSETABLE, null, null);
        db.close();
    }
    public Cursor getAllScanDevices() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + SCANTABLE, null);
        return res;
    }
    public void deleteScanTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDTELGLUCOSETABLE, null, null);
        db.close();
    }

       public List<String> getTableColumns(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> columns = new ArrayList<String>();
        String cmd = "pragma table_info(" + tableName + ");";
        Cursor cur = db.rawQuery(cmd, null);

        while (cur.moveToNext()) {
            columns.add(cur.getString(cur.getColumnIndex("name")));
        }
        cur.close();
        return columns;
    }

}

