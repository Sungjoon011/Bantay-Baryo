package bantaybaryo;

public class IncidentReport extends Report {
    private String personsInvolved;
    private String mediaPath; // File path, URI, or URL of the attached photo/media
    private String mediaType; // e.g., "IMAGE" or "NONE"

    public IncidentReport(String reportId, String reporterId, String category, String location,
                          String description, String dateSubmitted, String status,
                          String personsInvolved, String mediaPath, String mediaType) {
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.personsInvolved = personsInvolved;
        this.mediaPath = mediaPath;
        this.mediaType = mediaType;
    }

    public IncidentReport(String reportId, String reporterId, String category, String location,
                          String description, String dateSubmitted, String status,
                          String personsInvolved) {
        this(reportId, reporterId, category, location, description, dateSubmitted, status, personsInvolved, null, "NONE");
    }

    @Override
    public String getReportType() {
        return "Incident";
    }

    @Override
    public String displayDetails() {
        String mediaDetails = (mediaPath != null && !mediaPath.trim().isEmpty())
                ? "\nAttached Photo: " + mediaPath
                : "\nAttached Photo: None";

        return "INCIDENT REPORT\n" +
                "Category: " + getCategory() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Persons Involved: " + personsInvolved +
                mediaDetails + "\n" +
                "Description: " + getDescription();
    }

    public String getPersonsInvolved() { return personsInvolved; }
    public void setPersonsInvolved(String personsInvolved) { this.personsInvolved = personsInvolved; }

    public String getMediaPath() { return mediaPath; }
    public void setMediaPath(String mediaPath) { this.mediaPath = mediaPath; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
}