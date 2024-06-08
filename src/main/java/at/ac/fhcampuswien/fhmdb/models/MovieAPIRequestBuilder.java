package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class MovieAPIRequestBuilder {
    private String baseUrl;
    private List<String> queryParams;

    public MovieAPIRequestBuilder() {
        this.baseUrl = "https://prog2.fh-campuswien.ac.at/movies";
        this.queryParams = new ArrayList<>();
    }

    public MovieAPIRequestBuilder query(String query) {
        if (query != null) {
            queryParams.add("query=" + query);
        }
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        if (genre != null) {
            queryParams.add("genre=" + genre);
        }
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        if (releaseYear != null) {
            queryParams.add("releaseYear=" + releaseYear);
        }
        return this;
    }

    public MovieAPIRequestBuilder rating(String rating) {
        if (rating != null) {
            queryParams.add("ratingFrom=" + rating);
        }
        return this;
    }

    public String build() {
        if (queryParams.isEmpty()) {
            return baseUrl;
        }
        return baseUrl + "?" + String.join("&", queryParams);
    }


}
