package models;

public class User {
    private String firstName, lastName, username, gender, occupation;
    private int age; 
    
    public User(String firstName, String lastName, String username, String gender, String occupation, int age) {          
    	if(firstName.isEmpty() || containsIllegals(firstName)) {
    		throw new IllegalArgumentException();
    	} 
    	else {
    		if(firstName.length() > 9) {
    			this.firstName = firstName.substring(0, 9);
    		}
    		else {
    			this.firstName = firstName;
    		}
    	}
    	
    	if(lastName.isEmpty() || containsIllegals(lastName)) {
    		throw new IllegalArgumentException();
    	} 
    	else {
    		if(lastName.length() > 9) {
    			this.lastName = lastName.substring(0, 9);
    		}
    		else {
    			this.lastName = lastName;
    		}
    	}    
        
        if(username.isEmpty() || containsIllegals(username)) {
        	throw new IllegalArgumentException();
    	} 
        else {
    		if(lastName.length() > 9) {
    			this.username = username.substring(0, 9);
    		}
    		else {
    			this.username = username;
    		}
    	}    
        
        if(gender.equals("F") || gender.equals("M")) {
        	this.gender = gender;
        } else {
        	this.gender = "U"; // Undefined
        }
        
        this.occupation = occupation;
        if(age > 0) {
        	this.age = age;
        } else {
        	age = 0;
        }
    }
    
    @Override 
    public String toString() {
        return "Name: " + getFirstName() 
        + "; Last name: " + getLastName() 
        + "; User ID: " + getUsername() 
        + "; Gender: " + getGender() 
        + "; Occupation: " + getOccupation()
        + "; Age: " + getAge() + ".";
    }
    
    public boolean containsIllegals(String s) {
        String[] arr = s.split("[~#@*+%{}<>\\[\\]|\"\\_^]", 2);
        return arr.length > 1;
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
        if(age > 0) {
            this.age = age;
        }
    }
  
}
