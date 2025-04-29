package com.example.mooviemood.ui.home;

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

import java.util.HashMap;

public class MoodFragment extends Fragment {

    private GridLayout moodGrid;
    private MoodType currentMood = MoodType.OPEN_MINDED;
    private final HashMap<MoodType, ImageView> moodIcons = new HashMap<>();

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
        int initialIcon = (mood == currentMood) ? mood.getActiveIconRes() : mood.iconRes;
        icon.setImageResource(initialIcon);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(150, 150);
        icon.setLayoutParams(imgParams);
        container.addView(icon);

        moodIcons.put(mood, icon);

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
            updateMoodSelection(mood);
        });

        return wrapper;
    }

    private void updateMoodSelection(MoodType selectedMood) {
        if (selectedMood == currentMood) return;

        // Réinitialiser l'ancien en mode normal
        ImageView oldIcon = moodIcons.get(currentMood);
        if (oldIcon != null) oldIcon.setImageResource(currentMood.iconRes);

        // Activer le nouveau
        ImageView newIcon = moodIcons.get(selectedMood);
        if (newIcon != null) newIcon.setImageResource(selectedMood.getActiveIconRes());

        currentMood = selectedMood;
    }

    public static MoodType getCurrentMood() {
        return instance != null ? instance.currentMood : MoodType.OPEN_MINDED;
    }
    
    private static MoodFragment instance;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }
    
}
