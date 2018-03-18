package com.example.katarzkubat.popularmoviesapp.UI;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katarzkubat.popularmoviesapp.Adapters.ReviewAdapter;
import com.example.katarzkubat.popularmoviesapp.Adapters.TrailerAdapter;
import com.example.katarzkubat.popularmoviesapp.Data.MoviebaseContract;
import com.example.katarzkubat.popularmoviesapp.Model.Movies;
import com.example.katarzkubat.popularmoviesapp.Model.Reviews;
import com.example.katarzkubat.popularmoviesapp.Model.Trailers;
import com.example.katarzkubat.popularmoviesapp.R;
import com.example.katarzkubat.popularmoviesapp.Utilities.NetworkUtils;
import com.example.katarzkubat.popularmoviesapp.Utilities.OpenReviewsJsonUtils;
import com.example.katarzkubat.popularmoviesapp.Utilities.OpenTrailersJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static com.example.katarzkubat.popularmoviesapp.UI.MainActivity.OBJECT_NAME;

public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private Movies singleMovieDetail;
    private static final int LOADER_ID = 0;
    private boolean movieIsAdded;
    private ImageButton favoriteBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView posterImage = findViewById(R.id.moviePoster);
        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView releaseDate = findViewById(R.id.releaseDate);
        TextView movieDescription = findViewById(R.id.plotDescription);
        TextView popularityRate = findViewById(R.id.popularityRate);
        TextView ratingValue = findViewById(R.id.ratingValue);
        TextView voteCount = findViewById(R.id.voteCount);

        favoriteBtn = findViewById(R.id.addToFavorite);

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movieIsAdded) {
                    addToFavorite(v);
                } else {
                    removeFromFavorite(v);
                }
            }
        });

        RecyclerView reviewRecycler = findViewById(R.id.review_section);
        RecyclerView trailerRecycler = findViewById(R.id.trailer_section);

        trailerRecycler.setHasFixedSize(true);
        reviewRecycler.setHasFixedSize(true);

        reviewAdapter = new ReviewAdapter(this);
        reviewRecycler.setAdapter(reviewAdapter);

        trailerAdapter = new TrailerAdapter(this);
        trailerRecycler.setAdapter(trailerAdapter);

        Intent takeMovieObject = getIntent();
        singleMovieDetail = takeMovieObject.getParcelableExtra(OBJECT_NAME);

        if (!singleMovieDetail.getMoviePoster().isEmpty()) {

            Picasso.with(this)
                    .load(NetworkUtils.getImageUrl(singleMovieDetail.getMoviePoster(), "w154")).into(posterImage);
        }

        if (!singleMovieDetail.getMovieTitle().isEmpty()) {
            movieTitle.setText(singleMovieDetail.getMovieTitle());
        }

        if (!singleMovieDetail.getReleaseDate().isEmpty()) {
            releaseDate.setText(singleMovieDetail.getReleaseDate());
        }

        if (!singleMovieDetail.getPlotDescription().isEmpty()) {
            movieDescription.setText(singleMovieDetail.getPlotDescription());
        }

        if (!Double.toString(singleMovieDetail.getMoviePopularity()).isEmpty()) {
            popularityRate.setText(Double.toString(singleMovieDetail.getMoviePopularity()));
        }

        if (!Integer.toString(singleMovieDetail.getVoteAverage()).isEmpty()) {
            ratingValue.setText(Integer.toString(singleMovieDetail.getVoteAverage()));
        }

        if (!Integer.toString(singleMovieDetail.getVoteCount()).isEmpty()) {
            voteCount.setText(Integer.toString(singleMovieDetail.getVoteCount()));
        }

        new PopulateTrailers().execute(getResources().getString(R.string.api_key),
                Integer.toString(singleMovieDetail.getId()));

        new PopulateReviews().execute(getResources().getString(R.string.api_key),
                Integer.toString(singleMovieDetail.getId()));

        getSupportLoaderManager().initLoader(LOADER_ID, null, DetailActivity.this);
    }

    private void addToFavorite(View view) {

        int movieId = singleMovieDetail.getId();
        String movieTitle = singleMovieDetail.getMovieTitle();
        String movieDescription = singleMovieDetail.getPlotDescription();
        String releaseDate = singleMovieDetail.getReleaseDate();
        int movieRating = singleMovieDetail.getVoteAverage();
        int movieVoteCounting = singleMovieDetail.getVoteCount();
        String moviePoster = singleMovieDetail.getMoviePoster();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_TITLE, movieTitle);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_SYNOPSIS, movieDescription);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, releaseDate);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_USER_RATING, movieRating);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_VOTE_COUNT, movieVoteCounting);
        contentValues.put(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_POSTER, moviePoster);

        Uri uri = getContentResolver().insert(MoviebaseContract.MoviesEntry.CONTENT_URI, contentValues);
        getSupportLoaderManager().restartLoader(LOADER_ID, null, DetailActivity.this);
    }

    private void removeFromFavorite(View view) {

        String where = MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID + "=" + singleMovieDetail.getId();
        getContentResolver().delete(MoviebaseContract.MoviesEntry.CONTENT_URI, where, null);
        getSupportLoaderManager().restartLoader(LOADER_ID, null, DetailActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {

            Cursor movies = null;

            @Override
            protected void onStartLoading() {
                if (movies != null) {
                    deliverResult(movies);
                } else {
                    forceLoad();
                }
                super.onStartLoading();
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    String where = MoviebaseContract.MoviesEntry
                            .COLUMN_MOVIE_ID + " = " + singleMovieDetail.getId();

                    return getContentResolver().query
                            (MoviebaseContract.MoviesEntry.CONTENT_URI,
                                    null,
                                    where,
                                    null,
                                    null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                movies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (null != data && data.getCount() > 0) {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            movieIsAdded = true;

        } else {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            movieIsAdded = false;
        }

        favoriteBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class PopulateReviews extends AsyncTask<String, Void, ArrayList<Reviews>> {

        @Override
        protected ArrayList<Reviews> doInBackground(String... strings) {

            URL reviewRequestUrl = NetworkUtils.buildReviewUrl(strings[0], strings[1]);

            try {
                String jsonReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(reviewRequestUrl);
                return OpenReviewsJsonUtils.
                        getReviewFromJson(jsonReviewResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviews) {
            reviewAdapter.setReviews(reviews);
        }
    }

    private class PopulateTrailers extends AsyncTask<String, Void, ArrayList<Trailers>> {

        @Override
        protected ArrayList<Trailers> doInBackground(String... strings) {

            URL trailerRequestUrl = NetworkUtils.buildTrailerUrl(strings[0], strings[1]);

            try {
                String jsonTrailersResponse = NetworkUtils
                        .getResponseFromHttpUrl(trailerRequestUrl);

                return OpenTrailersJsonUtils
                        .getTrailersFromJson(jsonTrailersResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailers> trailers) {
            trailerAdapter.setTrailers(trailers);
        }
    }
}



