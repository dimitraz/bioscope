package models;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import models.User;

public class UserTest {
	private User[] users = {
		new User("Fred", "Flinstone", "freddy", "Secret123", 29),
		new User("Fred", "Flinstone", "freddy", "Secret123", 29),
		new User("Fred", "Flinstone", "freddy", "Secret123", 29),
		new User("Fred", "Flinstone", "freddy", "Secret123", 29),
	};
	
    // Test basic user information
    @Test
    public void testNewUser() {
        User user = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertEquals(user.getFirstName(), "Fred");
        assertEquals(user.getLastName(), "Flinstone");
        assertEquals(user.getUsername(), "freddy");
        assertEquals(user.getAge(), 29);
    }
    
    // Test boundary conditions
    @Test 
    public void testInvalidAge() {
        User user = new User("Fred", "Flinstone", "freddy", "Secret123", -29);
        assertEquals(user.getAge(), 0);
    }
       
    
    @Test (expected = IllegalArgumentException.class) 
    public void testEmptyName() {
        User user = new User("", "Flinstone", "freddy", "Secret123", 29);
    }
    
    @Test
    public void testDuplicateUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testUserExistence() {
        User user = new User("Fred", "Flinstone", "freddy", "Secret123", 29);
        assertNotNull(user);
    }

    @Test (expected = IllegalArgumentException.class) 
    public void testName() {
    	User user = new User("***", "{}]{|/", "freddy", "Secret123", 29);
    }

    @Test 
    public void testStringLimit() {
    	User user = new User("Hansel-And-Gretel", "CharlieAndTheChocolateFactory", "WillyWonka", "123Secret", 29);
    	assertEquals(user.getFirstName(), "Hansel-An");
    	assertEquals(user.getLastName(), "CharlieAn");
    	assertEquals(user.getUsername(), "willywonka");	
    }
    
    @Test
    public void testUsernameCase() {
        User user = new User("Fred", "Flinstone", "Freddy", "Secret123", 29);
        assertEquals(user.getUsername(), "freddy");
    }
    
	@Test
	public void testIds() {
		Set<Long> ids = new HashSet<>();
		for (User user : users) {
			ids.add(user.getId());
		}
		assertEquals(users.length, ids.size());
	}
	
	@Test
	public void testUserEquality() {
	    User user = new User("Hansel-And-Gretel", "CharlieAndTheChocolateFactory", "WillyWonka", "123Secret", 29);
	    User userTwo = new User("Hansel-And-Gretel", "CharlieAndTheChocolateFactory", "WillyWonka", "123Secret", 29);
        assertEquals(user, userTwo);
	}
}
