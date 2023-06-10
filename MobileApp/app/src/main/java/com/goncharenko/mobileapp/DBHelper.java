package com.goncharenko.mobileapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "currenciesDb";
    public static final String TABLE_CURRENCIES = "currencies";

    public static final String KEY_ID = "_id";

    public static final String KEY_FROM = "currency_from";
    public static final String KEY_TO = "currency_to";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_RESULT = "result";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CURRENCIES + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_FROM + " TEXT, "
                + KEY_TO + " TEXT, "
                + KEY_AMOUNT + " DOUBLE, "
                + KEY_RESULT + " DOUBLE"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCIES);
        onCreate(db);
    }
}
