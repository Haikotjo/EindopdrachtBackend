package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class MenuDTO {
    private Long id;
    private String name;
    private String description;
    private List<MenuItemDTO> menuItems;

    // Constructor
    public MenuDTO() {
    }

    public MenuDTO(Long id, String name, String description, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menuItems = menuItems;
    }

    // Getters en setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
