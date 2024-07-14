package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByNameIgnoreCase(String name);

    /**
     * Find all menu items by the owner's ID.
     *
     * @param ownerId the ID of the owner
     * @return list of MenuItem entities
     */
    List<MenuItem> findByRestaurant_Owner_Id(Long ownerId);

    List<MenuItem> findByRestaurant_Id(Long restaurantId);


    Optional<MenuItem> findByIdAndRestaurant_Owner_Id(Long id, Long ownerId);
}
