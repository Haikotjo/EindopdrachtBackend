package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UserRole> {
}
