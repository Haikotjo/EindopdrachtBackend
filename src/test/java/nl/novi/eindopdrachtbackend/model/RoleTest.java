package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(UserRole.CUSTOMER);
    }

    @Test
    void testRoleGettersAndSetters() {
        // Test initial value from constructor
        assertEquals(UserRole.CUSTOMER, role.getRolename());

        // Test setter
        role.setRolename(UserRole.OWNER);
        assertEquals(UserRole.OWNER, role.getRolename());
    }

    @Test
    void testRoleConstructor() {
        Role adminRole = new Role(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, adminRole.getRolename());
    }
}
