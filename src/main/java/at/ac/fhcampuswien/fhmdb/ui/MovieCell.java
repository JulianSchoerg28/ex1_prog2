package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.Database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.Database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.Database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.List;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label releaseYear = new Label();
    private final Label rating = new Label();

    private final JFXButton watchlistBtn = new JFXButton();
    private final VBox layout = new VBox(title, detail, genre, releaseYear, rating, watchlistBtn);






    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    (movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available")
            );
            genre.setText(movie.getGenre().toString());
            releaseYear.setText(String.valueOf(movie.getReleaseYear()));
            rating.setText(String.valueOf(movie.getRating())  + "/10");


            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            releaseYear.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            genre.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);

            watchlistBtn.setStyle("-fx-background-color: #f5c518;");


            try {
                WatchlistRepository repository = new WatchlistRepository();
                List<WatchlistMovieEntity> watchlistEntity = repository.getWatchlist();

                if (watchlistEntity.stream().anyMatch(movieEntity -> movie.getId().equals(movieEntity.getApiID()))){
                    watchlistBtn.setText("remove");
                    watchlistBtn.setOnAction(event -> {
                        try {
                            WatchlistRepository watchlist = new WatchlistRepository();
                            watchlist.removeFromWatchlist(getItem());

                        } catch (DatabaseException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }else{
                    watchlistBtn.setText("add to Watchlist");
                    watchlistBtn.setOnAction(event -> {
                        try {
                            WatchlistRepository watchlist = new WatchlistRepository();
                            watchlist.addToWatchlist(getItem());

                        } catch (DatabaseException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }



            }catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

            setGraphic(layout);







        }
    }

    //TODO: Text von button ändern oder notfalls 2 buttons und jeweils nur einen anzeigen(sollte leichter sein)
    private void updateWatchlistButtons() {

    }
}

