package com.example.mooviemood.ui.mood;

import com.example.mooviemood.R;

public enum MoodType {
    HAPPY("Happy", R.drawable.ic_happy, R.drawable.ic_happy_active),
    SAD("Sad", R.drawable.ic_sad, R.drawable.ic_sad_active),
    NOSTALGIC("Nostalgic", R.drawable.ic_nostalgic, R.drawable.ic_nostalgic_active),
    IN_LOVE("In Love", R.drawable.ic_inlove, R.drawable.ic_inlove_active),
    BRAVE("Brave", R.drawable.ic_brave, R.drawable.ic_brave_active),
    REFLECTION("Reflection", R.drawable.ic_reflection, R.drawable.ic_reflection_active),
    OPEN_MINDED("Open-minded", R.drawable.ic_openmiended, R.drawable.ic_openmiended_active);

    public final String label;
    public final int iconRes;
    public final int activeIconRes;

    MoodType(String label, int iconRes, int activeIconRes) {
        this.label = label;
        this.iconRes = iconRes;
        this.activeIconRes = activeIconRes;
    }

    public int getActiveIconRes() {
        return activeIconRes;
    }

    //id des films...
    public int[] getGenreIds() {
        switch (this) {
            case HAPPY:
                return new int[]{35}; // Comedy
            case SAD:
                return new int[]{18}; // Drama
            case NOSTALGIC:
                return new int[]{10752, 36}; // War, History
            case IN_LOVE:
                return new int[]{10749}; // Romance
            case BRAVE:
                return new int[]{28, 12}; // Action, Adventure
            case REFLECTION:
                return new int[]{99}; // Documentary
            case OPEN_MINDED:
                return new int[]{878, 9648, 14}; // Sci-Fi, Mystery, Fantasy
            default:
                return new int[]{};
        }
    }
}
