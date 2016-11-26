import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void testUser() {
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(0, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(1, recommender.getUsers().size());

        assertEquals(fred, recommender.getUserByEmail("Cartoon@gmail.com"));
    }

    @Test
    public void testUserEmpty() {
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(0, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(1, recommender.getUsers().size());
    }
}