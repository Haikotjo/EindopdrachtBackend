//package nl.novi.eindopdrachtbackend.model;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RestaurantTest {
//
//    private Role createRole(UserRole userRole) {
//        return new Role(userRole);
//    }
//
//    @Test
//    public void testRestaurantConstructor() {
//        // Create an instance of Restaurant using the constructor
//        Restaurant restaurant = new Restaurant("The Fat Duck", "High Street, Bray", "0123456789");
//
//        // Verify that the constructor correctly initializes the fields
//        assertEquals("The Fat Duck", restaurant.getName(), "The name does not match");
//        assertEquals("High Street, Bray", restaurant.getAddress(), "The address does not match");
//        assertEquals("0123456789", restaurant.getPhoneNumber(), "The phone number does not match");
//    }
//
//    @Test
//    public void testRestaurantFullConstructor() {
//        Set<Menu> menus = new HashSet<>();
//        Set<Order> orders = new HashSet<>();
//        Role ownerRole = createRole(UserRole.OWNER);
//        Set<Role> roles = new HashSet<>();
//        roles.add(ownerRole);
//        User owner = new User("Owner Name", "owner@example.com", "pass123", roles, "123456789");
//
//        Restaurant restaurant = new Restaurant("The Gourmet Spot", "Downtown Street, City", "0123456789", menus, orders, owner);
//
//        assertEquals("The Gourmet Spot", restaurant.getName());
//        assertEquals("Downtown Street, City", restaurant.getAddress());
//        assertEquals("0123456789", restaurant.getPhoneNumber());
//        assertSame(menus, restaurant.getMenus());
//        assertSame(orders, restaurant.getOrders());
//        assertSame(owner, restaurant.getOwner());
//    }
//}
