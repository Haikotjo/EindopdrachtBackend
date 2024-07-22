package nl.novi.eindopdrachtbackend.exception;

/**
 * ResourceNotFoundException is a custom exception that is thrown when a requested resource is not found.
 * This exception extends RuntimeException and can be used to indicate that a specific resource could not be found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message, which provides more information about the reason for the exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
