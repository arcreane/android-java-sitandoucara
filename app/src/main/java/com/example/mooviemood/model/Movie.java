package com.example.mooviemood.model;

import java.util.List;

public class Movie {
    public String title;
    public String overview;
    public String posterPath;
    public List<String> providers;
    public String moodLabel;

    public int id;
    public Movie(int id, String title, String overview, String posterPath, List<String> providers, String moodLabel) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.providers = providers;
        this.moodLabel = moodLabel;
    }

    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Movie movie = (Movie) obj;
    return id == movie.id;
}

@Override
public int hashCode() {
    return id;
}

    
}

