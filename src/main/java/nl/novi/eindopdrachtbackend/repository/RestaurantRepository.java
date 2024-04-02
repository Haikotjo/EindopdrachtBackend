package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
