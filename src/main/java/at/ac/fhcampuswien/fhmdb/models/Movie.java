package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    // TODO add more properties here

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
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

        List<Genre> WoWgenre = new ArrayList<>();
        Movie WolfofWallstreat = new Movie("Wolf of Wallstreat", "Bla BLa BLa",WoWgenre);
        List<Genre> Incgenre = new ArrayList<>();
        Movie Inception = new Movie("Inception","Bla Bla BLa",Incgenre);
        List<Genre> TDNgenre = new ArrayList<>();
        Movie TheDarkNight = new Movie("The Dark Night","Bla BLa Bla",TDNgenre);


        movies.add(WolfofWallstreat);
        movies.add(Inception);
        movies.add(TheDarkNight);




        return movies;
    }
}
