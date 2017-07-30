package com.example.monica.testfinalprojecttwo.Loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.monica.testfinalprojecttwo.Utility.NetworkFunctions;
import com.example.monica.testfinalprojecttwo.models.Review;

import java.util.List;

/**
 * Created by monica on 4/17/2017.
 */

public class reviewLoaderClass implements LoaderManager.LoaderCallbacks<List<Review>> {

    private onReviewsDeliver reviewsDeliver;
    private Context mContext;
    private long movieID;

    public reviewLoaderClass(onReviewsDeliver reviews,long movieID,Context context) {

        this.movieID=movieID;
        this.reviewsDeliver=reviews;
        this.mContext=context;
    }

    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<List<Review>>(mContext) {
            @Override
            public List<Review> loadInBackground() {

                try {
                    NetworkFunctions networkFunctions = new NetworkFunctions(mContext);
                    return networkFunctions.ReviewStream(movieID);
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
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {

        reviewsDeliver.onReviewsDeliver(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {

    }
    public interface onReviewsDeliver {
        void  onReviewsDeliver(List<Review> reviews);
    }

}

