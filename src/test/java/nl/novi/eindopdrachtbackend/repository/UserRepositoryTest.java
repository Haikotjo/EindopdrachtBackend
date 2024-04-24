package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createQuery("DELETE FROM DeliveryAddress").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Order").executeUpdate();

        entityManager.getEntityManager().createQuery("DELETE FROM User").executeUpdate();
        entityManager.clear();

        User johnDoe = new User("John Doe", "johndoe@example.com", "password", UserRole.CUSTOMER, "555-1234");
        User janeDoe = new User("Jane Doe", "janedoe@example.com", "password", UserRole.CUSTOMER, "555-5678");
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
        assertTrue(foundUsers.stream().allMatch(user -> user.getRole() == UserRole.CUSTOMER), "All users should have the CUSTOMER role");
    }

}
