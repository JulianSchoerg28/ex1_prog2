package at.ac.fhcampuswien.fhmdb.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AscendingSort implements SortState{

    @Override
    public void sort(List<Movie> movies) {
        List<Movie> sortedMovie = new ArrayList<>(movies).stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
        movies.clear();
        movies.addAll(sortedMovie);
    }
}
