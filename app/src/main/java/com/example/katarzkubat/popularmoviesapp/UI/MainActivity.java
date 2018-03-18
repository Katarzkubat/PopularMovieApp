package com.example.katarzkubat.popularmoviesapp.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.katarzkubat.popularmoviesapp.Adapters.MoviePosterAdapter;
import com.example.katarzkubat.popularmoviesapp.Model.Movies;
import com.example.katarzkubat.popularmoviesapp.MoviePreferences;
import com.example.katarzkubat.popularmoviesapp.OpenMoviesJsonUtils;
import com.example.katarzkubat.popularmoviesapp.R;
import com.example.katarzkubat.popularmoviesapp.Utilities.MovieClicker;
import com.example.katarzkubat.popularmoviesapp.Utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieClicker {

    public static final String OBJECT_NAME = "movies";

    private MoviePosterAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_movies);

        GridLayoutManager layoutManager = new GridLayoutManager
                (this, 2, GridLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        movieAdapter = new MoviePosterAdapter(this, this);

        recyclerView.setAdapter(movieAdapter);
    }

    public void onClick(Movies singleMovie) {
        Intent openDetail = new Intent(this, DetailActivity.class);
        openDetail.putExtra(OBJECT_NAME, singleMovie);
        startActivity(openDetail);
    }

    private class PopulateMovies extends AsyncTask<String, Void, ArrayList<Movies>> {

        @Override
        protected ArrayList<Movies> doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }

            String moviePreferences = MoviePreferences
                    .getChosenPreferences(MainActivity.this);

            URL movieRequestUrl = NetworkUtils.buildMovieUrl(strings[0], moviePreferences);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                return OpenMoviesJsonUtils
                        .getMoviesFromJson(jsonMoviesResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            if (movies != null) {
                movieAdapter.setMovies(movies);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new PopulateMovies().execute(getResources().getString(R.string.api_key));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        if (id == R.id.favorite) {
            Intent favoriteActivity = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(favoriteActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
