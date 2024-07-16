package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

/**
 * Data Transfer Object for Menu input.
 */
public class MenuInputDTO {
    private String name;
    private String description;
    private List<Long> menuItemIds;

    // Getters en Setters

    /**
     * Gets the name of the menu.
     *
     * @return the name of the menu
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the menu.
     *
     * @param name the name of the menu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the menu.
     *
     * @return the description of the menu
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the menu.
     *
     * @param description the description of the menu
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of menu item IDs included in this menu.
     *
     * @return the list of menu item IDs included in this menu
     */
    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    /**
     * Sets the list of menu item IDs included in this menu.
     *
     * @param menuItemIds the list of menu item IDs included in this menu
     */
    public void setMenuItemIds(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
