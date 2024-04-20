package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class MenuInputDTO {
    private String name;
    private String description;
    private List<Long> menuItemsIds;

    // Getters en Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMenuItemsIds() {
        return menuItemsIds;
    }

    public void setMenuItemsIds(List<Long> menuItemsIds) {
        this.menuItemsIds = menuItemsIds;
    }
}
