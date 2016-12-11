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
        User user = Accounts.getLoggedInUser();
        List<Movie> movies = Accounts.recommenderAPI().getUserRecommendations(user.getId());
        render(user, movies);
    }
}
