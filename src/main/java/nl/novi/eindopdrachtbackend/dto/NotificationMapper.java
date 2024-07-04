
package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.model.User;

/**
 * Mapper class for converting between Notification entities and DTOs.
 */
public class NotificationMapper {

    /**
     * Converts a Notification entity to a NotificationDTO.
     *
     * @param notification the Notification entity
     * @return the converted NotificationDTO
     */
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getUser().getId()
        );
    }

    /**
     * Converts a NotificationInputDTO to a Notification entity.
     *
     * @param notificationInputDTO the NotificationInputDTO
     * @param user                 the User entity associated with the notification
     * @return the converted Notification entity
     */
    public static Notification toNotification(NotificationInputDTO notificationInputDTO, User user) {
        return new Notification(
                notificationInputDTO.getMessage(),
                user
        );
    }
}
