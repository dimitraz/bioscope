package controllers;

import java.util.Date;

import com.google.common.base.Objects;

import models.User;

public class Rating {
   long userID, movieID;
   int rating;
   Date timestamp;
 
   public Rating(long userID, long movieID, int rating) {
       this.userID = userID;
       this.movieID = movieID;
       if(rating <= 5 && rating >= -5) {
           this.rating = rating;
       }
       else {
           throw new IllegalArgumentException();
       }
   }
   
   @Override
   public String toString() { 
       return "User ID: " + getUserID()
       + "; Movie ID: " + getMovieID()
       + "; Rating: " + getRating()
       + ".";
   }
   
   @Override
   public boolean equals(final Object obj) {
       if (obj instanceof Rating) {
           final Rating other = (Rating) obj;
           return Objects.equal(getUserID(), other.getUserID()) 
                   && Objects.equal(getMovieID(), other.getMovieID());
                   // && Objects.equal(getRating(), other.getRating());
       } else {
           return false;
       }
   }
   
   // Getters and Setters
   public long getUserID() {
       return userID;
   }
    
   public void setUserID(long userID) {
       this.userID = userID;
   }
    
   public long getMovieID() {
       return movieID;
   }
    
   public void setMovieID(long movieID) {
       this.movieID = movieID;
   }
    
   public int getRating() {
       return rating;
   }
    
   public void setRating(int rating) {
       this.rating = rating;
   }
    
   public Date getTimestamp() {
       return timestamp;
   }
    
   public void setTimestamp(Date timestamp) {
       this.timestamp = timestamp;
   }
}
