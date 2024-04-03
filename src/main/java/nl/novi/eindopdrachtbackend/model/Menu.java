package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

//Relation to menuItem
    @ManyToMany
    @JoinTable(
            name = "menu_menuItem",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> menuItems;

//Relation to restaurants
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    
}
