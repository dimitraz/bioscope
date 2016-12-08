import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;

import controllers.RecommenderAPI;
import models.Movie;
import models.Rating;
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

    // User methods
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
    public void testGetUsers() {
        assertEquals(0, recommender.getUsers().size());
        
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        recommender.addUser(fred);
        assertEquals(1, recommender.getUsers().size());
        
        User jack = new User("Jack", "Lantern", "234", "M", "timburton@gmail.com", 22);
        recommender.addUser(jack);
        assertEquals(2, recommender.getUsers().size());
        
        Collection<User> users = recommender.getUsers();
        assertEquals(Iterables.get(users, 0), fred); 
        assertEquals(Iterables.get(users, 1), jack); 
    }
    
    @Test
    public void testGetUsersEmpty() {
        User fred = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(0, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        assertEquals(1, recommender.getUsers().size());
    }
    
    @Test
    public void testRemoveUser() {
        User jack = new User("Jack", "Lantern", "234", "M", "timburton@gmail.com", 22);
        recommender.addUser(jack);
        assertEquals(Iterables.get(recommender.getUsers(), 0), jack); 
        
        recommender.removeUser(jack.getID());
        assertEquals(recommender.getUsers().size(), 0);
        assertNull(recommender.getUser(jack.getID()));
        assertNull(recommender.getUserByEmail(jack.getEmail()));
    }

    // Movie methods
    @Test
    public void testGetMovie() {
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        // Create Movie
        Movie movie = new Movie(0l, "Kill Your Darlings", "2010", "http://", genres);
        
        // Add movie
        recommender.addMovie(movie);
        
        long id = movie.getId();
        assertEquals(recommender.getMovies().size(), 1);
        assertEquals(recommender.getMovie(id), movie);
    }
    
    @Test 
    public void testListMovies() {
        fail();
    }
    
    // Rating methods
    @Test
    public void testAddRating() {
        User user = new User("Fred", "Flinstone", "234", "M", "Cartoon@gmail.com", 29);
        Rating rating = new Rating(0, 1, -5);
        
        recommender.addUser(user);
        recommender.addRating(rating); 
        
        assertEquals(user.getRatings().get(0), rating);
    }
}