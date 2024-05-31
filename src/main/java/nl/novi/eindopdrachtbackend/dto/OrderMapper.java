package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressMapper.toDeliveryAddressDTO(order.getDeliveryAddress());
        return new OrderDTO(
                order.getId(),
                order.isFulfilled(),
                order.getCustomer().getId(),
                order.getRestaurant().getId(),
                deliveryAddressDTO
        );
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

