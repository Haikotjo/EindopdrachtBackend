package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRoleUpdateDTOTest {

    @Test
    void testGetRole() {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setRole("ADMIN");
        assertEquals("ADMIN", userRoleUpdateDTO.getRole());
    }

    @Test
    void testSetRole() {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setRole("USER");
        assertEquals("USER", userRoleUpdateDTO.getRole());
    }

    @Test
    void testRoleIsInitiallyNull() {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        assertNull(userRoleUpdateDTO.getRole());
    }

    @Test
    void testRoleCanBeUpdated() {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setRole("ADMIN");
        assertEquals("ADMIN", userRoleUpdateDTO.getRole());

        userRoleUpdateDTO.setRole("USER");
        assertEquals("USER", userRoleUpdateDTO.getRole());
    }
}
