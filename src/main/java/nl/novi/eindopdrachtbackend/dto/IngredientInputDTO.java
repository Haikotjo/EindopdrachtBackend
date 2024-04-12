package nl.novi.eindopdrachtbackend.dto;

public class IngredientInputDTO {
    private String name;
    private int quantity;

    // Getters en Setters
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
