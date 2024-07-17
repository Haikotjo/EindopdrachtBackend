package nl.novi.eindopdrachtbackend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword1 = "password123";
        String rawPassword2 = "password456";
        String rawPassword3 = "password789";
        String rawPassword4 = "ownerpassword1";
        String rawPassword5 = "ownerpassword2";
        String rawPassword6 = "ownerpassword3";
        String rawPassword7 = "adminpassword";
        String rawPassword8 = "ownerpassword4";

        String encodedPassword1 = encoder.encode(rawPassword1);
        String encodedPassword2 = encoder.encode(rawPassword2);
        String encodedPassword3 = encoder.encode(rawPassword3);
        String encodedPassword4 = encoder.encode(rawPassword4);
        String encodedPassword5 = encoder.encode(rawPassword5);
        String encodedPassword6 = encoder.encode(rawPassword6);
        String encodedPassword7 = encoder.encode(rawPassword7);
        String encodedPassword8 = encoder.encode(rawPassword8);

        System.out.println("John Doe's hashed password: " + encodedPassword1);
        System.out.println("Jane Smith's hashed password: " + encodedPassword2);
        System.out.println("Alice Johnson's hashed password: " + encodedPassword3);
        System.out.println("Owner One's hashed password: " + encodedPassword4);
        System.out.println("Owner Two's hashed password: " + encodedPassword5);
        System.out.println("Owner Three's hashed password: " + encodedPassword6);
        System.out.println("Owner Fours's hashed password: " + encodedPassword8);
        System.out.println("Admin User's hashed password: " + encodedPassword7);
    }
}
