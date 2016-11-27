package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import models.User;
import utils.SerializerInterface;

public class RecommenderAPI implements RecommenderInterface {
    public Map<Long, User> userIndex = new HashMap<>();
    public Map<String, User> emailIndex = new HashMap<>();
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
        userIndex.put(user.id, user);
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
    public void addMovie(String title, int year, String url) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRating(long userID, long movieID, int rating) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getMovie(long movieID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getUserRatings(long userID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getUserRecommendations(long userID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getTopTenMovies() {
        // TODO Auto-generated method stub

    }
}