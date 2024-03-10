package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie implements Comparable<Movie> {
    private String title;
    private String description;
    private List<Genre> genres;


    // TODO add more properties here

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenre() {
        return genres;
    }

    public static List<Movie> initializeMovies(List<Genre> allGenre) {
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here

        Movie wolfOfWallStreet = new Movie("Wolf of Wall Street", "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.", Arrays.asList(allGenre.get(3), allGenre.get(5)));

        Movie inception = new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", Arrays.asList(allGenre.get(1), allGenre.get(17), allGenre.get(15)));

        Movie findingNemo = new Movie("Finding Nemo", "After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.", Arrays.asList(allGenre.get(2), allGenre.get(8), allGenre.get(13)));

        Movie shawshankRedemption = new Movie("The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", Arrays.asList(allGenre.get(6), allGenre.get(5)));

        Movie theGodfather = new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", Arrays.asList(allGenre.get(5), allGenre.get(6)));

        Movie theDarkKnight = new Movie("The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham. The Dark Knight must accept one of the greatest psychological and physical tests of his ability to fight injustice.", Arrays.asList(allGenre.get(5), allGenre.get(17), allGenre.get(0)));

        Movie interstellar = new Movie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", Arrays.asList(allGenre.get(15), allGenre.get(1), allGenre.get(6)));

        Movie spiritedAway = new Movie("Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", Arrays.asList(allGenre.get(2), allGenre.get(8), allGenre.get(9)));

        Movie lifeOfPi = new Movie("Life of Pi", "A young man who survives a disaster at sea is hurtled into an epic journey of adventure and discovery. While cast away, he forms an unexpected connection with another survivor: a fearsome Bengal tiger.", Arrays.asList(allGenre.get(1), allGenre.get(6), allGenre.get(7)));

        Movie gladiator = new Movie("Gladiator", "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.", Arrays.asList(allGenre.get(10), allGenre.get(0), allGenre.get(6)));

        Movie fightClub = new Movie("Fight Club", "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.", Arrays.asList(allGenre.get(6), allGenre.get(5), allGenre.get(4)));

        Movie theMatrix = new Movie("The Matrix", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", Arrays.asList(allGenre.get(15), allGenre.get(0), allGenre.get(17)));

        Movie forrestGump = new Movie("Forrest Gump", "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.", Arrays.asList(allGenre.get(6), allGenre.get(4), allGenre.get(13)));


        movies.add(wolfOfWallStreet);
        movies.add(inception);
        movies.add(findingNemo);
        movies.add(shawshankRedemption);
        movies.add(theGodfather);
        movies.add(theDarkKnight);
        movies.add(interstellar);
        movies.add(spiritedAway);
        movies.add(lifeOfPi);
        movies.add(gladiator);
        movies.add(fightClub);
        movies.add(theMatrix);
        movies.add(forrestGump);


        //0="ACTION"        1="ADVENTURE",      2="ANIMATION",
        //3="BIOGRAPHY",    4="COMEDY",         5="CRIME",
        //6="DRAMA",        7="DOCUMENTARY",    8="FAMILY",
        //9="FANTASY",      10="HISTORY",       11="HORROR",
        //12="MUSICAL",     13="MYSTERY",       14="ROMANCE",
        //15="SCIENCE_FICTION",                 16="SPORT",
        //17="THRILLER",    18="WAR",           19="WESTERN"

        return movies;
    }

    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
