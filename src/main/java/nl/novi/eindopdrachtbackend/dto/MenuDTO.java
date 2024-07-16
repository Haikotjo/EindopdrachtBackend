package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

/**
 * Data Transfer Object for Menu.
 */
public class MenuDTO {
    private Long id;
    private String name;
    private String description;
    private List<MenuItemDTO> menuItems;

    // Constructors

    /**
     * Default constructor.
     */
    public MenuDTO() {
    }

    /**
     * Constructor for MenuDTO.
     *
     * @param id          the ID of the menu
     * @param name        the name of the menu
     * @param description the description of the menu
     * @param menuItems   the list of menu items included in the menu
     */
    public MenuDTO(Long id, String name, String description, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menuItems = menuItems;
    }

    // Getters and Setters

    /**
     * Gets the ID of the menu.
     *
     * @return the ID of the menu
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the menu.
     *
     * @param id the ID of the menu
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Gets the list of menu items included in this menu.
     *
     * @return the list of menu items included in this menu
     */
    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    /**
     * Sets the list of menu items included in this menu.
     *
     * @param menuItems the list of menu items included in this menu
     */
    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
