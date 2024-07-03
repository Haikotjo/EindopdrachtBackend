package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.NotificationDTO;
import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.dto.NotificationMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.NotificationRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(final NotificationRepository notificationRepository, final UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all notifications (Admin only)
     *
     * @return list of all NotificationDTOs
     */
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get notification by ID for Admin
     *
     * @param id notification ID
     * @return NotificationDTO
     */
    public NotificationDTO getNotificationById(final Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * Get notification by ID for Owner (own notifications only)
     *
     * @param id notification ID
     * @param ownerEmail owner email
     * @return NotificationDTO
     */
    public NotificationDTO getNotificationByIdForOwner(final Long id, final String ownerEmail) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        if (!notification.getUser().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("You are not authorized to view this notification.");
        }

        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * Get all notifications for a specific user (Admin only)
     *
     * @param userId user ID
     * @return list of NotificationDTOs
     */
    public List<NotificationDTO> getNotificationsByUserId(final Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new notification for the logged-in owner or for a specific user by admin
     *
     * @param notificationInputDTO notification input data
     * @param user user entity
     * @return created NotificationDTO
     */
    public NotificationDTO createNotification(final NotificationInputDTO notificationInputDTO, final User user) {
        Notification notification = NotificationMapper.toNotification(notificationInputDTO, user);
        notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * Update a notification for the logged-in owner or by admin
     *
     * @param id notification ID
     * @param notificationInputDTO notification input data
     * @param user user entity
     * @return updated NotificationDTO
     */
    public NotificationDTO updateNotification(final Long id, final NotificationInputDTO notificationInputDTO, final User user) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        // Ensure the user is the owner or an admin
        if (!notification.getUser().equals(user) && !user.getRoles().contains("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to update this notification");
        }

        notification.setMessage(notificationInputDTO.getMessage());
        notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * Delete notification for the owner
     *
     * @param notificationId notification ID
     * @param owner owner entity
     */
    public void deleteNotificationForOwner(final Long notificationId, final User owner) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        if (!notification.getUser().getId().equals(owner.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this notification");
        }
        notificationRepository.delete(notification);
    }

    /**
     * Delete notification for admin
     *
     * @param notificationId notification ID
     * @param userId user ID
     */
    public void deleteNotificationForAdmin(final Long notificationId, final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Notification does not belong to the specified user");
        }
        notificationRepository.delete(notification);
    }
}
