package controllers;

import models.Movie;
import play.Logger;
import play.mvc.Controller;

public class Movies extends Controller {
    public static void index(Long id) {
        Movie movie = Home.recommenderAPI().getMovie(id);
        Logger.info("Movie ID: " + id);
        render(movie);
    }
}
