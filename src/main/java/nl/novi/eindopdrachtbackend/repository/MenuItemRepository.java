package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByNameIgnoreCase(String name);

    /**
     * Find all menu items by the owner's ID.
     *
     * @param ownerId the ID of the owner
     * @return list of MenuItem entities
     */
    List<MenuItem> findByRestaurant_Owner_Id(Long ownerId);
}
