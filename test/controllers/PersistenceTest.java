package controllers;

import controllers.RecommenderAPI;
import exceptions.GenreLengthException;

import static org.junit.Assert.*;

import java.io.File;

import models.Movie;
import models.Rating;
import models.User;

import org.junit.Before;
import org.junit.Test;

import utils.SerializerInterface;
import utils.JSONSerializer;

public class PersistenceTest {
    RecommenderAPI recommender;
    User[] users = new User[4];
    Movie[] movies = new Movie[3];
    Rating[] ratings = new Rating[12];
    

    public void populate(RecommenderAPI recommender) throws GenreLengthException {
        String[] genres = {"0", "0", "1", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "1"};
        
        users[0] = new User("Creed", "Bratton", "creed", "@dundermifflin123", 60);
        users[1] = new User("Jim", "Halpert", "jimbo", "@dundermifflin123", 32);
        users[2] = new User("Dwight", "Shrute", "dwight", "@dundermifflin123", 35);
        users[3] = new User("Pam", "Halpert", "pamela", "@dundermifflin123", 32);

        movies[0] = new Movie("Kill Your Darlings", "2010", "http://", genres);
        movies[1] = new Movie("Kill Your", "2010", "http://", genres);
        movies[2] = new Movie("Kill", "2010", "http://", genres);

        ratings[0] = new Rating(users[0].getId(), movies[0].getId(), 5);
        ratings[1] = new Rating(users[0].getId(), movies[0].getId(), 4);
        ratings[2] = new Rating(users[0].getId(), movies[1].getId(), 3);
        ratings[3] = new Rating(users[1].getId(), movies[2].getId(), -5);
        ratings[4] = new Rating(users[1].getId(), movies[2].getId(), -4);
        ratings[5] = new Rating(users[1].getId(), movies[1].getId(), -3);
        ratings[6] = new Rating(users[2].getId(), movies[2].getId(), -3);
        ratings[7] = new Rating(users[2].getId(), movies[0].getId(), 4);
        ratings[8] = new Rating(users[2].getId(), movies[1].getId(), -2);
        ratings[9] = new Rating(users[3].getId(), movies[0].getId(), 0);
        ratings[10] = new Rating(users[3].getId(), movies[2].getId(), 0);
        ratings[11] = new Rating(users[3].getId(), movies[1].getId(), 0);
        
        for (User user : users) {
            recommender.addUser(user);
        }
        
        for (Movie movie : movies) {
            recommender.addMovie(movie);
        }
        
        for (Rating rating : ratings) {
            try {
                recommender.addRating(rating);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPopulate() throws GenreLengthException {
        RecommenderAPI recommender = new RecommenderAPI(null);
        populate(recommender);
        
        assertEquals(4, recommender.getUsers().size());

        assertEquals(users.length, recommender.getUsers().size());
        assertEquals(3, recommender.getUserByUsername(users[0].getUsername()).getRatings().size());
    }

    void deleteFile(String fileName) {
        File datastore = new File("testdatastore.json");
        if (datastore.exists()) {
            datastore.delete();
        }
    }

    @Test
    public void testJSONSerializer() throws Exception {
        String datastoreFile = "testdatastore.json";
        deleteFile(datastoreFile);

        JSONSerializer serializer = new JSONSerializer(new File(datastoreFile));
        recommender = new RecommenderAPI(serializer);
        
        recommender.write();

        RecommenderAPI recommender2 = new RecommenderAPI(serializer);
        recommender2.load();

        assertEquals(recommender.getUsers().size(), recommender2.getUsers().size());
        for (User user : recommender.getUsers()) {
            assertTrue(recommender2.getUsers().contains(user));
        }
        deleteFile("testdatastore.json");
    }

}