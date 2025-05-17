package com.example.mooviemood.ui.dashboard;


import com.example.mooviemood.ui.mood.MoodFragment;
import com.example.mooviemood.ui.mood.MoodType;

import com.example.mooviemood.utils.TiltDetector;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;


import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mooviemood.R;
import com.example.mooviemood.model.Movie;
import com.example.mooviemood.repository.MovieRepository;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private ImageView poster;
    private TextView title, description, providersText;
    private ImageView btnLike;
    private TextView currentMoodText;


    private ArrayList<Movie> movies = new ArrayList<>();
    public static ArrayList<Movie> favorites = new ArrayList<>();
    private int currentIndex = 0;

    private TiltDetector tiltDetector;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        currentMoodText = root.findViewById(R.id.current_mood);
        updateCurrentMoodText();

        // init views
        poster = root.findViewById(R.id.poster);
        title = root.findViewById(R.id.title);
        description = root.findViewById(R.id.description);
        providersText = root.findViewById(R.id.providers_text);

        ImageView btnNext = root.findViewById(R.id.btn_next);
        ImageView btnPrev = root.findViewById(R.id.btn_prev);
        btnLike = root.findViewById(R.id.btn_like);

         // init tilt detector
         tiltDetector = new TiltDetector(requireContext(), new TiltDetector.TiltCallback() {
            @Override
            public void onTiltLeft() {
                goToPreviousMovie();
            }

            @Override
            public void onTiltRight() {
                goToNextMovie();
            }
        });

         // init data
        MoodType currentMood = MoodFragment.getCurrentMood();
        int[] genreIds = currentMood.getGenreIds();
        String genreParam = TextUtils.join(",", convertToList(genreIds));
        
        MovieRepository.fetchMoviesByGenres(genreParam, new MovieRepository.MovieCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> result) {
                movies = result;
                showMovie(currentIndex);
            }

            @Override
            public void onError(Exception e) {
                title.setText("Erreur de chargement");
            }
        });

         // navigation buttons

        btnNext.setOnClickListener(v -> {
            if (currentIndex < movies.size() - 1) {
                currentIndex++;
                showMovie(currentIndex);
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showMovie(currentIndex);
            }
        });

       btnLike.setOnClickListener(v -> {
    Movie currentMovie = movies.get(currentIndex);

    if (favorites.contains(currentMovie)) {
        favorites.remove(currentMovie);
        btnLike.setImageResource(R.drawable.ic_favorite);
    } else {
       MoodType selectedMood = MoodFragment.getCurrentMood();
Movie withMood = new Movie(
    currentMovie.id,
    currentMovie.title,
    currentMovie.overview,
    currentMovie.posterPath,
    currentMovie.providers,
    selectedMood.label
);

        favorites.add(withMood);
        btnLike.setImageResource(R.drawable.ic_favorite_filled);
    }
});


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tiltDetector != null) tiltDetector.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (tiltDetector != null) tiltDetector.stop();
    }

    private void showMovie(int index) {
        if (movies.isEmpty() || index < 0 || index >= movies.size()) return;
        Movie movie = movies.get(index);

        Glide.with(this)
            .load(movie.posterPath)
            .into(poster);

        title.setText(movie.title);
        description.setText(movie.overview);
        providersText.setText("Chargement...");

        // Ajout affichage des plateformes
        MovieRepository.fetchWatchProviders(movie.id, result -> {
            providersText.setText(result);
        });

        // Actualise le cœur
        btnLike.setImageResource(
            favorites.contains(movie) ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite
        );
    }

    private void updateCurrentMoodText() {
        MoodType currentMood = MoodFragment.getCurrentMood();
        if (currentMoodText != null && currentMood != null) {
            currentMoodText.setText("Your Mood: " + currentMood.label);
        }
    }

    private void goToPreviousMovie() {
        if (currentIndex > 0) {
            currentIndex--;
            showMovie(currentIndex);
        }
    }
    
    private void goToNextMovie() {
        if (currentIndex < movies.size() - 1) {
            currentIndex++;
            showMovie(currentIndex);
        }
    }

    private ArrayList<String> convertToList(int[] genreIds) {
        ArrayList<String> list = new ArrayList<>();
        for (int id : genreIds) {
            list.add(String.valueOf(id));
        }
        return list;
    }
    
}
