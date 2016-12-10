package controllers;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Movie;
import models.Rating;
import models.User;

public interface RecommenderInterface {
    public User addUser(String firstName, String lastName, String username, String gender, String email, int age);
    public void removeUser(long userID);
    public User getUser(long userID);
    public User getUserByEmail(String email);
    public Collection<User> getUsers();
    public Collection<Movie> getMovies();
    public Movie addMovie(long id, String title, String year, String url, String[] genres);
    public void addRating(long userID, long movieID, int rating) throws Exception;
    public Movie getMovie(long movieID);
    public List<Rating> getUserRatings(long userID);
    public List<Movie> getUserRecommendations(long userID);
    public List<Movie> getTopTenMovies();
    public void load() throws Exception;
    public void write() throws Exception;;
}
