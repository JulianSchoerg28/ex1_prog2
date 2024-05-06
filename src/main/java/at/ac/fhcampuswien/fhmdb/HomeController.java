package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

    @FXML
    public JFXComboBox<String> releaseYearBox;

    @FXML
    public JFXComboBox<String> ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    @FXML
    public JFXButton watchlistBtn;

    @FXML
    public JFXButton homeBtn;




    public List<Movie> allMovies;

    {
        try {
            allMovies = new ArrayList<>(MovieAPI.getMovies());
        } catch (MovieApiException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            movieListView.getItems().setAll(MovieAPI.getMovies());
        } catch (MovieApiException e) {
            showAlert("Error", "Unable to load movies from API: " + e.getMessage());
            movieListView.getItems().setAll(Collections.emptyList());
        }

        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data


        // initialize GenreComboBox
        genreComboBox.setPromptText("Filter by Genre");
        String[] genreArray = {"ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY",
                "HORROR", "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION",
                "SPORT", "THRILLER", "WAR", "WESTERN"};

        ObservableList<String> genreObservableList = FXCollections.observableArrayList(genreArray);
        genreComboBox.setItems(genreObservableList);


        // initialize releaseYearBox
        releaseYearBox.setPromptText("Filter by Release Year");
        ObservableList<String> releasYearList = FXCollections.observableArrayList();

        for (Movie movie : allMovies) {
            String year = String.valueOf(movie.getReleaseYear());
            if (!releasYearList.contains(year)) {
                releasYearList.add(year);
            }
        }
        Collections.sort(releasYearList);
        Collections.reverse(releasYearList);
        releaseYearBox.setItems(releasYearList);


        //initialize ratingComboBox
        ratingComboBox.setPromptText("Filter by Rating");
        ObservableList<String> ratingList = FXCollections.observableArrayList();

        for (int i = 0; i <= 10; i++) {
            ratingList.add(String.valueOf(i));
        }
        ratingComboBox.setItems(ratingList);


        searchBtn.setOnAction(actionEvent -> {
            ObservableList<Movie> filteredMovieList = FXCollections.observableArrayList();


            String query = searchField.getText();
            String genre = genreComboBox.getValue();
            String releaseYear = releaseYearBox.getValue();
            String rating = ratingComboBox.getValue();

            try {
                filteredMovieList.addAll(MovieAPI.filteredMovies(query, genre, releaseYear, rating));
            } catch (MovieApiException e) {
                throw new RuntimeException(e);
            }

            if (movieListView != null) {
                movieListView.setItems(filteredMovieList);
                movieListView.setCellFactory(movieListView -> new MovieCell());
            }

            observableMovies = filteredMovieList;
        });


        resetBtn.setOnAction(actionEvent -> {
            try {
                resetFilter();
            } catch (MovieApiException e) {
                throw new RuntimeException(e);
            }
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortBtn.setText("Sort (desc)");
                sortasc(observableMovies);
            } else {
                sortBtn.setText("Sort (asc)");
                sortdesc(observableMovies);
            }
        });

        homeBtn.setStyle("-fx-background-color: #00FF00;");

    }

    public void sortasc(ObservableList<Movie> observableMovies) {
        List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
        observableMovies.setAll(sortedMovie);
    }

    public void sortdesc(ObservableList<Movie> observableMovies) {
        List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle).reversed()).collect(Collectors.toList());
        observableMovies.setAll(sortedMovie);
    }


    public void resetFilter() throws MovieApiException {

        if (genreComboBox != null) {
            genreComboBox.setValue(null);
        }
        if (releaseYearBox != null) {
            releaseYearBox.setValue(null);
        }
        if (ratingComboBox != null) {
            ratingComboBox.setValue(null);
        }
        if (searchField != null) {
            searchField.setText("");
        }


        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();

        newMovieList.addAll(MovieAPI.getMovies());

        if (movieListView != null) {
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = newMovieList;
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        List<Movie> moviesss = movies.stream().filter(movie -> movie.getDirectors().contains(director)).collect(Collectors.toList());
        //Ausgabe zu Testzwecken:
        moviesss.forEach(System.out::println);
        System.out.println(moviesss.size());
        return moviesss.size();
    }
//    gibt die Anzahl der Filme eines bestimmten Regisseurs zurück.

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        List<Movie> betweenYears = movies.stream().filter(movie -> {
                    try {
                        int year = movie.getReleaseYear();
                        return year >= startYear && year <= endYear;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return betweenYears;
    }


    public int getLongestMovieTitel(List<Movie> movies) {
        int titelLength = movies.stream()
                .map(Movie::getTitle)
                .max(Comparator.comparingInt(String::length))
                .map(String::length)
                .orElse(0);
        return titelLength;
    }

    public String getMostPopularActor(List<Movie> movies) {
        String mostPopular = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return mostPopular;
    }

    public void switchToHome() throws MovieApiException {
        //falls wir dieses unkreative Farbe wechseln durch ein vernünftiges Menü ersetzen: es ist noch einmal die farbe bei initialize oben :D
        homeBtn.setStyle("-fx-background-color: #00FF00;");
        watchlistBtn.setStyle("-fx-background-color: #f5c518;");

        resetFilter();

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();
        newMovieList.addAll(allMovies);
        if (movieListView != null) {
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }
        observableMovies = newMovieList;

    }

    public void switchToWatchlist() throws MovieApiException {
        homeBtn.setStyle("-fx-background-color: #f5c518;");
        watchlistBtn.setStyle("-fx-background-color: #00FF00;");

        resetFilter();

        observableMovies.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


}



