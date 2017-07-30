package com.example.monica.testfinalprojecttwo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FavouriteMovieFragment())
                .commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Fragment fragment =null;
        int title=0;
        String value="popular";

        if (id == R.id.nav_now_playing) {
            title=R.string.nowPlaying;
            value="now_playing";
           fragment=new MainFragment();

            // Handle the camera action
        }

        else if (id == R.id.nav_popular) {
            value="popular";
            title=R.string.popular;
            fragment=new MainFragment();

        }
        else if (id == R.id.nav_top_rated) {

            value="top_rated";
            title=R.string.topRated;
            fragment=new MainFragment();
        }
        else if (id == R.id.nav_upcoming) {

            value="upcoming";
            title=R.string.upcoming;
            fragment=new MainFragment();

        }

        else if(id==R.id.nav_favourite)
        {

            title=R.string.favourite;
            fragment=new FavouriteMovieFragment();

        }
        setTitle(title);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sort", value);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

