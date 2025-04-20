package com.example.mooviemood.ui.home;

import com.example.mooviemood.R;

public enum MoodType {
    HAPPY("Happy", R.drawable.ic_happy),
    SAD("Sad", R.drawable.ic_sad),
    NOSTALGIC("Nostalgic", R.drawable.ic_nostalgic),
    IN_LOVE("In Love", R.drawable.ic_inlove),
    BRAVE("Brave", R.drawable.ic_brave),
    REFLECTION("Reflection", R.drawable.ic_reflection),
    OPEN_MINDED("Open-minded", R.drawable.ic_openmiended);

    public final String label;
    public final int iconRes;

    MoodType(String label, int iconRes) {
        this.label = label;
        this.iconRes = iconRes;
    }
}
