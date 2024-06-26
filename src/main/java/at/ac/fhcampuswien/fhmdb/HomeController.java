package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.Database.*;
import at.ac.fhcampuswien.fhmdb.ObserverPattern.Observer;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.MovieAPIRequestBuilder;
import at.ac.fhcampuswien.fhmdb.sort.AscendingSort;
import at.ac.fhcampuswien.fhmdb.sort.DescendingSort;
import at.ac.fhcampuswien.fhmdb.sort.MovieSort;
import at.ac.fhcampuswien.fhmdb.sort.UnsortedState;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;



public class HomeController implements Initializable, Observer {
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

    public  List<MovieEntity> database = new ArrayList<>();
    public List<Movie> allMovies = new ArrayList<>();
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    public WatchlistRepository watchlistRepository;
    private static boolean homeScreen = true;

    private MovieSort movieSort = new MovieSort();
    private AscendingSort ascendingSort = new AscendingSort();
    private DescendingSort descendingSort = new DescendingSort();
    private UnsortedState unsortedState = new UnsortedState();

    private static HomeController instance;

    private HomeController() {}

    public static synchronized HomeController getInstance(){
        if (instance == null) {
            instance = new HomeController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        movieListView.setCellFactory(lv -> new MovieCell(onAddToWatchlistClicked));
        try {
            watchlistRepository = new WatchlistRepository();
            try{
                watchlistRepository.addObserver(this);
            }catch (Exception e){
                showAlert("Observer Error", "Failed to add Observer");
            }

        } catch (DatabaseException e) {
            showAlert("Database Error","Failed to initialize the watchlist: "+e.getCause());
        }



        try {
            allMovies = MovieAPI.getMovies();
            if (!allMovies.isEmpty()){
                updateDB(allMovies);
            }

        } catch (MovieApiException e) {
            showAlert("MovieAPI Error", "Unable to load movies from API: " + e.getMessage());
            try {
                allMovies = getMoviesfromDB();
            } catch (DatabaseException ex) {
                showAlert("Database Error", "Failed to update movies in database: " + ex.getMessage());
            }

        }
            observableMovies.addAll(allMovies);

            movieListView.setItems(observableMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked));

            initializeGenreComboBox();
            initializeReleaseYearBox();
            initializeRatingComboBox();

            setupUIListeners();

            homeBtn.setStyle("-fx-background-color: #00FF00");

            movieSort.setState(unsortedState);

    }

    private List<Movie> getMoviesfromDB () throws DatabaseException {
        List<Movie> movies = new ArrayList<>();
        MovieRepository repository = new MovieRepository();
        movies = repository.MovieEntityToMovie(repository.getAllMovies());

        return movies;
    }

    private void updateDB (List<Movie> movies) {
        MovieRepository repository = null;
        try {
            repository = new MovieRepository();
            repository.addAllMovies(movies);
        } catch (DatabaseException e) {
            showAlert("Database Error", "Failed to update Database: "+ e.getMessage());
        }
    }

    private void initializeGenreComboBox() {

        // initialize GenreComboBox
        genreComboBox.setPromptText("Filter by Genre");
        String[] genreArray = {"ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY",
                "HORROR", "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION",
                "SPORT", "THRILLER", "WAR", "WESTERN"};

        ObservableList<String> genreObservableList = FXCollections.observableArrayList(genreArray);
        genreComboBox.setItems(genreObservableList);
    }


    private void initializeReleaseYearBox() {
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
    }

    private void initializeRatingComboBox() {

        //initialize ratingComboBox
        ratingComboBox.setPromptText("Filter by Rating");
        ObservableList<String> ratingList = FXCollections.observableArrayList();

        for (int i = 0; i <= 10; i++) {
            ratingList.add(String.valueOf(i));
        }
        ratingComboBox.setItems(ratingList);
    }

    private void setupUIListeners() {
        searchBtn.setOnAction(actionEvent -> performSearch());
        resetBtn.setOnAction(actionEvent -> resetFilter());
        sortBtn.setOnAction(actionEvent -> toggleSortOrder());

    }

    private void performSearch() {
        if (homeScreen) {
            ObservableList<Movie> filteredMovieList = FXCollections.observableArrayList();

            String requestURL = new MovieAPIRequestBuilder()
                    .genre(genreComboBox.getValue())
                    .query(searchField.getText())
                    .releaseYear(releaseYearBox.getValue())
                    .rating(ratingComboBox.getValue())
                    .build();

            try {
                filteredMovieList.addAll(MovieAPI.filteredMovies(requestURL));
            } catch (MovieApiException e) {
                showAlert("Error", "Failed to filer movies from API: " + e.getMessage());
            }

            if (movieListView != null) {
                movieListView.setItems(filteredMovieList);
                movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked));
            }

