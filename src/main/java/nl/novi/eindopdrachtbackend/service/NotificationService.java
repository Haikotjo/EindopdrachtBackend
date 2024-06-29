package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.NotificationRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    private static final int MIN_QUANTITY_THRESHOLD = 10;
    private static final int EXPIRATION_WARNING_DAYS = 7;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public void createNotification(Long userId) {
        // Get the user's email address
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        String email = user.getEmail();

        // Get low stock and expiring ingredients
        List<Ingredient> lowStockIngredients = ingredientRepository.findLowStockIngredients(MIN_QUANTITY_THRESHOLD);
        LocalDate expirationWarningDate = LocalDate.now().plusDays(EXPIRATION_WARNING_DAYS);
        List<Ingredient> expiringIngredients = ingredientRepository.findExpiringIngredients(expirationWarningDate);

        // Create notification message
        StringBuilder message = new StringBuilder();
        message.append("Attention! The following ingredients require your attention:\n\n");

        if (!lowStockIngredients.isEmpty()) {
            message.append("Low Stock Ingredients:\n");
            for (Ingredient ingredient : lowStockIngredients) {
                message.append("- ").append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append(" ").append(ingredient.getUnit()).append("\n");
            }
        }

        if (!expiringIngredients.isEmpty()) {
            message.append("\nExpiring Ingredients:\n");
            for (Ingredient ingredient : expiringIngredients) {
                message.append("- ").append(ingredient.getName()).append(": Expiration Date ").append(ingredient.getExpirationDate()).append("\n");
            }
        }

        // Create and save the notification
        Notification notification = new Notification(message.toString());
        notificationRepository.save(notification);

        // Send the email
        emailService.sendSimpleMessage(email, "Ingredient Notification", message.toString());
    }
}