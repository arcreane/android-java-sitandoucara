package com.example.mooviemood.ui.movie;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.mooviemood.ui.movie.MovieModel;
import com.example.mooviemood.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieRepository {

    public interface MovieCallback {
        void onSuccess(ArrayList<MovieModel> movies);
        void onError(Exception e);
    }

    // Interface pour récupérer une chaîne (utilisée pour les providers)
    public interface StringCallback {
        void onResult(String result);
    }

    // Appel pour récupérer les films par genre
    public static void fetchMoviesByGenres(String genreIds, MovieCallback callback) {
        new AsyncTask<Void, Void, ArrayList<MovieModel>>() {
            Exception error;
    
            @Override
            protected ArrayList<MovieModel> doInBackground(Void... voids) {
                try {
                    String urlStr = Constants.TMDB_BASE_URL + "/discover/movie?api_key=" + Constants.TMDB_API_KEY + "&with_genres=" + genreIds;
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
    
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
    
                    JSONObject response = new JSONObject(sb.toString());
                    JSONArray results = response.getJSONArray("results");
    
                    ArrayList<MovieModel> movies = new ArrayList<>();
    
                   for (int i = 0; i < results.length(); i++) {
    JSONObject m = results.getJSONObject(i);
    int id = m.getInt("id");
    String title = m.getString("title");
    String overview = m.getString("overview");
    String posterPath = Constants.TMDB_IMAGE_URL + m.getString("poster_path");

    
    String moodLabel = com.example.mooviemood.ui.mood.MoodFragment.getCurrentMood().label;

   
    movies.add(new MovieModel(id, title, overview, posterPath, new ArrayList<>(), moodLabel));
}

    
                    return movies;
                } catch (Exception e) {
                    error = e;
                    return null;
                }
            }
    
            @Override
            protected void onPostExecute(ArrayList<MovieModel> movies) {
                if (movies != null) {
                    callback.onSuccess(movies);
                } else {
                    callback.onError(error);
                }
            }
        }.execute();
    }
    
    // Appel pour récupérer les plateformes de diffusion d'un film
    public static void fetchWatchProviders(int movieId, StringCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String urlStr = Constants.TMDB_BASE_URL + "/movie/" + movieId + "/watch/providers?api_key=" + Constants.TMDB_API_KEY;
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject data = new JSONObject(sb.toString());
                    JSONObject fr = data.optJSONObject("results").optJSONObject("FR");

                    if (fr != null && fr.has("flatrate")) {
                        JSONArray platforms = fr.getJSONArray("flatrate");
                        ArrayList<String> names = new ArrayList<>();
                        for (int i = 0; i < platforms.length(); i++) {
                            names.add(platforms.getJSONObject(i).getString("provider_name"));
                        }
                        return TextUtils.join(" - ", names);
                    } else {
                        return "Not available on streaming platforms";
                    }

                } catch (Exception e) {
                    return "Erreur";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onResult(result);
            }
        }.execute();
    }
}
