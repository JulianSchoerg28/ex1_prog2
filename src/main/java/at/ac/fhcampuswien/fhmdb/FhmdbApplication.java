package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.Database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.Database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.Database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.Database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();


        DatabaseManager.getDatabase().testdb();


        try {

            WatchlistRepository watchlist = new WatchlistRepository();
            List<WatchlistMovieEntity> watchmovies = watchlist.getWatchlist();

            for (WatchlistMovieEntity movie: watchmovies){
                System.out.println("id: " + movie.getId() + "  apiID: " + movie.getApiID());
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        launch();
    }
}