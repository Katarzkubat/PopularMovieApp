package com.example.katarzkubat.popularmoviesapp.Model;

public class Trailers {

    private String name = "";
    private String trailerPath = "";
    private String trailerSite = "";

    public Trailers(String name, String site, String trailerPath) {
        this.name = name;
        this.trailerPath = trailerPath;
        this.trailerSite = site;
    }

    public String getTrailerPath() {
        return trailerPath;
    }
}
