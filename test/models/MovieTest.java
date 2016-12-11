package models;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Iterables;

import controllers.RecommenderAPI;
import exceptions.GenreLengthException;
import models.Genre;
import models.Movie;

public class MovieTest {;

    // Test basic user information
    @Test
    public void testAddMovie() throws GenreLengthException {
        String genres[] = {"0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "1", "1", "0", "1", "1", "1"};
        Movie movie = new Movie("Amelie", "2009", "http://", genres);
        assertEquals(movie.getTitle(), "Amelie");
        assertEquals(movie.getReleaseDate(), "2009");
        assertEquals(movie.getUrl(), "http://");
    }
    
    @Test (expected = GenreLengthException.class)
    public void testIllegalGenreNumber() throws GenreLengthException {
        String genres[] = {"0", "1"};
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
    }
    
    @Test
    public void testConvertGenres() throws GenreLengthException {
        String genres[] = {"0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "1", "1", "0", "1", "1", "0"};
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        
        List<String> convertGenres = movie.getGenres(); 
        assertEquals(convertGenres.get(0), "Adventure");
        assertEquals(convertGenres.get(1), "Crime");
        assertEquals(convertGenres.get(2), "Film-Noir");
        assertEquals(convertGenres.get(3), "Mystery");
        assertEquals(convertGenres.get(4), "Romance");
        assertEquals(convertGenres.get(5), "Thriller");
        assertEquals(convertGenres.get(6), "War");
        assertEquals(convertGenres.size(), 7);
    }
    
    @Test
    public void testEquals() throws GenreLengthException {
        String genres[] = {"0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "1", "1", "0", "1", "1", "0"};
        
        RecommenderAPI recommender = new RecommenderAPI(null);
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        recommender.addMovie(movie);
        Collection<Movie> movies = recommender.getMovies();
        Movie movie2  = Iterables.get(movies, 0);
        
        assertEquals(movie, movie2);
    }
}
