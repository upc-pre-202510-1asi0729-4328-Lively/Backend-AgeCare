package pe.edu.upc.center.agecare.notification.domain.model.aggregates;

import pe.edu.upc.center.agecare.notification.domain.model.valueobjects.UserId;
import pe.edu.upc.center.agecare.notification.domain.model.valueobjects.FamilyMemberId;
import pe.edu.upc.center.agecare.notification.domain.model.entities.Alert;
import pe.edu.upc.center.agecare.notification.domain.model.entities.Message;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
@Table(name = "notifications") // Cambia el nombre si tu tabla es diferente
public class Notification {
    @Id
    private Long id;
    private String type;
    private String content;
    private Date timestamp;
    private String status;
    private int recipientId;
    private int senderId;
    private Long userId;
    private Long familyMemberId;
    @OneToOne
    private Alert alert;
    @Transient
    private Message message;

    public Notification(long id, String type, String content, Date timestamp, String status, int recipientId, int senderId, Long userId, Long familyMemberId) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.userId = userId;
        this.familyMemberId = familyMemberId;
    }

    public void deliver() {
        this.status = "Delivered";
        // Trigger domain event for delivery
    }

    public void archive() {
        this.status = "Archived";
        // Trigger domain event for archiving
    }

    // Getters and setters for type, content, status
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getters and setters omitted for brevity
}