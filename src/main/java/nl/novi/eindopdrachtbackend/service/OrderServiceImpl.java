package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;
import nl.novi.eindopdrachtbackend.dto.OrderMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            RestaurantRepository restaurantRepository, DeliveryAddressRepository deliveryAddressRepository,
                            MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                throw new ResourceNotFoundException("No orders found");
            }
            return orders.stream()
                    .map(OrderMapper::toOrderDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the error for debugging purposes
            throw new RuntimeException("Failed to retrieve orders", e);
        }
    }

    /**
     * {@inheritDoc}
     */
        @Override
        public OrderDTO getOrderByIdForAdmin(Long id) {
            try {
                Order order = orderRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
                return OrderMapper.toOrderDTO(order);
            } catch (ResourceNotFoundException e) {
                throw e;
            } catch (Exception e) {
                // Log the error for debugging purposes
                throw new RuntimeException("Failed to retrieve order with id " + id, e);
            }
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getOrdersForCustomer(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Order> orders = orderRepository.findByCustomerId(userId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for the current user.");
        }
        return OrderMapper.toOrderDTOList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDTO> getOrdersForOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for owner with id: " + ownerId));
        List<Order> orders = orderRepository.findByRestaurantId(restaurant.getId());
        return orders.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO getOrderByIdForCustomer(Long orderId, Long customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this customer id :: " + customerId));
        return OrderMapper.toOrderDTO(order);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO getOrderByIdForOwner(Long orderId, Long ownerId) {
        Order order = orderRepository.findByIdAndRestaurantOwnerId(orderId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this owner id :: " + ownerId));
        return OrderMapper.toOrderDTO(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO createOrder(Long customerId, OrderInputDTO orderInputDTO) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        Restaurant restaurant = restaurantRepository.findById(orderInputDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + orderInputDTO.getRestaurantId()));
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderInputDTO.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + orderInputDTO.getDeliveryAddressId()));

        Set<MenuItem> menuItems = orderInputDTO.getMenuItemIds().stream()
                .map(menuItemId -> menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId)))
                .collect(Collectors.toSet());

        Order order = new Order(customer, restaurant, deliveryAddress, orderInputDTO.isFulfilled());
        order.setMenuItems(menuItems);
        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toOrderDTO(savedOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO updateOrderForCustomer(Long orderId, OrderInputDTO orderInputDTO, Long customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId + " and customer id :: " + customerId));

        updateOrderFields(order, orderInputDTO);
        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDTO(updatedOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO updateOrderForAdmin(Long orderId, OrderInputDTO orderInputDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));

        updateOrderFields(order, orderInputDTO);
        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDTO(updatedOrder);
    }

    /**
     * {@inheritDoc}
     */
    private void updateOrderFields(Order order, OrderInputDTO orderInputDTO) {
        order.setFulfilled(orderInputDTO.isFulfilled());

        Restaurant restaurant = restaurantRepository.findById(orderInputDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + orderInputDTO.getRestaurantId()));
        order.setRestaurant(restaurant);

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderInputDTO.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + orderInputDTO.getDeliveryAddressId()));
        order.setDeliveryAddress(deliveryAddress);

        Set<MenuItem> menuItems = new HashSet<>(menuItemRepository.findAllById(orderInputDTO.getMenuItemIds()));
        order.setMenuItems(menuItems);

        order.setOrderDateTime(LocalDateTime.now());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        orderRepository.delete(order);
    }




//    @Override
//    public String generatePrintableOrder(Long orderId, LocalDateTime date) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
//        User customer = userRepository.findById(order.getCustomer().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + order.getCustomer().getId()));
//        Restaurant restaurant = restaurantRepository.findById(order.getRestaurant().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + order.getRestaurant().getId()));
//
//        StringBuilder printableOrder = new StringBuilder();
//        printableOrder.append("Beste ").append(customer.getName()).append(",\n");
//        printableOrder.append("Bedankt voor uw bestelling bij ").append(restaurant.getName()).append(".\n");
//        printableOrder.append("Uw bestelling:\n");
//
//        for (MenuItem item : order.getMenuItems()) {
//            printableOrder.append(item.getName()).append(" - €").append(item.getPrice()).append("\n");
//        }
//
//        printableOrder.append("Totaal: €").append(order.getTotalPrice()).append("\n");
//        printableOrder.append("Datum en tijd: ").append(order.getOrderDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
//
//        return printableOrder.toString();
//    }
//
//    @Override
//    public String generatePrintableDailySummary(Long restaurantId, LocalDateTime date) {
//        List<OrderDTO> orders = findOrdersByRestaurantAndDate(restaurantId, date);
//
//        StringBuilder summary = new StringBuilder();
//        summary.append("Orders van ").append(date.toLocalDate()).append(" bij restaurant ID ").append(restaurantId).append(":\n\n");
//
//        double totalDayRevenue = 0.0;
//        for (OrderDTO order : orders) {
//            summary.append("Order ID: ").append(order.getId()).append("\n");
//            for (MenuItemDTO item : order.getMenuItems()) {
//                summary.append(item.getName()).append(" - €").append(item.getPrice()).append("\n");
//            }
//            summary.append("Totaal voor deze order: €").append(order.getTotalPrice()).append("\n\n");
//            totalDayRevenue += order.getTotalPrice();
//        }
//
//        summary.append("Totaal omzet van de dag: €").append(totalDayRevenue).append("\n");
//
//        return summary.toString();
//    }
}