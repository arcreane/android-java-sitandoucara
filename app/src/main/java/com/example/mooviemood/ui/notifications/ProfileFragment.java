package com.example.mooviemood.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mooviemood.R;
import com.example.mooviemood.model.Movie;
import com.example.mooviemood.ui.dashboard.MoviesFragment;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private GridLayout gridLayout;
    private TextView emptyText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        gridLayout = root.findViewById(R.id.profile_grid);
        emptyText = root.findViewById(R.id.empty_text);

        renderFavorites();
        return root;
    }

    private void renderFavorites() {
        gridLayout.removeAllViews();
        ArrayList<Movie> favorites = MoviesFragment.favorites;

        if (favorites.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
            for (Movie movie : favorites) {
                View card = LayoutInflater.from(getContext()).inflate(R.layout.item_favorite, gridLayout, false);

                ImageView img = card.findViewById(R.id.favorite_poster);
                TextView title = card.findViewById(R.id.favorite_title);
                ImageView heart = card.findViewById(R.id.favorite_heart);

                Glide.with(this).load(movie.posterPath).into(img);
                title.setText(movie.title);
                heart.setImageResource(R.drawable.ic_favorite_filled);

                heart.setOnClickListener(v -> {
                    MoviesFragment.favorites.remove(movie);
                    renderFavorites();
                });

                gridLayout.addView(card);
            }
        }
    }
}

