package controllers;
import play.*;
import play.mvc.*;
import utils.JSONSerializer;
import utils.SerializerInterface;

import java.io.File;
import java.util.*;
import models.*;

public class Home extends Controller {
    RecommenderAPI recommenderAPI;
    
    public static void index() {
        File datastore = new File("datastore.json");
        SerializerInterface serializer = new JSONSerializer(datastore);
        RecommenderAPI recommenderAPI = new RecommenderAPI(serializer);
        
        try {
            recommenderAPI.load();
        } 
        catch(Exception e) {
            Logger.info("Failure occurred");
        }
        
        Collection<User> users = recommenderAPI.getUsers();
        render(users);
        
        List<Movie> topTen = recommenderAPI.getTopTenMovies();
        render(topTen);
    }

}
