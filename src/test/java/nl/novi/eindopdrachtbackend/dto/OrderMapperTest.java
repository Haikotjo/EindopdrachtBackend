package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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

        Order order = new Order(customer, restaurant, deliveryAddress, true);
        setField(order, "id", 1L);

        // Act
        OrderDTO orderDTO = OrderMapper.toDTO(order);

        // Assert
        assertEquals(1L, orderDTO.getId());
        assertTrue(orderDTO.isFulfilled());
        assertEquals(1L, orderDTO.getCustomerId());
        assertEquals(2L, orderDTO.getRestaurantId());
        assertEquals(3L, orderDTO.getDeliveryAddressId());
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

        // Act
        Order order = OrderMapper.fromInputDTO(orderInputDTO, customer, restaurant, deliveryAddress);

        // Assert
        assertTrue(order.isFulfilled());
        assertEquals(customer, order.getCustomer());
        assertEquals(restaurant, order.getRestaurant());
        assertEquals(deliveryAddress, order.getDeliveryAddress());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
