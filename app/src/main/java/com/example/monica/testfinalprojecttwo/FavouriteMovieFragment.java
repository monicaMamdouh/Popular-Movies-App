package com.example.monica.testfinalprojecttwo;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monica.testfinalprojecttwo.Loaders.moviesCursorLoader;
import com.example.monica.testfinalprojecttwo.adapters.FavouriteMovieAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMovieFragment extends Fragment  implements FavouriteMovieAdapter.FavouriteMovieAdapterClickHandler,moviesCursorLoader.onFavMoviesDeliver {


    RecyclerView mRecyclerView;
    FavouriteMovieAdapter favouriteMovieAdapter;
    private static final int MOVIE_LOADER_ID = 4;

    public FavouriteMovieFragment() {
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


        favouriteMovieAdapter = new FavouriteMovieAdapter(getActivity(), this);
        mRecyclerView.setAdapter(favouriteMovieAdapter);
        favouriteMovieAdapter.notifyDataSetChanged();

        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, new moviesCursorLoader(this, getActivity())).forceLoad();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, new moviesCursorLoader(this, getActivity())).forceLoad();


    }




    @Override
    public void onMoviesDeliver(Cursor cursor)
    {
        favouriteMovieAdapter.setFavMoviesData(cursor);

    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(getActivity(), FavouriteMovieDetailActivity.class);
        intent.putExtra("cursorPosition",position);
        startActivity(intent);
    }
}