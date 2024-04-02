package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
