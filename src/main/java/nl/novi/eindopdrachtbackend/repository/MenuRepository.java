package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByNameIgnoreCase(String name);
}
