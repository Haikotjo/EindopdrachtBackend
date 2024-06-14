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
        entityManager.getEntityManager().createQuery("DELETE FROM Order").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Menu").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Restaurant").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM DeliveryAddress").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM User").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Role").executeUpdate();
        entityManager.clear();

        Role customerRole = new Role(UserRole.CUSTOMER);
        customerRole = roleRepository.save(customerRole);

        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

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
}
