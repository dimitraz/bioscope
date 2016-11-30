package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class User {
    private List<Integer> ratings = new ArrayList<>();
    static long counter = 0l;
    public long id;
    private String firstName, lastName, username, gender, email;
    private int age;

    public User(String firstName, String lastName, String username, String gender, String email, int age) {
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

        if (username.isEmpty() || containsIllegals(username)) {
            throw new IllegalArgumentException();
        } else {
            if (lastName.length() > 9) {
                this.username = username.substring(0, 9);
            } else {
                this.username = username;
            }
        }

        if (gender.equals("F") || gender.equals("M")) {
            this.gender = gender;
        } else {
            this.gender = "U"; // Undefined
        }

        this.email = email.toLowerCase();

        if (age > 0) {
            this.age = age;
        } else {
            age = 0;
        }

        this.id = counter++;
    }

    @Override
    public String toString() {
        return "Name: " + getFirstName() 
        + "; Last name: " + getLastName() 
        + "; Username: " + getUsername()
        + "; User ID: " + id 
        + "; Gender: " + getGender() 
        + "; Email: " + getEmail() 
        + "; Age: " + getAge()
        + ".";
    }

    // Check if strings contain illegal characters
    public boolean containsIllegals(String s) {
        String[] arr = s.split("[~#@*+%{}<>\\[\\]|\"\\_^]", 2);
        return arr.length > 1;
    }

    // User hash code
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.firstName, this.lastName, this.email);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof User) {
            final User other = (User) obj;
            return Objects.equal(firstName, other.firstName) 
                    && Objects.equal(lastName, other.lastName)
                    && Objects.equal(email, other.email);
        } else {
            return false;
        }
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

}
