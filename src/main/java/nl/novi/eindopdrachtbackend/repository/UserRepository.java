package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameIgnoreCase(String name);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.rolename = :role")
    List<User> findByRole(UserRole role);
}
