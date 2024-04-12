package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;

public class IngredientDTO {
    private Long id;
    private String name;
    private int quantity;


    // Constructor

    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.quantity = ingredient.getQuantity();
    }

    // Getters en Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
