package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository(){
        this.dao = DatabaseManager.getDatabase().getWatchlistDao();
    }


    //TODO: die funktion soll ein int zurück geben? was soll sie da zurückgeben?
    public void addToWatchlist(Movie movie) throws SQLException {
        dao.create(MovieToWatchlistMovieEntity(movie));
    }

    //TODO: die funktion soll ein int zurück geben? was soll sie da zurückgeben?
    public void removeFromWatchlist(Movie movie) throws SQLException{
        dao.delete(MovieToWatchlistMovieEntity(movie));
    }

    public WatchlistMovieEntity MovieToWatchlistMovieEntity(Movie movie){
        return new WatchlistMovieEntity(movie.getId());
    }

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }



}
