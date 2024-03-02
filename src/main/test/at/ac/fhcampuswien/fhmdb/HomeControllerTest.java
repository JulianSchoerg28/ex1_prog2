package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    @Test
    public void testAddition() {
        int result = 2 + 2;
        assertEquals(4, result); // Überprüfe, ob das Ergebnis 4 ist
    }

    @Test
    public void testGenreName(){
        Genre Drama = new Genre("Drama");
        String result = Drama.getGenreName();
        assertEquals("Drama",result);
    }

    @Test
    public void test_sort_movies_asc(){
        //given
        HomeController homeController = new HomeController();
        Movie movie1 = new Movie("AA", "", new ArrayList<>());
        Movie movie2 = new Movie("AB", "", new ArrayList<>());
        Movie movie3 = new Movie("B", "", new ArrayList<>());

        List<Movie> actual = new ArrayList<>();
        actual.add(movie3);
        actual.add(movie1);
        actual.add(movie2);

        //when
        homeController.sortasc(actual);

        //then
        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);
        expected.add(movie3);
        assertEquals(expected, actual);
    }
    @Test
    public void test_sort_movies_desc(){
        //given
        HomeController homeController = new HomeController();
        Movie movie1 = new Movie("AA", "", new ArrayList<>());
        Movie movie2 = new Movie("AB", "", new ArrayList<>());
        Movie movie3 = new Movie("B", "", new ArrayList<>());

        List<Movie> actual = new ArrayList<>();
        actual.add(movie2);
        actual.add(movie1);
        actual.add(movie3);

        //when
        homeController.sortdesc(actual);

        //then
        List<Movie> expected = new ArrayList<>();
        expected.add(movie3);
        expected.add(movie2);
        expected.add(movie1);
        assertEquals(expected, actual);
    }

}