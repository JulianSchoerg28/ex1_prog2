package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;

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
            System.out.print(responseData);


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
