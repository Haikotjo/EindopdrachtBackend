package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deliveryAddresses")
public class DeliveryAddress {
    @Id
    private Long id;

    private String street;

    private int houseNumber;
    private String city;
    private String postcode;
    private String country;


//  constructors

    public DeliveryAddress() {
    }

    public DeliveryAddress(String street,int houseNumber, String city, String postcode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }

    //Relation to user
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    //Relation order
    @OneToMany(mappedBy = "deliveryAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        order.setDeliveryAddress(this);
    }


    public void removeOrder(Order order) {
        orders.remove(order);
        order.setDeliveryAddress(null);
    }


    //    getters and setters

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
