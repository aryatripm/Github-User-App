package com.arya.githubuserapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.arya.githubuserapp.db.DatabaseContract.FavoriteUserColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbgithubuser";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER," +
                    " %s INTEGER," +
                    " %s INTEGER)",
            DatabaseContract.TABLE_NAME,
            FavoriteUserColumns._ID,
            FavoriteUserColumns.LOGIN,
            FavoriteUserColumns.NAME,
            FavoriteUserColumns.LOCATION,
            FavoriteUserColumns.COMPANY,
            FavoriteUserColumns.AVATAR_URL,
            FavoriteUserColumns.FOLLOWERS,
            FavoriteUserColumns.FOLLOWING,
            FavoriteUserColumns.PUBLIC_REPOS
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
