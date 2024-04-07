package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie implements Comparable<Movie> {

    private String id;
    private String title;
    private List<String> genres;
    private int releaseYear;
    private String description;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;

    private List<String> writers;
    private List<String>mainCast;
    private double rating;

    //Ich glaube man braucht nur die 3 (für den HomeController)
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getGenre() {
        return genres;
    }
    public List<String> getDirectors() {
        return directors;
    }
    public String getReleaseYear() { return String.valueOf(releaseYear); }

    public String getRating(){
        return String.valueOf(rating);
    }

    public List<String> getMainCast(){
        return mainCast;
    }



    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
