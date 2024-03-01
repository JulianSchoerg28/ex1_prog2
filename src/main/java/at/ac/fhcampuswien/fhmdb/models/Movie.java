package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie{
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

    public List<Genre> getGenre() {
        return genres;
    }

    public static List<Movie> initializeMovies(List<Genre> allGenre){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here

        Movie WolfofWallstreat = new Movie("Wolf of Wallstreat", "Bla BLa BLa", Arrays.asList(allGenre.get(3),allGenre.get(5)));

        Movie Inception = new Movie("Inception","Bla Bla BLa",Arrays.asList(allGenre.get(3),allGenre.get(5)));

        Movie TheDarkNight = new Movie("The Dark Night","Bla BLa Bla",Arrays.asList(allGenre.get(3),allGenre.get(5)));


        movies.add(WolfofWallstreat);
        movies.add(Inception);
        movies.add(TheDarkNight);

        //0="ACTION"        1="ADVENTURE",      2="ANIMATION",
        //3="BIOGRAPHY",    4="COMEDY",         5="CRIME",
        //6="DRAMA",        7="DOCUMENTARY",    8="FAMILY",
        //9="FANTASY",      10="HISTORY",       11="HORROR",
        //12="MUSICAL",     13="MYSTERY",       14="ROMANCE",
        //15="SCIENCE_FICTION",                 16="SPORT",
        //17="THRILLER",    18="WAR",           19="WESTERN"

        return movies;
    }
}
