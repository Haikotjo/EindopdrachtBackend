package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a menu item entity in the system.
 */
@Entity
@Table(name = "menuItems")
public class MenuItem {
    /**
     * The unique identifier for the menu item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the menu item.
     */
    private String name;

    /**
     * The price of the menu item.
     */
    private double price;

    /**
     * The description of the menu item.
     */
    private String description;

    /**
     * The image of the menu item.
     */
    private String image;

    /**
     * The set of ingredients used in this menu item.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "menu_item_ingredients",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    /**
     * The set of menus that include this menu item.
     */
    @ManyToMany(mappedBy = "menuItems", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Menu> menus = new HashSet<>();

    /**
     * The set of orders that include this menu item.
     */
    @ManyToMany(mappedBy = "menuItems", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    /**
     * The restaurant offering this menu item.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Constructors

    /**
     * Default constructor.
     */
    public MenuItem() {
    }

    /**
     * Constructor used when adding a menu item.
     *
     * @param name        the name of the menu item
     * @param price       the price of the menu item
     * @param description the description of the menu item
     * @param image       the image of the menu item
     */
    public MenuItem(String name, double price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    /**
     * Constructor used for detailed menu item creation.
     *
     * @param name        the name of the menu item
     * @param price       the price of the menu item
     * @param description the description of the menu item
     * @param image       the image of the menu item
     * @param ingredients the set of ingredients used in this menu item
     * @param menus       the set of menus that include this menu item
     * @param restaurant  the restaurant offering this menu item
     */
    public MenuItem(String name, double price, String description, String image, Set<Ingredient> ingredients, Set<Menu> menus, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients != null ? ingredients : new HashSet<>();
        this.menus = menus != null ? menus : new HashSet<>();
        this.restaurant = restaurant;
    }

    // Getters and Setters

    /**
     * Gets the ID of the menu item.
     *
     * @return the ID of the menu item
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return the name of the menu item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the menu item.
     *
     * @param name the name of the menu item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return the price of the menu item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the menu item.
     *
     * @param price the price of the menu item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the description of the menu item.
     *
     * @return the description of the menu item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the menu item.
     *
     * @param description the description of the menu item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the image of the menu item.
     *
     * @return the image of the menu item
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image of the menu item.
     *
     * @param image the image of the menu item
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the set of ingredients used in this menu item.
     *
     * @return the set of ingredients used in this menu item
     */
    public Set<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        return ingredients;
    }

    /**
     * Sets the set of ingredients used in this menu item.
     *
     * @param ingredients the set of ingredients used in this menu item
     */
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Adds an ingredient to the set of ingredients used in this menu item.
     *
     * @param ingredient the ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        this.getIngredients().add(ingredient);
        ingredient.getMenuItems().add(this);
    }

    /**
     * Gets the set of menus that include this menu item.
     *
     * @return the set of menus that include this menu item
     */
    public Set<Menu> getMenus() {
        if (menus == null) {
            menus = new HashSet<>();
        }
        return menus;
    }

    /**
     * Sets the set of menus that include this menu item.
     *
     * @param menus the set of menus that include this menu item
     */
    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    /**
     * Gets the set of orders that include this menu item.
     *
     * @return the set of orders that include this menu item
     */
    public Set<Order> getOrders() {
        if (orders == null) {
            orders = new HashSet<>();
        }
        return orders;
    }

    /**
     * Sets the set of orders that include this menu item.
     *
     * @param orders the set of orders that include this menu item
     */
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets the restaurant offering this menu item.
     *
     * @return the restaurant offering this menu item
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the restaurant offering this menu item.
     *
     * @param restaurant the restaurant offering this menu item
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
