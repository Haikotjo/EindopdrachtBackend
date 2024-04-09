package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameIgnoreCase(String name);
}
