package at.ac.fhcampuswien.fhmdb.exceptions;

public class MovieApiException extends Exception{
    public MovieApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieApiException(String message) {
        super(message);
    }
}
