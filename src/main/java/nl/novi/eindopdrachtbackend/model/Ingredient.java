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

//Relation to menuItem
@ManyToMany(mappedBy = "ingredients", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
private Set<MenuItem> menuItems = new HashSet<>();

    public void addMenuItem(MenuItem menuItem) {
        this.getMenuItems().add(menuItem);
        menuItem.getIngredients().add(this);
    }



//    constructors

    public Ingredient() {
    }

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
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
        if (menuItems == null) {
            menuItems = new HashSet<>();
        }
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
