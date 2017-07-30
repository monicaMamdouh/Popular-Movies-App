package com.example.monica.testfinalprojecttwo;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMovieDetailFragment extends Fragment {




    @BindView(R.id.backDrop) ImageView mDropImageView;
    @BindView(R.id.movie_poster) ImageView mPosterImageView;
    @BindView(R.id.movie_overview) TextView mOverViewTextView;
    @BindView(R.id.movie_title) TextView mTitleTextView;
    @BindView(R.id.movie_release_date) TextView mDateTextView;
    @BindView(R.id.movie_rate) TextView mRateTextView;

    private Unbinder unbinder;
    int idMovie;
    int cursorPosition;
    Cursor cursor;

    public FavouriteMovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_favourite, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        String[] ids=new String[]{String.valueOf(321612)};
        cursorPosition=getActivity().getIntent().getIntExtra("cursorPosition",0);



        try {
            cursor= getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        cursor.moveToPosition(cursorPosition);
        int dropIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP);
        int overviewIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int posterIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);
        int dateIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int rateIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);
        int titleIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int idMovieIndex=cursor.getColumnIndex(MovieContract.MovieEntry._ID);

        String drop=cursor.getString(dropIndex);
        String overview=cursor.getString(overviewIndex);
        String poster=cursor.getString(posterIndex);
        String date=cursor.getString(dateIndex);
        String rate=cursor.getString(rateIndex);
        String title=cursor.getString(titleIndex);
        idMovie=cursor.getInt(idMovieIndex);

        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w640/" + drop).placeholder(R.drawable.error).error(R.drawable.icon).noFade().into(mDropImageView);
        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w320/" + poster).placeholder(R.drawable.error).error(R.drawable.icon).noFade().into(mPosterImageView);
        mOverViewTextView.setText(overview);
        mTitleTextView.setText(title);
        mDateTextView.setText(date);
        mRateTextView.setText(rate);



        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
