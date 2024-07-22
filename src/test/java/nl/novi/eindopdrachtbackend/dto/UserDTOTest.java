//package nl.novi.eindopdrachtbackend.dto;
//
//import org.junit.jupiter.api.Test;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserDTOTest {
//
//    @Test
//    void testUserDTOConstructorsAndSetters() {
//        List<String> roles = new ArrayList<>();
//        roles.add("CUSTOMER");
//
//        // Test constructor with arguments
//        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", roles, "555-1234");
//
//        // Test getters
//        assertEquals(1L, userDTO.getId());
//        assertEquals("John Doe", userDTO.getName());
//        assertEquals("john.doe@example.com", userDTO.getEmail());
//        assertEquals(roles, userDTO.getRoles());
//        assertEquals("555-1234", userDTO.getPhoneNumber());
//
//        // Test setters
//        userDTO.setId(2L);
//        userDTO.setName("Jane Doe");
//        userDTO.setEmail("jane.doe@example.com");
//        List<String> newRoles = new ArrayList<>();
//        newRoles.add("OWNER");
//        userDTO.setRoles(newRoles);
//        userDTO.setPhoneNumber("555-5678");
//
//        // Test updated values
//        assertEquals(2L, userDTO.getId());
//        assertEquals("Jane Doe", userDTO.getName());
//        assertEquals("jane.doe@example.com", userDTO.getEmail());
//        assertEquals(newRoles, userDTO.getRoles());
//        assertEquals("555-5678", userDTO.getPhoneNumber());
//    }
//}
