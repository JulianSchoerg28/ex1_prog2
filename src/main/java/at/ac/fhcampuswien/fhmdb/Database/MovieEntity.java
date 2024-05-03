package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "movie")
public class MovieEntity {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField()
    private String apiId;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private String description;
    @DatabaseField()
    private String genres;
    @DatabaseField()
    private int releaseYear;
    @DatabaseField()
    private String imgURL;
    @DatabaseField()
    private int lengthInMinutes;
    @DatabaseField()
    private double rating;

    public MovieEntity(){}
    public MovieEntity(String apiId, String title, String description, String genres, int releaseYear, String imgURL, int lengthInMinutes, double rating) {
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
            MovieEntity newEntity = new MovieEntity( movie.getId(), movie.getTitle(), movie.getDescription(),genresToString(movie.getGenre()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
        returnList.add(newEntity);
        }

        return returnList;
    }










}
