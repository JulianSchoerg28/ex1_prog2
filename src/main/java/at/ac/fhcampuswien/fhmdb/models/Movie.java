package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        Movie WolfofWallstreat = new Movie("Wolf of Wallstreat", "Bla BLa BLa");
        Movie Inception = new Movie("Inception","Bla Bla BLa" );
        Movie TheDarkNight = new Movie("The Dark Night","Bla BLa Bla");


        movies.add(WolfofWallstreat);
        movies.add(Inception);
        movies.add(TheDarkNight);




        return movies;
    }
}
