package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    List<User> getAllUsers();
    User getUserById(Long id);
    void deleteUser(Long id);
    List<User> findByNameIgnoreCase(String name);
}
