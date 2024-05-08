package at.ac.fhcampuswien.fhmdb.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;


@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    String apiID;


    public long getId() {
        return id;
    }

    public String getApiID() {
        return apiID;
    }

    public WatchlistMovieEntity(){}

    public WatchlistMovieEntity(String apiID) {
        this.apiID = apiID;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WatchlistMovieEntity movie = (WatchlistMovieEntity) obj;
        return (apiID != null) ? apiID.equals(movie.apiID) : (movie.apiID == null);
    }

    @Override
    public int hashCode() {
        return apiID != null ? apiID.hashCode() : 0;
    }

}
