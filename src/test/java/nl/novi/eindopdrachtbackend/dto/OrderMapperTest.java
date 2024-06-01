package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {

    @Test
    public void toDTOTest() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        User customer = new User();
        setField(customer, "id", 1L);

        Restaurant restaurant = new Restaurant();
        setField(restaurant, "id", 2L);

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        setField(deliveryAddress, "id", 3L);
        deliveryAddress.setStreet("Main Street");
        deliveryAddress.setHouseNumber(123);
        deliveryAddress.setCity("Springfield");
        deliveryAddress.setPostcode("12345");
        deliveryAddress.setCountry("USA");

        Order order = new Order(customer, restaurant, deliveryAddress, true);
        setField(order, "id", 1L);

        // Act
        OrderDTO orderDTO = OrderMapper.toDTO(order);

        // Assert
        assertEquals(1L, orderDTO.getId());
        assertTrue(orderDTO.isFulfilled());
        assertEquals(1L, orderDTO.getCustomerId());
        assertEquals(2L, orderDTO.getRestaurantId());

        // Check DeliveryAddressDTO fields
        assertNotNull(orderDTO.getDeliveryAddress());
        assertEquals(3L, orderDTO.getDeliveryAddress().getId());
        assertEquals("Main Street", orderDTO.getDeliveryAddress().getStreet());
        assertEquals(123, orderDTO.getDeliveryAddress().getHouseNumber());
        assertEquals("Springfield", orderDTO.getDeliveryAddress().getCity());
        assertEquals("12345", orderDTO.getDeliveryAddress().getPostcode());
        assertEquals("USA", orderDTO.getDeliveryAddress().getCountry());
    }

    @Test
    public void fromInputDTOTest() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        OrderInputDTO orderInputDTO = new OrderInputDTO(true, 1L, 2L, 3L);

        User customer = new User();
        setField(customer, "id", 1L);

        Restaurant restaurant = new Restaurant();
        setField(restaurant, "id", 2L);

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        setField(deliveryAddress, "id", 3L);
        deliveryAddress.setStreet("Main Street");
        deliveryAddress.setHouseNumber(123);
        deliveryAddress.setCity("Springfield");
        deliveryAddress.setPostcode("12345");
        deliveryAddress.setCountry("USA");

        // Act
        Order order = OrderMapper.fromInputDTO(orderInputDTO, customer, restaurant, deliveryAddress);

        // Assert
        assertTrue(order.isFulfilled());
        assertEquals(customer, order.getCustomer());
        assertEquals(restaurant, order.getRestaurant());
        assertEquals(deliveryAddress, order.getDeliveryAddress());
    }

    @Test
    public void toOrderDTOListTest() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        User customer = new User();
        setField(customer, "id", 1L);

        Restaurant restaurant = new Restaurant();
        setField(restaurant, "id", 2L);

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        setField(deliveryAddress, "id", 3L);
        deliveryAddress.setStreet("Main Street");
        deliveryAddress.setHouseNumber(123);
        deliveryAddress.setCity("Springfield");
        deliveryAddress.setPostcode("12345");
        deliveryAddress.setCountry("USA");

        Order order1 = new Order(customer, restaurant, deliveryAddress, true);
        setField(order1, "id", 1L);

        Order order2 = new Order(customer, restaurant, deliveryAddress, false);
        setField(order2, "id", 2L);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        // Act
        List<OrderDTO> orderDTOList = OrderMapper.toOrderDTOList(orders);

        // Assert
        assertNotNull(orderDTOList);
        assertEquals(2, orderDTOList.size());

        OrderDTO orderDTO1 = orderDTOList.get(0);
        assertEquals(1L, orderDTO1.getId());
        assertTrue(orderDTO1.isFulfilled());
        assertEquals(1L, orderDTO1.getCustomerId());
        assertEquals(2L, orderDTO1.getRestaurantId());
        assertNotNull(orderDTO1.getDeliveryAddress());
        assertEquals(3L, orderDTO1.getDeliveryAddress().getId());

        OrderDTO orderDTO2 = orderDTOList.get(1);
        assertEquals(2L, orderDTO2.getId());
        assertFalse(orderDTO2.isFulfilled());
        assertEquals(1L, orderDTO2.getCustomerId());
        assertEquals(2L, orderDTO2.getRestaurantId());
        assertNotNull(orderDTO2.getDeliveryAddress());
        assertEquals(3L, orderDTO2.getDeliveryAddress().getId());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
