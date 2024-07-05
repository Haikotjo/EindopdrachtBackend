package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.HashSet;
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
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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


    //    constructors

    public Menu() {
    }

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Menu(String name, String description, Set<MenuItem> menuItems, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.menuItems = menuItems;
        this.restaurant = restaurant;
    }

    // getters and setters

    public void addMenuItem(MenuItem menuItem) {
        this.getMenuItems().add(menuItem);
        menuItem.getMenus().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}


