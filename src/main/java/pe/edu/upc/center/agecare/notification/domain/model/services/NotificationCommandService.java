package pe.edu.upc.center.agecare.notification.domain.model.services;

import pe.edu.upc.center.agecare.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.agecare.notification.domain.model.commands.CreateNotificationCommand;

public interface NotificationCommandService {
    Notification createNotification(CreateNotificationCommand command);
    Notification deliverNotification(long id);
    Notification archiveNotification(long id);
    void deleteById(Long id);
}