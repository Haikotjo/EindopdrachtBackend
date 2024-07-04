package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Notification entities from the database.
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
}
