package controllers;

import play.*;
import play.mvc.*;
import utils.JSONSerializer;
import utils.SerializerInterface;

import java.io.File;
import java.util.*;
import models.*;

public class Accounts extends Controller {
    static RecommenderAPI recommenderAPI = loadRecommender();

    public static RecommenderAPI loadRecommender() {
        File datastore = new File("datastoreLarge.json");
        SerializerInterface serializer = new JSONSerializer(datastore);
        RecommenderAPI recommenderAPI = new RecommenderAPI(serializer);
        
        try {
            recommenderAPI.load();
        } 
        catch(Exception e) {
            Logger.info("Failure occurred while loading file " + datastore);
        }
        
        return recommenderAPI;
    }
    
    public static RecommenderAPI recommenderAPI() {
        return recommenderAPI;
    }
    
    public static void save() {
        File datastore = new File("datastoreLarge.json");
        RecommenderAPI recommenderAPI = Accounts.recommenderAPI();
        
        try {
            recommenderAPI.write();
        } catch (Exception e) {
            Logger.info("Failure occurred while writing to file " + datastore);
            e.printStackTrace();
        }
        index();
    }
    
    public static void index() {
        Collection<Movie> topTen = recommenderAPI().getTopTenMovies();
        render(topTen);
    }
    
    public static void signup() {
        render();
    }

    public static void login() {
        render();
    }

    public static void logout() {
        User user = getLoggedInUser();
        user.setLoggedIn(false);
        session.clear();
        Accounts.save();   
    }

    public static User getLoggedInUser() {
        User user = null;
        if (session.contains("logged_in_userid")) {
            String userId = session.get("logged_in_userid");
            user = Accounts.recommenderAPI().getUser(Long.parseLong(userId));
        } else {
            login();
        }
        return user;
    }

    public static void register(String firstName, String lastName, String username, String password, int age) {
        Logger.info(firstName + " " + lastName + " " + username + " " + age);
        User user = new User(firstName, lastName, username, password, age);
        Accounts.recommenderAPI().addUser(user);
        Accounts.save();
        index();
    }

    public static void authenticate(String username, String password) {
        Logger.info("Attempting to authenticate with " + username + ":" + password);
        User user = Accounts.recommenderAPI().getUserByUsername(username);
        Logger.info("User: " + user);

        if ((user != null) && (user.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_userid", user.getId());
            user.setLoggedIn(true);
            Home.index();
        } else {
            Logger.info("Authentication failed");   
            login();
        }
    }

}