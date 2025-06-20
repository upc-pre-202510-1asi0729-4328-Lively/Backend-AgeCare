package pe.edu.upc.center.agecare.notification.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.upc.center.agecare.notification.application.internal.commandservices.NotificationCommandServiceImpl;
import pe.edu.upc.center.agecare.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.agecare.notification.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.center.agecare.notification.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.center.agecare.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.agecare.notification.domain.model.services.NotificationCommandService;
import pe.edu.upc.center.agecare.notification.domain.model.services.NotificationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.center.agecare.notification.domain.model.exceptions.NotificationNotFoundException;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = "application/json")
@Tag(name = "Notifications", description = "Notifications Management Endpoints")
public class NotificationController {

    @Autowired
    private NotificationCommandService commandService;

    @Autowired
    private NotificationQueryService queryService;

    @PostMapping
    public Notification createNotification(@RequestBody CreateNotificationCommand command) {
        return commandService.createNotification(command);
    }

    @PostMapping("/{id}/deliver")
    public Notification deliverNotification(@PathVariable long id) {
        return commandService.deliverNotification(id);
    }

    @PostMapping("/{id}/archive")
    public Notification archiveNotification(@PathVariable long id) {
        return commandService.archiveNotification(id);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return queryService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable long id) {
        return queryService.getNotificationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable long userId) {
        return queryService.getNotificationsByUserId(userId);
    }

    @GetMapping("/search")
    public List<Notification> searchNotifications(
            @RequestParam(required = false) Long recipientId,
            @RequestParam(required = false) String status) {
        return queryService.searchNotifications(recipientId, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        commandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotificationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}