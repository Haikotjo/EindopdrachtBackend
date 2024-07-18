package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameIgnoreCase(String name);

    Optional<Restaurant> findByOwner(User owner);
}
