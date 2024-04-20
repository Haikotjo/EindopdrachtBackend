package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {

    public static MenuDTO toMenuDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        if (menu.getMenuItems() != null) {
            List<MenuItemDTO> menuItemDTOS = menu.getMenuItems().stream()
                    .map(MenuItemMapper::toMenuItemDTO)
                    .collect(Collectors.toList());
            dto.setMenuItems(menuItemDTOS);
        }
        return dto;
    }

    public static Menu toMenu(MenuInputDTO inputDTO) {
        Menu menu = new Menu();
        menu.setName(inputDTO.getName());
        menu.setDescription(inputDTO.getDescription());
        return menu;
    }
}
