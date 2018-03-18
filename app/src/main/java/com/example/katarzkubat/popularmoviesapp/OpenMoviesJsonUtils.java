package com.example.katarzkubat.popularmoviesapp;

import com.example.katarzkubat.popularmoviesapp.Model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenMoviesJsonUtils {

    private static final String RESULTS = "results";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String ID = "id";

    public static ArrayList<Movies> getMoviesFromJson(String jsonMovies) {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        int id = 0;
        int voteCount = 0;
        int voteAverage = 0;
        String title = "";
        double popularity = 0;
        String posterPath = "";
        String overview = "";
        String releaseDate = "";

        try {
            JSONObject jsonObject = new JSONObject(jsonMovies);
            if (jsonObject.has(RESULTS)) {

                JSONArray results = jsonObject.getJSONArray(RESULTS);
                for (int i = 0; i < results.length(); i++) {

                    JSONObject item = results.getJSONObject(i);

                    if (item.has(ID)) {
                        id = item.optInt(ID);
                    }


                    if (item.has(VOTE_COUNT)) {
                        voteCount = item.optInt(VOTE_COUNT);
                    }

                    if (item.has(VOTE_AVERAGE)) {
                        voteAverage = item.optInt(VOTE_AVERAGE);
                    }

                    if (item.has(TITLE)) {
                        title = item.optString(TITLE);
                    }

                    if (item.has(POPULARITY)) {
                        popularity = item.optDouble(POPULARITY);
                    }

                    if (item.has(POSTER_PATH)) {
                        posterPath = item.optString(POSTER_PATH);
                    }

                    if (item.has(OVERVIEW)) {
                        overview = item.optString(OVERVIEW);
                    }

                    if (item.has(RELEASE_DATE)) {
                        releaseDate = item.optString(RELEASE_DATE);
                    }

                    Movies movies = new Movies(id, title, posterPath, overview, releaseDate, voteCount, popularity, voteAverage);
                    moviesArrayList.add(movies);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesArrayList;
    }
}
