package controllers;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;

import controllers.RecommenderAPI;
import exceptions.GenreLengthException;
import models.Movie;
import models.Rating;
import models.User;
import utils.JSONSerializer;
import utils.SerializerInterface;

public class RecommenderAPITest {
    private RecommenderAPI recommender;

    @Before
    public void setup() throws GenreLengthException {
        recommender = new RecommenderAPI();
        
        User creed = new User("Creed", "Bratton", "creed", "@dundermifflin123", 60);
        User jim = new User("Jim", "Halpert", "jimbo", "@dundermifflin123", 32);
        recommender.addUser(creed);
        recommender.addUser(jim);
        
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        recommender.addMovie(movie);
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
        assertEquals(2, recommender.getUsers().size());
        
        // Add user
        recommender.addUser("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(3, recommender.getUsers().size());
        
        long id = Iterables.get(recommender.getUsers(), 0).getId();
        assertEquals(fred, recommender.getUserByUsername("freddy"));
        assertEquals(fred, recommender.getUser(id));
    }

    @Test
    public void testGetUsers() {
        assertEquals(2, recommender.getUsers().size());
        
        User fred = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        recommender.addUser(fred);
        assertEquals(3, recommender.getUsers().size());
        
        User jack = new User("Jack", "Pumpkin King", "lantern", "Hall0ween", 22);
        recommender.addUser(jack);
        assertEquals(4, recommender.getUsers().size());
        
        Collection<User> users = recommender.getUsers();
        assertEquals(Iterables.get(users, 2), fred); 
        assertEquals(Iterables.get(users, 3), jack); 
    }
    
    @Test
    public void testGetUsersEmpty() {
        User fred = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(2, recommender.getUsers().size());
        
        recommender.addUser("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(3, recommender.getUsers().size());
    }
    
    @Test
    public void testRemoveUser() {
        User jack = new User("Jack", "Pumpkin King", "lantern", "Hall0ween", 22);
        recommender.addUser(jack);
        assertEquals(Iterables.get(recommender.getUsers(), 2), jack); 
        
        recommender.removeUser(jack.getId());
        assertEquals(recommender.getUsers().size(), 2);
        assertNull(recommender.getUser(jack.getId()));
        assertNull(recommender.getUserByUsername(jack.getUsername()));
    }

    // Movie methods
    @Test
    public void testGetMovie() throws GenreLengthException { 
        Movie movie = Iterables.get(recommender.getMovies(), 0);
        long id = movie.getId();
        assertEquals(recommender.getMovies().size(), 1);
        assertEquals(recommender.getMovie(movie.getId()), movie);
    }
    
    // Rating methods
    @Test
    public void testAddRating() throws Exception, GenreLengthException {
        long idU = Iterables.get(recommender.getUsers(), 0).getId();
        long idM = Iterables.get(recommender.getMovies(), 0).getId();
        
        Rating rating = new Rating(idU, idM, -5);
        recommender.addRating(rating); 
        assertEquals(Iterables.get(recommender.getUsers(), 0).getRatings().get(0), rating);
    }
    
    @Test
    public void testAddRatings() throws GenreLengthException, Exception {
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        Movie movie = new Movie("Kill Your Darlings", "2010", "http://", genres);
        recommender.addMovie(movie);
        
        User creed = new User("Creed", "Bratton", "creed", "@dundermifflin123", 60);
        recommender.addUser(creed);
        
        Rating rating1 = new Rating(creed.getId(), movie.getId(), 2); 
        recommender.addRating(rating1);
        
        assertEquals(creed.getRatings().size(), 1);
        assertEquals(movie.getRatings().size(), 1);
        assertEquals(creed.getRatings().get(0), rating1);
        assertEquals(movie.getRatings().get(0), rating1);
    }
    
    @Test
    public void testUserRecommendations() throws Exception {      
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        Movie movie1 = new Movie("Kill Your Darlings", "2010", "http://", genres);
        Movie movie2 = new Movie("Kill Your", "2010", "http://", genres);
        Movie movie3 = new Movie("Kill Darlings", "2010", "http://", genres);
        recommender.addMovie(movie1);
        recommender.addMovie(movie2);
        recommender.addMovie(movie3);
        
        User jim = new User("Jim", "Halpert", "jimbo", "@dundermifflin123", 32);
        User creed = new User("Creed", "Bratton", "creed", "@dundermifflin123", 60);
        recommender.addUser(jim);
        recommender.addUser(creed);
        
        Rating rating1 = new Rating(jim.getId(), movie1.getId(), 5);
        // Rating rating2 = new Rating(jim.getId(), movie2.getId(), 4);
        // Rating rating3 = new Rating(jim.getId(), movie3.getId(), 3);
        
        Rating rating4 = new Rating(creed.getId(), movie1.getId(), 5);
        Rating rating5 = new Rating(creed.getId(), movie2.getId(), 4);
        Rating rating6 = new Rating(creed.getId(), movie3.getId(), 3);
        
        recommender.addRating(rating1);
        // recommender.addRating(rating2);
        // recommender.addRating(rating3);
        recommender.addRating(rating4);
        recommender.addRating(rating5);
        recommender.addRating(rating6);
        
        assertEquals(recommender.getUserRecommendations(jim.getId()).size(), 2);
        assertEquals(Iterables.get(recommender.getUserRecommendations(jim.getId()), 0), movie2);
        assertEquals(Iterables.get(recommender.getUserRecommendations(jim.getId()), 1), movie3);
    }
    
    @Test
    public void testGetTopTen() throws Exception {
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        Movie movie1 = Iterables.get(recommender.getMovies(), 0);
        Movie movie2 = new Movie("Kill Your", "2010", "http://", genres);
        Movie movie3 = new Movie("Kill Darlings", "2010", "http://", genres);
        recommender.addMovie(movie2);
        recommender.addMovie(movie3);  
        
        long id = Iterables.get(recommender.getUsers(), 0).getId();
        Rating rating1 = new Rating(id, movie1.getId(), 5);
        Rating rating2 = new Rating(id, movie1.getId(), 4);
        Rating rating3 = new Rating(id, movie2.getId(), 2);
        Rating rating4 = new Rating(id, movie2.getId(), 0);
        Rating rating5 = new Rating(id, movie3.getId(), -4);
        Rating rating6 = new Rating(id, movie3.getId(), 2);
        
        recommender.addRating(rating1);
        recommender.addRating(rating2);
        recommender.addRating(rating3);
        recommender.addRating(rating4);
        recommender.addRating(rating5);
        recommender.addRating(rating6);
        
        List<Movie> topTen = recommender.getTopTenMovies();
        assertEquals(topTen.size(), 3);
        assertEquals(topTen.get(0), movie1);
        assertEquals(topTen.get(1), movie2);
        assertEquals(topTen.get(2), movie3);
    }
}