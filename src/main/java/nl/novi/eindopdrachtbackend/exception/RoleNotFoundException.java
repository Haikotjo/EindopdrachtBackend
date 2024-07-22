package nl.novi.eindopdrachtbackend.exception;

/**
 * RoleNotFoundException is a custom exception that is thrown when a requested role is not found.
 * This exception extends RuntimeException and can be used to indicate that a specific role could not be found.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new RoleNotFoundException with the specified detail message.
     *
     * @param message the detail message, which provides more information about the reason for the exception
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
