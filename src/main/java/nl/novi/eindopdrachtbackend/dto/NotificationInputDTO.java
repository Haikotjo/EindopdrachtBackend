
package nl.novi.eindopdrachtbackend.dto;

/**
 * Data Transfer Object for Notification input.
 */
public class NotificationInputDTO {
    private String message;
    private Long userId;

    // Getters and setters

    /**
     * Gets the notification message.
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message.
     *
     * @param message the notification message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the user ID associated with the notification.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the notification.
     *
     * @param userId the user ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}