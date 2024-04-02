package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
