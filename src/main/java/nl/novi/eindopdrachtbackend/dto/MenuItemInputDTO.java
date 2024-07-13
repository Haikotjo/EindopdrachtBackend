package nl.novi.eindopdrachtbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Data Transfer Object for MenuItem input.
 */
public class MenuItemInputDTO {
    private String name;
    private double price;
    private String description;
    private String image;
    private List<Long> ingredientIds;

    // Getters and Setters

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
     * Gets the list of ingredient IDs used in this menu item.
     *
     * @return the list of ingredient IDs used in this menu item
     */
    public List<Long> getIngredientIds() {
        return ingredientIds;
    }

    /**
     * Sets the list of ingredient IDs used in this menu item.
     *
     * @param ingredientIds the list of ingredient IDs used in this menu item
     */
    public void setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
