package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieAPITest {
    @Test
    public void testGetMoviesWithInvalidURL_throwsMovieApiException() {
        // Rufe die Methode direkt auf und überprüfe, ob die erwartete Ausnahme geworfen wird
        assertThrows(MovieApiException.class, () -> MovieAPI.getMovies());
    }
}
