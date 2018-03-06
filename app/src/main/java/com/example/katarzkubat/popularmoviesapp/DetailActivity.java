package com.example.katarzkubat.popularmoviesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katarzkubat.popularmoviesapp.Model.Movies;
import com.example.katarzkubat.popularmoviesapp.Model.Reviews;
import com.example.katarzkubat.popularmoviesapp.Model.Trailers;
import com.example.katarzkubat.popularmoviesapp.Utilities.NetworkUtils;
import com.example.katarzkubat.popularmoviesapp.Utilities.OpenReviewsJsonUtils;
import com.example.katarzkubat.popularmoviesapp.Utilities.OpenTrailersJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static com.example.katarzkubat.popularmoviesapp.MainActivity.OBJECT_NAME;

public class DetailActivity extends AppCompatActivity {

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

        ImageButton favoriteBtn = findViewById(R.id.addToFavorite);
        Button seeReview = findViewById(R.id.seeReviewBtn);

        Intent takeMovieObject = getIntent();
        final Movies singleMovieDetail = takeMovieObject.getParcelableExtra(OBJECT_NAME);

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

       // new PopulateReviews().execute(getResources().getString(R.string.api_key),
       //         Integer.toString(singleMovieDetail.getId()));
    }

   /* private class PopulateReviews extends AsyncTask<String, Void, ArrayList<Reviews>> {

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

        }
    } */

    //SOMETHING WRONG HAPPENS HERE :)

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

            Log.d("HOWMANY_TRAILERS", Integer.toString(trailers.size()));

            for (final Trailers trailer : trailers) {
                Button trailerButton = findViewById(R.id.launchTrailerBtn);
                if (trailer.getTrailerPath() != null) {
                    trailerButton.setVisibility(View.VISIBLE);
                    trailerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String urlTrailerAsString = NetworkUtils
                                    .getTrailerUrl(trailer.getTrailerSite(), trailer.getTrailerPath());
                            Log.d("OPENTRAILER", urlTrailerAsString);
                            openTrailer(urlTrailerAsString);
                        }
                    });
                } else {
                    trailerButton.setVisibility(View.GONE);
                }
            }
        }
    }

    private void openTrailer(String trailerUri) {
        Log.d("OTWIERAJSIE", trailerUri);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUri));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
}


