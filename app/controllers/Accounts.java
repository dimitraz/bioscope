package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Accounts extends Controller {
    
  public static void signup() {
	  render();
  }

  public static void login() {	  
	  render();
  }

  public static void logout() {
	  User user = getLoggedInUser();
	  user.loggedIn = false;

	  session.clear();
	  index();
  }

  public static void index() {
  	//Collection<User> users = recommenderAPI.getUsers();
	//render(users);
  }

  public static User getLoggedInUser() {
	  User user = null;
	  if (session.contains("logged_in_userid")) {
		  String userId = session.get("logged_in_userid");
		  user = Home.recommenderAPI().getUser(Long.parseLong(userId));
	  } else {
		  login();
	  }
	  return user;
  }
  
  public static void register(String firstName, String lastName, int age, String nationality, String email, String password, String password2) {
	  Logger.info(firstName + " " + lastName + " " + email + " " + password);
	  User user = new User(firstName, lastName, email, password, age, nationality);

	  index();
  }

  public static void authenticate(String email, String password) {
	  Logger.info("Attempting to authenticate with " + email + ":" + password);
	 // User user = User.getByEmail(email);
    
	  /*if ((user != null) && (user.checkPassword(password) == true)) {
		  Logger.info("Authentication successful");
		  session.put("logged_in_userid", user.id);
		  user.loggedIn = true;
		  user.save();
		  Home.index();
	  } else {
		  Logger.info("Authentication failed");
		  login();
	  }
	  */
  }
  
}