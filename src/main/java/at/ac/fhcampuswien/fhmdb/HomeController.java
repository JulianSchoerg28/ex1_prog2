package at.ac.fhcampuswien.fhmdb;

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

    public List<Movie> allMovies = new ArrayList<>(MovieAPI.getMovies());
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        for (Movie movie:allMovies) {
            String year = movie.getReleaseYear();
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

        for (int i = 0; i <=10 ; i++) {
            ratingList.add(String.valueOf(i));
        }
        ratingComboBox.setItems(ratingList);





        searchBtn.setOnAction(actionEvent -> {
            ObservableList<Movie> filteredMovieList = FXCollections.observableArrayList();


            String query = searchField.getText();
            String genre = genreComboBox.getValue();
            String releaseYear = releaseYearBox.getValue();
            String rating = ratingComboBox.getValue();

            filteredMovieList.addAll(MovieAPI.filteredMovies(query, genre, releaseYear, rating));

            if(movieListView != null){
                movieListView.setItems(filteredMovieList);
                movieListView.setCellFactory(movieListView -> new MovieCell());
            }

            observableMovies = filteredMovieList;
        });



        resetBtn.setOnAction(actionEvent -> {
            resetFilter();
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
    }

    public void sortasc(ObservableList<Movie> observableMovies){
        List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
        observableMovies.setAll(sortedMovie);
    }
    public void sortdesc(ObservableList<Movie> observableMovies){
        List<Movie> sortedMovie = new ArrayList<>(observableMovies).stream().sorted(Comparator.comparing(Movie::getTitle).reversed()).collect(Collectors.toList());
        observableMovies.setAll(sortedMovie);
    }


    public void resetFilter(){

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

        if(movieListView != null){
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = newMovieList;
    }

    long countMoviesFrom(List<Movie> movies, String director){
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
                        int year = Integer.parseInt(movie.getReleaseYear());
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



//    String getMostPopularActor(List<Movie> movies){
//
//    }










}



