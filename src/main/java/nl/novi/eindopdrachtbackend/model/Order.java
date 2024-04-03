package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relation to users
    @ManyToOne
    private User customer;

    private boolean fulfilled;


    //    constructors

    public Order() {
    }

    public Order(User customer, boolean fulfilled) {
        this.customer = customer;
        this.fulfilled = fulfilled;
    }

// getters and setters


    public Long getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
}

