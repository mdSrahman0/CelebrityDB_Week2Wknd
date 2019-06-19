package com.example.celebritydb_week2wknd;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class CelebrityProviderContract {

    public static final String CONTENT_AUTHORITY = "com.example.celebritydb_week2wknd";
    public static final Uri BASE_CONTENT_ID = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CELEBRITY = "celebrity";

    public static final class CelebrityEntry implements BaseColumns {
        // actually identifies the database
        // this is what other apps (and this one) will use to access the content provider
        public static final Uri CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_CELEBRITY).build();

        // directory (database) search
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CELEBRITY;

        // searching for one item
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CELEBRITY;

        // building the URI in the system so content provider knows what it's looking at (maybe - might not be correct)
        public static Uri buildCelebrityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
