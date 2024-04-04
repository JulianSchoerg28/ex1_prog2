package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class HomeControllerTest {


    @Test
    void requestFilteredMovies() {

        List<Movie> actual = MovieAPI.filteredMovies("The", "DRAMA", "1972", "5");

        String expected = "The Godfather";
        String actualTitle = null;
        for (Movie movie : actual) {
            actualTitle = movie.getTitle();
        }

        assertEquals(expected, actualTitle);

    }

    @Test
    void filterGenre() {

        List<Movie> returnValue = MovieAPI.filteredMovies(null, "ACTION", null, null);
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (!movie.getGenre().contains("ACTION")) {
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filterYear() {

        List<Movie> returnValue = MovieAPI.filteredMovies(null, null, "2008", null);
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (!Objects.equals(movie.getReleaseYear(), "2008")) {
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filterRating() {

        List<Movie> returnValue = MovieAPI.filteredMovies(null, null, null, "9");
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (Double.parseDouble(movie.getRating()) < 9.0) {
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filterQuerry() {

        String querry = "the";

        List<Movie> returnValue = MovieAPI.filteredMovies(querry, null, null, null);
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (!(movie.getTitle().toLowerCase().contains(querry.toLowerCase()) || movie.getDescription().toLowerCase().contains(querry.toLowerCase()))) {
                System.out.println(movie.getTitle());
                System.out.println(movie.getDescription());
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filterQuerry_ignores_Uppercase() {

        String querry = "THE";

        List<Movie> returnValue = MovieAPI.filteredMovies(querry, null, null, null);
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (!(movie.getTitle().toLowerCase().contains(querry.toLowerCase()) || movie.getDescription().toLowerCase().contains(querry.toLowerCase()))) {
                System.out.println(movie.getTitle());
                System.out.println(movie.getDescription());
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filterQuerry_ignores_Space() {

        String querry = "the ";

        List<Movie> returnValue = MovieAPI.filteredMovies(querry, null, null, null);
        Boolean correct = true;

        for (Movie movie : returnValue) {
            if (!(movie.getTitle().toLowerCase().contains(querry.toLowerCase()) || movie.getDescription().toLowerCase().contains(querry.toLowerCase()))) {
                System.out.println(movie.getTitle());
                System.out.println(movie.getDescription());
                correct = false;
            }
        }

        assertEquals(true, correct);
    }

    @Test
    void filter_ignores_empty_Sttring() {

        List<Movie> expected = MovieAPI.getMovies();
        List<Movie> actual = MovieAPI.filteredMovies("", "", "", "");

        List<String> actualTitle = new ArrayList<>();
        for (Movie movie : actual) {
            actualTitle.add(movie.getTitle());
        }
        List<String> expectedTitle = new ArrayList<>();
        for (Movie movie : expected) {
            expectedTitle.add(movie.getTitle());
        }

        Collections.sort(actualTitle);
        Collections.sort(expectedTitle);

        assertEquals(expectedTitle, actualTitle);
    }


}