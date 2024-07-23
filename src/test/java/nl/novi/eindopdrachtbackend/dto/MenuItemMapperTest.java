package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemMapperTest {

    private MenuItem menuItem;
    private MenuItemInputDTO inputDTO;
    private Restaurant restaurant;
    private List<Ingredient> ingredients;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        restaurant = new Restaurant();
        setField(restaurant, "id", 1L);

        ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        setField(ingredient, "id", 1L);
        ingredient.setName("Tomato");
        ingredient.setCost(1.0);
        ingredients.add(ingredient);

        menuItem = new MenuItem();
        setField(menuItem, "id", 1L);
        menuItem.setName("Pizza");
        menuItem.setPrice(12.5);
        menuItem.setDescription("Delicious cheese pizza");
        menuItem.setImage("image_url");
        menuItem.setIngredients(new HashSet<>(ingredients));
        menuItem.setRestaurant(restaurant);

        inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Pizza");
        inputDTO.setPrice(12.5);
        inputDTO.setDescription("Delicious cheese pizza");
        inputDTO.setImage("image_url");
        inputDTO.setIngredientIds(List.of(1L));
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testToMenuItemDTO() {
        MenuItemDTO dto = MenuItemMapper.toMenuItemDTO(menuItem);
        assertEquals(menuItem.getId(), dto.getId());
        assertEquals(menuItem.getName(), dto.getName());
        assertEquals(menuItem.getPrice(), dto.getPrice());
        assertEquals(menuItem.getDescription(), dto.getDescription());
        assertEquals(menuItem.getImage(), dto.getImage());

        List<IngredientDTO> ingredientDTOS = dto.getIngredients();
        assertEquals(ingredients.size(), ingredientDTOS.size());
        assertEquals(ingredients.get(0).getId(), ingredientDTOS.get(0).getId());
        assertEquals(ingredients.get(0).getName(), ingredientDTOS.get(0).getName());
        assertEquals(ingredients.get(0).getCost(), ingredientDTOS.get(0).getCost());
    }

    @Test
    public void testToMenuItem() {
        MenuItem entity = MenuItemMapper.toMenuItem(inputDTO, restaurant.getId());
        assertEquals(inputDTO.getName(), entity.getName());
        assertEquals(inputDTO.getPrice(), entity.getPrice());
        assertEquals(inputDTO.getDescription(), entity.getDescription());
        assertEquals(inputDTO.getImage(), entity.getImage());
        assertEquals(restaurant.getId(), entity.getRestaurant().getId());
    }
}