            observableMovies = filteredMovieList;
            movieSort.sort(observableMovies);
        }
    }

    private void toggleSortOrder() {
        if (sortBtn.getText().equals("Sort (asc)")) {
                sortBtn.setText("Sort (desc)");
                movieSort.setState(ascendingSort);
            } else {
                sortBtn.setText("Sort (asc)");
                movieSort.setState(descendingSort);
            }
        movieSort.sort(observableMovies);
    }


/*
     public void sortasc(ObservableList<Movie> observableMovies) {
       List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
       observableMovies.setAll(sortedMovie);
    }

    public void sortdesc(ObservableList<Movie> observableMovies) {
        List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle).reversed()).collect(Collectors.toList());
        observableMovies.setAll(sortedMovie); }
*/




    public void resetFilter() {

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

        try {
            newMovieList.addAll(MovieAPI.getMovies());
        }catch(MovieApiException e){
            showAlert("Error", "Unable to load movies from API" + e.getMessage());
            newMovieList.clear();
        }

        if (movieListView != null) {
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked));
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

    public void switchToHome() {
        try {
            homeScreen = true;
            disableButtons(false);
            homeBtn.setStyle("-fx-background-color: #00FF00;");
            watchlistBtn.setStyle("-fx-background-color: #f5c518;");

            resetFilter();
            ObservableList<Movie> newMovieList = FXCollections.observableArrayList();
            newMovieList.addAll(allMovies);
            if (movieListView != null) {
                movieListView.setItems(newMovieList);
                movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked));
            }
            observableMovies = newMovieList;
        } catch (Exception e) {
            showAlert("Error","Error!");
        }
    }

    public void switchToWatchlist() {
        homeScreen = false;
        disableButtons(true);
        homeBtn.setStyle("-fx-background-color: #f5c518;");
        watchlistBtn.setStyle("-fx-background-color: #00FF00;");

        resetFilter();

        observableMovies.clear();

        try {
            ObservableList<Movie> watchlist = FXCollections.observableArrayList();
            WatchlistRepository repository = new WatchlistRepository();
            List<WatchlistMovieEntity> watchlistEntity = repository.getWatchlist();

            //sieve out duplicates
            List<WatchlistMovieEntity> distinctentity = watchlistEntity.stream()
                    .distinct()
                    .toList();


            for (WatchlistMovieEntity entity: distinctentity ) {
                Movie wantedmovie;

                //find corresponding movie objects to ApiId
                wantedmovie = allMovies.stream()
                        .filter(movie -> Objects.equals(movie.getId(), entity.getApiID()))
                        .findFirst()
                        .orElse(null);

                //add found movie to List
                if (wantedmovie != null){
                    watchlist.add(wantedmovie);
                }
            }

            if (movieListView != null) {
                movieListView.setItems(watchlist);
                movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked));
            }

            observableMovies = watchlist;

        } catch (DatabaseException e) {
            showAlert("Database Error", "Unable to access watchlist" + e.getMessage());
        }catch (Exception e){
            showAlert("Error","Error!");
        }

    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void watchlistAlert(String titel, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean isHomeScreen() {
        return homeScreen;
    }

//    private List<Movie> loadAllMovies() {
//        try {
//            return MovieAPI.getMovies();
//        } catch (MovieApiException e) {
//            showAlert("Error", "Unable to load movies from API: " + e.getMessage());
//            return new ArrayList<>();
//        }
//    }

    public void disableButtons (boolean maybe){
        searchField.setDisable(maybe);
        genreComboBox.setDisable(maybe);
        releaseYearBox.setDisable(maybe);
        ratingComboBox.setDisable(maybe);
        searchBtn.setDisable(maybe);
        resetBtn.setDisable(maybe);
    }

/*    private void setupDatabase() throws DatabaseException {
        try {
            DatabaseManager.getDatabase();
        }catch (DatabaseException e){
            throw new DatabaseException("Could not get Database", e.getCause());
        }
    }*/

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) -> {
        WatchlistRepository watchlistRepository = new WatchlistRepository();
        try {
            Movie movie = (Movie) clickedItem;
            if (watchlistRepository.isInWatchlist(movie) && !homeScreen) {
                watchlistRepository.removeFromWatchlist(movie);
                switchToWatchlist();

            } else {
                watchlistRepository.addToWatchlist(movie);
            }
        }catch(ClassCastException cce){
            throw new DatabaseException(cce.getMessage(), cce.getCause());
        }catch (DatabaseException e){
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    };


    @Override
    public void update(String message) {
        Platform.runLater(() -> watchlistAlert("Watchlist Update", message));
    }


}



