package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class User {
    private List<Rating> ratings = new ArrayList<>();
    public static long counter = 1l;
    private long id;
    private String firstName, lastName, username, password;
    private int age;
    private boolean loggedIn;

    public User(String firstName, String lastName, String username, String password, int age) {
        if (firstName.isEmpty() || containsIllegals(firstName)) {
            throw new IllegalArgumentException();
        } else {
            if (firstName.length() > 9) {
                this.firstName = firstName.substring(0, 9);
            } else {
                this.firstName = firstName;
            }
        }

        if (lastName.isEmpty() || containsIllegals(lastName)) {
            throw new IllegalArgumentException();
        } else {
            if (lastName.length() > 9) {
                this.lastName = lastName.substring(0, 9);
            } else {
                this.lastName = lastName;
            }
        }

        this.username = username.toLowerCase();

        if (age > 0) {
            this.age = age;
        } else {
            age = 0;
        }
        
        this.password = password;

        this.id = counter++;
    }

    /** 
     * Method to check whether a string contains an 
     * illegal character
     * 
     * @param s string to check
     * @return boolean 
     */
    public boolean containsIllegals(String s) {
        String[] arr = s.split("[~#@*+%{}<>\\[\\]|\"\\_^]", 2);
        return arr.length > 1;
    }
    
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    } 
    
    // User to string
    @Override
    public String toString() {
        return "Name: " + getFirstName() 
        + "; Last name: " + getLastName() 
        + "; User ID: " + id 
        + "; Username: " + getUsername() 
        + "; Age: " + getAge()
        + ".";
    }

    // User hash code
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.firstName, this.lastName, this.username);
    }

    // User equals method    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        return Objects.equal(this.getFirstName(), other.getFirstName())
            && Objects.equal(this.getLastName(), other.getLastName())
            && Objects.equal(this.getUsername(), other.getUsername())
            && Objects.equal(this.getAge(), other.getAge());
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

    public long getId() {
        return id;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
