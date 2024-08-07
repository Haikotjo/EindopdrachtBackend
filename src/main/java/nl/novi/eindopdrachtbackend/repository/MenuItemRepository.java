package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing MenuItem entities.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Find all menu items by the owner's ID.
     *
     * @param ownerId the ID of the owner
     * @return list of MenuItem entities
     */
    List<MenuItem> findByRestaurant_Owner_Id(Long ownerId);

    /**
     * Find all menu items by the restaurant's ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return list of MenuItem entities
     */
    List<MenuItem> findByRestaurant_Id(Long restaurantId);

    /**
     * Find a menu item by its ID and the owner's ID.
     *
     * @param id the ID of the menu item
     * @param ownerId the ID of the owner
     * @return an Optional containing the found MenuItem, or empty if not found
     */
    Optional<MenuItem> findByIdAndRestaurant_Owner_Id(Long id, Long ownerId);

    /**
     * Find menu items by name, ignoring case.
     *
     * @param name the name of the menu item
     * @return list of MenuItem entities
     */
    List<MenuItem> findByNameIgnoreCase(String name);
}
