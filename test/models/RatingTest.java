package models;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.RecommenderAPI;
import exceptions.GenreLengthException;
import models.Genre;
import models.Movie;
import models.Rating;
import models.User;

public class RatingTest { 
    User user;
    Movie movie;
    RecommenderAPI recommender;
    
    @Before 
    public void SetUp() throws GenreLengthException {
        user = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        recommender = new RecommenderAPI();
        recommender.addUser(user);
        
        String genres[] = {"0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "1", "1", "0", "1", "1", "0"};
        movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        recommender.addMovie(movie);
    }

    @After
    public void TearDown() {
        user = null;
        recommender = null;
    }
    
    @Test
    public void testNewRating() {
        Rating rating = new Rating(1, 1, -3);
        assertEquals(rating.getUserId(), 1);
        assertEquals(rating.getMovieId(), 1);
        assertEquals(rating.getRating(), -3);
    }
    
    @Test
    public void testRatingEquality() {
        Rating rating = new Rating(0, 1, -5);
        Rating ratingTwo = new Rating (0, 1, -5);
        
        assertTrue(rating.equals(ratingTwo));
    }
    
    @Test
    public void testBoundaryRatings() throws Exception {
        Rating rating = new Rating(user.getId(), movie.getId(), -5);
        Rating ratingTwo = new Rating (user.getId(), movie.getId(), 5);
        
        recommender.addRating(rating);
        recommender.addRating(ratingTwo);
        
        assertEquals(user.getRatings().get(0), rating);
        assertEquals(user.getRatings().get(1), ratingTwo);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testIllegalRating() {
        Rating rating = new Rating(1, 1, -32);    
    }
 
}
