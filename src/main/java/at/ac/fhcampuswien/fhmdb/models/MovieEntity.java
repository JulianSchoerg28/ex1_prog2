package at.ac.fhcampuswien.fhmdb.models;

import java.util.List;

public class MovieEntity {

    private long id;
    private String apiId;
    private String title;
    private String description;
    private String genres;
    private int releaseYear;
    private String imgURL;
    private int lengthinMinutes;
    private double rating;

    public String genresToString(List<String> genres){
        StringBuilder genreString = new StringBuilder();
        for (String genre:genres) {
            genreString.append(genre + ",");
        }

        return genreString.toString();
    }








}
