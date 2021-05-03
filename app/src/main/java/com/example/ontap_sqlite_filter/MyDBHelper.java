package com.example.ontap_sqlite_filter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    // khai bao csdl, version...
    private static final String DBName = "vetautest1db.db";
    private static final int VERSION = 1;
    // bang va cac truong
    private static final String TABLE_NAME = "VeTau";
    private static final String ID = "_id";
    private static final String GADI = "gadi";
    private static final String GADEN = "gaden";
    private static final String DONGIA = "dongia";
    private static final String KHUHOI = "khuhoi";
    private SQLiteDatabase myDB;

    public MyDBHelper(@Nullable Context context) {
        super(context, DBName, null, VERSION);
    }

    public static String getID() {
        return ID;
    }

    public static String getGADI() {
        return GADI;
    }

    public static String getGADEN() {
        return GADEN;
    }

    public static String getDONGIA() {
        return DONGIA;
    }

    public static String getKHUHOI() {
        return KHUHOI;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // tao bang
        String queryTable = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY autoincrement, " + GADI + " TEXT NOT NULL, " + GADEN + " TEXT NOT NULL," + DONGIA + " FLOAT NOT NULL, " + KHUHOI + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB(){
        myDB = getWritableDatabase();
    }
    public void closeDB(){
        if (myDB!=null && myDB.isOpen()){
            myDB.close();
        }
    }

    // insert
    public long Add( String gadi,String gaden,float dongia, int khuhoi){
        // Insert thong qua ContentValues
        ContentValues values = new ContentValues();
        values.put(GADI,gadi);
        values.put(GADEN, gaden);
        values.put(DONGIA,dongia);
        values.put(KHUHOI, khuhoi);
        return myDB.insert(TABLE_NAME,null, values);
    }
    // update
    public long Update(int id, String gadi,String gaden,float dongia, int khuhoi){
        // Insert thong qua ContentValues
        ContentValues values = new ContentValues();
        values.put(ID,id);
        values.put(GADI,gadi);
        values.put(GADEN, gaden);
        values.put(DONGIA,dongia);
        values.put(KHUHOI, khuhoi);
        String where = ID + " = " + id;
        return myDB.update(TABLE_NAME, values, where, null);
    }
    // delete
    public long Delete(int id){
        String where = ID + " = " + id;
        return myDB.delete(TABLE_NAME, where,null);
    }
    public Cursor GetAllRecord(){
        String query = "SELECT * FROM " + TABLE_NAME;
        return myDB.rawQuery(query, null);
    }

    public ArrayList<VeTau> ListAll(){
        ArrayList<VeTau> lstVeTau = new ArrayList<>();
        Cursor cursor = myDB.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            boolean khuhoi= cursor.getInt(cursor.getColumnIndex(getKHUHOI()))==1?true:false;
            VeTau sv = new VeTau(cursor.getInt(cursor.getColumnIndex(getID())),
                    cursor.getString(cursor.getColumnIndex(getGADI())),
                    cursor.getString(cursor.getColumnIndex(getGADEN())),
                    cursor.getFloat(cursor.getColumnIndex(getDONGIA())),
                    khuhoi);
            lstVeTau.add(sv);
            cursor.moveToNext();
        }
        return lstVeTau;
    }
}