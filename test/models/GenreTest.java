package models;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import models.Genre;
import models.Movie;

public class GenreTest {
    String[] genres;
    
    @Test
    public void testGenreList() {
        Genre genre = new Genre();
        assertEquals(genre.parseData().get(0), "unknown");
        assertEquals(genre.parseData().get(18), "Western");
    }
}
