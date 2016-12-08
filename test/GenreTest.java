import static org.junit.Assert.assertEquals;

import org.junit.Test;

import models.Genre;
import models.Movie;

public class GenreTest {
    String[] genres;
    
    @Test
    public void testGenreList() {
        // new Movie(1, "Toy Story", "2009", "url", genres);
        Genre genre = new Genre();
        assertEquals(genre.parseData().get(0), "unknown");
    }
}
