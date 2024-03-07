package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

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
        Movie movie1 = new Movie("Aa", "", new ArrayList<>());
        Movie movie2 = new Movie("Ab", "", new ArrayList<>());
        Movie movie3 = new Movie("Be", "", new ArrayList<>());

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

    @Test
    public void test_genre_filter(){
        //given
        HomeController homeController = new HomeController();
        Movie movie1 = new Movie("A", "", new ArrayList<>(Arrays.asList(new Genre("CRIME"), new Genre("HORROR"))));
        Movie movie2 = new Movie("B", "", new ArrayList<>(Arrays.asList(new Genre("HORROR"), new Genre("MYSTERY"))));
        Movie movie3 = new Movie("C", "", new ArrayList<>(Arrays.asList(new Genre("SPORT"))));

        List<Movie> actual = new ArrayList<>();
        actual.add(movie1);
        actual.add(movie2);
        actual.add(movie3);

        //when
        homeController.filter("HORROR");

        //then
        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);
        assertEquals(expected, actual);

    }

    @Test
    void query_no_input(){
        HomeController homeController = new HomeController();
        //given
        List<Movie> actual = new ArrayList<>();
        actual.add(new Movie("A", "abc", new ArrayList<>()));
        actual.add(new Movie("b", "abc", new ArrayList<>()));
        actual.add(new Movie("c", "cde", new ArrayList<>()));
        String query = null;
        
        //when
        //homeController.searchFieldfilter(actual, query);

        List<Movie> expected = new ArrayList<>();
        expected.add(new Movie("A", "abc", new ArrayList<>()));
        expected.add(new Movie("b", "abc", new ArrayList<>()));
        expected.add(new Movie("c", "cde", new ArrayList<>()));

        //then
        assertEquals(expected, actual);



    }

}