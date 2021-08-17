package com.arya.githubuserapp.db;

import static android.provider.BaseColumns._ID;

import static com.arya.githubuserapp.db.DatabaseContract.FavoriteUserColumns.LOGIN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static SQLiteDatabase database;

    private static UserHelper INSTANCE;

    private UserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void  close() {
        dataBaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryByLogin(String login) {
        return database.query(
                DATABASE_TABLE,
                null,
                LOGIN + " = ?",
                new String[]{login},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteByLogin(String login) {
        return database.delete(DATABASE_TABLE,
                LOGIN + " = ?",
                new String[]{login});
    }
}
