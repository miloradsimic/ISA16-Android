package com.restaurant.milorad.isa_proj_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;

public class DatabaseController extends DatabaseHandler {

    ZctLogger mLogger = new ZctLogger(DatabaseController.class.getSimpleName(), BuildConfig.DEBUG);

    public DatabaseController(Context context) {
        super(context);
    }

    /**
     * Create user in database and take its ID. If user already exist just return its ID, else add user to DB.
     *
     * @param userEmail User Id
     * @return User ID, if user is already added to the DB than return its id
     */
    public long createUser(String userEmail) {
        long userId;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + "=?";
        String[] selectionArgs = new String[]{userEmail};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userId = cursor.getInt(0);
            mLogger.d("User [" + userEmail + "] already exists in DB with ID [" + userId + "]");
        } else {
            ContentValues values = new ContentValues();
            values.put(USER_EMAIL, userEmail);
            userId = db.insert(TABLE_USERS, null, values);
            mLogger.d("User [" + userEmail + "] added to DB and it has ID [" + userId + "]");
        }

        cursor.close();
        db.close();

        return userId;
    }



}
