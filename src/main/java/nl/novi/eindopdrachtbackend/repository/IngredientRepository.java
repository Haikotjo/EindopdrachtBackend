package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Ingredient entity.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Finds ingredients that have a quantity less than the specified threshold.
     *
     * @param minQuantityThreshold the minimum quantity threshold
     * @return a list of ingredients with quantity less than the specified threshold
     */
    @Query("SELECT i FROM Ingredient i WHERE i.quantity < ?1")
    List<Ingredient> findLowStockIngredients(int minQuantityThreshold);

    /**
     * Finds ingredients that have an expiration date on or before the specified date.
     *
     * @param expirationWarningDate the expiration warning date
     * @return a list of ingredients expiring on or before the specified date
     */
    @Query("SELECT i FROM Ingredient i WHERE i.expirationDate <= ?1")
    List<Ingredient> findExpiringIngredients(LocalDate expirationWarningDate);

    /**
     * Finds ingredients by the owner's ID.
     *
     * @param ownerId the ID of the owner
     * @return a list of ingredients owned by the specified owner
     */
    List<Ingredient> findByOwner_Id(Long ownerId);

    /**
     * Finds an ingredient by its ID and owner's ID.
     *
     * @param id the ID of the ingredient
     * @param ownerId the ID of the owner
     * @return an optional ingredient entity
     */
    Optional<Ingredient> findByIdAndOwner_Id(Long id, Long ownerId);
}
