package nl.novi.eindopdrachtbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Data Transfer Object for MenuItem.
 */
public class MenuItemDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private double price;
    private String description;
    private String image;
    private List<IngredientDTO> ingredients;

    // Constructors

    /**
     * Constructor for MenuItemDTO.
     *
     * @param id          the ID of the menu item
     * @param name        the name of the menu item
     * @param price       the price of the menu item
     * @param description the description of the menu item
     * @param image       the image of the menu item
     * @param ingredients the list of ingredients used in the menu item
     */
    public MenuItemDTO(Long id, String name, double price, String description, String image, List<IngredientDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    /**
     * Default constructor.
     */
    public MenuItemDTO() {
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
     * Sets the ID of the menu item.
     *
     * @param id the ID of the menu item
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets the list of ingredients used in this menu item.
     *
     * @return the list of ingredients used in this menu item
     */
    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the list of ingredients used in this menu item.
     *
     * @param ingredients the list of ingredients used in this menu item
     */
    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
