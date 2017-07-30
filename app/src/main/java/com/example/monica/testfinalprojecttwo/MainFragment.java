package com.example.monica.testfinalprojecttwo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monica.testfinalprojecttwo.Loaders.moviesLoaderClass;
import com.example.monica.testfinalprojecttwo.adapters.MovieAdapter;
import com.example.monica.testfinalprojecttwo.models.Movie;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler, moviesLoaderClass.onMoviesDeliver {



    MovieAdapter movieAdapter;
    String SortingPref;
    private static final int MOVIE_LOADER_ID = 0;
    RecyclerView mRecyclerView;


    public MainFragment() {
        // Required empty public constructor


        // Set a Toolbar to replace the ActionBar.
      // T toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);



        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }



        movieAdapter = new MovieAdapter(getActivity(), this);
        mRecyclerView.setAdapter(movieAdapter);



        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        SortingPref = sharedPrefs.getString("sort","popular");
        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, new moviesLoaderClass(this,SortingPref,getActivity())).forceLoad();
        return rootView;
    }



    @Override
    public void onMoviesDeliver(List<Movie> Movies)
    {
        movieAdapter.setMoviesData(Movies);

    }

    @Override
    public void onClick(Movie movie,int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movieObject", movie);
        intent.putExtra("cursorPos",position);
        startActivity(intent);

    }


}

