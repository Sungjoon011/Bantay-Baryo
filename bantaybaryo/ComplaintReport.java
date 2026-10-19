package bantaybaryo;

public class ComplaintReport extends Report {
    private String respondentName;
    private String mediaPath; // File path, URI, or URL of the picture/video
    private String mediaType; // e.g., "IMAGE", "VIDEO", or "NONE"

    // Constructor with media attachment
    public ComplaintReport(String reportId, String reporterId, String category, String location,
                           String description, String dateSubmitted, String status,
                           String respondentName, String mediaPath, String mediaType) {
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.respondentName = respondentName;
        this.mediaPath = mediaPath;
        this.mediaType = mediaType;
    }

    // Overloaded constructor for complaints without media
    public ComplaintReport(String reportId, String reporterId, String category, String location,
                           String description, String dateSubmitted, String status,
                           String respondentName) {
        this(reportId, reporterId, category, location, description, dateSubmitted, status, respondentName, null, "NONE");
    }

    @Override
    public String getReportType() {
        return "Complaint";
    }

    @Override
    public String displayDetails() {
        String mediaDetails = (mediaPath != null && !mediaPath.trim().isEmpty())
                ? "\nMedia (" + mediaType + "): " + mediaPath
                : "\nMedia: None";

        return "COMPLAINT REPORT\n" +
                "Category: " + getCategory() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Respondent: " + respondentName +
                mediaDetails + "\n" +
                "Description: " + getDescription();
    }

    public String getRespondentName() { return respondentName; }
    public void setRespondentName(String respondentName) { this.respondentName = respondentName; }

    public String getMediaPath() { return mediaPath; }
    public void setMediaPath(String mediaPath) { this.mediaPath = mediaPath; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
}