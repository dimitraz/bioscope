package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import models.Movie;
import models.Rating;
import models.User;
import utils.SerializerInterface;

public class RecommenderAPI implements RecommenderInterface {
    public Map<Long, User> userIndex = new HashMap<>();
    public Map<String, User> emailIndex = new HashMap<>();
    public Map<Long, Movie> movieList = new HashMap<>();
  
    private SerializerInterface serializer;

    public RecommenderAPI() {
    }

    public RecommenderAPI(SerializerInterface serializer) {
        this.serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        serializer.read();
        emailIndex = (Map<String, User>) serializer.pop();
        userIndex = (Map<Long, User>) serializer.pop();
        movieList = (Map<Long, Movie>) serializer.pop();
    }

    @Override
    public void write() throws Exception {
        serializer.push(movieList);
        serializer.push(userIndex);
        serializer.push(emailIndex);
        serializer.write();
    }

    @Override
    public User addUser(String firstName, String lastName, String username, String gender, String email, int age) {
        User user = new User(firstName, lastName, username, gender, email, age);
        userIndex.put(user.getID(), user);
        emailIndex.put(email, user);
        return user;
    }
    
    public User addUser(User user) {
        userIndex.put(user.id, user);
        emailIndex.put(user.getEmail(), user);
        return user; 
    }

    @Override
    public void removeUser(long userID) {
        User user = userIndex.remove(userID);
        emailIndex.remove(user.getEmail());
    }

    @Override
    public User getUser(long userID) {
        return userIndex.get(userID);
    }

    @Override
    public User getUserByEmail(String email) {
        return emailIndex.get(email);
    }

    @Override
    public Collection<User> getUsers() {
        return userIndex.values();
    }
    
    @Override
    public Collection<Movie> getMovies() {
        return movieList.values();
    }

    @Override
    public Movie addMovie(long id, String title, String releaseDate, String url, String[] genres) {
        Movie movie = new Movie(id, title, releaseDate, url, genres);
        movieList.put(id, movie);
        return movie;
    }
    
    public Movie addMovie(Movie movie) {
        movieList.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public void addRating(long userID, long movieID, int rating) throws Exception {
        User user = getUser(userID);
        Movie movie = getMovie(movieID);
        if(user != null && movie != null) {
            user.addRating(userID, movieID, rating);
            movie.addRating(userID, movieID, rating);
        }
        else {
            if(user == null) throw new Exception("Cannot add rating: user does not exist.");
            if(movie == null) throw new Exception("Cannot add rating: movie does not exist.");
        }
    }
    
    public void addRating(Rating rating) throws Exception {
        User user = getUser(rating.getUserID());
        Movie movie = getMovie(rating.getMovieID());
        if(user != null && movie != null) {
            user.addRating(rating);
            movie.addRating(rating);
        }
        else {
            if(user == null) throw new Exception("Cannot add rating: user " + rating.getUserID() + " does not exist.");
            if(movie == null) throw new Exception("Cannot add rating: movie does not exist.");
        }
    }

    @Override
    public Movie getMovie(long movieID) {
        return movieList.get(movieID);
    }
    
    public List<Rating> getMovieRatings(long movieID) {
        Movie movie = getMovie(movieID);
        return movie.getRatings();
    }

    @Override
    public List<Rating> getUserRatings(long userID) {
        User user = getUser(userID);
        return user.getRatings();
    }
    
    public List<Movie> getUserRecommendations(long userID) {
        User user1 = getUser(userID); 
        User user2 = bestMatchedUser(userID);
        List<Rating> ratings1 = user1.getRatings();
        List<Rating> ratings2 = user2.getRatings();
        
        List<Long> ids1 = new ArrayList<>();
        List<Long> ids2 = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        Set<Movie> moviesSet = new HashSet<>(); 
        
        // Get movie ids for user 1
        for(Rating r1 : ratings1) {
            ids1.add(r1.getMovieID());
        }
        
        // Get movie ids for user 2
        for(Rating r2: ratings2) {
            ids2.add(r2.getMovieID());
        }
                
        ids.addAll(ids2);
        // Get movie IDs that user 2 hasn't rated
        ids.removeAll(ids1);
        
        for(Long id : ids) {
            if(moviesSet.size() < 10) {
                Movie movie = getMovie(id);
                moviesSet.add(movie);
            }
            else {
                break;
            }
        }
        
        List<Movie> moviesList = new ArrayList<>(moviesSet);
        Collections.sort(moviesList, new SortByRatingComparator());
        return moviesList;
    }
    
    public User bestMatchedUser(long userID) {
        User user1 = getUser(userID);               // User 1
        List<Rating> ratings1 = user1.getRatings(); // User 1's ratings
        Collection<User> users = getUsers();        // List of all users
        
        int product = 0;                            // Dot product 
        int highestProduct = Integer.MIN_VALUE;     // Highest product so far
        User highestUser = null;                    // Highest match with user 1
        
        for(User user2 : users) {
            if(!user2.equals(user1)) {
                List<Rating> ratings2 = user2.getRatings(); // User 2's ratings
                
                for(Rating r1 : ratings1) {
                    for(Rating r2 : ratings2) {
                        if(r1.getMovieID() == r2.getMovieID()) {
                            product += r1.getRating()*r2.getRating();
                        }
                    }
                }
            }
            
            // If product is the highest so far
            // Save the user's details
            if(product > highestProduct) {
                highestProduct = product;
                highestUser = user2;
            }
            
            // System.out.println(product + " " + user2.getFirstName());
        }
        // System.out.println("Highest: " + highestProduct + " " + highestUser.getFirstName());
        return highestUser;
    }

    @Override
    public List<Movie> getTopTenMovies() {
        List<Movie> topTenMovies = new ArrayList<>();
        List<Movie> moviesByRating = new ArrayList<>(movieList.values());
        Collections.sort(moviesByRating, new SortByRatingComparator());
        
        for(Movie m : moviesByRating) {
            if(topTenMovies.size() < 10) {
                topTenMovies.add(m);
            }
            else {
                break;
            }
        }
        return topTenMovies;
    }
    
    public class SortByRatingComparator implements Comparator<Movie> {
        public int compare(Movie m1, Movie m2) {
            return Integer.compare(m2.averageRating(), (m1.averageRating()));
        }
    }
    
    public class SortRatingsComparator implements Comparator<Rating> {
        public int compare(Rating r1, Rating r2) {
            return Integer.compare(r2.getRating(), (r1.getRating()));
        }
    }
}