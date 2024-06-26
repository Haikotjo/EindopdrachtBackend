package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.HashSet;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "menu_item_ingredients",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    //Relation to Menu
    @ManyToMany(mappedBy = "menuItems", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Menu> menus;

    // Relation to Orders
    @ManyToMany(mappedBy = "menuItems", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();


    public void addIngredient(Ingredient ingredient) {
        this.getIngredients().add(ingredient);
        ingredient.getMenuItems().add(this);
    }


    //    constructors

    public MenuItem() {
    }

    public MenuItem(String name, double price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public MenuItem(String name, double price, String description, String image, Set<Ingredient> ingredients, Set<Menu> menus) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
        this.menus = menus;
    }


    // getters and setters

    public Long getId() {
        return id;
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

    public Set<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Menu> getMenus() {
        if (menus == null){
            menus = new HashSet<>();
        }
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Order> getOrders() {
        if (orders == null){
            orders = new HashSet<>();
        }
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}


