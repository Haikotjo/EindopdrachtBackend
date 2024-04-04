package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByNameIgnoreCase(String name);
}
