package com.example.katarzkubat.popularmoviesapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MoviebaseContract {

    public static final String AUTHORITY = "com.example.katarzkubat.popularmoviesapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String MOVIES_PATH = "movies";

    public static final class MoviesEntry implements BaseColumns {

        public static final String TABLE_MOVIES = "tablemovies";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_POSTER = "poster";
        public static final String COLUMN_MOVIE_SYNOPSIS = "synopsis";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "date";
        public static final String COLUMN_MOVIE_USER_RATING = "vote";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "counting";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIES_PATH).build();

    }
}
