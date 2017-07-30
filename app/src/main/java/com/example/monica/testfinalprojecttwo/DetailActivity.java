package com.example.monica.testfinalprojecttwo;


import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;
import com.example.monica.testfinalprojecttwo.models.Movie;

public class DetailActivity extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new DetailFragment())
                .commit();

        movie = new Movie();
        movie = getIntent().getParcelableExtra("movieObject");
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.start));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setFlag(1);
                ContentValues movieValues = new ContentValues();
                movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieID());
                movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
                movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverView());
                movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                movieValues.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getRating());
                movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER, movie.getPoster());
                movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackDrop());


                try {
                    Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
                    if (uri != null) {
                        Toast.makeText(getApplicationContext(), "movie marked as 'FAVOURITE'", Toast.LENGTH_SHORT).show();
                        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.correct));

                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "This Movie is already marked as 'FAVOURITE'", Toast.LENGTH_SHORT).show();
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.correct));

                }

            }
        });

    }

}
