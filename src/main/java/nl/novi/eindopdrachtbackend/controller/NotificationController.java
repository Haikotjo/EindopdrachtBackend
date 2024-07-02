package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.NotificationDTO;
import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    // Get all notifications (Admin only)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Get notification by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    // Create a new notification
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationInputDTO notificationInputDTO) {
        // Get the currently authenticated user
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));

        NotificationDTO newNotification = notificationService.createNotification(notificationInputDTO, currentUser);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

//    // Update a notification
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
//    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id, @RequestBody NotificationInputDTO notificationInputDTO) {
//        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationInputDTO);
//        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
//    }

    // Delete a notification
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
