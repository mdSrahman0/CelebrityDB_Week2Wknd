package com.example.celebritydb_week2wknd;

import java.util.Locale;

public class DatabaseContract {
    public static final String DATABASE_NAME = "db_name";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "celebrity_table";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_PROFESSION = "profession";

    public static String getCreateTableStatement(){
        return String.format(Locale.US,
                "CREATE TABLE %s(%s TEXT PRIMARY_KEY, %s TEXT, %s TEXT)",
                TABLE_NAME, FIELD_NAME, FIELD_AGE, FIELD_PROFESSION);
    }

    // get all the celebrities
    public static String getSelectedAllCelebritiesItem() {
        return String.format(Locale.US, "SELECT * FROM %s", TABLE_NAME);
    }

    public static String whereClauseForUpdate(String name) {
        return String.format(Locale.US, "WHERE %s = %s", FIELD_NAME);
    }

    //



}
