package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {

    Dao<MovieEntity, Long> dao;

    public MovieRepository() throws DatabaseException {
        this.dao = DatabaseManager.getDatabase().getMovieDao();
    }

    List<MovieEntity> getAllMovies() throws DatabaseException {
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

    public void addAllMovies(List<Movie> movies) throws SQLException {
        for(Movie movie : movies){
            dao.create(MovieToMovieEntity(movie));
        }

    }

    public MovieEntity MovieToMovieEntity(Movie movie){
        return new MovieEntity( movie.getId(), movie.getTitle(), movie.getDescription(), MovieEntity.genresToString(movie.getGenre()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }



}
