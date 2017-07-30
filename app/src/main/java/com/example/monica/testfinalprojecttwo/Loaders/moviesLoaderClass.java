package com.example.monica.testfinalprojecttwo.Loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;
import com.example.monica.testfinalprojecttwo.Utility.NetworkFunctions;
import com.example.monica.testfinalprojecttwo.models.Movie;

import java.util.List;

/**
 * Created by monica on 4/14/2017.
 */

public class moviesLoaderClass implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private onMoviesDeliver moviesDeliver;
    private Context mContext;
    private String sorting;
    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.COLUMN_POSTER
    };
    private List<Movie> movieList;


    public moviesLoaderClass(onMoviesDeliver moviesDeliver, String sorting, Context context) {


        this.moviesDeliver = moviesDeliver;
        this.mContext = context;
        this.sorting = sorting;
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(mContext) {
            @Override
            public List<Movie> loadInBackground() {

                try {
                    NetworkFunctions networkFunctions = new NetworkFunctions(mContext);
                    movieList = networkFunctions.movieNetwork(sorting);
                    return movieList;
                }
                catch (Exception e)
                {
                    return null;
                }
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {

        moviesDeliver.onMoviesDeliver(data);



    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        moviesDeliver.onMoviesDeliver(null);

    }


    public interface onMoviesDeliver {
        void  onMoviesDeliver(List<Movie> Movies);

    }

}
