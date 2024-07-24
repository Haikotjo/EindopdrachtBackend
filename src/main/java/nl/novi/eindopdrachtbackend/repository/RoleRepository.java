package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Role} entities.
 * This interface provides CRUD operations for the Role entity.
 */
public interface RoleRepository extends JpaRepository<Role, UserRole> {
}
