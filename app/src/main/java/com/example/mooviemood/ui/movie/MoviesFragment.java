package com.example.mooviemood.ui.movie;


import com.example.mooviemood.ui.mood.MoodFragment;
import com.example.mooviemood.ui.mood.MoodModel;

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
import com.example.mooviemood.ui.movie.MovieModel;
import com.example.mooviemood.ui.movie.MovieRepository;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private ImageView poster;
    private TextView title, description, providersText;
    private ImageView btnLike;
    private TextView currentMoodText;


   private ArrayList<MovieModel> movies = new ArrayList<>();
   public static ArrayList<MovieModel> favorites = new ArrayList<>();


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
        MoodModel currentMood = MoodFragment.getCurrentMood();
        int[] genreIds = currentMood.getGenreIds();
        String genreParam = TextUtils.join(",", convertToList(genreIds));

        // Récupère les films depuis l'API en fonction du mood sélectionné
        MovieRepository.fetchMoviesByGenres(genreParam, new MovieRepository.MovieCallback() {
            @Override
            public void onSuccess(ArrayList<MovieModel> result) {
                movies = result;
                showMovie(currentIndex);
            }

            @Override
            public void onError(Exception e) {
                title.setText("Erreur de chargement");
            }
        });

        
// Bouton Suivant 
btnNext.setOnClickListener(v -> {
    if (currentIndex < movies.size() - 1) {
        currentIndex++;
        showMovie(currentIndex);
    }
});

// Bouton Précédent 
btnPrev.setOnClickListener(v -> {
    if (currentIndex > 0) {
        currentIndex--;
        showMovie(currentIndex);
    }
});

// Bouton Like  (ajoute ou retire le film des favoris)
btnLike.setOnClickListener(v -> {
    MovieModel currentMovie = movies.get(currentIndex);

    if (favorites.contains(currentMovie)) {
        favorites.remove(currentMovie);
        btnLike.setImageResource(R.drawable.ic_favorite);
    } else {
        // Associe dynamiquement le mood sélectionné avant d'ajouter
        currentMovie.moodLabel = MoodFragment.getCurrentMood().label;
        favorites.add(currentMovie);
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

    // Met à jour les vues avec les infos du film actuel
    private void showMovie(int index) {
        if (movies.isEmpty() || index < 0 || index >= movies.size()) return;
       MovieModel movie = movies.get(index);


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

    // Actualise dynamiquement le texte du mood sélectionné
    private void updateCurrentMoodText() {
        MoodModel currentMood = MoodFragment.getCurrentMood();
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
    
    // Convertit un tableau d'IDs (int[]) en liste de String pour l'URL de l'API
    private ArrayList<String> convertToList(int[] genreIds) {
        ArrayList<String> list = new ArrayList<>();
        for (int id : genreIds) {
            list.add(String.valueOf(id));
        }
        return list;
    }
    
}
