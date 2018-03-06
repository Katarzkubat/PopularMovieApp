package com.example.katarzkubat.popularmoviesapp.Model;

/**
 * Created by katarz.kubat on 05.03.2018.
 */

public class Trailers {

    String name = "";
    String trailerPath = "";
    String trailerSite = "";

    public Trailers() {}

    public Trailers(String name, String site, String trailerPath) {
        this.name = name;
        this.trailerPath = trailerPath;
        this.trailerSite = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public String getTrailerSite() {
        return trailerSite;
    }

    public void setTrailerSite(String site) {
        this.trailerSite = site;
    }
}
 /*
 "id":220,
"results":[
{
"id":"533ec652c3a36854480001d3",
"iso_639_1":"en",
"iso_3166_1":"US",
"key":"zMd7Li98DAw",
"name":"Critics' Pick",
"site":"YouTube",
"size":480,
"type":"Featurette"
}
  */