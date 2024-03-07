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

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

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
        for (Genre genre : allGenre) {
            genreComboBox.getItems().add(genre.getGenreName());
        }


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> {
            filterMovies();
            //searchbox();
        });

        resetBtn.setOnAction(actionEvent -> {
            resetFilter();
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
                sortasc(observableMovies);
            } else {
                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
                sortdesc(observableMovies);
            }
        });
    }

    public void sortasc(List<Movie> observableMovies) {
        Collections.sort(observableMovies);
    }

    public void sortdesc(List<Movie> observableMovies) {
        Collections.sort(observableMovies);
        Collections.reverse(observableMovies);
    }

    public void filterMovies(){
        String selectedGenre = genreComboBox.getValue();
        if(selectedGenre == null){
            return;
        }

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();
        newMovieList.addAll(filter(selectedGenre));

        if(movieListView != null){
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = newMovieList;

    }

    public Set<Movie> filter (String selectedGenre){
        Set<Movie> filtertMovies = new HashSet<>();

        for(Movie movie : allMovies){
            List<Genre> genres = movie.getGenre();
            for(Genre genre : genres){
                if((genre.getGenreName()).equals(selectedGenre)){
                    filtertMovies.add(movie);
                }
            }
        }
        return filtertMovies;
    }

    public void resetFilter(){
        //TODO: nochmal schauen, funkt nd immer zu 100%
        genreComboBox.setValue(null);

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();

        newMovieList.addAll(allMovies);

        if(movieListView != null){
            movieListView.setItems(newMovieList);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }

        observableMovies = newMovieList;
    }

    public void searchbox(){
        String input = searchField.getText();

    }


}



