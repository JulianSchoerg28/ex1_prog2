package at.ac.fhcampuswien.fhmdb.Database;

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
    Dao<MovieEntity, Long> dao;

    private static DatabaseManager instance;

    private DatabaseManager(){
        try{
            try {
                createConnectionSource();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dao = DaoManager.createDao(connectionSource, MovieEntity.class);
            createTables();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void testDB() throws SQLException {
        MovieEntity movie = new MovieEntity("145", "test", "asdasdsa", "genree", 2024, "www.asdsa.at", 120, 3);
        dao.create(movie);
    }

    public static DatabaseManager getDatabase(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    private static void createTables() throws SQLException{
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
    }

    private static void createConnectionSource() throws SQLException {

        //Prof hatte es so wie auskommentiert, ging allerdings nicht :(
//        JdbcConnectionSource source = new JdbcConnectionSource(DB_URL, user, password);
        connectionSource = new JdbcConnectionSource(DB_URL, user, password);
    }


}
