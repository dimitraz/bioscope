package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

import edu.princeton.cs.introcs.In;

public class Movie {
    private String title, url, releaseDate;
    long id; 
    Genre genre = new Genre();
    String[] genres;
    List<String> genreList = genre.parseData();
    List<String> genresIn;
    List<Rating> ratings = new ArrayList<>();
   
    public Movie(long id, String title, String releaseDate, String url, String[] genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.url = url;
        this.genres = genres;
        genresIn = convertGenres();
    }
    
    /**
     * Converts genres from a list of numbers (1 for applicable, 
     * 0 for not applicable) to an array list of genre strings
     * 
     * @return genresIn ArrayList of the film's genres
     */
    public List<String> convertGenres() {
        List<String> genresIn = new ArrayList<String>();
        
        for(int i = 0; i < genres.length; i++) {
            if(genres[i].equals("1")) {
                genresIn.add(genreList.get(i));
            }
        }  
        if(genresIn.size() == 0) {
            genresIn.add(genreList.get(0));
        }
        
        return genresIn;
    }
    
    /** 
     * Calculates the average rating of the film
     * 
     * @return average film rating
     */
    public int averageRating() {
        int sum = 0;
        for(Rating r : ratings) {
            sum += r.getRating();
        }
        if(ratings.size() != 0) {
            return sum/ratings.size();
        }
        else {
            return 0;
        }
    }
    
    // Movie to string
    @Override
    public String toString() { 
        Joiner joiner = Joiner.on(", ").skipNulls();
        String genres = joiner.join(genresIn);

        return "Id: " + getId()
        + "; Title: " + getTitle()
        + "; Date: " + getReleaseDate()
        + "; URL: " + getUrl()
        + "; Genres: " + genres
        + ".";
    }
    
    // Movie equality method
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Movie) {
            final Movie other = (Movie) obj;
            return Objects.equal(id, other.getId()) 
                    && Objects.equal(title, other.getTitle())
                    && Objects.equal(releaseDate, other.getReleaseDate())
                    && Objects.equal(url, other.getUrl())
                    && Objects.equal(genres, other.getGenres());
        } else {
            return false;
        }
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return convertGenres();
    }

    // LOOK AT
    public void setGenres(String[] genres) {
        this.genres = genres;
    }
    
    public List<Rating> getRatings() {
        return ratings;
    }
    
    public void addRating(Rating rating) {
        ratings.add(rating);
    }
    
    public void addRating(long ID, long movieID, int rating) {
        ratings.add(new Rating(ID, movieID, rating));
    }
}
