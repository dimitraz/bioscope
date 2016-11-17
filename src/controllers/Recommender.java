package controllers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.introcs.In;
import models.User;

public class Recommender {
    private static List<User> users = new ArrayList<User>();
    
    private static void parseData() throws Exception {
        File usersFile = new File("data/users5.dat");
        In inUsers = new In(usersFile);
        // each field is separated(delimited) by a '|'
        String delims = "[|]";
        while (!inUsers.isEmpty()) {
            // get user and rating from data source
            String userDetails = inUsers.readLine();

            // parse user details string
            String[] userTokens = userDetails.split(delims);

            // output user data to console.
            if (userTokens.length == 7) {
                User user = new User(userTokens[1], userTokens[2], userTokens[0], userTokens[4], userTokens[5], Integer.parseInt(userTokens[3]));
                users.add(user);
            } 
            else {
                throw new Exception("Invalid member length: " + userTokens.length);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            parseData();
        } catch (Exception e) {            
        }
        for(User u : users) {
            System.out.println(u);
        }
    }
}
