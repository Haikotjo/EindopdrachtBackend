package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.NotificationDTO;
import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.model.User;

import java.util.List;

public interface NotificationService {

    /**
     * Get all notifications (Admin only)
     *
     * @return list of all NotificationDTOs
     */
    List<NotificationDTO> getAllNotifications();

    /**
     * Get notification by ID for Admin
     *
     * @param id notification ID
     * @return NotificationDTO
     */
    NotificationDTO getNotificationById(Long id);

    /**
     * Get notification by ID for Owner (own notifications only)
     *
     * @param id notification ID
     * @param ownerEmail owner email
     * @return NotificationDTO
     */
    NotificationDTO getNotificationByIdForOwner(Long id, String ownerEmail);

    /**
     * Get all notifications for a specific user (Admin only)
     *
     * @param userId user ID
     * @return list of NotificationDTOs
     */
    List<NotificationDTO> getNotificationsByUserId(Long userId);

    /**
     * Create a new notification for the logged-in owner or for a specific user by admin
     *
     * @param notificationInputDTO notification input data
     * @param user user entity
     * @return created NotificationDTO
     */
    NotificationDTO createNotification(NotificationInputDTO notificationInputDTO, User user);

    /**
     * Update a notification for the logged-in owner or by admin
     *
     * @param id notification ID
     * @param notificationInputDTO notification input data
     * @param user user entity
     * @return updated NotificationDTO
     */
    NotificationDTO updateNotification(Long id, NotificationInputDTO notificationInputDTO, User user);

    /**
     * Delete notification for the owner
     *
     * @param notificationId notification ID
     * @param owner owner entity
     */
    void deleteNotificationForOwner(Long notificationId, User owner);

    /**
     * Delete notification for admin
     *
     * @param notificationId notification ID
     * @param userId user ID
     */
    void deleteNotificationForAdmin(Long notificationId, Long userId);
}
