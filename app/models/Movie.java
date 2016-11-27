package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.In;

public class Movie {
    private String title, url, releaseDate;
    int id; 
    String[] genres;
   
    public Movie(int id, String title, String releaseDate, String url, String[] genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.url = url;
        this.genres = genres;
    }
    
    @Override
    public String toString() {       
        return "Id: " + getId()
        + "; Title: " + getTitle()
        + "; Date: " + getReleaseDate()
        + "; URL: " + getUrl()
        + ".";
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
