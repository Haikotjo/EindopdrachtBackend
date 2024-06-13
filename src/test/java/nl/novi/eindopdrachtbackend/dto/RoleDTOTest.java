package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleDTOTest {

    @Test
    void testRoleDTO() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        RoleDTO roleDto = new RoleDTO("ADMIN");

        // Act
        Field field = roleDto.getClass().getDeclaredField("rolename");
        field.setAccessible(true);
        String rolename = (String) field.get(roleDto);

        // Assert
        assertEquals("ADMIN", rolename);
        assertEquals(UserRole.ADMIN, roleDto.toUserRole());

        // Act again for setters
        roleDto.setRolename("CUSTOMER");

        // Assert updated values
        assertEquals("CUSTOMER", roleDto.getRolename());
        assertEquals(UserRole.CUSTOMER, roleDto.toUserRole());
    }
}
