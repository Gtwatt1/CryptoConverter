package com.example.zone3.cryptocoinconverter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zone3.cryptocoinconverter.Model.CryptoConver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gtwatt on 10/30/17.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "CryptoConverManager";

    private static final String TABLE_CRYPTOCONV = "cryptoC";

    private static final String KEY_ID = "id";
    private static final String KEY_Crypto = "crypto";
    private static final String KEY_Currency = "currency";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CRYPTOCONV + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Crypto + " TEXT,"
                + KEY_Currency + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRYPTOCONV);

        onCreate(db);
    }

    public void addCryptoConv(CryptoConver cc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Crypto, cc.getCryptoType().toString());
        values.put(KEY_Currency, cc.getCurrency().toString());

        db.insert(TABLE_CRYPTOCONV, null, values);
        db.close();
        Log.d("Database handler", "addCryptoConv: Success" );
        Log.d("Database handler", "addCryptoConv: " + cc.getCryptoType());

    }

    public List<CryptoConver> getAllCryptoConv() {
        List<CryptoConver> cryptoConvers = new ArrayList<CryptoConver>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CRYPTOCONV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CryptoConver cc = new CryptoConver();
//                cc.setID(Integer.parseInt(cursor.getString(0)));
                cc.setCryptoType(cursor.getString(1));
                cc.setCurrency(cursor.getString(2));
                cryptoConvers.add(cc);
            } while (cursor.moveToNext());
        }
        Log.d("Database", "getAllCryptoConv: " + cryptoConvers.toString());
        return cryptoConvers;
    }

    public void deleteCryptoConver(CryptoConver cc) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CRYPTOCONV, KEY_ID + " = ?",
                new String[] { String.valueOf(cc.getId()) });
        db.close();
    }
}