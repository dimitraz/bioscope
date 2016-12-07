import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.Rating;
import controllers.RecommenderAPI;
import models.Genre;
import models.Movie;
import models.User;

public class RatingTest { 
    User user;
    RecommenderAPI recommender;
    
    @Before 
    public void SetUp() {
        user = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        recommender = new RecommenderAPI();
        recommender.addUser(user);
    }

    @After
    public void TearDown() {
        user = null;
        recommender = null;
    }
    
    @Test
    public void testNewRating() {
        Rating rating = new Rating(1, 1, -3);
        assertEquals(rating.getUserID(), 1);
        assertEquals(rating.getMovieID(), 1);
        assertEquals(rating.getRating(), -3);
    }
    
    @Test
    public void testRatingEquality() {
        Rating rating = new Rating(0, 1, -5);
        Rating ratingTwo = new Rating (0, 1, -5);
        
        assertTrue(rating.equals(ratingTwo));
    }
    
    @Test
    public void testBoundaryRatings() {
        Rating rating = new Rating(0, 1, -5);
        Rating ratingTwo = new Rating (0, 2, -5);
        
        recommender.addRating(rating);
        recommender.addRating(ratingTwo);
        
        User user = recommender.getUser(0);
        assertEquals(user.getRatings().get(0), rating);
        assertEquals(user.getRatings().get(1), ratingTwo);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testIllegalRating() {
        Rating rating = new Rating(1, 1, -32);    
    }
    
    // @Test (expected = NullPointerException.class)
    public void testNullUser() {
        RecommenderAPI recommender = new RecommenderAPI();
        Rating rating = new Rating(-3, 1, -3);  
        User user = recommender.getUser(rating.getUserID());
    }
}
