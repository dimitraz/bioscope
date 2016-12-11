import static org.junit.Assert.*;

import java.io.File;
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
import utils.JSONSerializer;
import utils.SerializerInterface;

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
        User fred = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(0, recommender.getUsers().size());
        
        // Add user
        recommender.addUser("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(1, recommender.getUsers().size());
        
        long id = Iterables.get(recommender.getUsers(), 0).getId();
        assertEquals(fred, recommender.getUserByUsername("freddy"));
        assertEquals(fred, recommender.getUser(id));
    }

    @Test
    public void testGetUsers() {
        assertEquals(0, recommender.getUsers().size());
        
        User fred = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        recommender.addUser(fred);
        assertEquals(1, recommender.getUsers().size());
        
        User jack = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        recommender.addUser(jack);
        assertEquals(2, recommender.getUsers().size());
        
        Collection<User> users = recommender.getUsers();
        assertEquals(Iterables.get(users, 0), fred); 
        assertEquals(Iterables.get(users, 1), jack); 
    }
    
    @Test
    public void testGetUsersEmpty() {
        User fred = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(0, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(1, recommender.getUsers().size());
    }
    
    @Test
    public void testRemoveUser() {
        User jack = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        recommender.addUser(jack);
        assertEquals(Iterables.get(recommender.getUsers(), 0), jack); 
        
        recommender.removeUser(jack.getId());
        assertEquals(recommender.getUsers().size(), 0);
        assertNull(recommender.getUser(jack.getId()));
        assertNull(recommender.getUserByUsername(jack.getUsername()));
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
    
    // Rating methods
    @Test
    public void testAddRating() throws Exception {
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        User user = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        Movie movie = new Movie(0l, "Kill Your Darlings", "2010", "http://", genres);
        recommender.addUser(user);
        recommender.addMovie(movie);
        assertEquals(recommender.getUsers().size(), 1);
        
        long idU = Iterables.get(recommender.getUsers(), 0).getId();
        long idM = Iterables.get(recommender.getMovies(), 0).getId();
        
        Rating rating = new Rating(idU, idM, -5);
        recommender.addRating(rating); 
        assertEquals(user.getRatings().get(0), rating);
    }
    
    @Test
    public void testUserRatings() {
        fail();
    }
    
    @Test
    public void testMovieRatings() {
        fail();
    }
    
    // @Test
    public void testUserRecommendations() throws Exception {      
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        Movie movie1 = new Movie(1, "Kill Your Darlings", "2010", "http://", genres);
        Movie movie2 = new Movie(2, "Kill Your", "2010", "http://", genres);
        Movie movie3 = new Movie(3, "Kill Darlings", "2010", "http://", genres);
        recommender.addMovie(movie1);
        recommender.addMovie(movie2);
        recommender.addMovie(movie3);
        
        User jim = new User("Jim", "Halpert", "jimbo", "@dundermifflin123", 32);
        User creed = new User("Creed", "Bratton", "creed", "@dundermifflin123", 60);
        recommender.addUser(jim);
        recommender.addUser(creed);
        
        Rating rating1 = new Rating(jim.getId(), 1, 5);
        Rating rating2 = new Rating(jim.getId(), 2, 4);
        Rating rating3 = new Rating(jim.getId(), 3, 3);
        
        Rating rating4 = new Rating(creed.getId(), 1, 5);
        Rating rating5 = new Rating(creed.getId(), 2, 4);
        Rating rating6 = new Rating(creed.getId(), 3, 3);
        
        recommender.addRating(rating1);
        recommender.addRating(rating2);
        recommender.addRating(rating3);
        recommender.addRating(rating4);
        recommender.addRating(rating5);
        recommender.addRating(rating6);
        
        assertEquals(Iterables.get(recommender.getUserRecommendations(jim.getId()), 0), movie1);
        assertEquals(Iterables.get(recommender.getUserRecommendations(jim.getId()), 1), movie2);
        assertEquals(Iterables.get(recommender.getUserRecommendations(jim.getId()), 2), movie3);
    }
    
    @Test
    public void testGetTopTen() {
        
    }
}