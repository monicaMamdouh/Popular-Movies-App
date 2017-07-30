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
import com.squareup.picasso.Picasso;



/**
 * Created by monica on 4/22/2017.
 */

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder> {

    private Context context;
    private Cursor cursor;
    int position;

    private FavouriteMovieAdapterClickHandler mClickHandler;

    public FavouriteMovieAdapter(Context mContext, FavouriteMovieAdapter.FavouriteMovieAdapterClickHandler onClick) {
        this.context=mContext;
        this.mClickHandler=onClick;

    }

    @Override
    public FavouriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycle_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavouriteMovieAdapter.FavouriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteMovieViewHolder holder, int position) {

        cursor.moveToPosition(position);
        int posterIndex=cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);
        String poster=cursor.getString(posterIndex);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w320/"+poster).into(holder.mPosImageView);

        //add tag
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (cursor == c) {
            return null; // bc nothing has changed
        }
        Cursor tempCusror = cursor;
        this.cursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return tempCusror;
    }


    public class FavouriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mPosImageView;
        public FavouriteMovieViewHolder(View view)
        {
            super(view);
            mPosImageView=(ImageView)view.findViewById(R.id.poster);
            mPosImageView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            position = getAdapterPosition();

            mClickHandler.onClick(position);

        }
    }

    public interface FavouriteMovieAdapterClickHandler
    {
        void onClick(int position);
    }

    public void setFavMoviesData(Cursor mCursor) {

        if (cursor == mCursor)
        {
            return;
        }
        Cursor temp = mCursor;
        cursor=mCursor;
        notifyDataSetChanged();



    }



}