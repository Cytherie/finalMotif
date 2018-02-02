package org.gandroid.motif;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "MOTIF";
    public static final String DATABASE_NAME="userdata.db";
    public static final String TABLE_NAME="periodcycles";
    public static final String COL1= "ID";
    public static final String COL2 = "LASTPERIOD";
    public static final String COL3 = "CYCLEDAYS";
    public static final String COL4 = "DATEENDED";


    public static final String COLID = "ID";
    public static final String COLPRANGE = "PERIODRANGE";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE IF NOT EXISTS "
                +TABLE_NAME+"("+COL1
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL2+ " VARCHAR(20),"
                +COL3+" VARCHAR (20),"
                +COL4+" VARCHAR (20))";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS periodcycles");
        onCreate(sqLiteDatabase);
    }


    public void updatePeriodrange(String newcyclerange, int id){
        SQLiteDatabase dbase = this.getWritableDatabase();
        String query = "UPDATE userinfo SET PERIODRANGE= '"+newcyclerange+"' WHERE ID="+id;

        dbase.execSQL(query);
    }

    public void updateFields(String newcycledays,String newdateended, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+" SET "+COL3+
                "='"+newcycledays+"',"+COL4+"='"+newdateended+ "' WHERE "+COL1 +"='"+id+"'";

        Log.d(TAG,"Tried to update fields:"+query);
        Log.d(TAG,"Updated to new cycledays: "+newcycledays+"And date ended."+newdateended);

        db.execSQL(query);
    }

    public boolean addData(String dbhLASTPERIOD,String dbhCYCLEDAYS,String dbhDATEENDED){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,dbhLASTPERIOD);
        contentValues.put(COL3,dbhCYCLEDAYS);
        contentValues.put(COL4,dbhDATEENDED);

        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result== -1){
            return  false;
        }else {
            return  true;
        }
    }

    public Cursor getCycleEvents(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor DataList = sqLiteDatabase.rawQuery("SELECT * FROM eventlist",null);
        return  DataList;

    }

    public Cursor getListContents(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM periodcycles",null);
        return data;
    }
}
