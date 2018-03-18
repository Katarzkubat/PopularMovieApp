package com.example.katarzkubat.popularmoviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class MoviePreferences {

    public static String getChosenPreferences(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        String keyOption = context.getString(R.string.option);
        String defaultOption = context.getString(R.string.most_popular);

        return sharedPreferences.getString(keyOption, defaultOption);
    }
}
