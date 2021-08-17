package com.arya.githubuserapp.db;

import android.database.Cursor;

import com.arya.githubuserapp.entity.User;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<User> mapCursorToArrayList(Cursor userCursor) {

        ArrayList<User> usersList = new ArrayList<>();

        while (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns._ID));
            String login = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.LOGIN));
            String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.NAME));
            String location = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.LOCATION));
            String company = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.COMPANY));
            String avatar_url = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL));
            int followers = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.FOLLOWERS));
            int following = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.FOLLOWING));
            int public_repos = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.PUBLIC_REPOS));
            usersList.add(new User(login,name,location,company,avatar_url,followers,following,public_repos));
        }

        return usersList;
    }
}
