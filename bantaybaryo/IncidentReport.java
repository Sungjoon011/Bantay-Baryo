package bantaybaryo;

public class IncidentReport extends Report {
    // Specific attribute only for incidents
    private String personsInvolved;

    public IncidentReport(String reportId, String reporterId, String category, String location, String description, String dateSubmitted, String status, String personsInvolved) {
        // Pass the shared data up to the parent Report class
        super(reportId, reporterId, category, location, description, dateSubmitted, status);
        this.personsInvolved = personsInvolved;
    }

    // Fulfilling the abstract methods (Polymorphism)
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

    // Getter and Setter
    public String getPersonsInvolved() { return personsInvolved; }
    public void setPersonsInvolved(String personsInvolved) { this.personsInvolved = personsInvolved; }
}