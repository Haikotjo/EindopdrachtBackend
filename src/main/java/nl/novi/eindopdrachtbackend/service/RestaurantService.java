package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.RestaurantDTO;
import nl.novi.eindopdrachtbackend.dto.RestaurantInputDTO;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import java.util.List;

public interface RestaurantService {
    /**
     * Get all restaurants (Admin only)
     *
     * @return list of all RestaurantDTOs
     */
    List<RestaurantDTO> getAllRestaurants();

    /**
     * Get all restaurants (public).
     *
     * @return list of all RestaurantDTOs with limited information
     */
    List<RestaurantDTO> getAllRestaurantsPublic();

    /**
     * Get the restaurant for the logged-in owner.
     *
     * @param email email of the logged-in owner
     * @return RestaurantDTO for the logged-in owner
     */
    RestaurantDTO getRestaurantForLoggedInOwner(String email);

//    RestaurantDTO createRestaurant(RestaurantInputDTO restaurantInputDTO);
//    RestaurantDTO updateRestaurant(Long id, RestaurantInputDTO restaurantInputDTO);
//    void deleteRestaurant(Long id);
//    List<RestaurantDTO> findByNameIgnoreCase(String name);
//    List<MenuDTO> getAllMenus();
}
