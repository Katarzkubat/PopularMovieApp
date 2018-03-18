package com.example.katarzkubat.popularmoviesapp.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String API_KEY = "api_key";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final static String MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String TRAILER_PATH = "videos";
    private final static String REVIEW_PATH = "reviews";
    private final static String TRAILER_KEY = "v";
    private final static String TRAILER_URL = "https://www.youtube.com/watch";

    public static URL buildMovieUrl(String secretKey, String preference) {

        Uri builtMovieUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendQueryParameter(API_KEY, secretKey)
                .appendPath(preference)
                .build();

        URL movieUrl = null;
        try {
            movieUrl = new URL(builtMovieUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return movieUrl;
    }

    public static URL buildTrailerUrl(String secretKey, String id) {

        Uri builtTrailerUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath(TRAILER_PATH)
                .appendQueryParameter(API_KEY, secretKey)
                .build();

        URL trailerUrl = null;

        try {
            trailerUrl = new URL(builtTrailerUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return trailerUrl;
    }

    public static URL buildReviewUrl(String secretKey, String id) {

        Uri builtReviewUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath(REVIEW_PATH)
                .appendQueryParameter(API_KEY, secretKey)
                .build();

        URL reviewUrl = null;

        try {

            reviewUrl = new URL(builtReviewUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return reviewUrl;
    }

    public static String getTrailerUrl(String trailerPath) {

        Uri builtTrailerUri = Uri.parse(TRAILER_URL).buildUpon()
                .appendQueryParameter(TRAILER_KEY, trailerPath).build();
        return builtTrailerUri.toString();
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getImageUrl(String path, String size) {
        Uri builtImageUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendPath(size)
                .appendPath(path.substring(1))
                .build();
        return builtImageUri.toString();
    }
}
