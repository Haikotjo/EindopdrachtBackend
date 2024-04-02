package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}
