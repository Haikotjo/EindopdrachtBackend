package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link Menu} entities.
 * This interface provides CRUD operations for the Menu entity.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * Finds all menus by the restaurant's ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of Menu entities associated with the specified restaurant
     */
    List<Menu> findByRestaurant_Id(Long restaurantId);

    /**
     * Finds all menus by name, ignoring case.
     *
     * @param name the name of the menu
     * @return a list of Menu entities with names matching the specified name, ignoring case
     */
    List<Menu> findByNameIgnoreCase(String name);
}
