package bantaybaryo;

import java.io.Serializable;

public abstract class Report implements Serializable {
    private String reportId;
    private String reporterId;
    private String category;
    private String location;
    private String description;
    private String dateSubmitted;
    private String status;

    public Report(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.category = category;
        this.location = location;
        this.description = description;
        this.dateSubmitted = dateSubmitted;
        this.status = status;
    }

    public abstract String getReportType();
    public abstract String displayDetails();

    public String getReportId() { return reportId; }
    public String getReporterId() { return reporterId; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getDateSubmitted() { return dateSubmitted; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}