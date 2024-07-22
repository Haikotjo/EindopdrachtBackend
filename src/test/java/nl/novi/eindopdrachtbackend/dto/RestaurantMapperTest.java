//package nl.novi.eindopdrachtbackend.dto;
//
//import nl.novi.eindopdrachtbackend.model.Order;
//import nl.novi.eindopdrachtbackend.model.Restaurant;
//import nl.novi.eindopdrachtbackend.model.User;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Field;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RestaurantMapperTest {
//
//    @Test
//    void testToDTO() {
//        // Arrange
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("Gourmet Place");
//        restaurant.setAddress("123 Tasty Street");
//        restaurant.setPhoneNumber("1234567890");
//
//        User owner = new User();
//        owner.setName("John Doe");
//        restaurant.setOwner(owner);
//
//        restaurant.setMenus(new HashSet<>());  // Assumed to be empty for simplicity
//
//        // Act
//        RestaurantDTO dto = RestaurantMapper.toDTO(restaurant);
//
//        // Assert
//        assertEquals("Gourmet Place", dto.getName());
//        assertEquals("123 Tasty Street", dto.getAddress());
//        assertEquals("1234567890", dto.getPhoneNumber());
//        assertNotNull(dto.getOwner());
//        assertEquals("John Doe", dto.getOwner().getName());
//        assertTrue(dto.getMenus().isEmpty());
//    }
//
//    @Test
//    void testFromInputDTO() {
//        // Arrange
//        RestaurantInputDTO inputDTO = new RestaurantInputDTO();
//        inputDTO.setName("Gourmet Place");
//        inputDTO.setAddress("123 Tasty Street");
//        inputDTO.setPhoneNumber("1234567890");
//
//        // Act
//        Restaurant restaurant = RestaurantMapper.fromInputDTO(inputDTO);
//
//        // Assert
//        assertEquals("Gourmet Place", restaurant.getName());
//        assertEquals("123 Tasty Street", restaurant.getAddress());
//        assertEquals("1234567890", restaurant.getPhoneNumber());
//    }
//
//    private void setIdUsingReflection(Object obj, Long idValue) throws NoSuchFieldException, IllegalAccessException {
//        Field idField = obj.getClass().getDeclaredField("id");
//        idField.setAccessible(true);
//        idField.set(obj, idValue);
//    }
//
//    @Test
//    void testToDTOWithReflection() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        Restaurant restaurant = new Restaurant();
//        setIdUsingReflection(restaurant, 1L);
//        restaurant.setName("Gourmet Place");
//        restaurant.setAddress("123 Tasty Street");
//        restaurant.setPhoneNumber("1234567890");
//
//        // Act
//        RestaurantDTO dto = RestaurantMapper.toDTO(restaurant);
//
//        // Assert
//        assertEquals(1L, dto.getId());
//    }
//
//    @Test
//    void testToDTOWithOrders() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        Restaurant restaurant = new Restaurant();
//        setIdUsingReflection(restaurant, 1L);
//        restaurant.setName("Gourmet Place");
//        restaurant.setAddress("123 Tasty Street");
//        restaurant.setPhoneNumber("1234567890");
//
//        User owner = new User();
//        setIdUsingReflection(owner, 1L);
//        owner.setName("John Doe");
//        restaurant.setOwner(owner);
//
//        User customer = new User();
//        setIdUsingReflection(customer, 2L);
//        customer.setName("Jane Smith");
//
//        Order order = new Order();
//        setIdUsingReflection(order, 1L);
//        order.setRestaurant(restaurant);
//        order.setCustomer(customer); // Voeg de klant toe aan de bestelling
//
//        Set<Order> orders = new HashSet<>();
//        orders.add(order);
//        restaurant.setOrders(orders);
//
//        // Act
//        RestaurantDTO dto = RestaurantMapper.toDTO(restaurant);
//
//        // Assert
//        assertEquals(1L, dto.getId());
//        assertEquals("Gourmet Place", dto.getName());
//        assertEquals("123 Tasty Street", dto.getAddress());
//        assertEquals("1234567890", dto.getPhoneNumber());
//        assertNotNull(dto.getOwner());
//        assertEquals("John Doe", dto.getOwner().getName());
//        assertNotNull(dto.getOrders());
//        assertEquals(1, dto.getOrders().size());
//        assertEquals(1L, dto.getOrders().iterator().next().getId());
//    }
//}
