package com.example.monica.testfinalprojecttwo.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;


/**
 * Created by monica on 4/17/2017.
 */
public class moviesCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private onFavMoviesDeliver onFavMoviesDeliver;
    private Context mContext;


    public moviesCursorLoader(onFavMoviesDeliver moviesDeliver, Context context) {


        this.onFavMoviesDeliver= moviesDeliver;
        this.mContext = context;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new AsyncTaskLoader<Cursor>(mContext) {
            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }

        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       onFavMoviesDeliver.onMoviesDeliver(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        onFavMoviesDeliver.onMoviesDeliver(null);

    }

    public interface onFavMoviesDeliver {
        void  onMoviesDeliver(Cursor cursor);
    }
}


