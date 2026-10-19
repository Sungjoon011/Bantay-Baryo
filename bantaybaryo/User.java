package bantaybaryo;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String contactNumber;
    private String status;

    public User(String id, String firstName, String middleName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.contactNumber = "";
        this.status = "ACTIVE";
    }

    public abstract String getRoleType();

    public String getFullName() {
        if (middleName == null || middleName.trim().isEmpty()) {
            return firstName + " " + lastName;
        }
        return firstName + " " + middleName + " " + lastName;
    }

    public String getId() { return id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}