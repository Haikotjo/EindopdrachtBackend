package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpirationDateCheckerService {


    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private static final int EXPIRATION_WARNING_DAYS = 7;

    @Autowired
    public ExpirationDateCheckerService(IngredientRepository ingredientRepository, UserRepository userRepository, NotificationService notificationService) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    /**
     * Schedules a daily check for expiring ingredients and sends notifications to owners if ingredients are expiring soon.
     * This method is executed at midnight every day.
     */

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpirationDates() {
        System.out.println("checkExpirationDates method called");
        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            System.out.println("Checking ingredient: " + ingredient.getName() + " with expiration date: " + ingredient.getExpirationDate());
            if (ingredient.getQuantity() > 0 && isExpiringSoon(ingredient)) {
                System.out.println("Ingredient " + ingredient.getName() + " is expiring soon");
                User owner = ingredient.getOwner();
                if (owner != null) {
                    System.out.println("Found owner: " + owner.getEmail() + " for ingredient: " + ingredient.getName());
                    NotificationInputDTO notificationInputDTO = new NotificationInputDTO();
                    notificationInputDTO.setMessage("Ingredient " + ingredient.getName() + " is expiring soon.");
                    notificationService.createNotification(notificationInputDTO, owner);
                    System.out.println("Notification created for user: " + owner.getEmail() + " about ingredient: " + ingredient.getName());
                } else {
                    System.out.println("No owner found for ingredient: " + ingredient.getName());
                }
            } else {
                System.out.println("Ingredient " + ingredient.getName() + " is not expiring soon or quantity is 0.");
            }
        }
    }

    /**
     * Checks if the given ingredient is expiring soon, within the defined warning period.
     *
     * @param ingredient the ingredient to check
     * @return true if the ingredient is expiring soon, false otherwise
     */
    private boolean isExpiringSoon(Ingredient ingredient) {
        LocalDate expirationDate = ingredient.getExpirationDate();
        LocalDate warningDate = expirationDate.minusDays(EXPIRATION_WARNING_DAYS);
        boolean expiringSoon = warningDate.isBefore(LocalDate.now());
        System.out.println("Ingredient " + ingredient.getName() + " expiring soon: " + expiringSoon);
        return expiringSoon;
    }
}
