package at.ac.fhcampuswien.fhmdb;

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
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.lang.reflect.Type;




public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;
/*    public List<Genre> allGenre = Genre.initializeGenre();

    public List<Movie> allMovies = MovieAPI.getMovies();*/
    public ObservableList<Genre> allGenre = FXCollections.observableArrayList(Genre.initializeGenre()); // Typ der Liste auf Genre geändert
    public List<Movie> allMovies = new ArrayList<>(MovieAPI.getMovies());

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allMovies.addAll(allMovies);
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
//            filter();
//          brauchte nur was zum testen
           long i = countMoviesFrom(allMovies, "Martin Scorsese");
            System.out.println(i);
        });

        resetBtn.setOnAction(actionEvent -> {
//            resetFilter();
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

        if (genreComboBox != null) {
            genreComboBox.setValue(null);
        }
        if (searchField != null) {
            searchField.setText("");
        }

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();

        newMovieList.addAll(allMovies);

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






}



