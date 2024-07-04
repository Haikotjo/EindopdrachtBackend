package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an ingredient entity in the system.
 */
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private String unit;
    private double cost;
    private String supplier;
    private String expirationDate;
    private String description;

    @ManyToMany(mappedBy = "ingredients", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MenuItem> menuItems = new HashSet<>();

    // Constructors

    /**
     * Default constructor.
     */
    public Ingredient() {
    }

    /**
     * Constructor used by the owner to keep track of ingredient details.
     *
     * @param name          the name of the ingredient
     * @param quantity      the quantity of the ingredient
     * @param unit          the unit of measurement for the ingredient
     * @param cost          the cost of the ingredient
     * @param supplier      the supplier of the ingredient
     * @param expirationDate the expiration date of the ingredient
     * @param description   the description of the ingredient
     */
    public Ingredient(String name, int quantity, String unit, double cost, String supplier, String expirationDate, String description) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.description = description;
    }

    /**
     * Constructor used when adding ingredients to a menu item.
     *
     * @param name       the name of the ingredient
     * @param quantity   the quantity of the ingredient
     * @param menuItems  the set of menu items that use this ingredient
     */
    public Ingredient(String name, int quantity, Set<MenuItem> menuItems) {
        this.name = name;
        this.quantity = quantity;
        this.menuItems = menuItems;
    }

    // Getters and Setters

    /**
     * Gets the ID of the ingredient.
     *
     * @return the ID of the ingredient
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the ingredient.
     *
     * @return the name of the ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ingredient.
     *
     * @param name the name of the ingredient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity of the ingredient.
     *
     * @return the quantity of the ingredient
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the ingredient.
     *
     * @param quantity the quantity of the ingredient
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit of measurement for the ingredient.
     *
     * @return the unit of measurement for the ingredient
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of measurement for the ingredient.
     *
     * @param unit the unit of measurement for the ingredient
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the cost of the ingredient.
     *
     * @return the cost of the ingredient
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the ingredient.
     *
     * @param cost the cost of the ingredient
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the supplier of the ingredient.
     *
     * @return the supplier of the ingredient
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the ingredient.
     *
     * @param supplier the supplier of the ingredient
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Gets the expiration date of the ingredient.
     *
     * @return the expiration date of the ingredient
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the ingredient.
     *
     * @param expirationDate the expiration date of the ingredient
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the description of the ingredient.
     *
     * @return the description of the ingredient
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the ingredient.
     *
     * @param description the description of the ingredient
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the set of menu items that use this ingredient.
     *
     * @return the set of menu items that use this ingredient
     */
    public Set<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new HashSet<>();
        }
        return menuItems;
    }

    /**
     * Sets the set of menu items that use this ingredient.
     *
     * @param menuItems the set of menu items that use this ingredient
     */
    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Adds a menu item to the set of menu items that use this ingredient.
     *
     * @param menuItem the menu item to add
     */
    public void addMenuItem(MenuItem menuItem) {
        this.getMenuItems().add(menuItem);
        menuItem.getIngredients().add(this);
    }
}
