package com.example.monica.testfinalprojecttwo.Utility;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.monica.testfinalprojecttwo.BuildConfig;
import com.example.monica.testfinalprojecttwo.models.Movie;
import com.example.monica.testfinalprojecttwo.models.Review;
import com.example.monica.testfinalprojecttwo.models.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monica on 4/14/2017.
 */

public class NetworkFunctions {

    private Context context;

    public NetworkFunctions(Context context)
    {

        this.context=context;
    }

    final String ApiKey = "api_key";

    public List<Movie> movieNetwork(String sorting)
    {

        String MovieJsonStr= null;
        final String MOVIE_BASE_URL =
                "https://api.themoviedb.org/3/movie/";
        Uri builtUri=Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendEncodedPath(sorting)
                .appendQueryParameter(ApiKey, BuildConfig.MOVIES_API_KEY)
                .build();
        MovieJsonStr=getJson(builtUri.toString());
        try {
            return getMoviesFromJson(MovieJsonStr);
        } catch (JSONException e) {
            //Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;

    }

    public List<Review> ReviewStream(long movieID)
    {
        String reviewJsonStr=null;
        final String Review_BASE_URL =
                "https://api.themoviedb.org/3/movie/";
        //https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>

        Uri builtUri = Uri.parse(Review_BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieID))
                .appendEncodedPath("reviews")
                .appendQueryParameter(ApiKey, BuildConfig.MOVIES_API_KEY)
                .build();
        reviewJsonStr=getJson(builtUri.toString());
        try {
            return getReviewsFromJson(reviewJsonStr);
        } catch (JSONException e) {
            // Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;

    }
    public List<Trailers> TrailersSream(long movieID)
    {
        final String TRAILER_BASE_URL =
                "https://api.themoviedb.org/3/movie/";
        String trailerJsonStr=null;


        Uri builtUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieID))
                .appendEncodedPath("videos")
                .appendQueryParameter(ApiKey, BuildConfig.MOVIES_API_KEY)
                .build();
        trailerJsonStr=getJson(builtUri.toString());
        try {
            return getTrailerFromJson(trailerJsonStr);
        } catch (JSONException e) {
            // Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;

    }

    public List<Review> getReviewsFromJson(String JsonStr) throws JSONException {


        final String review_author = "author";
        final String review_content = "content";
        List<Review> reviewList=new ArrayList<>();

        try {

            JSONObject reviewJsonObject = new JSONObject(JsonStr);
            JSONArray reviewsArray = reviewJsonObject.getJSONArray("results");
            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject currentMovie = reviewsArray.getJSONObject(i);
                String authorJson = currentMovie.getString(review_author);
                String contentJson = currentMovie.getString(review_content);
                Review review=new Review(authorJson,contentJson);
                reviewList.add(review);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return reviewList;
    }

    public List<Movie> getMoviesFromJson(String JsonStr) throws JSONException {


        final String Overview = "overview";
        final String Title = "original_title";
        final String ReleaseData = "release_date";
        final String Rating = "vote_average";
        final String Poster = "poster_path";
        final String BackDrop="backdrop_path";
        final String id="id";
        List<Movie>movies=new ArrayList<>();


        try {

            JSONObject movieJsonObject = new JSONObject(JsonStr);
            JSONArray moviesArray = movieJsonObject.getJSONArray("results");
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject currentMovie = moviesArray.getJSONObject(i);
                long idJson=currentMovie.getLong(id);
                String OverviewJson = currentMovie.getString(Overview);
                String TitleJson = currentMovie.getString(Title);
                String ReleaseDataJson = currentMovie.getString(ReleaseData);
                String RatingJson = currentMovie.getString(Rating);
                String PosterJson = currentMovie.getString(Poster);
                String BackDropJson=currentMovie.getString(BackDrop);
                Movie movie=new Movie();
                movie.setMovieID(idJson);
                movie.setTitle(TitleJson);
                movie.setBackDrop(BackDropJson);
                movie.setOverView(OverviewJson);
                movie.setPoster(PosterJson);
                movie.setRating(RatingJson);
                movie.setReleaseDate(ReleaseDataJson);
                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return movies;
    }


    public List<Trailers> getTrailerFromJson(String JsonStr) throws JSONException {


        final String trailer_key = "key";
        List<Trailers> trailersList=new ArrayList<>();


        try {

            JSONObject trailerJsonObject = new JSONObject(JsonStr);
            JSONArray trailerArray = trailerJsonObject.getJSONArray("results");
            for (int i = 0; i < trailerArray.length(); i++) {
                JSONObject currentMovie = trailerArray.getJSONObject(i);
                String keyJson = currentMovie.getString(trailer_key);
                Trailers trailers=new Trailers(keyJson);
                trailersList.add(trailers);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return trailersList;
    }

    private String getJson(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonStr = null;

        try {

            URL url = new URL(urlString);
            //Log.v(LOG_TAG, "Built URI " + builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            return JsonStr = buffer.toString();

        } catch (IOException e) {
            // Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }


}

