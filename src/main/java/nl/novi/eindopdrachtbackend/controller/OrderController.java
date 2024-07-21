package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }


    /**
     * Get all orders (Admin only).
     *
     * @return ResponseEntity containing a list of OrderDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Get a specific order by its ID (Admin only).
     *
     * @param id the ID of the order
     * @return ResponseEntity containing the OrderDTO object for the specified ID
     */
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OrderDTO> getOrderByIdForAdmin(@PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderByIdForAdmin(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the orders for the logged-in customer.
     *
     * @return ResponseEntity containing the list of OrderDTO objects
     */
    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<OrderDTO>> getOrdersForCustomer() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
            List<OrderDTO> orders = orderService.getOrdersForCustomer(currentUser.getId());
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all orders for the restaurant of the logged-in owner.
     *
     * @return ResponseEntity containing a list of OrderDTOs for the restaurant
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<OrderDTO>> getOrdersForRestaurant() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
            List<OrderDTO> orders = orderService.getOrdersForOwner(currentUser.getId());
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Get a specific order by ID for the logged-in customer.
     *
     * @param id the ID of the order
     * @return ResponseEntity containing the OrderDTO object for the specified order
     */
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<OrderDTO> getOrderByIdForCustomer(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            OrderDTO order = orderService.getOrderByIdForCustomer(id, currentUser.getId());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a specific order by ID for the logged-in restaurant owner.
     *
     * @param id the ID of the order
     * @return ResponseEntity containing the OrderDTO object for the specified order
     */
    @GetMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<OrderDTO> getOrderByIdForOwner(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            OrderDTO order = orderService.getOrderByIdForOwner(id, currentUser.getId());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





//
//
//    @PostMapping
//    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderInputDTO orderInputDTO) {
//        OrderDTO createdOrder = orderService.createOrder(orderInputDTO);
//        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderInputDTO orderInputDTO) {
//        OrderDTO updatedOrder = orderService.updateOrder(id, orderInputDTO);
//        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/customer/{customerId}")
//    public ResponseEntity<List<OrderDTO>> findOrdersByCustomerId(@PathVariable Long customerId) {
//        List<OrderDTO> orders = orderService.findOrdersByCustomerId(customerId);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }
//
//    @GetMapping("/restaurant/{restaurantId}")
//    public ResponseEntity<List<OrderDTO>> findOrdersByRestaurantId(@PathVariable Long restaurantId) {
//        List<OrderDTO> orders = orderService.findOrdersByRestaurantId(restaurantId);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }
//    @GetMapping("/restaurant/{restaurantId}/print")
//    public ResponseEntity<String> printOrdersByRestaurantAndDate(@PathVariable Long restaurantId, @RequestParam String date) {
//        LocalDateTime localDate = LocalDateTime.parse(date + "T00:00:00");
//        String summary = orderService.generatePrintableDailySummary(restaurantId, localDate);
//        return new ResponseEntity<>(summary, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}/print")
//    public ResponseEntity<String> printOrder(@PathVariable Long id) {
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println("Received request to print order with id: " + id);
//        String printableOrder = orderService.generatePrintableOrder(id, now);
//        System.out.println("Generated printable order: " + printableOrder);
//        return new ResponseEntity<>(printableOrder, HttpStatus.OK);
//    }
//
//
//
//
//
    /**
     * Retrieve the currently authenticated user from the security context.
     *
     * This method fetches the email of the currently authenticated user from the security context,
     * retrieves the corresponding User entity from the user repository, and returns the User object.
     * If the user is not found, it throws a ResourceNotFoundException.
     *
     * @return the User object representing the currently authenticated user
     * @throws ResourceNotFoundException if no user is found with the current authenticated email
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }
}
