package models;

import java.util.Date;

import com.google.common.base.Objects;

public class Rating {
    long userId, movieId;
    int rating;

    /** 
     * User's opinion of the movie in a number
     * between -5 and 5
     * @param userId id of the user giving the rating
     * @param movieId id of the movie being rated
     * @param rating number given
     */
    public Rating(long userId, long movieId, int rating) {
        this.userId = userId;
        this.movieId= movieId;
        if (rating <= 5 && rating >= -5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between -5 and 5");
        }
    }

    // Rating to string
    @Override
    public String toString() {
        return "User ID: " + getUserId() 
        + "; Movie ID: " + getMovieId() 
        + "; Rating: " + getRating() + ".";
    }

    // Rating equality
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Rating) {
            final Rating other = (Rating) obj;
            return Objects.equal(getUserId(), other.getUserId()) 
                    && Objects.equal(getMovieId(), other.getMovieId())
                    && Objects.equal(getRating(), other.getRating());
        } else {
            return false;
        }
    }

    // Getters and Setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieID(long movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
