package bantaybaryo;

public class BarangayStaff extends User {
    // Staff-specific encapsulated variable
    private String staffPosition;

    public BarangayStaff(String id, String firstName, String middleName, String lastName, String username, String password, String staffPosition) {
        super(id, firstName, middleName, lastName, username, password);
        this.staffPosition = staffPosition;
    }

    // Polymorphism/Abstraction: Fulfilling the abstract method
    @Override
    public String getRoleType() {
        return "Barangay Staff";
    }

    // --- GETTERS AND SETTERS ---
    public String getStaffPosition() { return staffPosition; }
    public void setStaffPosition(String staffPosition) { this.staffPosition = staffPosition; }
}