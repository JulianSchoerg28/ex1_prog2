package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.HomeController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {


    public static List<Movie> getMovies() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://prog2.fh-campuswien.ac.at/movies")
                .addHeader("User-Agent", "http.agent")
                .build();

        List<Movie> movies = null;
        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();

            Gson gson = new Gson();

            Type movieListType = new TypeToken<List<Movie>>() {
            }.getType();

            movies = gson.fromJson(responseData, movieListType);

            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static List<Movie> filteredMovies(String query, String genre, String releaseYear, String rating){

        //build the correct Url

        StringBuilder urlString = new StringBuilder();
        urlString.append("https://prog2.fh-campuswien.ac.at/movies?");

        if (query == null && genre == null && releaseYear == null &&rating == null){
            return null;
        }

        List<String> queryParams = new ArrayList<>();

        if (query != null) {
            queryParams.add("query=" + query);
        }
        if (genre != null) {
            queryParams.add("genre=" + genre);
        }
        if (releaseYear != null) {
            queryParams.add("releaseYear=" + releaseYear);
        }
        if (rating != null) {
            queryParams.add("ratingFrom=" + rating);
        }

        urlString.append(String.join("&", queryParams));
        System.out.println(urlString.toString());

        //call the url

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlString.toString())
                .addHeader("User-Agent", "http.agent")
                .build();

        List<Movie> movies = null;
        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();

            Gson gson = new Gson();

            Type movieListType = new TypeToken<List<Movie>>() {
            }.getType();

            movies = gson.fromJson(responseData, movieListType);

            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }




}
