package pe.edu.upc.center.agecare.notification.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.agecare.notification.domain.model.aggregates.Notification;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(long userId);
    List<Notification> findByRecipientId(Long recipientId);
    List<Notification> findByStatus(String status);
    List<Notification> findByRecipientIdAndStatus(Long recipientId, String status);
}