package com.example.monica.testfinalprojecttwo.Loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.monica.testfinalprojecttwo.Utility.NetworkFunctions;
import com.example.monica.testfinalprojecttwo.models.Trailers;

import java.util.List;

/**
 * Created by monica on 4/17/2017.
 */

public class trailerLoaderClass implements LoaderManager.LoaderCallbacks<List<Trailers>> {

    private onTrailersDeliver trailersDeliver;
    private Context mContext;
    private long movieID;

    public trailerLoaderClass(onTrailersDeliver trailersDeliver,long movieID,Context context) {

        this.movieID=movieID;
        this.trailersDeliver=trailersDeliver;
        this.mContext=context;
    }

    @Override
    public Loader<List<Trailers>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<List<Trailers>>(mContext) {
            @Override
            public List<Trailers> loadInBackground() {

                try {
                    NetworkFunctions networkFunctions = new NetworkFunctions(mContext);
                    return networkFunctions.TrailersSream(movieID);
                }
                  catch (Exception e)
                    {
                        e.printStackTrace();
                        return null;
                    }


            }

        };
    }

    @Override
    public void onLoadFinished(Loader<List<Trailers>> loader, List<Trailers> data) {
        trailersDeliver.onTrailersDeliver(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Trailers>> loader) {

    }


    public interface onTrailersDeliver {
        void  onTrailersDeliver(List<Trailers> trailers);
    }

}
