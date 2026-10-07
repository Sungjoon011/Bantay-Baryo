package bantaybaryo;

public abstract class Report {
    // Encapsulation: Variables are strictly private
    private String reportId;
    private String reporterId;
    private String category;
    private String location;
    private String description;
    private String dateSubmitted;
    private String status;

    // Protected constructor (can only be called by subclasses via super)
    protected Report(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.category = category;
        this.location = location;
        this.description = description;
        this.dateSubmitted = dateSubmitted;
        this.status = status; // Typically "PENDING", "IN_PROGRESS", or "RESOLVED"
    }

    // Abstraction & Polymorphism: Subclasses MUST define these behaviors
    public abstract String getReportType();
    public abstract String displayDetails();

    // --- GETTERS AND SETTERS ---
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getReporterId() { return reporterId; }
    public void setReporterId(String reporterId) { this.reporterId = reporterId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(String dateSubmitted) { this.dateSubmitted = dateSubmitted; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}