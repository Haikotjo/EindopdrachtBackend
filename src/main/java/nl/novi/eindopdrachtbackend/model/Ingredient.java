package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

//Relation to menuItem
    @ManyToMany(mappedBy = "ingredients")
    private Set<MenuItem> menuItems;
}
