package com.example.katarzkubat.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        TextView reviewAuthor = findViewById(R.id.reviewTitleLbl);
        TextView reviewContent = findViewById(R.id.conentDescription);

        reviewAuthor.setText("");
        reviewContent.setText("");

       /* Intent takeReviewContent = getIntent();

        if (takeReviewContent.hasExtra(Intent.EXTRA_TEXT)) {
            String sentContent = takeReviewContent.getStringExtra(Intent.EXTRA_TEXT);
            reviewContent.setText(sentContent);


        } */
    }
}
