package nl.novi.eindopdrachtbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nl.novi.eindopdrachtbackend.model.Ingredient;

/**
 * Data Transfer Object for Ingredient.
 */
public class IngredientDTO {
    private Long id;
    private String name;
    private Double cost;
    private String unit;
    private Integer quantity;
    private String supplier;
    private String expirationDate;
    private String description;
    private Long ownerId;

    // Constructor
    /**
     * Default constructor.
     */
    public IngredientDTO() {
    }

    /**
     * Constructs an IngredientDTO from an Ingredient entity.
     *
     * @param ingredient the Ingredient entity
     */
    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
        this.cost = ingredient.getCost();
        this.supplier = ingredient.getSupplier();
        this.expirationDate = ingredient.getExpirationDate();
        this.description = ingredient.getDescription();
    }

    /**
     * Constructs a simplified IngredientDTO for customers.
     *
     * @param id    the ID of the ingredient
     * @param name  the name of the ingredient
     * @param cost  the cost of the ingredient
     * @param description the description of the ingredient
     */
    public IngredientDTO(Long id, String name, Double cost, String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public IngredientDTO(Long id, String name, double cost, int quantity, String unit, String supplier, String expirationDate, String description, Long ownerId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.unit = unit;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.description = description;
        this.ownerId = ownerId;
    }

    // Getters and setters

    /**
     * Gets the ID of the ingredient.
     *
     * @return the ID of the ingredient
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the ingredient.
     *
     * @param id the ID of the ingredient
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets the owner ID of the ingredient.
     *
     * @return the owner ID of the ingredient
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the owner ID of the ingredient.
     *
     * @param ownerId the owner ID of the ingredient
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}
