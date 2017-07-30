package com.example.monica.testfinalprojecttwo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;
import com.example.monica.testfinalprojecttwo.R;
import com.example.monica.testfinalprojecttwo.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by monica on 4/14/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private List<Movie> mMoviesData;
    private Context context;
    private MovieAdapterOnClickHandler mClickHandler;
    int Position ;


    public interface MovieAdapterOnClickHandler {
       void onClick(Movie movie,int position);
    }



    public MovieAdapter(Context mContext,MovieAdapterOnClickHandler clickHandler) {
        this.context=mContext;
        this.mClickHandler=clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {


        public final ImageView mPosterImageView;


        public MovieAdapterViewHolder(View view) {
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.poster);
            mPosterImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Position = getAdapterPosition();
            Movie movie=mMoviesData.get(Position);
            mClickHandler.onClick(movie,Position);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycle_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieAdapterViewHolder holder, int position) {
        Movie movie =mMoviesData.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w320/"+movie.getPoster()).into(holder.mPosterImageView);


    }


    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.size();
    }


    public void setMoviesData(List<Movie> movieData) {


        mMoviesData=movieData;

        notifyDataSetChanged();
    }





}