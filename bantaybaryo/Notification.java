package bantaybaryo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification implements Serializable {
    private String recipientId;
    private String reportId;
    private String message;
    private String timestamp;
    private boolean isRead;

    public Notification(String recipientId, String reportId, String message) {
        this.recipientId = recipientId;
        this.reportId = reportId;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a"));
        this.isRead = false;
    }

    public String getRecipientId() { return recipientId; }
    public String getReportId() { return reportId; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}