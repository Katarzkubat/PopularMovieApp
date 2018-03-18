package com.example.katarzkubat.popularmoviesapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviebase";
    private static final int DATABASE_VERSION = 4;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVTABLE = "CREATE TABLE " + MoviebaseContract.MoviesEntry.TABLE_MOVIES + " (" +
                MoviebaseContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_POSTER + " TEXT," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " INTEGER," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_VOTE_COUNT + " INTEGER," +
                MoviebaseContract.MoviesEntry.COLUMN_MOVIE_USER_RATING + " INTEGER" +
                "); ";
        db.execSQL(SQL_CREATE_MOVTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("ONUPGRADE", "ONUPGRADE");
        db.execSQL("DROP TABLE IF EXISTS " + MoviebaseContract.MoviesEntry.TABLE_MOVIES);
        onCreate(db);
    }
}
