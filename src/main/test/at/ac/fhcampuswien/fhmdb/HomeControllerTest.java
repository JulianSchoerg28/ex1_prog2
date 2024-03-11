package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class HomeControllerTest {

    @Test
    void test_GenreName(){
        Genre Drama = new Genre("Drama");
        String result = Drama.getGenreName();
        assertEquals("Drama",result);
    }

    @Test
    void test_sort_movies_asc(){
        //given
        HomeController homeController = new HomeController();
        Movie movie1 = new Movie("AA", "", new ArrayList<>());
        Movie movie2 = new Movie("AB", "", new ArrayList<>());
        Movie movie3 = new Movie("B", "", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
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
    void test_sort_movies_desc(){
        //given
        HomeController homeController = new HomeController();
        Movie movie1 = new Movie("Aa", "", new ArrayList<>());
        Movie movie2 = new Movie("Ab", "", new ArrayList<>());
        Movie movie3 = new Movie("Be", "", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
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
    void genre_filter(){
        //given
        HomeController homeController = new HomeController();

        Genre crime = new Genre("CRIME");
        Genre horror = new Genre("HORROR");
        Genre mystery = new Genre("MYSTERY");
        Genre sport = new Genre("SPORT");

        Movie movie1 = new Movie("A", "", new ArrayList<>(Arrays.asList(crime, horror)));
        Movie movie2 = new Movie("B", "", new ArrayList<>(Arrays.asList(horror, mystery)));
        Movie movie3 = new Movie("C", "", new ArrayList<>(Arrays.asList(sport)));

        List<Movie> actual = new ArrayList<>();
        List<Movie> movielist = new ArrayList<>();

        movielist.add(movie1);
        movielist.add(movie2);
        movielist.add(movie3);

        //when
        actual.addAll(homeController.filterGenre(horror, movielist));
        actual.stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());


        //then
        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);


        assertEquals(expected, actual);

    }

    @Test
    void searchbox_filter_Titel(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc", "abc", new ArrayList<>());
        Movie movie2 = new Movie("Bcd", "uio", new ArrayList<>());
        Movie movie3 = new Movie("Cde", "jkl", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "Abc";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_filters_description(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc", "def", new ArrayList<>());
        Movie movie2 = new Movie("Bcd", "defgh", new ArrayList<>());
        Movie movie3 = new Movie("Cde", "jkl", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "def";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie2);
        expected.add(movie1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_filters_titel_and_descripton(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc defg", "bcd", new ArrayList<>());
        Movie movie2 = new Movie("Cde", "jkl", new ArrayList<>());
        Movie movie3 = new Movie("Bcd", "Abc def", new ArrayList<>());


        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "abc";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie3);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_with_uppercaseletters(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc", "abc", new ArrayList<>());
        Movie movie2 = new Movie("Bcd", "abc", new ArrayList<>());
        Movie movie3 = new Movie("Cde", "jkl", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "AB";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_with_lowercaseletters(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc", "abc", new ArrayList<>());
        Movie movie2 = new Movie("Bcd", "abc", new ArrayList<>());
        Movie movie3 = new Movie("Cde", "jkl", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "ab";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_words_with_space_between(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("Abc", "abc def", new ArrayList<>());
        Movie movie2 = new Movie("Bcd", "abc", new ArrayList<>());
        Movie movie3 = new Movie("Abc def", "jkl", new ArrayList<>());

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        allMovies.add(movie1);
        allMovies.add(movie2);
        allMovies.add(movie3);

        String query = "abc def";

        //when
        actual.addAll(homeController.searchbox(query,allMovies));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie1);
        expected.add(movie3);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void searchbox_and_genre_filter(){
        //given
        HomeController homeController = new HomeController();

        Genre crime = new Genre("CRIME");
        Genre horror = new Genre("HORROR");
        Genre mystery = new Genre("MYSTERY");
        Genre sport = new Genre("SPORT");

        Movie movie1 = new Movie("A", "ac", new ArrayList<>(Arrays.asList(crime, horror)));
        Movie movie2 = new Movie("B", "bc", new ArrayList<>(Arrays.asList(horror, mystery)));
        Movie movie3 = new Movie("C", "ab", new ArrayList<>(Arrays.asList(sport)));

        ObservableList<Movie> actual = FXCollections.observableArrayList();
        List<Movie> movielist = new ArrayList<>();
        ObservableList<Movie> filteredList = FXCollections.observableArrayList();

        movielist.add(movie1);
        movielist.add(movie2);
        movielist.add(movie3);

        String query = "B";

        //when
        filteredList.addAll(homeController.filterGenre(horror,movielist));
        actual.addAll(homeController.searchbox(query,filteredList));

        List<Movie> expected = new ArrayList<>();
        expected.add(movie2);

        //then
        assertEquals(expected, actual);

    }
    @Test
    void filter_reset(){
        //given
        HomeController homeController = new HomeController();

        Movie movie1 = new Movie("A", "A", new ArrayList<>());
        Movie movie2 = new Movie("B", "B", new ArrayList<>());
        Movie movie3 = new Movie("C", "C", new ArrayList<>());
        homeController.allMovies.add(movie1);
        homeController.allMovies.add(movie2);
        homeController.allMovies.add(movie3);



        homeController.observableMovies = FXCollections.observableArrayList();

        homeController.resetFilter();

        assertEquals(homeController.allMovies.size(), homeController.observableMovies.size());
        assertTrue(homeController.observableMovies.containsAll(homeController.allMovies));
    }







}