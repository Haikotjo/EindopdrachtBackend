package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
