package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class MenuInputDTO {
    private String name;
    private String description;
    private List<Long> menuItemIds;

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

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(List<Long> menuItemsIds) {
        this.menuItemIds = menuItemsIds;
    }
}
