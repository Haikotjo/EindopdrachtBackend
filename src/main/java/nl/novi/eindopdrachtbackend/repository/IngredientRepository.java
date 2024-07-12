package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE i.quantity < ?1")
    List<Ingredient> findLowStockIngredients(int minQuantityThreshold);

    @Query("SELECT i FROM Ingredient i WHERE i.expirationDate <= ?1")
    List<Ingredient> findExpiringIngredients(LocalDate expirationWarningDate);

    List<Ingredient> findByOwner_Id(Long ownerId);

    Optional<Ingredient> findByIdAndOwner_Id(Long id, Long ownerId);

}