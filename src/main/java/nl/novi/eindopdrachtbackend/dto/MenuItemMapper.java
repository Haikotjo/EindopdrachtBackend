package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemMapper {

    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        dto.setDescription(menuItem.getDescription());
        dto.setImage(menuItem.getImage());
        if (menuItem.getIngredients() != null) {
            List<IngredientDTO> ingredientDTOS = menuItem.getIngredients().stream()
                    .map(IngredientMapper::toIngredientDTO)
                    .collect(Collectors.toList());
            dto.setIngredients(ingredientDTOS);
        }
        return dto;
    }

    public static MenuItem toMenuItem(MenuItemInputDTO inputDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(inputDTO.getName());
        menuItem.setPrice(inputDTO.getPrice());
        menuItem.setDescription(inputDTO.getDescription());
        menuItem.setImage(inputDTO.getImage());
        return menuItem;
    }
}
