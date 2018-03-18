package com.example.katarzkubat.popularmoviesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.katarzkubat.popularmoviesapp.Model.Trailers;
import com.example.katarzkubat.popularmoviesapp.R;
import com.example.katarzkubat.popularmoviesapp.Utilities.NetworkUtils;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private ArrayList<Trailers> trailers = new ArrayList<>();
    private final Context context;

    public TrailerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        Trailers singleTrailer = trailers.get(position);
    }

    @Override
    public int getItemCount() {
        if (null == trailers) {
            return 0;
        }
        return trailers.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView trailerButton;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            trailerButton = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Trailers singleTrailer = trailers.get(position);
            Intent openTrailer = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(NetworkUtils.getTrailerUrl(singleTrailer.getTrailerPath())));
            if (openTrailer.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(openTrailer);
            }
        }
    }

    public void setTrailers(ArrayList<Trailers> trailersList) {
        trailers = trailersList;
        notifyDataSetChanged();
    }
}
