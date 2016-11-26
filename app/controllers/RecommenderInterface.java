package controllers;
import java.util.Collection;

import models.User;

public interface RecommenderInterface {
    public User addUser(String firstName, String lastName, String username, String gender, String email, int age);
    public void removeUser(long userID);
    public User getUser(long userID);
    public User getUserByEmail(String email);
    public Collection<User> getUsers();
    public void addMovie(String title, int year, String url);
    public void addRating(long userID, long movieID, int rating);
    public void getMovie(long movieID);
    public void getUserRatings(long userID);
    public void getUserRecommendations(long userID);
    public void getTopTenMovies();
    public void load();
    public void write();
}
