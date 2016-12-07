import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.Rating;
import controllers.RecommenderAPI;
import models.User;

public class RecommenderAPITest {
    private RecommenderAPI recommender;

    @Before
    public void setup() {
        recommender = new RecommenderAPI();
    }

    @After
    public void tearDown() {
        recommender = null;
    }

    @Test
    public void testGetUser() {
        // Create user
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(0, recommender.getUsers().size());
        
        // Add user
        recommender.addUser("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(1, recommender.getUsers().size());

        long id = fred.getID();
        assertEquals(fred, recommender.getUserByEmail("Cartoon@gmail.com"));
        assertEquals(fred, recommender.getUser(id));
    }

    @Test
    public void testGetUserEmpty() {
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(0, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(1, recommender.getUsers().size());
    }
    
    @Test
    public void testAddMovie() {
        fail();
    }
    
    @Test 
    public void testListMovies() {
        fail();
    }
    
    @Test
    public void testAddRating() {
        User user = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        Rating rating = new Rating(0, 1, -5);
        
        recommender.addUser(user);
        recommender.addRating(rating); 
        
        assertEquals(user.getRatings().get(0), rating);
    }
}