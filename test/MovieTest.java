import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
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
public class MovieTest {
    private String[] genres;
    private Movie[] movies = {
        new Movie(1, "Toy Story", "2009", "url", genres),
        new Movie(2, "Life Aquatic with Steve Zissou", "2009", "url", genres),
        new Movie(3, "The Martian", "2009", "url", genres),
        new Movie(4, "Amelie", "2009", "url", genres)
    };
    
    // Test basic user information
    @Test
    public void addMovie() {
        Movie movie = new Movie(4, "Amelie", "2009", "url", genres);
        assertEquals(movie.getId(), 4);
        assertEquals(movie.getTitle(), "Amelie");
        assertEquals(movie.getReleaseDate(), "2009");
        assertEquals(movie.getUrl(), "url");
    }
}
