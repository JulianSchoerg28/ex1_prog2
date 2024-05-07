package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class Movie implements Comparable<Movie> {

    private String id;
    private String title;
    private List<Genre> genres;
    private int releaseYear;
    private String description;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;

    private List<String> writers;
    private List<String>mainCast;
    private double rating;


    public String getId() {return id;}
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getImgUrl(){return imgUrl;}

    public int getLengthInMinutes() {return lengthInMinutes;}

    public List<Genre> getGenre() {
        return genres;
    }
    public List<String> getDirectors() {
        return directors;
    }
    public int getReleaseYear() { return releaseYear; }

    public double getRating(){
        return rating;
    }

    public List<String> getMainCast(){
        return mainCast;
    }


    public Movie(String id, String title, List<Genre> genres, int releaseYear, String description, String imgUrl, int lengthInMinutes, double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = null;
        this.writers = null;
        this.mainCast = null;
        this.rating = rating;
    }

    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }

    public List<String> getActors() { return mainCast;
    }
}
