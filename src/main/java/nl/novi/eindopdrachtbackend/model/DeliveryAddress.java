package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveryAddresses")
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String postcode;
    private String country;
}
