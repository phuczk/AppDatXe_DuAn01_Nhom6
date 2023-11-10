package com.sonans.appdatxe_duan01_nhom6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String Table_Name = "KhachHangN";
    public static final String Id_Col = "id";
    public static final String Name_Col = "nameKH";
    public static final String Phone_Col = "phone";
    public static final String UserName_Col = "userName";
    public static final String Password_Col = "password";


    public static final String type = "type";

    public DbHelper(Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_Name + "( " + Id_Col + " TEXT, "
                + Name_Col + " TEXT, "
                + Phone_Col + " TEXT, " + UserName_Col +" TEXT, " +
                Password_Col +" TEXT) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
            onCreate(db);
        }
    }
}
