package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
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
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;
    public List<Genre> allGenre = Genre.initializeGenre();

    public List<Movie> allMovies = Movie.initializeMovies(allGenre);


    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data


        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        ObservableList<Genre> genreObservableList = FXCollections.observableArrayList(allGenre);
            genreComboBox.setItems(genreObservableList);

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> {
            filter();
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

    public void filter(){
        Genre selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
        String input = searchField.getText();
        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();
        ObservableList<Movie> filteredMovieList = FXCollections.observableArrayList();

        if(selectedGenre != null){
            newMovieList.addAll(filterGenre(selectedGenre, allMovies));
        }else{
            newMovieList.addAll(allMovies);
        }

        if(input != null){
            filteredMovieList.addAll(searchbox(input, newMovieList));
        }else{
            filteredMovieList.addAll(newMovieList);
        }

        if(movieListView != null){
            movieListView.setItems(filteredMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = filteredMovieList;
    }

    public Set<Movie> filterGenre(Genre selectedGenre, List<Movie> moviestosort){
        Set<Movie> filtertMovies = new HashSet<>();
        for(Movie movie : moviestosort){
           if (movie.getGenre().contains(selectedGenre)){
               filtertMovies.add(movie);
           }
        }
        return filtertMovies;
    }

    public Set<Movie> searchbox(String input, ObservableList<Movie> moviesToFilter){
        Set<Movie> filtertMovies = new HashSet<>();
        for(Movie movie : moviesToFilter){
            //Strings nur zur besseren Übersicht, kann natürlich auch direkt im if überprüft werden
            String title = movie.getTitle().toLowerCase();
            String description = movie.getDescription().toLowerCase();
            if((title.contains(input.toLowerCase()) ||description.contains(input.toLowerCase()))){
                filtertMovies.add(movie);
            }
        }
        return filtertMovies;
    }
    public void resetFilter(){
        genreComboBox.setValue(null);
        searchField.setText(null);

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();

        newMovieList.addAll(allMovies);

        if(movieListView != null){
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = newMovieList;
    }




}



