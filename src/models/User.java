package models;

public class User {
    private String firstName, lastName, username, gender, occupation;
    private int age; 
    
    public User(String firstName, String lastName, String username, String gender, String occupation, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.occupation = occupation;
        this.age = age;
    }
    
    @Override 
    public String toString() {
        return "Name: " + getFirstName() 
        + "; Last name: " + getLastName() 
        + "; User ID: " + getUsername() 
        + "; Gender: " + getGender() 
        + "; Occupation: " + getOccupation()
        + "; Age: " + getAge()
        + "\n";
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
  
}
