package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import org.w3c.dom.Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieRepository {

    Dao<MovieEntity, Long> dao;

    public MovieRepository() throws DatabaseException {
        try {
            this.dao = DatabaseManager.getDatabase().getMovieDao();
        }catch (Exception e){
            throw new DatabaseException("Could not get database", e);
        }
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try{
            return dao.queryForAll();
        }catch (SQLException e){
            throw new DatabaseException("Couldn't get all movies from database", e);
        }catch (NullPointerException e){
            throw new DatabaseException("Dao is null", e);
        }
    }

    public void removeAll() throws DatabaseException {
        try {
            dao.deleteBuilder().delete();
        }catch (SQLException e){
            throw new DatabaseException("Failed to delete all movies", e);
        }
    }

    public void addAllMovies(List<Movie> movies) throws DatabaseException {
        List<MovieEntity> database = getAllMovies();
        try {
            for(Movie movie : movies) {
                MovieEntity foundmovie = database.stream()
                        .filter(movieEntity -> Objects.equals(movieEntity.getApiId(), movie.getId()))
                        .findFirst()
                        .orElse(null);

                if (foundmovie == null) {
                    System.out.println(movie.getTitle());
                    dao.create(MovieToMovieEntity(movie));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add all movies",e);
        }
    }

    public MovieEntity MovieToMovieEntity(Movie movie){
        return new MovieEntity( movie.getId(), movie.getTitle(), movie.getDescription(), MovieEntity.genresToString(movie.getGenre()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }

    public List<Movie> MovieEntityToMovie (List<MovieEntity> entities){
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity entity : entities){
            Movie movie = new Movie(entity.getApiId(), entity.getTitle(),genresToList(entity.getGenres().trim()), entity.getReleaseYear(), entity.getDescription(), entity.getImgURL(), entity.getLengthInMinutes(), entity.getRating());
            movies.add(movie);
        }
        return movies;
    }

    public static List<Genre> genresToList (String genres){
        List<Genre> genresList = new ArrayList<>();
        for (String s : genres.split(",")) {
            try {
                genresList.add(Genre.valueOf(s.trim().toUpperCase())); // trim and convert to upper case for flexibility
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Invalid genre \"" + s + "\" will be ignored.");
                // Log the error or handle it as necessary
            }
        }
        return genresList;
    }



}
