package com.example.mooviemood.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ArrayList<Movie> movies = new ArrayList<>();
    public static ArrayList<Movie> favorites = new ArrayList<>();
    private int currentIndex = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        poster = root.findViewById(R.id.poster);
        title = root.findViewById(R.id.title);
        description = root.findViewById(R.id.description);
        providersText = root.findViewById(R.id.providers_text);

        ImageView btnNext = root.findViewById(R.id.btn_next);
        ImageView btnPrev = root.findViewById(R.id.btn_prev);
        btnLike = root.findViewById(R.id.btn_like);

        MovieRepository.fetchMoviesByGenre(35, new MovieRepository.MovieCallback() {
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
                btnLike.setImageResource(R.drawable.ic_favorite); // vide
            } else {
                favorites.add(currentMovie);
                btnLike.setImageResource(R.drawable.ic_favorite_filled); // plein
            }
        });

        return root;
    }

    private void showMovie(int index) {
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
}
