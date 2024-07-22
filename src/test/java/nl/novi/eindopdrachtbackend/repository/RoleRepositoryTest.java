//package nl.novi.eindopdrachtbackend.repository;
//
//import nl.novi.eindopdrachtbackend.model.Role;
//import nl.novi.eindopdrachtbackend.model.UserRole;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class RoleRepositoryTest {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @BeforeEach
//    void setUp() {
//        Role customerRole = new Role();
//        customerRole.setRolename(UserRole.CUSTOMER);
//        roleRepository.save(customerRole);
//
//        Role ownerRole = new Role();
//        ownerRole.setRolename(UserRole.OWNER);
//        roleRepository.save(ownerRole);
//
//        Role adminRole = new Role();
//        adminRole.setRolename(UserRole.ADMIN);
//        roleRepository.save(adminRole);
//    }
//
//    @Test
//    void testFindById() {
//        Optional<Role> foundRole = roleRepository.findById(UserRole.CUSTOMER);
//        assertThat(foundRole.isPresent()).isTrue();
//        assertThat(foundRole.get().getRolename()).isEqualTo(UserRole.CUSTOMER);
//    }
//
//    @Test
//    void testRoleCount() {
//        long count = roleRepository.count();
//        assertThat(count).isEqualTo(3);
//    }
//}
