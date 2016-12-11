package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class EditAccount extends Controller {
    
    public static void change(String firstName, String lastName, String username, String password, int age) {
        User user = Accounts.getLoggedInUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setAge(age);
        user.setPassword(password);
        
        Home.index();
    }

    public static void index() {
        User user = Accounts.getLoggedInUser();
        render(user);
    }
    
    public static void delete() {
        User user = Accounts.getLoggedInUser();
        Accounts.recommenderAPI().removeUser(user.getId());
        Accounts.save();
        
        Accounts.index();
    }
}