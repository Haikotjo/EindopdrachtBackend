package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;

//Relation to Menu
    @OneToMany(mappedBy = "restaurant")
    private Set<Menu> menus;

    public void addMenu(Menu menu) {
        this.getMenus().add(menu);
        menu.setRestaurant(this);
    }

    //Relation to Orders
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setRestaurant(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setRestaurant(null);
    }


//    constructors

    public Restaurant() {
    }

    public Restaurant(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Restaurant(String name, String address, String phoneNumber, Set<Menu> menus, List<Order> orders) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menus = menus;
        this.orders = orders;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Menu> getMenus() {
        if (menus == null) {
            menus = new HashSet<>();
        }
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public List<Order> getOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
