package com.example.katarzkubat.popularmoviesapp.Model;

public class Reviews {

    private String title = "";
    private String content = "";

    public Reviews(String author, String content) {

        this.title = author;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}

