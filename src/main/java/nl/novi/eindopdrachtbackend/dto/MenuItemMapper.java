package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between MenuItem entities and DTOs.
 */
public class MenuItemMapper {

    /**
     * Converts a MenuItem entity to a detailed MenuItemDTO.
     *
     * @param menuItem the MenuItem entity
     * @return the detailed MenuItemDTO
     */
    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        dto.setDescription(menuItem.getDescription());
        dto.setImage(menuItem.getImage());

        List<IngredientDTO> ingredientDTOS = menuItem.getIngredients().stream()
                .map(IngredientMapper::toCustomerIngredientDTO)
                .collect(Collectors.toList());
        dto.setIngredients(ingredientDTOS);

        return dto;
    }

    /**
     * Converts a MenuItemInputDTO to a MenuItem entity.
     *
     * @param inputDTO the MenuItemInputDTO
     * @return the MenuItem entity
     */
    public static MenuItem toMenuItem(MenuItemInputDTO inputDTO, Long restaurantId) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(inputDTO.getName());
        menuItem.setPrice(inputDTO.getPrice());
        menuItem.setDescription(inputDTO.getDescription());
        menuItem.setImage(inputDTO.getImage());
        menuItem.setRestaurant(new Restaurant(restaurantId));
        return menuItem;
    }
}
