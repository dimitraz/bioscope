package controllers;

import models.Movie;
import models.Rating;
import play.Logger;
import play.mvc.Controller;

public class Movies extends Controller {
    public static void index(Long id) {
        Movie movie = Accounts.recommenderAPI().getMovie(id);
        Logger.info("Movie ID: " + id);
        render(movie);
    }
    
    public static void rateMovie(Long id, int number) {
        if(Accounts.getLoggedInUser() != null) {
            Logger.info("Movie ID: " + id + " User id: " + 
                    Accounts.getLoggedInUser().getId() + 
                    " Rating: " + number);
            
            Rating rating = new Rating(Accounts.getLoggedInUser().getId(), id, number);
            try {
                Accounts.recommenderAPI().addRating(rating);
            } catch (Exception e) {
                Logger.info("Rating could not be added.");
                e.printStackTrace();
            }
        }
        index(id);
    }
}
