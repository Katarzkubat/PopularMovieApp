package com.example.katarzkubat.popularmoviesapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katarzkubat.popularmoviesapp.Data.MoviebaseContract;
import com.example.katarzkubat.popularmoviesapp.R;
import com.example.katarzkubat.popularmoviesapp.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class FavoriteCursorAdapter extends RecyclerView.Adapter<FavoriteCursorAdapter.FavoriteViewHolders> {

    private Cursor cursor;
    private final Context context;

    public FavoriteCursorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FavoriteViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View singleView = LayoutInflater
                .from(context).inflate(R.layout.favorite_list_item, parent, false);
        return new FavoriteViewHolders(singleView);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolders holder, int position) {

        cursor.moveToPosition(position);

        final int id = cursor.getInt(cursor.getColumnIndex(MoviebaseContract.MoviesEntry._ID));
        int movieIndex = cursor.getInt(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_ID));
        String movieTitle = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_TITLE));
        String movieDescription = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_SYNOPSIS));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE));
        String movieRating = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_USER_RATING));
        String movieVoteCounting = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_VOTE_COUNT));
        String moviePoster = cursor.getString(cursor.getColumnIndex(MoviebaseContract.MoviesEntry.COLUMN_MOVIE_POSTER));

        if (movieTitle != null) {
            holder.movieTitleTextView.setText(movieTitle);
        }
        if (movieDescription != null) {
            holder.movieDescriptionTextView.setText(movieDescription);
        }
        if (releaseDate != null) {
            holder.releaseDateTextView.setText(releaseDate);
        }
        if (movieRating != null) {
            holder.movieRatingTextView.setText(movieRating);
        }

        if (movieVoteCounting != null) {
            holder.movieCountingVoteTextView.setText(movieVoteCounting);
        }
        holder.itemView.setTag(id);

        if (moviePoster != null) {
            Picasso.with(context).load(NetworkUtils
                    .getImageUrl(moviePoster, "w92"))
                    .into(holder.moviePoster);
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    class FavoriteViewHolders extends RecyclerView.ViewHolder {

        final TextView movieTitleTextView;
        final TextView movieDescriptionTextView;
        final TextView releaseDateTextView;
        final TextView movieRatingTextView;
        final TextView movieCountingVoteTextView;
        final ImageView moviePoster;

        FavoriteViewHolders(View itemView) {
            super(itemView);
            movieTitleTextView = itemView.findViewById(R.id.movie_title_favorite_item);
            movieDescriptionTextView = itemView.findViewById(R.id.movie_description_favorite_item);
            releaseDateTextView = itemView.findViewById(R.id.movie_date_favorite_item);
            movieRatingTextView = itemView.findViewById(R.id.movie_rating_favorite_item);
            movieCountingVoteTextView = itemView.findViewById(R.id.vote_counting_favorite_item);
            moviePoster = itemView.findViewById(R.id.movie_poster_favorite_item);
        }
    }

    public void swapCursor(Cursor mCursor) {

        if (cursor == mCursor) {
            return;
        }
        Cursor newVal = cursor;
        this.cursor = mCursor;

        if (mCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}
