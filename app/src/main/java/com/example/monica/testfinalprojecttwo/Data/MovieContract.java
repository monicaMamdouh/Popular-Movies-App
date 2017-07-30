package com.example.monica.testfinalprojecttwo.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by monica on 4/11/2017.
 */

public class MovieContract {


    public static final String CONTENT_AUTHORITY = "com.example.monica.testfinalprojecttwo";
    public static final Uri BASE_CONTENT_URI =  Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_RATING= "voteRate";
        public static final String COLUMN_POSTER= "Poster";
        public static final String COLUMN_BACKDROP= "backDrop";

        public static Uri buildWeatherUriWithDate(long movieID) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(movieID))
                    .build();
        }


    }
}
