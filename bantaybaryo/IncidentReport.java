package bantaybaryo;

public class IncidentReport extends Report {
    private String personsInvolved;

    public IncidentReport(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status, String personsInvolved) {
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.personsInvolved = personsInvolved;
    }

    @Override
    public String getReportType() {
        return "Incident";
    }

    @Override
    public String displayDetails() {
        return "INCIDENT REPORT\n" +
                "Category: " + getCategory() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Persons Involved: " + personsInvolved + "\n" +
                "Description: " + getDescription();
    }

    public String getPersonsInvolved() { return personsInvolved; }
    public void setPersonsInvolved(String personsInvolved) { this.personsInvolved = personsInvolved; }
}