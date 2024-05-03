package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class MovieEntity {

    private long id;
    private String apiId;
    private String title;
    private String description;
    private String genres;
    private int releaseYear;
    private String imgURL;
    private int lengthInMinutes;
    private double rating;


    public MovieEntity(long id, String apiId, String title, String description, String genres, int releaseYear, String imgURL, int lengthInMinutes, double rating) {
        this.id = id;
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgURL = imgURL;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public String genresToString(List<Genre> genres){
        StringBuilder genreString = new StringBuilder();
        for (Genre genre:genres) {
            genreString.append(genre + ",");
        }

        return genreString.toString();
    }

    public List<MovieEntity> fromMovie(List<Movie> movies){
        List<MovieEntity> returnList = new ArrayList<>();
        for (Movie movie: movies) {
            MovieEntity newEntity = new MovieEntity(145, movie.getId(), movie.getTitle(), movie.getDescription(),genresToString(movie.getGenre()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
        returnList.add(newEntity);
        }

        return returnList;
    }










}
