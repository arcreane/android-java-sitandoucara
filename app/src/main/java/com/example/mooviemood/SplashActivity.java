package com.example.mooviemood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Écran d’accueil temporaire 

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); 
        setContentView(R.layout.activity_splash);

        TextView splashText = findViewById(R.id.splash_text);
        SpannableString spannable = new SpannableString("Welcome to MoovieMood");

        int start = spannable.toString().indexOf("Mood");
        int end = start + "Mood".length();

        spannable.setSpan(
            new ForegroundColorSpan(Color.parseColor("#9F0C2D")),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        splashText.setText(spannable);

        // Redirige vers MainActivity après le délai
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); 
    }
}
