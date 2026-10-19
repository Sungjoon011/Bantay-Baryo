package bantaybaryo;

public class BarangayStaff extends User {
    private String staffPosition;

    public BarangayStaff(String id, String firstName, String middleName, String lastName, String username, String password, String staffPosition) {
        super(id, firstName, middleName, lastName, username, password);
        this.staffPosition = staffPosition;
    }

    @Override
    public String getRoleType() {
        return "Barangay Staff";
    }

    public String getStaffPosition() { return staffPosition; }
    public void setStaffPosition(String staffPosition) { this.staffPosition = staffPosition; }
}