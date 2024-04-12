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

    public IngredientDTO() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
