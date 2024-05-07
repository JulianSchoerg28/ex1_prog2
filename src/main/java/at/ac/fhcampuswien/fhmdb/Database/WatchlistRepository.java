package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        try {
            this.dao = DatabaseManager.getDatabase().getWatchlistDao();
        } catch (RuntimeException e) {
            throw new DatabaseException("Failed to initialise watchlist repository", e);
        }
    }


    //TODO: die funktion soll ein int zurück geben? was soll sie da zurückgeben?
    public void addToWatchlist(Movie movie) throws DatabaseException{
        try {
            dao.create(MovieToWatchlistMovieEntity(movie));
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add movie", e);
        }
    }

    //TODO: die funktion soll ein int zurück geben? was soll sie da zurückgeben?
    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        try{
            for (WatchlistMovieEntity entity: dao) {
                if (Objects.equals(entity.getApiID(), movie.getId())) {
                    dao.deleteById(entity.id);
                }
            }

        }catch (SQLException e){
            throw new DatabaseException("Failed to delete movie", e);
        }
    }

    public WatchlistMovieEntity MovieToWatchlistMovieEntity(Movie movie){
        return new WatchlistMovieEntity(movie.getId());
    }

    public List<WatchlistMovieEntity> getWatchlist() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get watchlist from database", e);
        }
    }
    public boolean isInWatchlist(Movie movie) throws DatabaseException {
        List<WatchlistMovieEntity> watchlist = getWatchlist();
        return watchlist.stream().anyMatch(entity -> entity.getApiID().equals(movie.getId()));
    }



}
