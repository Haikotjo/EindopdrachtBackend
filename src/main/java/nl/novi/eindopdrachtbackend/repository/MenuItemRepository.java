package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
