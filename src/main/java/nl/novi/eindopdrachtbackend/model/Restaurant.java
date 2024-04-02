package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

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
}
