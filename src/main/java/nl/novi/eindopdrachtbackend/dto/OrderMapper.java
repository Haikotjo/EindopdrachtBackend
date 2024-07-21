package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.MenuItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Order entities and DTOs.
 */
public class OrderMapper {

    /**
     * Converts an Order entity to an OrderDTO.
     *
     * @param order the Order entity
     * @return the OrderDTO
     */
    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

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
                totalPrice,
                order.getOrderDateTime()
        );
    }

    /**
     * Converts an OrderInputDTO to an Order entity.
     *
     * @param inputDTO the OrderInputDTO
     * @param customer the User entity representing the customer
     * @param restaurant the Restaurant entity
     * @param deliveryAddress the DeliveryAddress entity
     * @param menuItems the set of MenuItem entities
     * @return the Order entity
     */
    public static Order fromInputDTO(OrderInputDTO inputDTO, User customer, Restaurant restaurant, DeliveryAddress deliveryAddress, Set<MenuItem> menuItems) {
        if (inputDTO == null || customer == null || restaurant == null || deliveryAddress == null || menuItems == null) {
            return null;
        }

        Order order = new Order();
        order.setFulfilled(inputDTO.isFulfilled());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setMenuItems(menuItems);
        order.setOrderDateTime(LocalDateTime.now());
        return order;
    }

    /**
     * Converts a list of Order entities to a list of OrderDTOs.
     *
     * @param orders the list of Order entities
     * @return the list of OrderDTOs
     */
    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }
}
