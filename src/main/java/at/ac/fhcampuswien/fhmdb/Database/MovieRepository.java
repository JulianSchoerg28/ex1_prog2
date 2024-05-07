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
        this.dao = DatabaseManager.getDatabase().getMovieDao();
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try{
            return dao.queryForAll();
        }catch (SQLException e){
            throw new DatabaseException("Couldn't get all movies from database", e);
        }
    }

    //TODO: die funktion soll ein int zurück geben? was soll sie da zurückgeben?
    public void removeAll() throws DatabaseException {
        try {
            dao.deleteBuilder().delete();
        }catch (SQLException e){
            throw new DatabaseException("Failed to delete all movies", e);
        }
    }

    //TODO: Was soll die Funktion können? Wenn sie einen movie holen soll, dann braucht sie doch movie als Parameter was laut Angabe nicht der Fall ist?
    public MovieEntity getMovie(Movie movie){
        return null;
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
            Movie movie = new Movie(entity.getApiId(), entity.getTitle(),genresToList(entity.getGenres()), entity.getReleaseYear(), entity.getDescription(), entity.getImgURL(), entity.getLengthInMinutes(), entity.getRating());
            movies.add(movie);
        }
        return movies;
    }

    public static List<Genre> genresToList (String genres){
        List<Genre> genresList = new ArrayList<>();
        for (String s : genres.split(",")){
            genresList.add(Genre.valueOf(s));
        }
        return genresList;
    }



}
