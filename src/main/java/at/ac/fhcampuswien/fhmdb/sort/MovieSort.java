package at.ac.fhcampuswien.fhmdb.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public class MovieSort {
    private SortState currentState;


    public MovieSort() {
        this.currentState = new UnsortedState();
    }

    public void setState(SortState state) {
        this.currentState = state;
    }

    public void sort(List<Movie> movies) {
        currentState.sort(movies);
    }

}
