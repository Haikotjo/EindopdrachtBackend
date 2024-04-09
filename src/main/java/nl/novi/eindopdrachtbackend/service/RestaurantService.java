package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Restaurant;
import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Long id, Restaurant restaurantDetails);
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);
    void deleteRestaurant(Long id);
    List<Restaurant> findByNameIgnoreCase(String name);
}
