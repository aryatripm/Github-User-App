package com.arya.githubuserapp.provider;

import static com.arya.githubuserapp.db.DatabaseContract.AUTHORITY;
import static com.arya.githubuserapp.db.DatabaseContract.FavoriteUserColumns.CONTENT_URI;
import static com.arya.githubuserapp.db.DatabaseContract.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.arya.githubuserapp.db.UserHelper;

public class GithubUserProvider extends ContentProvider {

    private static final int USER = 1;
    private static final int USER_ID = 2;
    private UserHelper userHelper;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, TABLE_NAME, USER);
        matcher.addURI(AUTHORITY, TABLE_NAME + "/*", USER_ID);
    }

    public GithubUserProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        if (matcher.match(uri) == USER_ID) {
            deleted = userHelper.deleteByLogin(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        if (matcher.match(uri) == USER) {
            added = userHelper.insert(values);
        } else {
            added = 0;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public boolean onCreate() {
        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        if (matcher.match(uri) == USER) {
            cursor = userHelper.queryAll();
        } else if (matcher.match(uri) == USER_ID) {
            cursor = userHelper.queryByLogin(uri.getLastPathSegment());
        } else {
            cursor = null;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        if (matcher.match(uri) == USER_ID) {
            updated = userHelper.update(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }
}