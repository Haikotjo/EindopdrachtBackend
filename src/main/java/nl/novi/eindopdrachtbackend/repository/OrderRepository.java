package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
