package com.example.katarzkubat.popularmoviesapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katarzkubat.popularmoviesapp.Model.Reviews;
import com.example.katarzkubat.popularmoviesapp.R;

import java.util.ArrayList;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<Reviews> reviews = new ArrayList<>();
    private final Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (null == reviews) {
            return 0;
        }
        return reviews.size();
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        Reviews singleReview = reviews.get(position);

        holder.reviewTitle.setText(singleReview.getTitle());
        holder.reviewContent.setText(singleReview.getContent());
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView reviewTitle;
        final TextView reviewContent;

        ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            reviewTitle = itemView.findViewById(R.id.reviewTitle);
            reviewContent = itemView.findViewById(R.id.reviewContent);
        }
    }

    public void setReviews(ArrayList<Reviews> reviewList) {
        reviews = reviewList;
        notifyDataSetChanged();
    }
}
