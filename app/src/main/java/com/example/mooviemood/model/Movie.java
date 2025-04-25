package com.example.mooviemood.model;

import java.util.List;

public class Movie {
    public String title;
    public String overview;
    public String posterPath;
    public List<String> providers;

    public Movie(String title, String overview, String posterPath, List<String> providers) {
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.providers = providers;
    }
}

