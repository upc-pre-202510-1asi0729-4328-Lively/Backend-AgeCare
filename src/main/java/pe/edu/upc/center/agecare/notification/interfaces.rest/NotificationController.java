package pe.edu.upc.center.agecare.notification.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.center.agecare.notification.application.internal.commandservices.NotificationCommandServiceImpl;
import pe.edu.upc.center.agecare.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.agecare.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.agecare.notification.domain.model.exceptions.NotificationNotFoundException;
import pe.edu.upc.center.agecare.notification.domain.model.services.NotificationCommandService;
import pe.edu.upc.center.agecare.notification.domain.model.services.NotificationQueryService;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = "application/json")
@Tag(name = "Notifications", description = "Notifications Management Endpoints")
public class NotificationController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationController(NotificationCommandService commandService, NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(
            summary = "Create a new notification",
            description = "Creates a new notification record",
            operationId = "createNotification",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping
    public Notification createNotification(@RequestBody CreateNotificationCommand command) {
        return commandService.createNotification(command);
    }

    @Operation(
            summary = "Mark notification as delivered",
            description = "Sets the status of a notification to DELIVERED",
            operationId = "deliverNotification",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification delivered successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    )
            }
    )
    @PostMapping("/{id}/deliver")
    public Notification deliverNotification(@PathVariable long id) {
        return commandService.deliverNotification(id);
    }

    @Operation(
            summary = "Archive a notification",
            description = "Sets the status of a notification to ARCHIVED",
            operationId = "archiveNotification",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification archived successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    )
            }
    )
    @PostMapping("/{id}/archive")
    public Notification archiveNotification(@PathVariable long id) {
        return commandService.archiveNotification(id);
    }

    @Operation(
            summary = "Get all notifications",
            description = "Fetches all notifications from the database",
            operationId = "getAllNotifications",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    )
            }
    )
    @GetMapping
    public List<Notification> getAllNotifications() {
        return queryService.getAllNotifications();
    }

    @Operation(
            summary = "Get notification by ID",
            description = "Fetch a single notification by its ID",
            operationId = "getNotificationById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Notification not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable long id) {
        return queryService.getNotificationById(id);
    }

    @Operation(
            summary = "Get notifications by user ID",
            description = "Fetch all notifications for a given user ID",
            operationId = "getNotificationsByUserId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notifications retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    )
            }
    )
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable long userId) {
        return queryService.getNotificationsByUserId(userId);
    }

    @Operation(
            summary = "Search notifications",
            description = "Search notifications by recipientId and/or status",
            operationId = "searchNotifications",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notifications found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notification.class))
                    )
            }
    )
    @GetMapping("/search")
    public List<Notification> searchNotifications(
            @RequestParam(required = false) Long recipientId,
            @RequestParam(required = false) String status) {
        return queryService.searchNotifications(recipientId, status);
    }

    @Operation(
            summary = "Delete notification",
            description = "Delete a notification by ID",
            operationId = "deleteNotification",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Notification deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Notification not found"
                    )
            }
    )
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
