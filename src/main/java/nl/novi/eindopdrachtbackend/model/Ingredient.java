package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    public Ingredient() {
    }

    // Constructor used by the owner to keep track of ingredient details,
    // such as unit, cost, supplier, expiration date, and description.
    public Ingredient(String name, int quantity, String unit, double cost, String supplier, String expirationDate, String description) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.description = description;
    }

    // Constructor used when adding ingredients to a menu item,
    // to ensure that the ingredient's menu items are also updated.
    public Ingredient(String name, int quantity, Set<MenuItem> menuItems) {
        this.name = name;
        this.quantity = quantity;
        this.menuItems = menuItems;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new HashSet<>();
        }
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.getMenuItems().add(menuItem);
        menuItem.getIngredients().add(this);
    }
}
