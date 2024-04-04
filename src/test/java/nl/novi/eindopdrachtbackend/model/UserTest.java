package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserConstructor() {
        // Create an instance of User using the constructor
        User user = new User("Jan Jansen", "jan@example.com", "password123", "ADMIN", "Main Street 1", "0612345678");

        // Verify that the constructor correctly initializes the fields
        assertEquals("Jan Jansen", user.getName(), "The name does not match");
        assertEquals("jan@example.com", user.getEmail(), "The email does not match");
        assertEquals("password123", user.getPassword(), "The password does not match");
        assertEquals("ADMIN", user.getRole(), "The role does not match");
        assertEquals("Main Street 1", user.getAddress(), "The address does not match");
        assertEquals("0612345678", user.getPhoneNumber(), "The phone number does not match");
    }
}
