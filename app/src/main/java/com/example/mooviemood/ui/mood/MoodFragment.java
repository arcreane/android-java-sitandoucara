package com.example.mooviemood.ui.home;
/* 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mooviemood.databinding.FragmentHomeBinding;

public class MoodFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}*/

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mooviemood.R;

public class MoodFragment extends Fragment {

    private GridLayout moodGrid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mood, container, false);
        moodGrid = root.findViewById(R.id.mood_grid);

        for (MoodType mood : MoodType.values()) {
            moodGrid.addView(createMoodView(mood));
        }

        return root;
    }

    private View createMoodView(MoodType mood) {
        Context context = requireContext();

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        container.setPadding(8, 8, 8, 8);
        container.setClickable(true);

        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(30);
        background.setColor(Color.WHITE);
        container.setBackground(background);

        ImageView icon = new ImageView(context);
        icon.setImageResource(mood.iconRes);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(150, 150);
        icon.setLayoutParams(imgParams);
        container.addView(icon);

        TextView label = new TextView(context);
        label.setText(mood.label);
        label.setTextColor(Color.WHITE);
        label.setTextSize(14);
        label.setGravity(Gravity.CENTER);
        label.setPadding(0, 8, 0, 0);

        LinearLayout wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setGravity(Gravity.CENTER);
        wrapper.setPadding(16, 16, 16, 16);
        wrapper.addView(container);
        wrapper.addView(label);

        container.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                .setTitle("Mood choisi")
                .setMessage("Tu as choisi : " + mood.label)
                .setPositiveButton("OK", null)
                .show();
        });

        return wrapper;
    }
}

