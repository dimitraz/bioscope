package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import edu.princeton.cs.introcs.In;

public class Movie {
    private String title, url, releaseDate;
    int id; 
    Genre genre = new Genre();
    List<String> genreList = genre.parseData();
    List<String> genresIn;
    String[] genres;
   
    public Movie(int id, String title, String releaseDate, String url, String[] genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.url = url;
        this.genres = genres;
        genresIn = convertGenres();
    }
    
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

    public List<String> getGenres() {
        return convertGenres();
    }

    // LOOK AT
    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
