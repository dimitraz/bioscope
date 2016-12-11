package controllers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.introcs.In;
import exceptions.GenreLengthException;
import models.Movie;
import models.Rating;
import models.User;
import utils.SerializerInterface;
import utils.JSONSerializer;

public class ParseFiles {
    
    /** 
     * Method to read in and parse a list of users 
     * and their details, in the format:
     * 
     * user id | first name | last name | age | gender |
     *  occupation | zip code
     *  
     * Only first name, last name and age are used.
     * 
     * @param recommenderAPI
     * @throws IOException
     */
    private static void parseUsers(RecommenderAPI recommenderAPI) throws IOException {
        File usersFile = new File("data/users.dat");
        In inUsers = new In(usersFile);

        String delims = "[|]";
        while (!inUsers.isEmpty()) {
            String userDetails = inUsers.readLine();
            String[] userTokens = userDetails.split(delims);

            if (userTokens.length == 7) {
                User user = new User(userTokens[1], userTokens[2], userTokens[1] + userTokens[2], "N0lan123", Integer.parseInt(userTokens[3]));
                recommenderAPI.addUser(user);
            } 
            else {
                throw new IOException("Invalid member length: " + userTokens.length);
            }
        }
    }
    
    /** 
     * Method to read in and parse a list of movies and 
     * movie details, in the format: 
     * 
     * movie id | movie title (release year) | release date | 
     * IMDb URL | {genres}
     * 
     * The last 19 fields are the genres, a 1 indicates the movie
     * is of that genre, a 0 indicates it is not; movies can be in 
     * several genres at once.
     * 
     * @param recommenderAPI
     * @throws IOException
     * @throws GenreLengthException 
     */
    private static void parseMovies(RecommenderAPI recommenderAPI) throws IOException, GenreLengthException {
        File moviesFile = new File("data/items.dat");
        In movies = new In(moviesFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!movies.isEmpty()) {
            String movieDetails = movies.readLine();
            String[] movieTokens = movieDetails.split(delims);

            if (movieTokens.length == 23) {
                String[] genres = {movieTokens[5], movieTokens[6], movieTokens[7], movieTokens[8], movieTokens[9], movieTokens[10], movieTokens[11], movieTokens[12], movieTokens[13], movieTokens[14], movieTokens[15], movieTokens[16], movieTokens[17], movieTokens[18], movieTokens[19], movieTokens[20], movieTokens[21], movieTokens[22]};
                Movie movie = new Movie(movieTokens[1], movieTokens[2], movieTokens[3], genres);
                recommenderAPI.addMovie(movie);
            } 
            else {
                throw new IOException("Invalid member length: " + movieTokens.length);
            }
        }   
    }
    
    /** 
     * Method to read in and parse a list of ratings
     * in the format:
     * 
     * user id | item id | rating | timestamp
     * The timestamp parameter is not used.
     * 
     * @param recommenderAPI
     * @throws Exception
     */
    private static void parseRatings(RecommenderAPI recommenderAPI) throws Exception, IOException {
        File ratingsFile = new File("data/ratings.dat");
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
                throw new IOException("Invalid member length: " + ratingsTokens.length);
            }
        }        
    }
    
    // Display all parsed data
    public static void main(String[] args) {
        File datastore = new File("datastoreLarge.json");
        SerializerInterface serializer = new JSONSerializer(datastore);
        RecommenderAPI recommenderAPI = new RecommenderAPI(serializer);
        
        // Load data
        try {
            // recommenderAPI.load();
        } 
        catch (Exception e) {
            System.out.println("Unable to load file.");
            e.printStackTrace();   
        }
        
        // Parse Movies
        try {
           parseMovies(recommenderAPI);
        } catch (Exception e) {
            System.out.println("Unable to parse movies.");
            e.printStackTrace();
        } 
        
        // Parse Users
        try {
            parseUsers(recommenderAPI);
        }
        catch(Exception e) {
            System.out.println("Unable to parse users.");
            e.printStackTrace();
        }
        
        // Parse Ratings
        try { 
           parseRatings(recommenderAPI);
            
        }
        catch (Exception e) {
            System.out.println("Unable to parse ratings.");
            e.printStackTrace();
        }

        // Display users
        System.out.println("Users:");
        Collection<User> users = recommenderAPI.getUsers();
        for (User u : users) {
            System.out.println(u);
        }
        
        // Display user ratings
        /*for (User u : users) {
            System.out.println("\nRatings for " + u.getFirstName());
            List<Rating> ratingList = u.getRatings();
            for (Rating r : ratingList) {
                System.out.println(r);
            }
        }
        */
        /*System.out.println("\nAll Movies:");
        Collection<Movie> movies = recommenderAPI.getMovies();
        for (Movie m : movies) {
            System.out.println(m);
        }*/
        
        System.out.println("\nTop ten movies:");
        List<Movie> topTen = recommenderAPI.getTopTenMovies();
        for (Movie m : topTen) {
            System.out.println(m.getTitle() + " " + m.averageRating());
        }
        
        /*for (User u : users) { 
            System.out.println("\nRecommendations for " + u.getFirstName() + " (" + u.getID() + ")");
            
            List<Movie> rec = recommenderAPI.getUserRecommendations(u.getID());
            for (Movie m : rec) {
                System.out.println(m.getId() + " " + m.getTitle());
            }
        }*/
        
        // Write to file
        try {
            recommenderAPI.write();
        } 
        catch (Exception e) {
            System.out.println("Unable to write to file.");
            e.printStackTrace();
        } 
    }
}
