package models;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import exceptions.GenreLengthException;
import models.Genre;
import models.Movie;

// Right BICEP
// Right - Are the results right?
// B - Are all the boundary conditions CORRECT?
// I - Can you check inverse relationships?
// C - Can you cross-check results using other means?
// E - Can you force error conditions to happen?
// P - Are performance characteristics within bounds?
//  Conformance - Does the value conform to an expected format?
//  Ordering - Is the set of values ordered or unordered as appropriate?
//  Range - Is the value within reasonable minimum and maximum values?
//  Reference - Does the code reference anything external that isn't under direct control of the code itself?
//  Existence - Does the value exist (e.g., is non-null, nonzero, present in a set, etc.)?
//  Cardinality - Are there exactly enough values?
//  Time (absolute and relative) - Is everything happening in order? At the right time? In time?
public class MovieTest {;

    // Test basic user information
    @Test
    public void testAddMovie() throws GenreLengthException {
        String genres[] = {"0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "1", "1", "0", "1", "1", "1"};
        Movie movie = new Movie("Amelie", "2009", "http://", genres);
        assertEquals(movie.getId(), 4);
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
        
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        Movie movieTwo = new Movie("Kill Your Darlings", "2010", "http://", genres);
        assertEquals(movie, movieTwo);
    }
}
