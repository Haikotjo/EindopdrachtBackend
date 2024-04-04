package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean fulfilled;

    //Relation to users
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    //Relation to restaurant
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



    //    constructors

    public Order() {
    }

    public Order(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Order(User customer, boolean fulfilled) {
        this.customer = customer;
        this.fulfilled = fulfilled;
    }

// getters and setters


    public Long getId() {
        return id;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

