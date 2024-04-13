package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class MenuItemDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String image;
    private List<IngredientDTO> ingredients;

    // Constructor
    public MenuItemDTO(Long id, String name, double price, String description, String image, List<IngredientDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }
    public MenuItemDTO() {
    }

    // Getters en setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
