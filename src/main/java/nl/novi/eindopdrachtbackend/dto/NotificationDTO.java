package nl.novi.eindopdrachtbackend.dto;

/**
 * Data Transfer Object for Notification.
 */
public class NotificationDTO {
    private Long id;
    private String message;
    private Long userId;

    /**
     * Constructor for NotificationDTO.
     *
     * @param id      the notification ID
     * @param message the notification message
     * @param userId  the user ID associated with the notification
     */
    public NotificationDTO(Long id, String message, Long userId) {
        this.id = id;
        this.message = message;
        this.userId = userId;
    }

    // Getters and setters

    /**
     * Gets the notification ID.
     *
     * @return the notification ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the notification ID.
     *
     * @param id the notification ID
     */
    public void setId(Long id) {
        this.id = id;
    }

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
