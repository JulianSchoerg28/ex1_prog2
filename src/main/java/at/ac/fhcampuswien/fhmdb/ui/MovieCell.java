package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label releaseYear = new Label();
    private final Label rating = new Label();

    private final JFXButton addWatchlistBtn = new JFXButton();
//    private Button watchlistBtn = new Button();
private ClickEventHandler<Movie> addToWatchlistClicked;


    private final VBox layout = new VBox(title, detail, genre, releaseYear, rating, addWatchlistBtn);

    public MovieCell(ClickEventHandler<Movie> addToWatchlistClicked) {
        super();
        this.addToWatchlistClicked = addToWatchlistClicked;
        addWatchlistBtn.setOnMouseClicked(mouseEvent -> {
            Movie item = getItem();
            if (item != null) {
                try {
                    addToWatchlistClicked.onClick(item);
                    updateButton();
                } catch (DatabaseException e) {
                    new HomeController().showAlert("sda", "asd"+ e.getMessage());
                }
            }
        });
    }

    private void updateButton(){
        if(addWatchlistBtn.getText().equals("add to Watchlist")){
            addWatchlistBtn.setText("remove from Watchlist");
        }else{
            addWatchlistBtn.setText("add to Watchlist");
        }
    }


    @Override
    protected void updateItem(Movie movie, boolean empty){
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


            addWatchlistBtn.setStyle("-fx-background-color: #f5c518;");
            if(HomeController.isHomeScreen()){
                addWatchlistBtn.setText("add to Watchlist");
            }else{
                addWatchlistBtn.setText("remove from Watchlist");
            }



////
//            try {
//                WatchlistRepository repository = new WatchlistRepository();
//                List<WatchlistMovieEntity> watchlistEntity = repository.getWatchlist();
//
//                if (watchlistEntity.stream().anyMatch(movieEntity -> movie.getId().equals(movieEntity.getApiID()))){
//                    watchlistBtn.setText("remove");
//                    watchlistBtn.setOnAction(event -> {
//                        try {
//                            WatchlistRepository watchlist = new WatchlistRepository();
//                            watchlist.removeFromWatchlist(getItem());
//
//                        } catch (DatabaseException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//
//                }else{
//                    watchlistBtn.setText("add to Watchlist");
//                    watchlistBtn.setOnAction(event -> {
//                        try {
//                            WatchlistRepository watchlist = new WatchlistRepository();
//                            watchlist.addToWatchlist(getItem());
//
//                        } catch (DatabaseException e) {
//                        }
//                    });
//                }
//
//
//            }catch (DatabaseException e) {
//                throw new RuntimeException(e);
//            }

            setGraphic(layout);


        }
    }
}

