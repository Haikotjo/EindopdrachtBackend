package nl.novi.eindopdrachtbackend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword1 = "password123";
        String rawPassword2 = "password456";
        String rawPassword3 = "password789";

        String encodedPassword1 = encoder.encode(rawPassword1);
        String encodedPassword2 = encoder.encode(rawPassword2);
        String encodedPassword3 = encoder.encode(rawPassword3);

        System.out.println("John Doe's hashed password: " + encodedPassword1);
        System.out.println("Jane Smith's hashed password: " + encodedPassword2);
        System.out.println("Alice Johnson's hashed password: " + encodedPassword3);
    }
}
