package com.example.monica.testfinalprojecttwo.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.monica.testfinalprojecttwo.Data.MovieContract.*;


/**
 * Created by monica on 4/11/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movieDb.db";
    private static final int DATABASE_VERSION = 50;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE, " +
                MovieEntry.COLUMN_BACKDROP + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TITLE  + " TEXT NOT NULL);";


        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS movie");
        onCreate(sqLiteDatabase);
    }
}
