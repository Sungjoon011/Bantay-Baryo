package bantaybaryo;

public class ComplaintReport extends Report {
    // Specific attribute only for complaints
    private String respondentName;

    public ComplaintReport(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status, String respondentName) {
        // Pass the shared data up to the parent Report class
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.respondentName = respondentName;
    }

    // Fulfilling the abstract methods (Polymorphism)
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

    // Getter and Setter
    public String getRespondentName() { return respondentName; }
    public void setRespondentName(String respondentName) { this.respondentName = respondentName; }
}