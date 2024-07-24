package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * This interface provides CRUD operations for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds users by their role.
     *
     * @param role the role to search for
     * @return a list of users with the specified role
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.rolename = :role")
    List<User> findByRole(UserRole role);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the found user, or empty if no user found
     */
    Optional<User> findByEmail(String email);
}
