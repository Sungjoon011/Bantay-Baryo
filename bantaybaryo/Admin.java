package bantaybaryo;

public class Admin extends BarangayStaff {

    public Admin(String id, String firstName, String middleName, String lastName, String username, String password, String staffPosition) {
        super(id, firstName, middleName, lastName, username, password, staffPosition);
    }

    @Override
    public String getRoleType() {
        return "Admin";
    }
}