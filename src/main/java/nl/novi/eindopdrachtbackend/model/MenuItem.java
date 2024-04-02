package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "menuItems")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;
    private String image;


//Relation to ingredients
    @ManyToMany
    @JoinTable(
            name = "menu_item_ingredients",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;


//Relation to Menu
    @ManyToMany(mappedBy = "menuItems")
    private Set<Menu> menus;
}
