package com.example.katarzkubat.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private final int id;
    private final String movieTitle;
    private final String moviePoster;
    private final String plotDescription;
    private final String releaseDate;
    private final int voteCount;
    private final double moviePopularity;
    private final int voteAverage;


    public Movies(int id, String movieTitle, String moviePoster, String plotDescription,
                  String releaseDate, int voteCount, double moviePopularity, int voteAverage) {

        this.id = id;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.plotDescription = plotDescription;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.moviePopularity = moviePopularity;
        this.voteAverage = voteAverage;

    }

    private Movies(Parcel in) {
        id = in.readInt();
        movieTitle = in.readString();
        moviePoster = in.readString();
        plotDescription = in.readString();
        releaseDate = in.readString();
        voteCount = in.readInt();
        moviePopularity = in.readDouble();
        voteAverage = in.readInt();

    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getPlotDescription() {
        return plotDescription;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getMoviePopularity() {
        return moviePopularity;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(movieTitle);
        dest.writeString(moviePoster);
        dest.writeString(plotDescription);
        dest.writeString(releaseDate);
        dest.writeInt(voteCount);
        dest.writeDouble(moviePopularity);
        dest.writeInt(voteAverage);
    }
}
