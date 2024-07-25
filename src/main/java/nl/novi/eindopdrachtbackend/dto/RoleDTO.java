package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;

/**
 * RoleDTO is a Data Transfer Object for role-related information.
 * It contains the necessary information for role management.
 */
public class RoleDTO {

    /**
     * The name of the role.
     */
    public String rolename;

    /**
     * Constructs a new RoleDTO with the specified role name.
     *
     * @param rolename the name of the role
     */
    public RoleDTO(String rolename) {
        this.rolename = rolename;
    }

    /**
     * Gets the name of the role.
     *
     * @return the role name
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * Sets the name of the role.
     *
     * @param rolename the name of the role to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * Converts this RoleDTO to a UserRole enum.
     *
     * @return the UserRole corresponding to this RoleDTO
     */
    public UserRole toUserRole() {
        return UserRole.valueOf(this.rolename);
    }
}
