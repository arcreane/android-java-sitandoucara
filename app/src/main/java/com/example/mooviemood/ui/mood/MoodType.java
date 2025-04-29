package com.example.mooviemood.ui.home;

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
}
