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

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkExpirationDates() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            if (isExpiringSoon(ingredient)) {
                for (MenuItem menuItem : ingredient.getMenuItems()) {
                    User owner = menuItem.getRestaurant().getOwner();
                    NotificationInputDTO notificationInputDTO = new NotificationInputDTO();
                    notificationInputDTO.setMessage("Ingredient " + ingredient.getName() + " is expiring soon.");
                    notificationService.createNotification(notificationInputDTO, owner);
                }
            }
        }
    }


    private boolean isExpiringSoon(Ingredient ingredient) {
        LocalDate expirationDate = LocalDate.parse(ingredient.getExpirationDate());
        return expirationDate.minusDays(EXPIRATION_WARNING_DAYS).isBefore(LocalDate.now());
    }
}
