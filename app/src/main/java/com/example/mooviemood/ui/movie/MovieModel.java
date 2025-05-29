package com.example.mooviemood.ui.movie;

import java.util.List;

public class MovieModel  {
    public String title;
    public String overview;
    public String posterPath;
    public List<String> providers;
    public String moodLabel;

    public int id;
    public MovieModel(int id, String title, String overview, String posterPath, List<String> providers, String moodLabel) {
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
        MovieModel movie = (MovieModel) obj;
        return id == movie.id;
}

    @Override
    public int hashCode() {
        return id;
    }

    
}

