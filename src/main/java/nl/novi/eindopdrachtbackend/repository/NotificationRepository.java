package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Notification;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Notification} entities.
 * This interface provides CRUD operations for the Notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Finds all notifications for a specific user based on the user's ID.
     *
     * @param userId the ID of the user whose notifications are to be retrieved
     * @return a list of notifications associated with the specified user
     */
    List<Notification> findByUserId(Long userId);

    List<Notification> findByUser(User user);
}
