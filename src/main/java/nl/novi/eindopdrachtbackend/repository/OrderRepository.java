package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Order} entities.
 * This interface provides CRUD operations for the Order entity.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find orders by customer ID.
     *
     * @param customerId the ID of the customer
     * @return a list of Order entities
     */
    List<Order> findByCustomerId(Long customerId);

    /**
     * Find orders by restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of Order entities
     */
    List<Order> findByRestaurantId(Long restaurantId);

    /**
     * Find orders by customer ID using a query.
     *
     * @param customerId the ID of the customer
     * @return a list of Order entities
     */
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findOrdersByCustomerId(@Param("customerId") Long customerId);

    /**
     * Find an order by ID and customer ID.
     *
     * @param id the ID of the order
     * @param customerId the ID of the customer
     * @return an Optional containing the Order entity if found
     */
    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);

    /**
     * Find an order by ID and restaurant owner ID.
     *
     * @param id the ID of the order
     * @param ownerId the ID of the restaurant owner
     * @return an Optional containing the Order entity if found
     */
    Optional<Order> findByIdAndRestaurantOwnerId(Long id, Long ownerId);

    /**
     * Find orders by restaurant ID and order date time between start and end of the day.
     *
     * @param restaurantId the ID of the restaurant
     * @param startOfDay the start of the day
     * @param endOfDay the end of the day
     * @return a list of Order entities
     */
    List<Order> findByRestaurantIdAndOrderDateTimeBetween(Long restaurantId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
