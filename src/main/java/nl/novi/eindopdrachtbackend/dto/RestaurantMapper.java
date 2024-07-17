package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Restaurant;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Restaurant entities and DTOs.
 */
public class RestaurantMapper {

    /**
     * Converts a Restaurant entity to a RestaurantDTO.
     *
     * @param restaurant the Restaurant entity
     * @return the RestaurantDTO
     */
    public static RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setPhoneNumber(restaurant.getPhoneNumber());

        if (restaurant.getOwner() != null) {
            UserDTO ownerDTO = new UserDTO();
            ownerDTO.setId(restaurant.getOwner().getId());
            ownerDTO.setName(restaurant.getOwner().getName());
            dto.setOwner(ownerDTO);
        }

        dto.setMenus(restaurant.getMenus().stream()
                .map(MenuMapper::toMenuDTO)
                .collect(Collectors.toSet()));

        dto.setOrders(restaurant.getOrders().stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toSet()));

        return dto;
    }

    /**
     * Converts a RestaurantInputDTO to a Restaurant entity.
     *
     * @param inputDTO the RestaurantInputDTO
     * @return the Restaurant entity
     */
    public static Restaurant toRestaurant(RestaurantInputDTO inputDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(inputDTO.getName());
        restaurant.setAddress(inputDTO.getAddress());
        restaurant.setPhoneNumber(inputDTO.getPhoneNumber());
        return restaurant;
    }
}
