package com.example.celebritydb_week2wknd;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.Locale;

import static com.example.celebritydb_week2wknd.DatabaseContract.TABLE_NAME;
import static com.example.celebritydb_week2wknd.DatabaseContract.FIELD_NAME;
import static com.example.celebritydb_week2wknd.CelebrityProviderContract.CONTENT_AUTHORITY;
import static com.example.celebritydb_week2wknd.CelebrityProviderContract.PATH_CELEBRITY;

public class CelebrityProvider extends ContentProvider {
    private DatabaseHelper dbHelper;
    public static final UriMatcher uriMatcher = buildUriMatcher();
    public static final int CELEBRITY = 100;
    public static final int CELEBRITY_ITEM = 101;

    public static UriMatcher buildUriMatcher() {
        // if the URI doesn't match, automatically return No Match
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // matches to the whole db
        matcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY, CELEBRITY);

        // matches to a specific record
        matcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY + "/#", CELEBRITY_ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor retCursor = null;
        switch (uriMatcher.match(uri)){
            case CELEBRITY:
                retCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CELEBRITY_ITEM:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(TABLE_NAME, projection, CelebrityProviderContract.CelebrityEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)}, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CELEBRITY:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_TYPE;
            case CELEBRITY_ITEM:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        long _id;
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case CELEBRITY:
                _id = writableDatabase.insert(TABLE_NAME, null, values);
                // greater than 0, means it did do insert
                if(_id > 0) {
                    returnUri = CelebrityProviderContract.CelebrityEntry.buildCelebrityUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // can take one string item or multiple strings (of the title of the music the user wants deleted).
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        // the "Where %s = " can also be included in the database contract instead of here
        int rowsAffected = writableDatabase.delete
                (TABLE_NAME, String.format(Locale.US, "WHERE %s = ", FIELD_NAME), selectionArgs);
        if(rowsAffected > 0) {
            // if there were rows affected, notify resolver of the requesting application
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();

        int rowsAffected = writableDatabase.update
                (TABLE_NAME, values, String.format(Locale.US, "WHERE %s = ", FIELD_NAME),
                        selectionArgs);
        if(rowsAffected > 0) {
            // if there were rows affected, notify resolver of the requesting application
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }
}
