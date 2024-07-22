//package nl.novi.eindopdrachtbackend.dto;
//
//import nl.novi.eindopdrachtbackend.model.Order;
//import nl.novi.eindopdrachtbackend.model.User;
//import nl.novi.eindopdrachtbackend.model.Restaurant;
//import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
//import nl.novi.eindopdrachtbackend.model.MenuItem;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class OrderMapperTest {
//
//    @Test
//    public void toDTOTest() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        User customer = new User();
//        setField(customer, "id", 1L);
//
//        Restaurant restaurant = new Restaurant();
//        setField(restaurant, "id", 2L);
//
//        DeliveryAddress deliveryAddress = new DeliveryAddress();
//        setField(deliveryAddress, "id", 3L);
//        deliveryAddress.setStreet("Main Street");
//        deliveryAddress.setHouseNumber(123);
//        deliveryAddress.setCity("Springfield");
//        deliveryAddress.setPostcode("12345");
//        deliveryAddress.setCountry("USA");
//
//        MenuItem menuItem = new MenuItem();
//        setField(menuItem, "id", 4L);
//        menuItem.setName("Pizza");
//        menuItem.setPrice(9.99);
//
//        Set<MenuItem> menuItems = new HashSet<>();
//        menuItems.add(menuItem);
//
//        Order order = new Order(customer, restaurant, deliveryAddress, true);
//        setField(order, "id", 1L);
//        order.setMenuItems(menuItems);
//        LocalDateTime orderDateTime = LocalDateTime.now();
//        order.setOrderDateTime(orderDateTime);
//
//        // Act
//        OrderDTO orderDTO = OrderMapper.toDTO(order);
//
//        // Assert
//        assertEquals(1L, orderDTO.getId());
//        assertTrue(orderDTO.isFulfilled());
//        assertEquals(1L, orderDTO.getCustomerId());
//        assertEquals(2L, orderDTO.getRestaurantId());
//
//        // Check DeliveryAddressDTO fields
//        assertNotNull(orderDTO.getDeliveryAddress());
//        assertEquals(3L, orderDTO.getDeliveryAddress().getId());
//        assertEquals("Main Street", orderDTO.getDeliveryAddress().getStreet());
//        assertEquals(123, orderDTO.getDeliveryAddress().getHouseNumber());
//        assertEquals("Springfield", orderDTO.getDeliveryAddress().getCity());
//        assertEquals("12345", orderDTO.getDeliveryAddress().getPostcode());
//        assertEquals("USA", orderDTO.getDeliveryAddress().getCountry());
//
//        // Check MenuItemDTO fields
//        assertNotNull(orderDTO.getMenuItems());
//        assertEquals(1, orderDTO.getMenuItems().size());
//        MenuItemDTO menuItemDTO = orderDTO.getMenuItems().get(0);
//        assertEquals(4L, menuItemDTO.getId());
//        assertEquals("Pizza", menuItemDTO.getName());
//        assertEquals(9.99, menuItemDTO.getPrice());
//
//        // Check totalPrice
//        assertEquals(9.99, orderDTO.getTotalPrice(), 0.001);
//
//        // Check orderDateTime
//        assertEquals(orderDateTime, orderDTO.getOrderDateTime());
//    }
//
//    @Test
//    public void fromInputDTOTest() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        OrderInputDTO orderInputDTO = new OrderInputDTO(true, 1L, 2L, 3L, new ArrayList<>());
//        User customer = new User();
//        setField(customer, "id", 1L);
//
//        Restaurant restaurant = new Restaurant();
//        setField(restaurant, "id", 2L);
//
//        DeliveryAddress deliveryAddress = new DeliveryAddress();
//        setField(deliveryAddress, "id", 3L);
//        deliveryAddress.setStreet("Main Street");
//        deliveryAddress.setHouseNumber(123);
//        deliveryAddress.setCity("Springfield");
//        deliveryAddress.setPostcode("12345");
//        deliveryAddress.setCountry("USA");
//
//        Set<MenuItem> menuItems = new HashSet<>();
//
//        // Act
//        Order order = OrderMapper.fromInputDTO(orderInputDTO, customer, restaurant, deliveryAddress, menuItems);
//
//        // Assert
//        assertTrue(order.isFulfilled());
//        assertEquals(customer, order.getCustomer());
//        assertEquals(restaurant, order.getRestaurant());
//        assertEquals(deliveryAddress, order.getDeliveryAddress());
//        assertNotNull(order.getOrderDateTime());
//    }
//
//    @Test
//    public void toOrderDTOListTest() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        User customer = new User();
//        setField(customer, "id", 1L);
//
//        Restaurant restaurant = new Restaurant();
//        setField(restaurant, "id", 2L);
//
//        DeliveryAddress deliveryAddress = new DeliveryAddress();
//        setField(deliveryAddress, "id", 3L);
//        deliveryAddress.setStreet("Main Street");
//        deliveryAddress.setHouseNumber(123);
//        deliveryAddress.setCity("Springfield");
//        deliveryAddress.setPostcode("12345");
//        deliveryAddress.setCountry("USA");
//
//        MenuItem menuItem = new MenuItem();
//        setField(menuItem, "id", 4L);
//        menuItem.setName("Pizza");
//        menuItem.setPrice(9.99);
//
//        Set<MenuItem> menuItems = new HashSet<>();
//        menuItems.add(menuItem);
//
//        Order order1 = new Order(customer, restaurant, deliveryAddress, true);
//        setField(order1, "id", 1L);
//        order1.setMenuItems(menuItems);
//        LocalDateTime orderDateTime1 = LocalDateTime.now();
//        order1.setOrderDateTime(orderDateTime1);
//
//        Order order2 = new Order(customer, restaurant, deliveryAddress, false);
//        setField(order2, "id", 2L);
//        order2.setMenuItems(menuItems);
//        LocalDateTime orderDateTime2 = LocalDateTime.now();
//        order2.setOrderDateTime(orderDateTime2);
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//
//        // Act
//        List<OrderDTO> orderDTOList = OrderMapper.toOrderDTOList(orders);
//
//        // Assert
//        assertNotNull(orderDTOList);
//        assertEquals(2, orderDTOList.size());
//
//        OrderDTO orderDTO1 = orderDTOList.get(0);
//        assertEquals(1L, orderDTO1.getId());
//        assertTrue(orderDTO1.isFulfilled());
//        assertEquals(1L, orderDTO1.getCustomerId());
//        assertEquals(2L, orderDTO1.getRestaurantId());
//        assertNotNull(orderDTO1.getDeliveryAddress());
//        assertEquals(3L, orderDTO1.getDeliveryAddress().getId());
//        assertNotNull(orderDTO1.getMenuItems());
//        assertEquals(1, orderDTO1.getMenuItems().size());
//        MenuItemDTO menuItemDTO1 = orderDTO1.getMenuItems().get(0);
//        assertEquals(4L, menuItemDTO1.getId());
//        assertEquals("Pizza", menuItemDTO1.getName());
//        assertEquals(9.99, menuItemDTO1.getPrice());
//        assertEquals(9.99, orderDTO1.getTotalPrice(), 0.001);
//        assertEquals(orderDateTime1, orderDTO1.getOrderDateTime());
//
//        OrderDTO orderDTO2 = orderDTOList.get(1);
//        assertEquals(2L, orderDTO2.getId());
//        assertFalse(orderDTO2.isFulfilled());
//        assertEquals(1L, orderDTO2.getCustomerId());
//        assertEquals(2L, orderDTO2.getRestaurantId());
//        assertNotNull(orderDTO2.getDeliveryAddress());
//        assertEquals(3L, orderDTO2.getDeliveryAddress().getId());
//        assertNotNull(orderDTO2.getMenuItems());
//        assertEquals(1, orderDTO2.getMenuItems().size());
//        MenuItemDTO menuItemDTO2 = orderDTO2.getMenuItems().get(0);
//        assertEquals(4L, menuItemDTO2.getId());
//        assertEquals("Pizza", menuItemDTO2.getName());
//        assertEquals(9.99, menuItemDTO2.getPrice());
//        assertEquals(9.99, orderDTO2.getTotalPrice(), 0.001);
//        assertEquals(orderDateTime2, orderDTO2.getOrderDateTime());
//    }
//
//    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
//        Field field = targetObject.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(targetObject, value);
//    }
//}
