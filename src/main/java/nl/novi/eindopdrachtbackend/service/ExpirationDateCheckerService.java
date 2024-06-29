package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpirationDateCheckerService {

    private final IngredientRepository ingredientRepository;
    private final NotificationRepository notificationRepository;
    private static final int EXPIRATION_WARNING_DAYS = 7;

    @Autowired
    public ExpirationDateCheckerService(IngredientRepository ingredientRepository, NotificationRepository notificationRepository) {
        this.ingredientRepository = ingredientRepository;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpirationDates() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            if (isExpiringSoon(ingredient)) {
                createNotification("Ingredient " + ingredient.getName() + " is expiring soon.");
            }
        }
    }

    private boolean isExpiringSoon(Ingredient ingredient) {
        LocalDate expirationDate = LocalDate.parse(ingredient.getExpirationDate());
        return expirationDate.minusDays(EXPIRATION_WARNING_DAYS).isBefore(LocalDate.now());
    }

    private void createNotification(String message) {
        Notification notification = new Notification(message);
        notificationRepository.save(notification);
    }
}
