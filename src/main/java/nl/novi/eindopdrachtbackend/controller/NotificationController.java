package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.NotificationDTO;
import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    /**
     * Get all notifications (Admin only)
     *
     * @return ResponseEntity containing a list of NotificationDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    /**
     * Get notification by ID for Admin
     *
     * @param id Notification ID
     * @return ResponseEntity containing the NotificationDTO
     */
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NotificationDTO> getNotificationByIdForAdmin(@PathVariable Long id) {
        NotificationDTO notification = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    /**
     * Get notification by ID for Owner (own notifications only)
     *
     * @param id Notification ID
     * @param principal Principal object representing the currently authenticated user
     * @return ResponseEntity containing the NotificationDTO
     */
    @GetMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<NotificationDTO> getNotificationByIdForOwner(@PathVariable Long id, Principal principal) {
        NotificationDTO notification = notificationService.getNotificationByIdForOwner(id, principal.getName());
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    /**
     * Get all notifications for a specific user (Admin only)
     *
     * @param userId User ID
     * @return ResponseEntity containing a list of NotificationDTO objects
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    /**
     * Get all notifications for the logged-in owner
     *
     * @return ResponseEntity containing a list of NotificationDTO objects
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForOwner() {
        User currentUser = getCurrentUser();
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(currentUser.getId());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    /**
     * Create a new notification for the logged-in owner
     *
     * @param notificationInputDTO Notification input data
     * @return ResponseEntity containing the created NotificationDTO
     */
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<NotificationDTO> createNotificationForOwner(@RequestBody NotificationInputDTO notificationInputDTO) {
        User currentUser = getCurrentUser();
        NotificationDTO newNotification = notificationService.createNotification(notificationInputDTO, currentUser);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    /**
     * Create a new notification for a specific user (admin only)
     *
     * @param notificationInputDTO Notification input data
     * @return ResponseEntity containing the created NotificationDTO
     */
    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NotificationDTO> createNotificationForUser(@RequestBody NotificationInputDTO notificationInputDTO) {
        Long userId = notificationInputDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        NotificationDTO newNotification = notificationService.createNotification(notificationInputDTO, user);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    /**
     * Update an existing notification for the logged-in owner
     *
     * @param id Notification ID
     * @param notificationInputDTO Notification input data
     * @return ResponseEntity containing the updated NotificationDTO
     */
    @PutMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<NotificationDTO> updateNotificationForOwner(@PathVariable Long id, @RequestBody NotificationInputDTO notificationInputDTO) {
        User currentUser = getCurrentUser();
        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationInputDTO, currentUser);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    /**
     * Update an existing notification for a specific user (admin only)
     *
     * @param id Notification ID
     * @param notificationInputDTO Notification input data
     * @return ResponseEntity containing the updated NotificationDTO
     */
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NotificationDTO> updateNotificationForUser(@PathVariable Long id, @RequestBody NotificationInputDTO notificationInputDTO) {
        Long userId = notificationInputDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationInputDTO, user);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    /**
     * Delete notification for the owner
     *
     * @param id Notification ID
     * @return ResponseEntity with HttpStatus NO_CONTENT
     */
    @DeleteMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Void> deleteNotificationForOwner(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        notificationService.deleteNotificationForOwner(id, currentUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete notification for admin
     *
     * @param id Notification ID
     * @param notificationInputDTO Notification input data
     * @return ResponseEntity with HttpStatus NO_CONTENT
     */
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteNotificationForAdmin(@PathVariable Long id, @RequestBody NotificationInputDTO notificationInputDTO) {
        Long userId = notificationInputDTO.getUserId();
        notificationService.deleteNotificationForAdmin(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Private method to get the currently authenticated user
     *
     * @return User entity
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
    }
}
