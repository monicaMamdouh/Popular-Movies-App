package com.example.monica.testfinalprojecttwo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.monica.testfinalprojecttwo.Data.MovieContract;

public class FavouriteMovieDetailActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    int idMovie;
    int cursorPosition;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new FavouriteMovieDetailFragment())
                .commit();
        cursorPosition=getIntent().getIntExtra("cursorPosition",0);
        try {
            cursor= getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } catch (Exception e) {
            return;
        }
        cursor.moveToPosition(cursorPosition);
        int idMovieIndex=cursor.getColumnIndex(MovieContract.MovieEntry._ID);
        idMovie=cursor.getInt(idMovieIndex);



        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingButton);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.correct));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {String stringId = Integer.toString(idMovie);
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();


                getContentResolver().delete(uri, null,null);
                Toast.makeText(getApplicationContext(),"movie mark as 'NOT FAVOURITE'",Toast.LENGTH_SHORT).show();
                floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.start));

            }

    });


    }
}
