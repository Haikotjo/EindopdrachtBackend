package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    /**
     * Find all menus by the restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return list of Menu entities
     */
    List<Menu> findByRestaurant_Id(Long restaurantId);
    List<Menu> findByNameIgnoreCase(String name);
}
