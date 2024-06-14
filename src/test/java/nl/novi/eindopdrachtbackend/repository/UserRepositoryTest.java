package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        // Clear the database
        entityManager.getEntityManager().createQuery("DELETE FROM Order").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Menu").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Restaurant").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM DeliveryAddress").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM User").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Role").executeUpdate();
        entityManager.clear();

        // Create roles
        Role customerRole = new Role(UserRole.CUSTOMER);
        customerRole = roleRepository.save(customerRole);

        // Assign roles to users
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        // Create users
        User johnDoe = new User("John Doe", "johndoe@example.com", "password", roles, "555-1234");
        User janeDoe = new User("Jane Doe", "janedoe@example.com", "password", roles, "555-5678");
        entityManager.persist(johnDoe);
        entityManager.persist(janeDoe);
        entityManager.flush();
    }

    @Test
    void whenFindByNameIgnoreCase_thenReturnUser() {
        // Action
        List<User> foundUsers = userRepository.findByNameIgnoreCase("john doe");

        // Verification
        assertEquals(1, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(user -> "John Doe".equals(user.getName())));
    }

    @Test
    void whenFindByNameIgnoreCaseWithDifferentCase_thenReturnUser() {
        // Action
        List<User> foundUsers = userRepository.findByNameIgnoreCase("JANE DOE");

        // Verification
        assertEquals(1, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(user -> "Jane Doe".equals(user.getName())));
    }

    @Test
    void whenFindByRole_thenReturnUsersWithThatRole() {
        // Action
        List<User> foundUsers = userRepository.findByRole(UserRole.CUSTOMER);

        // Verification
        assertEquals(2, foundUsers.size(), "Should return 2 users with CUSTOMER role");
        assertTrue(foundUsers.stream().allMatch(user -> user.getRoles().stream().anyMatch(role -> role.getRolename() == UserRole.CUSTOMER)), "All users should have the CUSTOMER role");
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        // Action
        Optional<User> foundUser = userRepository.findByEmail("johndoe@example.com");

        // Verification
        assertTrue(foundUser.isPresent(), "User with email 'johndoe@example.com' should be found");
        assertEquals("John Doe", foundUser.get().getName(), "The name of the found user should be 'John Doe'");
    }

    @Test
    void whenFindByEmailNotFound_thenReturnEmpty() {
        // Action
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Verification
        assertTrue(foundUser.isEmpty(), "No user should be found with email 'nonexistent@example.com'");
    }
}
