package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link DeliveryAddress} entities.
 * This interface provides CRUD operations for the DeliveryAddress entity.
 */
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    /**
     * Finds a delivery address by the associated user.
     *
     * @param user the user associated with the delivery address
     * @return an Optional containing the found delivery address, or empty if no delivery address found
     */
    Optional<DeliveryAddress> findByUser(User user);
}
