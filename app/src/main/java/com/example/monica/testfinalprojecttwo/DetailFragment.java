package com.example.monica.testfinalprojecttwo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.monica.testfinalprojecttwo.Loaders.reviewLoaderClass;
import com.example.monica.testfinalprojecttwo.Loaders.trailerLoaderClass;
import com.example.monica.testfinalprojecttwo.adapters.ReviewAdapter;
import com.example.monica.testfinalprojecttwo.adapters.TrailerAdapter;
import com.example.monica.testfinalprojecttwo.models.Movie;
import com.example.monica.testfinalprojecttwo.models.Review;
import com.example.monica.testfinalprojecttwo.models.Trailers;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler,reviewLoaderClass.onReviewsDeliver,trailerLoaderClass.onTrailersDeliver{

    @BindView(R.id.backDrop) ImageView mDropImageView;
    @BindView(R.id.movie_poster) ImageView mPosterImageView;
    @BindView(R.id.movie_overview) TextView mOverViewTextView;
    @BindView(R.id.movie_title) TextView mTitleTextView;
    @BindView(R.id.movie_release_date) TextView mDateTextView;
    @BindView(R.id.movie_rate) TextView mRateTextView;
   // @BindView(R.id.floatingButton) FloatingActionButton floatingActionButton;
    private Unbinder unbinder;

    RecyclerView mRecyclerView;
    ReviewAdapter mReviewAdapter;
    TrailerAdapter mTrailerAdapter;
    RecyclerView mTrailerRecyclerView;

    private static final int REVIEW_LOADER_ID = 1;
    private static final int TRAILER_LOADER_ID = 2;
    long movieID;
    Movie movie;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        movie=new Movie();
        movie=getActivity().getIntent().getParcelableExtra("movieObject");
        movieID=movie.getMovieID();
        String drop =movie.getBackDrop();
        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w640/" + drop).placeholder(R.drawable.error).error(R.drawable.icon).noFade().into(mDropImageView);
        String poster = movie.getPoster();
        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w320/" + poster).placeholder(R.drawable.error).error(R.drawable.icon).noFade().into(mPosterImageView);
        String overview =movie.getOverView();
        mOverViewTextView.setText(overview);
        String title =movie.getTitle();
        mTitleTextView.setText(title);
        String date = movie.getReleaseDate();
        mDateTextView.setText(date);
        String rate = movie.getRating();
        mRateTextView.setText(rate);


        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_review);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mReviewAdapter=new ReviewAdapter(getActivity());
        mRecyclerView.setAdapter(mReviewAdapter);


        mTrailerRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_trailer);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        mTrailerRecyclerView.setLayoutManager(gridLayoutManager);
        mTrailerAdapter=new TrailerAdapter(getActivity(),this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);


        getLoaderManager().initLoader(REVIEW_LOADER_ID, null, new reviewLoaderClass(this,movieID,getActivity())).forceLoad();
        getLoaderManager().initLoader(TRAILER_LOADER_ID, null, new trailerLoaderClass(this,movieID,getActivity())).forceLoad();


        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(String key) {
        String youtube_Path="http://www.youtube.com/watch?v="+key;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_Path)));


    }


    @Override
    public void onReviewsDeliver(List<Review> reviews) {
        mReviewAdapter.setReviewsData(reviews);
    }

    @Override
    public void onTrailersDeliver(List<Trailers> trailers) {
        mTrailerAdapter.setTrailerData(trailers);
    }
}

