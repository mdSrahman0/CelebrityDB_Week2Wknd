package com.example.celebritydb_week2wknd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.example.celebritydb_week2wknd.DatabaseContract.DATABASE_NAME;
import static com.example.celebritydb_week2wknd.DatabaseContract.DATABASE_VERSION;
import static com.example.celebritydb_week2wknd.DatabaseContract.TABLE_NAME;
import static com.example.celebritydb_week2wknd.DatabaseContract.FIELD_NAME;
import static com.example.celebritydb_week2wknd.DatabaseContract.FIELD_AGE;
import static com.example.celebritydb_week2wknd.DatabaseContract.FIELD_PROFESSION;
import static com.example.celebritydb_week2wknd.DatabaseContract.whereClauseForUpdate;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        // factory is set to null because we're not using a custom cursor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertCelebrity(Celebrity celebToInsert){
        // create content value which holds key value pairs, key being the column in the db and
        // value being the value associated with that column.

        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, celebToInsert.getName());
        contentValues.put(FIELD_AGE, celebToInsert.getAge());
        contentValues.put(FIELD_PROFESSION, celebToInsert.getProfession());

        // Get a writable database object
        SQLiteDatabase writableDatabase = this.getWritableDatabase();

        // insert into the db
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    // update an existing celebrity
    public void updateCelebrity(Celebrity celeb) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, celeb.getName());
        contentValues.put(FIELD_AGE, celeb.getAge());
        contentValues.put(FIELD_PROFESSION, celeb.getProfession());

        writableDatabase.update(TABLE_NAME, contentValues, whereClauseForUpdate(celeb.getName()), null);
        writableDatabase.close();
    }


    // return a list of all the celebrities in the db.
    public ArrayList<Celebrity> queryForAllCelebrities() {
        ArrayList<Celebrity> returnCelebsList = null; // hold all the celebrities we receive from db
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(DatabaseContract.getSelectedAllCelebritiesItem(), null);

        // check if we get something back from the query. While we do (meaning we still have items
        // in the db), add each object to our list
        if(cursor.moveToFirst()) {
            returnCelebsList = new ArrayList<>();
            do {
                Celebrity returnCelebs = null; // an empty object
                String celebName = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                String celebAge = cursor.getString(cursor.getColumnIndex(FIELD_AGE));
                String celebProfession = cursor.getString(cursor.getColumnIndex(FIELD_PROFESSION));

                // create the object
                returnCelebs = new Celebrity(celebName, celebAge, celebProfession);

                // add the object into our arraylist
                returnCelebsList.add(returnCelebs);
            } while(cursor.moveToFirst());
        }
        readableDatabase.close();
        cursor.close();
        return returnCelebsList;  // return the now populated arraylist
    }

    public void deleteCelebrity(String name){
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, whereClauseForUpdate(name), null);
        writableDatabase.close();
    }
}
