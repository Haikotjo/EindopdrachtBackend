package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

//Relation to menuItem
    @ManyToMany(mappedBy = "ingredients")
    private Set<MenuItem> menuItems;

//    constructors
    public Ingredient() {
    }

    public Ingredient(String name, int quantity, Set<MenuItem> menuItems) {
        this.name = name;
        this.quantity = quantity;
        this.menuItems = menuItems;
    }

    //    getters and setters
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

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
