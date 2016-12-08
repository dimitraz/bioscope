package controllers;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.User;
import utils.SerializerInterface;
import utils.JSONSerializer;

public class ParseFiles {
    // private static List<User> users = new ArrayList<User>();
    
    private static void parseUsers(RecommenderAPI recommenderAPI) throws Exception {
        File usersFile = new File("data/users5.dat");
        In inUsers = new In(usersFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!inUsers.isEmpty()) {
            // get user and rating from data source
            String userDetails = inUsers.readLine();

            // parse user details string
            String[] userTokens = userDetails.split(delims);

            // output user data to console.
            if (userTokens.length == 7) {
                User user = new User(userTokens[1], userTokens[2], userTokens[0], userTokens[4], userTokens[5], Integer.parseInt(userTokens[3]));
                recommenderAPI.addUser(user);
            } 
            else {
                throw new Exception("Invalid member length: " + userTokens.length);
            }
        }
    }
    
    private static void parseMovies(RecommenderAPI recommenderAPI) throws Exception {
        File moviesFile = new File("data/items5.dat");
        In movies = new In(moviesFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!movies.isEmpty()) {
            String movieDetails = movies.readLine();
            String[] movieTokens = movieDetails.split(delims);

            if (movieTokens.length == 23) {
                String[] genres = {movieTokens[5], movieTokens[6], movieTokens[7], movieTokens[8], movieTokens[9], movieTokens[10], movieTokens[11], movieTokens[12], movieTokens[13], movieTokens[14], movieTokens[15], movieTokens[16], movieTokens[17], movieTokens[18], movieTokens[19], movieTokens[20], movieTokens[21], movieTokens[22]};
                Movie movie = new Movie(Integer.parseInt(movieTokens[0]), movieTokens[1], movieTokens[2], movieTokens[3], genres);
                recommenderAPI.addMovie(movie);
            } 
            else {
                throw new Exception("Invalid member length: " + movieTokens.length);
            }
        }   
    }
    
    private static void parseRatings(RecommenderAPI recommenderAPI) throws Exception {
        File ratingsFile = new File("data/ratings5.dat");
        In ratings = new In(ratingsFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!ratings.isEmpty()) {
            String ratingsDetails = ratings.readLine();
            String[] ratingsTokens = ratingsDetails.split(delims);

            if (ratingsTokens.length == 4) {
                Rating rating = new Rating(Long.parseLong(ratingsTokens[0]), Long.parseLong(ratingsTokens[1]), Integer.parseInt(ratingsTokens[2]));                
                recommenderAPI.addRating(rating);
            } 
            else {
                throw new Exception("Invalid member length: " + ratingsTokens.length);
            }
        }        
    }
    
    public static void main(String[] args) {
        File datastore = new File("datastore.json");
        SerializerInterface serializer = new JSONSerializer(datastore);
        RecommenderAPI recommenderAPI = new RecommenderAPI(serializer);
        
        // Parse Movies
        try {
            parseMovies(recommenderAPI);
        } catch (Exception e1) {
        } 
        
        // Load data
        try {
            // recommenderAPI.load();
        } 
        catch (Exception e) {
        }
        
        // Parse Users
        try {
            parseUsers(recommenderAPI);
        }
        catch(Exception e) {
 
        }
        
        // Parse Ratings
        try { 
            parseRatings(recommenderAPI);
            
        }
        catch (Exception e) {
        }

        // Display users
        System.out.println("Users:");
        Collection<User> users = recommenderAPI.getUsers();
        for (User u : users) {
            System.out.println(u);
        }
        
        // Display user ratings
        for (User u : users) {
            System.out.println("\nRatings for " + u.getFirstName());
            List<Rating> ratingList = u.getRatings();
            for (Rating r : ratingList) {
                System.out.println(r);
            }
        }
        
        for (User u : users) { 
            System.out.println("\nRecommendations for " + u.getFirstName());
            Set<Movie> rec = recommenderAPI.getUserRecommendations(u.getID());
            for (Movie m : rec) {
                System.out.println(m.getTitle());
            }
        }
        
        System.out.println("\nAll Movies:");
        Collection<Movie> movies = recommenderAPI.getMovies();
        for (Movie m : movies) {
            System.out.println(m);
        }
        
        System.out.println("\nTop ten movies:");
        List<Movie> topTen = recommenderAPI.getTopTenMovies();
        for (Movie m : topTen) {
            System.out.println(m.getTitle() + " " + m.averageRating());
        }
        
        // Write to file
        try {
            recommenderAPI.write();
        } 
        catch (Exception e) {
        } 
    }
}
