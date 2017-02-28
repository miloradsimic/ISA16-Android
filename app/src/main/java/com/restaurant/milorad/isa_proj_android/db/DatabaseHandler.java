package com.restaurant.milorad.isa_proj_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Milorad on 25/2/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // DATABASE GENERAL INFO
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Restaurant";

    // TABLE NAMES
    public static final String TABLE_USERS = "users";

    // TABLE_USERS
    public static final String USER_ID = "usr_id";
    public static final String USER_EMAIL = "usr_email";
    public static final String[] USERS_COLUMNS = {USER_ID, USER_EMAIL};


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_EMAIL + " TEXT)";

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS  " + TABLE_USERS;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USERS);
        onCreate(db);
    }
}