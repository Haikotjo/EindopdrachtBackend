package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Enumerated(EnumType.STRING)
    private UserRole rolename;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    // Getters and Setters

    public UserRole getRolename() {
        return rolename;
    }

    public void setRolename(UserRole rolename) {
        this.rolename = rolename;
    }
}
