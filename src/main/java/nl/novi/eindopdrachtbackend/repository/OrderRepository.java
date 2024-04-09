package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByCustomerId(Long customerId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId")
    List<Order> findOrdersByRestaurantId(@Param("restaurantId") Long restaurantId);
}
