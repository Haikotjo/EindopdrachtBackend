package nl.novi.eindopdrachtbackend.dto;

/**
 * UserRoleUpdateDTO is a Data Transfer Object for updating user roles.
 * It contains the necessary information for updating a user's role.
 */
public class UserRoleUpdateDTO {

    /**
     * The new role of the user.
     */
    private String role;

    /**
     * Gets the new role of the user.
     *
     * @return the new role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the new role of the user.
     *
     * @param role the new role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
