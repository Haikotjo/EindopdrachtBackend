package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserConstructor() {
        // Maak een instantie van User aan met de constructor
        User user = new User("Jan Jansen", "jan@example.com", "password123", "ADMIN", "Main Street 1", "0612345678");

        // Verifieer dat de constructor de velden correct initialiseert
        assertEquals("Jan Jansen", user.getName(), "De naam komt niet overeen");
        assertEquals("jan@example.com", user.getEmail(), "Het e-mailadres komt niet overeen");
        assertEquals("password123", user.getPassword(), "Het wachtwoord komt niet overeen");
        assertEquals("ADMIN", user.getRole(), "De rol komt niet overeen");
        assertEquals("Main Street 1", user.getAddress(), "Het adres komt niet overeen");
        assertEquals("0612345678", user.getPhoneNumber(), "Het telefoonnummer komt niet overeen");
    }
}
