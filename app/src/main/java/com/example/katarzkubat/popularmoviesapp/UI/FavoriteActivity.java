package com.example.katarzkubat.popularmoviesapp.UI;

import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.katarzkubat.popularmoviesapp.Adapters.FavoriteCursorAdapter;
import com.example.katarzkubat.popularmoviesapp.Data.MoviebaseContract;
import com.example.katarzkubat.popularmoviesapp.R;

public class FavoriteActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private FavoriteCursorAdapter favoriteAdapter;
    private static final int LOADER_ID = 0;
    private TextView noMovieMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView favoriteRecycler = findViewById(R.id.favorite_recycler);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(this));

        favoriteAdapter = new FavoriteCursorAdapter(this);
        favoriteRecycler.setAdapter(favoriteAdapter);
        noMovieMessage = findViewById(R.id.error_message_display);


        new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView
                    .ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                String tagIdAsString = Integer.toString((int) viewHolder.itemView.getTag());
                Uri uri = MoviebaseContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(tagIdAsString).build();
                getContentResolver().delete(uri, null, null);
                getSupportLoaderManager().restartLoader(LOADER_ID, null, FavoriteActivity.this);
            }
        }).attachToRecyclerView(favoriteRecycler);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {

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
                    return getContentResolver().query
                            (MoviebaseContract.MoviesEntry.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID);

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
        if (data.getCount() == 0) {
            noMovieMessage.setVisibility(View.VISIBLE);
        } else {
            favoriteAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteAdapter.swapCursor(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, FavoriteActivity.this);
    }
}


