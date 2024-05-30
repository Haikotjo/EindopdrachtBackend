package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setFulfilled(order.isFulfilled());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setDeliveryAddressId(order.getDeliveryAddress().getId());
        return dto;
    }

    public static Order fromInputDTO(OrderInputDTO inputDTO, User customer, Restaurant restaurant, DeliveryAddress deliveryAddress) {
        Order order = new Order();
        order.setFulfilled(inputDTO.isFulfilled());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }
}
