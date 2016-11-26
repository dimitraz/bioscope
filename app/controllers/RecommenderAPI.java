package controllers;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import models.User;

public class RecommenderAPI implements RecommenderInterface {
	public Map<Long, User> userIndex = new HashMap<>(); 
	public Map<String, User> emailIndex = new HashMap<>();

	public RecommenderAPI() {
	}

	@Override
	public User addUser(String firstName, String lastName, String username, String gender, String email, int age) {
		User user = new User (firstName, lastName, username, gender, email, age);
		userIndex.put(user.id, user);
		emailIndex.put(email, user);
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

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub
		
	}

}