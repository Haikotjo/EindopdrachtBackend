package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Restaurant;

import java.util.stream.Collectors;

public class RestaurantMapper {

    public static RestaurantDTO toDTO(Restaurant restaurant) {
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

        dto.setMenus(restaurant.getMenus().stream().map(MenuMapper::toMenuDTO).collect(Collectors.toSet()));

        // Uitgecommentarieerd totdat OrderDTO en OrderMapper zijn geïmplementeerd
        // dto.setOrders(restaurant.getOrders().stream().map(OrderMapper::toDTO).collect(Collectors.toSet()));

        return dto;
    }

    public static Restaurant fromInputDTO(RestaurantInputDTO inputDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(inputDTO.getName());
        restaurant.setAddress(inputDTO.getAddress());
        restaurant.setPhoneNumber(inputDTO.getPhoneNumber());
        return restaurant;
    }
}
