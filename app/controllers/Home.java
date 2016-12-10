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
    
    public static RecommenderAPI recommenderAPI() {
        File datastore = new File("datastoreLarge.json");
        SerializerInterface serializer = new JSONSerializer(datastore);
        RecommenderAPI recommenderAPI = new RecommenderAPI(serializer);
        
        try {
            recommenderAPI.load();
        } 
        catch(Exception e) {
            Logger.info("Failure occurred");
        }
        
        return recommenderAPI;
    }
    
    public static void index() {
        Collection<Movie> topTen = recommenderAPI().getTopTenMovies();
        render(topTen);
    }
    
    public static Collection<User> getUsers() {
        return recommenderAPI().getUsers();
    }
}
