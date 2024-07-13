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
    /**
     * The unique identifier for the ingredient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the ingredient.
     */
    private String name;

    /**
     * The quantity of the ingredient.
     */
    @Column(nullable = false)
    private int quantity = 0;

    /**
     * The unit of measurement for the ingredient.
     */
    private String unit;

    /**
     * The cost of the ingredient.
     */
    private double cost;

    /**
     * The supplier of the ingredient.
     */
    private String supplier;

    /**
     * The expiration date of the ingredient.
     */
    private String expirationDate;

    /**
     * The description of the ingredient.
     */
    private String description;

    /**
     * The set of menu items that use this ingredient.
     */
    @ManyToMany(mappedBy = "ingredients", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MenuItem> menuItems = new HashSet<>();

    /**
     * The owner of the ingredient.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Constructors

    /**
     * Default constructor.
     */
    public Ingredient() {
    }

    /**
     * Constructor used when adding ingredients to a menu item.
     *
     * @param name      the name of the ingredient
     * @param quantity  the quantity of the ingredient
     * @param menuItems the set of menu items that use this ingredient
     */
    public Ingredient(String name, int quantity, Set<MenuItem> menuItems) {
        this.name = name;
        this.quantity = quantity;
        this.menuItems = menuItems;
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
     * @param menuItems     the set of menu items that use this ingredient
     * @param owner         the owner of the ingredient
     */
    public Ingredient(String name, int quantity, String unit, double cost, String supplier, String expirationDate, String description, Set<MenuItem> menuItems, User owner) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.description = description;
        this.menuItems = menuItems != null ? menuItems : new HashSet<>();
        this.owner = owner;
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

    /**
     * Gets the owner of the ingredient.
     *
     * @return the owner of the ingredient
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the ingredient.
     *
     * @param owner the owner of the ingredient
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
