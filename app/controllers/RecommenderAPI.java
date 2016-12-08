package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Movie;
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
    }

    @Override
    public void write() throws Exception {
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
    public void addRating(long userID, long movieID, int rating) {
        User user = getUser(userID);
        Movie movie = getMovie(movieID);
        if(user != null && movie != null) {
            user.addRating(userID, movieID, rating);
            movie.addRating(userID, movieID, rating);
        }
    }
    
    public void addRating(Rating rating) {
        User user = getUser(rating.getUserID());
        Movie movie = getMovie(rating.getMovieID());
        if(user != null && movie != null) {
            user.addRating(rating);
            movie.addRating(rating);
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

    @Override
    public Set<Movie> getUserRecommendations(long userID) {
        User user = getUser(userID);
        Collection<User> users = getUsers();
        List<Rating> userRatings = user.getRatings();
        List<Integer> list = new ArrayList<>();
        Set<Movie> recommendList = new HashSet<>();
        
        int count = 0;
        int highest = Integer.MIN_VALUE;
        User highestUser = null;
        
        for(User u : users) {
            if(u.getID() != user.getID()) {
                List<Rating> user2Ratings = u.getRatings();
                int product = 0;
                
                for(Rating r : userRatings) {
                    for(Rating r2 : user2Ratings) {
                        if(r.getMovieID() == r2.getMovieID()) {
                            product += r.getRating()*r2.getRating();
                        }
                    }
                }
                
                if(product > highest) {
                    highest = product;
                    highestUser = u;
                }
            }
        }
        Collections.sort(highestUser.getRatings(), new SortRatingsComparator());
        for(Rating r : highestUser.getRatings()) {
            for(Rating r2 : userRatings) {
                if((r.getMovieID() != r2.getMovieID()) && (recommendList.size() < 10)) {
                    Movie movie = getMovie(r.movieID);
                    recommendList.add(movie);
                }
            }
        }
        return recommendList;
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