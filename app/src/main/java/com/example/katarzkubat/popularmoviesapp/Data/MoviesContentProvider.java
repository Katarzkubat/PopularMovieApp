package com.example.katarzkubat.popularmoviesapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.katarzkubat.popularmoviesapp.Data.MoviebaseContract.MoviesEntry.TABLE_MOVIES;

public class MoviesContentProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    private MovieDbHelper movieDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviebaseContract.AUTHORITY, MoviebaseContract.MOVIES_PATH, MOVIES);
        uriMatcher.addURI(MoviebaseContract.AUTHORITY, MoviebaseContract.MOVIES_PATH + "/#", MOVIES_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();

        Cursor returnedCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                returnedCursor = db.query(TABLE_MOVIES,
                        projection, selection, selectionArgs,
                        null, null, MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID);
                break;

            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id?";
                String[] mSelectionArgs = new String[]{id};

                returnedCursor = db.query(TABLE_MOVIES,
                        projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnedCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        Uri returnedUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                long id = db.insert(TABLE_MOVIES, null, values);

                if (id > 0) {
                    returnedUri = ContentUris.withAppendedId(MoviebaseContract.MoviesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:

                rowsDeleted = db.delete(TABLE_MOVIES, selection, selectionArgs);
                break;

            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(TABLE_MOVIES, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated = db.update(TABLE_MOVIES, values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
