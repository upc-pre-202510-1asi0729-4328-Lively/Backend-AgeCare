package pe.edu.upc.center.agecare.notification.domain.model.commands;

import java.util.Date;

public class CreateNotificationCommand {
    private long id;
    private String type;
    private String content;
    private Date timestamp;
    private String status;
    private int recipientId;
    private int senderId;
    private Long userId;
    private Long familyMemberId;

    public CreateNotificationCommand(long id, String type, String content, Date timestamp, String status, int recipientId, int senderId, Long userId, Long familyMemberId) {
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

    public long getId() { return id; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public int getRecipientId() { return recipientId; }
    public int getSenderId() { return senderId; }
    public Long getUserId() { return userId; }
    public Long getFamilyMemberId() { return familyMemberId; }

    public void setId(long id) { this.id = id; }
}