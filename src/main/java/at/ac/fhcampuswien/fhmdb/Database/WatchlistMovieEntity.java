package at.ac.fhcampuswien.fhmdb.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity extends MovieEntity {
    @DatabaseField(generatedId = true)
    long id;
    @DatabaseField
    String apiID;

    public WatchlistMovieEntity(){}

    public WatchlistMovieEntity(String apiID) {
        this.apiID = apiID;
    }


}
