package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.model.User;

public class NotificationMapper {

    // Convert Notification entity to NotificationDTO
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getUser().getId()
        );
    }

    // Convert NotificationInputDTO to Notification entity
    public static Notification toNotification(NotificationInputDTO notificationInputDTO, User user) {
        return new Notification(
                notificationInputDTO.getMessage(),
                user
        );
    }
}
