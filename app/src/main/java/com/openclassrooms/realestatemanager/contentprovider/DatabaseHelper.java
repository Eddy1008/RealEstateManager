package com.openclassrooms.realestatemanager.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "REMDatabase.db";
    private static final String PROPERTY_TABLE_NAME = "property";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_DB_TABLE =
            " CREATE TABLE " + PROPERTY_TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " title TEXT NOT NULL, " +
            " address TEXT NOT NULL, " +
            " mainPhoto TEXT NOT NULL, " +
            " propertyDescription TEXT NOT NULL, " +
            " onSaleDate TEXT NOT NULL, " +
            " saleDealDate TEXT NOT NULL, " +
            " propertyPrice INTEGER NOT NULL, " +
            " propertySurface INTEGER NOT NULL, " +
            " roomNumber INTEGER NOT NULL, " +
            " bathroomNumber INTEGER NOT NULL, " +
            " bedroomNumber INTEGER NOT NULL, " +
            " propertyTypeId LONG NOT NULL, " +
            " propertySaleStatusId LONG NOT NULL, " +
            " realEstateAgentId LONG NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROPERTY_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
