package nl.novi.eindopdrachtbackend.security;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * MyUserDetailsService is an implementation of the UserDetailsService interface,
 * which loads user-specific data.
 */
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a MyUserDetailsService with the specified UserRepository.
     *
     * @param userRepository the UserRepository to use for retrieving user data
     */
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user by their username (email).
     *
     * @param email the email of the user to load
     * @return the UserDetails of the user
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ou = userRepository.findByEmail(email);
        if (ou.isPresent()) {
            User user = ou.get();
            return new MyUserDetails(user);
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
