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
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(final NotificationRepository notificationRepository, final UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationDTO getNotificationById(final Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationDTO getNotificationByIdForOwner(final Long id, final String ownerEmail) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        if (!notification.getUser().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("You are not authorized to view this notification.");
        }

        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationDTO> getNotificationsByUserId(final Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationDTO createNotification(final NotificationInputDTO notificationInputDTO, final User user) {
        Notification notification = NotificationMapper.toNotification(notificationInputDTO, user);
        notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationDTO updateNotification(final Long id, final NotificationInputDTO notificationInputDTO, final User user) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        if (!notification.getUser().equals(user) && !user.getRoles().contains("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to update this notification");
        }

        notification.setMessage(notificationInputDTO.getMessage());
        notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTO(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNotificationForOwner(final Long notificationId, final User owner) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        if (!notification.getUser().getId().equals(owner.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this notification");
        }
        notificationRepository.delete(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
