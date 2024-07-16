package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a menu entity in the system.
 */
@Entity
@Table(name = "menus")
public class Menu {
    /**
     * The unique identifier for the menu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the menu.
     */
    private String name;

    /**
     * The description of the menu.
     */
    private String description;

    /**
     * The set of menu items included in this menu.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "menu_menuItem",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> menuItems = new HashSet<>();

    /**
     * The restaurant that offers this menu.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // Constructors

    /**
     * Default constructor.
     */
    public Menu() {
    }

    /**
     * Constructor used when adding a menu.
     *
     * @param name        the name of the menu
     * @param description the description of the menu
     */
    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor used for detailed menu creation.
     *
     * @param name        the name of the menu
     * @param description the description of the menu
     * @param menuItems   the set of menu items included in this menu
     * @param restaurant  the restaurant that offers this menu
     */
    public Menu(String name, String description, Set<MenuItem> menuItems, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.menuItems = menuItems != null ? menuItems : new HashSet<>();
        this.restaurant = restaurant;
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
     * Gets the set of menu items included in this menu.
     *
     * @return the set of menu items included in this menu
     */
    public Set<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new HashSet<>();
        }
        return menuItems;
    }

    /**
     * Sets the set of menu items included in this menu.
     *
     * @param menuItems the set of menu items included in this menu
     */
    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Adds a menu item to the set of menu items included in this menu.
     *
     * @param menuItem the menu item to add
     */
    public void addMenuItem(MenuItem menuItem) {
        this.getMenuItems().add(menuItem);
        menuItem.getMenus().add(this);
    }

    /**
     * Gets the restaurant that offers this menu.
     *
     * @return the restaurant that offers this menu
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the restaurant that offers this menu.
     *
     * @param restaurant the restaurant that offers this menu
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
