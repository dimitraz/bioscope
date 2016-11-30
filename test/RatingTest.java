import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controllers.Rating;
import controllers.RecommenderAPI;
import models.Genre;
import models.Movie;
import models.User;

public class RatingTest { 
    @Test
    public void testNewRating() {
        Rating rating = new Rating(1, 1, -3);
        assertEquals(rating.getUserID(), 1);
        assertEquals(rating.getMovieID(), 1);
        assertEquals(rating.getRating(), -3);
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
