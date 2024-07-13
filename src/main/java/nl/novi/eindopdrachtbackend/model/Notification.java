package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

/**
 * Represents a notification entity.
 */
@Entity
@Table(name = "notifications")
public class Notification {

    /**
     * The unique identifier for the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The message content of the notification.
     */
    private String message;

    /**
     * The user associated with the notification.
     * This establishes a many-to-one relationship with the User entity,
     * meaning each notification is linked to a single user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor
    /**
     * Default constructor.
     */
    public Notification() {}

    /**
     * Constructs a new Notification with the given message and user.
     *
     * @param message the message content of the notification
     * @param user the user associated with the notification
     */
    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
    }

    // Getters and Setters
    /**
     * Gets the unique identifier for the notification.
     *
     * @return the id of the notification
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the message content of the notification.
     *
     * @return the message of the notification
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content of the notification.
     *
     * @param message the new message for the notification
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the user associated with the notification.
     *
     * @return the user of the notification
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the notification.
     *
     * @param user the new user for the notification
     */
    public void setUser(User user) {
        this.user = user;
    }
}
