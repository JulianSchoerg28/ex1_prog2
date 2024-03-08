package at.ac.fhcampuswien.fhmdb.models;

import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Genre {
    String genre;

    public String getGenreName(){return genre;}

    public Genre(String genre) {
        this.genre = genre;
    }

    public static List<Genre> initializeGenre(){
        List<Genre> allGenre = new ArrayList<>();


        String[] genreNames = {"ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY",
                "HORROR", "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION",
                "SPORT", "THRILLER", "WAR", "WESTERN"};

        // Loop through the array of genre names, create Genre objects, and add them to the list
        for (String genreName : genreNames) {
            allGenre.add(new Genre(genreName));
        }
        return allGenre;
    }

    @Override
    public String toString() {
        return genre;
    }


}
