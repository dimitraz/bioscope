package controllers;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import edu.princeton.cs.introcs.In;
import models.Movie;
import models.User;
import utils.SerializerInterface;
import utils.JSONSerializer;

public class Recommender {
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
    
    private static void parseMovies() throws Exception {
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
                System.out.println(movie);
            } 
            else {
                throw new Exception("Invalid member length: " + movieTokens.length);
            }
        }   
    }
    
    private static void parseRatings() throws Exception {
        File ratingsFile = new File("data/ratings5.dat");
        In ratings = new In(ratingsFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!ratings.isEmpty()) {
            String ratingsDetails = ratings.readLine();
            String[] ratingsTokens = ratingsDetails.split(delims);

            if (ratingsTokens.length == 4) {
                Rating rating = new Rating(Long.parseLong(ratingsTokens[0]), Long.parseLong(ratingsTokens[1]), Integer.parseInt(ratingsTokens[2]));
                System.out.println(rating);
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
            parseMovies();
        } catch (Exception e1) {
            e1.printStackTrace();
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
            parseRatings();
        }
        catch (Exception e) {
        }

        // Display users
        Collection<User> users = recommenderAPI.getUsers();
        for (User u : users) {
            System.out.println(u);
        }
        
        // Write to file
        try {
            recommenderAPI.write();
        } 
        catch (Exception e) {
        } 
    }
}
