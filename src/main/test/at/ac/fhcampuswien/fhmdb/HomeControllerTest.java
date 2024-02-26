package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import org.junit.jupiter.api.Test;

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


}