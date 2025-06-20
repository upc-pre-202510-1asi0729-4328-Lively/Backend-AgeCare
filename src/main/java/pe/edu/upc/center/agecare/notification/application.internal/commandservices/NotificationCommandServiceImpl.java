package pe.edu.upc.center.agecare.notification.application.internal.commandservices;

import pe.edu.upc.center.agecare.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.agecare.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.agecare.notification.domain.model.services.NotificationCommandService;
import pe.edu.upc.center.agecare.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;
import pe.edu.upc.center.agecare.notification.domain.model.exceptions.NotificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(CreateNotificationCommand command) {
        Notification notification = new Notification(
            command.getId(),
            command.getType(),
            command.getContent(),
            command.getTimestamp(),
            command.getStatus(),
            command.getRecipientId(),
            command.getSenderId(),
            command.getUserId(),
            command.getFamilyMemberId()
        );
        return notificationRepository.save(notification);
    }

    @Override
    public Notification deliverNotification(long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("DELIVERED"); // O el status que corresponda
        return notificationRepository.save(notification);
    }

    @Override
    public Notification archiveNotification(long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("ARCHIVED"); // O el status que corresponda
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new NotificationNotFoundException(id));
        notificationRepository.delete(notification);
    }
}