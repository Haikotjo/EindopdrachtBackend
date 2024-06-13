package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;

public class RoleDTO {
    public String rolename;

    // Constructor
    public RoleDTO(String rolename) {
        this.rolename = rolename;
    }

    // Getter and Setter
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    // Method to convert to UserRole enum
    public UserRole toUserRole() {
        return UserRole.valueOf(this.rolename);
    }
}
