package com.example.monica.testfinalprojecttwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.monica.testfinalprojecttwo.R;
import com.example.monica.testfinalprojecttwo.models.Trailers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by monica on 4/14/2017.
 */

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private List<Trailers> mTrailerList;
    private Context context;
    private TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String key);
    }
    public TrailerAdapter(Context mContext,TrailerAdapterOnClickHandler trailerAdapterOnClickHandler)
    {

        this.context=mContext;
        this.mClickHandler=trailerAdapterOnClickHandler;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {



        public final ImageView mTrailerImageView;


        public TrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerImageView=(ImageView)view.findViewById(R.id.trailer_imageView);
            mTrailerImageView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            int Position = getAdapterPosition();
            Trailers trailer=mTrailerList.get(Position);
            String key=trailer.getKey();
            mClickHandler.onClick(key);
        }

    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycle_view_trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        Trailers trailer=mTrailerList.get(position);
        Picasso.with(context).load(String.format("http://img.youtube.com/vi/%s/0.jpg",trailer.getKey())).into(holder.mTrailerImageView);


    }


    @Override
    public int getItemCount() {
        if (null == mTrailerList) return 0;
        return mTrailerList.size();
    }


    public void setTrailerData(List<Trailers> trailers) {
        mTrailerList=trailers;
        notifyDataSetChanged();
    }
}
