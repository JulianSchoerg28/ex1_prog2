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

        // Erstelle eine Anfrage an die URL
        Request request = new Request.Builder()
                .url("https://prog2.fh-campuswien.ac.at/movies")
                .addHeader("User-Agent", "http.agent")
                .build();

        List<Movie> movies = null;
        try {
            // Sende die Anfrage und erhalte die Antwort
            Response response = client.newCall(request).execute();

            // Überprüfe, ob die Anfrage erfolgreich war (Statuscode 200)
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Extrahiere den Inhalt der Antwort als String
            String responseData = response.body().string();
            System.out.print(responseData);


            // Erstelle einen Gson-Objekt
            Gson gson = new Gson();

            // Definiere den Typ für die Deserialisierung
            Type movieListType = new TypeToken<List<Movie>>() {
            }.getType();

            // Deserialisiere den JSON-String zu einer Liste von Movie-Objekten
            movies = gson.fromJson(responseData, movieListType);



            // Schließe die Response
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }








}
