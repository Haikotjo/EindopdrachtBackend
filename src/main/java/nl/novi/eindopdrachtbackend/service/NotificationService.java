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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return NotificationMapper.toNotificationDTO(notification);
    }

    public NotificationDTO createNotification(NotificationInputDTO notificationInputDTO, User user) {
        Notification notification = NotificationMapper.toNotification(notificationInputDTO, user);
        notificationRepository.save(notification);
        return NotificationMapper.toNotificationDTO(notification);
    }

//    public NotificationDTO updateNotification(Long id, NotificationInputDTO notificationInputDTO) {
//        Notification existingNotification = notificationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
//
//        User user = userRepository.findById(notificationInputDTO.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + notificationInputDTO.getUserId()));
//
//        existingNotification.setMessage(notificationInputDTO.getMessage());
//        existingNotification.setUser(user);
//
//        notificationRepository.save(existingNotification);
//        return NotificationMapper.toNotificationDTO(existingNotification);
//    }

    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notificationRepository.delete(notification);
    }

}
