package com.openclassrooms.realestatemanager.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PropertyProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.openclassrooms.realestatemanager.contentprovider.PropertyProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/property";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static final String PROPERTY_TABLE_NAME = "property";
    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (!hasPermission()) {
            return null;
        }
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(PROPERTY_TABLE_NAME, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private boolean hasPermission() {
        return true;
    }
}
