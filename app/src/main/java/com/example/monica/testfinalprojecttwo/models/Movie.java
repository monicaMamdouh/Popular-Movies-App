package com.example.monica.testfinalprojecttwo.models;

/**
 * Created by monica on 4/14/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;


public class Movie  implements Parcelable{


    private long movieID;
    private String title;
    private String poster;
    private String overView;
    private String rating;
    private String releaseDate;
    private String backDrop;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private int flag;


    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        //write all properties to the parcel
        dest.writeLong(movieID);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(overView);
        dest.writeString(rating);
        dest.writeString(releaseDate);
        dest.writeString(backDrop);
        dest.writeInt(flag);
    }
     public Movie()
     {}
    //constructor used for parcel
    public Movie(Parcel parcel){
        //read and set saved values from parcel
        movieID=parcel.readLong();
        title=parcel.readString();
        poster=parcel.readString();
        overView=parcel.readString();
        rating=parcel.readString();
        releaseDate=parcel.readString();
        backDrop=parcel.readString();
        flag=parcel.readInt();
    }

    public long getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverView() {
        return overView;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Creator<Movie> CREATOR = new Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return 0;
    }
}