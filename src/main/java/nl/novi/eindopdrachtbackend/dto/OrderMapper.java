package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    // Convert User entity to OrderDTO
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

    // Convert UserInputDTO to Order entity
    public static Order fromInputDTO(OrderInputDTO inputDTO, User customer, Restaurant restaurant, DeliveryAddress deliveryAddress) {
        Order order = new Order();
        order.setFulfilled(inputDTO.isFulfilled());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}

