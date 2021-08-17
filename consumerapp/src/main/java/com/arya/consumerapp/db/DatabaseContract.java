package com.arya.consumerapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.arya.githubuserapp";
    private static final String SCHEME = "content";

    public static final String TABLE_NAME = "favorite_user";

    public static final class FavoriteUserColumns implements BaseColumns {
        static String LOGIN = "login";
        static String NAME = "name";
        static String LOCATION = "location";
        static String COMPANY = "company";
        static String AVATAR_URL = "avatar_url";
        static String FOLLOWERS = "followers";
        static String FOLLOWING = "following";
        static String PUBLIC_REPOS = "public_repos";

        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
