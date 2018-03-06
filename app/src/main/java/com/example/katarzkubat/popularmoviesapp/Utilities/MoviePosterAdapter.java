package com.example.katarzkubat.popularmoviesapp.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.katarzkubat.popularmoviesapp.Model.Movies;
import com.example.katarzkubat.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movies> movies = new ArrayList<>();
    private final MovieClicker clicker;
    private final Context appContext;

    public MoviePosterAdapter(Context context, MovieClicker movieClicker) {
        appContext = context;
        clicker = movieClicker;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdGridItem = R.layout.movie_grid_item;
        boolean attachToParent = false;

        View view = inflater.inflate(layoutIdGridItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movies singleMovie = movies.get(position);
        Picasso.with(appContext).load(NetworkUtils.getImageUrl(singleMovie.getMoviePoster(), "w185"))
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (null == movies) {
            return 0;
        }
        return movies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView moviePoster;

        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_grid_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clicker.onClick(movies.get(position));
        }
    }

    public void setMovies(ArrayList<Movies> moviesList) {
        movies = moviesList;
        notifyDataSetChanged();
    }
}


