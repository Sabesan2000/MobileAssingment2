package com.example.assin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;

import java.util.List;

public class Database extends SQLiteOpenHelper {


    public Database(Context context) {
        super(context, "samosa.db", null, 1);
    }

        //Insert the user data to the database and reutrns a true statement if the databas e
    public Boolean insertuserdata(String longitude, String latitude, String place) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("longitude", longitude);
        contentValues.put("latitude", latitude);
        contentValues.put("place", place);
        long result=DB.insert("samosa", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }



    //removedata method is used to remove the most recent addition of entry in the database.
    public Boolean removedata (String longitude)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from samosa where longitude = ?", new String[]{longitude});
        if (cursor.getCount() > 0) {
            long result = DB.delete("samosa", "longitude=?", new String[]{longitude});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table samosa(longitude TEXT primary key , latitude TEXT, place TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists samosa");


    }
    //The getDtat method is used to view the current data.
    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from samosa", null);
        return cursor;

    }
}


