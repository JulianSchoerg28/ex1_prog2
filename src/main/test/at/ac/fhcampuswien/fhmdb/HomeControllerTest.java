package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.MovieAPIRequestBuilder;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class HomeControllerTest {



    @Test
    void getLongestMovieTitelFromMany() throws MovieApiException {
        //given
        List<Movie> movies = MovieAPI.getMovies();
        int longestTitel = new HomeController().getLongestMovieTitel(movies);

        String longestTitelfromApi = movies.stream()
                .map(Movie::getTitle)
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        assertTrue(longestTitel == longestTitelfromApi.length());
    }

    @Test
    void getLongestMovieTitelFromEmptyList() throws MovieApiException {
        List<Movie> movies = MovieAPI.getMovies();
        int longestTitel = new HomeController().getLongestMovieTitel(movies);

        if(movies.isEmpty()) {
            assertEquals(0, longestTitel);
        }
    }
    @Test
    void getMoviesBetweenTwoYears() throws MovieApiException {
        HomeController homeController = new HomeController();
        List<Movie>movies = MovieAPI.getMovies();

        int startYear = 2008;
        int endYear = 2010;

        List<Movie> filteredMovies = homeController.getMoviesBetweenYears(movies, startYear, endYear);
        boolean match = filteredMovies.stream().allMatch(movie -> {
            int year = movie.getReleaseYear();
            return year >= startYear && year <= endYear;
        });
        assertTrue(match);
    }

    @Test
    void countMoviesFromDirectors() throws MovieApiException {
        List<Movie> movies = MovieAPI.getMovies();
        HomeController homeController = new HomeController();
        long count = homeController.countMoviesFrom(movies, "Christopher Nolan");
        assertEquals(2, count);

    }

    @Test
    void getMostPopularMovies() throws MovieApiException {
        List<Movie> movies = MovieAPI.getMovies();
        HomeController homeController = new HomeController();
        String mostPopular = homeController.getMostPopularActor(movies);
        assertNotNull(mostPopular);
    }


    @Test
    void requestbuilder() throws MovieApiException{

        MovieAPIRequestBuilder request = new MovieAPIRequestBuilder()
                .query("the")
                .genre("ACTION")
                .releaseYear("2008")
                .rating("8");

        String url = request.build();
        List<Movie> movies = MovieAPI.filteredMovies(url);
        for (Movie movie: movies) {
            System.out.println(movie.getTitle());
        }
    }






}