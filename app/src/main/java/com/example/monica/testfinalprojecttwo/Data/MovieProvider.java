package com.example.monica.testfinalprojecttwo.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;


/**
 * Created by monica on 4/11/2017.
 */

public class MovieProvider  extends ContentProvider {




    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE, MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }

    private MovieDbHelper mMovieDbHelper;


    @Override
    public boolean onCreate() {

        Context context = getContext();
        mMovieDbHelper = new MovieDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case MOVIES:

                long id = db.insertWithOnConflict(MovieContract.MovieEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_IGNORE);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case MOVIES:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }


    // Implement query to handle requests for data by URI
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            case MOVIES:
                retCursor =  db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted; // starts as 0

        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A movie was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
