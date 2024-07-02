package nl.novi.eindopdrachtbackend.dto;

public class NotificationDTO {
    private Long id;
    private String message;
    private Long userId;

    // Constructor
    public NotificationDTO(Long id, String message, Long userId) {
        this.id = id;
        this.message = message;
        this.userId = userId;
    }

    // Getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
