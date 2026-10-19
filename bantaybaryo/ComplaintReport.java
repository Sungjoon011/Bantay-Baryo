package bantaybaryo;

public class ComplaintReport extends Report {
    private String respondentName;

    public ComplaintReport(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status, String respondentName) {
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.respondentName = respondentName;
    }

    @Override
    public String getReportType() {
        return "Complaint";
    }

    @Override
    public String displayDetails() {
        return "COMPLAINT REPORT\n" +
                "Category: " + getCategory() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Respondent: " + respondentName + "\n" +
                "Description: " + getDescription();
    }

    public String getRespondentName() { return respondentName; }
    public void setRespondentName(String respondentName) { this.respondentName = respondentName; }
}