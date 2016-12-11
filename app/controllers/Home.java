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
        List<Rating> ratings = user.getRatings();
        List<Movie> ratedMovies = new ArrayList<>();
        for(Rating r : ratings) {
            ratedMovies.add(Accounts.recommenderAPI().getMovie(r.getMovieId()));
        }
        
        List<Movie> movies = Accounts.recommenderAPI().getUserRecommendations(user.getId());
        Collection<Movie> topTen = Accounts.recommenderAPI().getTopTenMovies();
        
        RecommenderAPI api = Accounts.recommenderAPI();
        render(user, movies, topTen, ratedMovies, api);
    }
}
