package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.NotificationInputDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpirationDateCheckerService {

    private final IngredientRepository ingredientRepository;
    private final NotificationService notificationService;
    private static final int EXPIRATION_WARNING_DAYS = 7;

    @Autowired
    public ExpirationDateCheckerService(IngredientRepository ingredientRepository, NotificationService notificationService) {
        this.ingredientRepository = ingredientRepository;
        this.notificationService = notificationService;
    }

    /**
     * Schedules a daily check for expiring ingredients and sends notifications to owners if ingredients are expiring soon.
     * This method is executed at midnight every day.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpirationDates() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getQuantity() > 0 && isExpiringSoon(ingredient)) {
                for (MenuItem menuItem : ingredient.getMenuItems()) {
                    User owner = menuItem.getRestaurant().getOwner();
                    NotificationInputDTO notificationInputDTO = new NotificationInputDTO();
                    notificationInputDTO.setMessage("Ingredient " + ingredient.getName() + " is expiring soon.");
                    notificationService.createNotification(notificationInputDTO, owner);
                }
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
        return expirationDate.minusDays(EXPIRATION_WARNING_DAYS).isBefore(LocalDate.now());
    }

}
