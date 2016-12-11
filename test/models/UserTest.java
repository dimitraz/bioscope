package models;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import models.User;

// Right BICEP
// Right - Are the results right?
// B - Are all the boundary conditions CORRECT?
// I - Can you check inverse relationships?
// C - Can you cross-check results using other means?
// E - Can you force error conditions to happen?
// P - Are performance characteristics within bounds?
//  Conformance - Does the value conform to an expected format?
//  Ordering - Is the set of values ordered or unordered as appropriate?
//  Range - Is the value within reasonable minimum and maximum values?
//  Reference - Does the code reference anything external that isn't under direct control of the code itself?
//  Existence - Does the value exist (e.g., is non-null, nonzero, present in a set, etc.)?
//  Cardinality - Are there exactly enough values?
//  Time (absolute and relative) - Is everything happening in order? At the right time? In time?
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
