package com.example.monica.testfinalprojecttwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monica.testfinalprojecttwo.R;
import com.example.monica.testfinalprojecttwo.models.Review;

import java.util.List;

/**
 * Created by monica on 4/14/2017.
 */

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    private List<Review> mReviewList;
    private Context context;

    public ReviewAdapter(Context mContext)
    {
        this.context=mContext;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {


        public final TextView mAuthorTextView;
        public final TextView mContentTextView;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            mAuthorTextView=(TextView)view.findViewById(R.id.review_author);
            mContentTextView=(TextView)view.findViewById(R.id.review_content);

        }

    }

    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycle_item_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        Review review =mReviewList.get(position);
        holder.mAuthorTextView.setText(review.getAuthor());
        holder.mContentTextView.setText(review.getReview_content());
    }


    @Override
    public int getItemCount() {
        if (null == mReviewList) return 0;
        return mReviewList.size();
    }


    public void setReviewsData(List<Review> reviews) {
        mReviewList=reviews;
        notifyDataSetChanged();
    }
}