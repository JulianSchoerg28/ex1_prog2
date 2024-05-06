package at.ac.fhcampuswien.fhmdb.Database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseManager {
    public static final String DB_URL = "jdbc:h2:file: ./db/moviedb";

    public static final String user = "user";
    public static final String password = "password";

    private  static ConnectionSource connectionSource;
    private Dao<MovieEntity, Long> movieDao;

    private Dao<WatchlistMovieEntity, Long> watchlistDao;

    private static DatabaseManager instance;

    private DatabaseManager(){
        try{
            try {
                createConnectionSource();
            } catch (Exception e) {
                e.printStackTrace();
            }
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void testdb (){
        try {
            WatchlistMovieEntity movie = new WatchlistMovieEntity("1245");
            watchlistDao.create(movie);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return this.movieDao;
    }
    public Dao<WatchlistMovieEntity, Long> getWatchlistDao(){
        return this.watchlistDao;
    }


    public static DatabaseManager getDatabase(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    private static void createTables() throws SQLException{
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);

    }

    private static void createConnectionSource() throws SQLException {

        //Prof hatte es so wie auskommentiert, ging allerdings nicht :(
//        JdbcConnectionSource source = new JdbcConnectionSource(DB_URL, user, password);
        connectionSource = new JdbcConnectionSource(DB_URL, user, password);
    }
}
