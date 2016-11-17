package controllers;

public interface RecommenderInterface {
    public void addUser(String firstName, String lastName, int age, String gender, String occupation);
    public void removeUser(long userID);
    public void addMovie(String title, int year, String url);
    public void addRating(long userID, long movieID, int rating);
    public void getMovie(long movieID);
    public void getUserRatings(long userID);
    public void getUserRecommendations(long userID);
    public void getTopTenMovies();
    public void load();
    public void write();
}
