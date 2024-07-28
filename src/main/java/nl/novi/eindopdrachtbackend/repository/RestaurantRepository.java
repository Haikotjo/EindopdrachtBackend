package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Restaurant} entities.
 * This interface provides CRUD operations for the Restaurant entity.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /**
     * Find restaurants by name, case insensitive.
     *
     * @param name the name of the restaurant
     * @return a list of Restaurant entities matching the given name
     */
    List<Restaurant> findByNameIgnoreCase(String name);

    /**
     * Find a restaurant by its owner.
     *
     * @param owner the owner of the restaurant
     * @return an Optional containing the Restaurant entity
     */
    Optional<Restaurant> findByOwner(User owner);

    /**
     * Find a restaurant by the owner's ID.
     *
     * @param ownerId the ID of the owner
     * @return an Optional containing the Restaurant entity
     */
    Optional<Restaurant> findByOwnerId(Long ownerId);
}
