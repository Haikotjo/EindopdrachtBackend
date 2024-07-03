package nl.novi.eindopdrachtbackend.dto;

public class NotificationInputDTO {
    private String message;
    private Long userId;

    // Getters en setters
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
