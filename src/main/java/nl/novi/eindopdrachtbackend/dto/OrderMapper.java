package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.MenuItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    // Convert Order entity to OrderDTO
    public static OrderDTO toDTO(Order order) {
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressMapper.toDeliveryAddressDTO(order.getDeliveryAddress());
        List<MenuItemDTO> menuItemDTOs = order.getMenuItems().stream()
                .map(MenuItemMapper::toMenuItemDTO)
                .collect(Collectors.toList());

        double totalPrice = order.getTotalPrice();

        return new OrderDTO(
                order.getId(),
                order.isFulfilled(),
                order.getCustomer().getId(),
                order.getRestaurant().getId(),
                deliveryAddressDTO,
                menuItemDTOs,
                totalPrice
        );
    }

    // Convert OrderInputDTO to Order entity
    public static Order fromInputDTO(OrderInputDTO inputDTO, User customer, Restaurant restaurant, DeliveryAddress deliveryAddress, Set<MenuItem> menuItems) {
        Order order = new Order();
        order.setFulfilled(inputDTO.isFulfilled());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setMenuItems(menuItems);
        return order;
    }

    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
