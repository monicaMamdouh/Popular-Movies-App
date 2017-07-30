package com.example.monica.testfinalprojecttwo.models;

/**
 * Created by monica on 4/14/2017.
 */

public class Review {

    private String author;
    private String reviewContent;

    public Review(String mAuthor, String mReviewContent) {
        author = mAuthor;
        reviewContent = mReviewContent;
    }

    public String getAuthor() {
        return author;
    }


    public String getReview_content() {
        return reviewContent;
    }


}
