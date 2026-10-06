package bantaybaryo;

public abstract class User {
    // Encapsulation: Variables are strictly private
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String status;

    // Constructor
    protected User(String id, String firstName, String middleName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.status = "ACTIVE"; // Default status upon creation
    }

    // Abstraction: Subclasses MUST implement this method
    public abstract String getRoleType();

    // Utility Method
    public String getFullName() {
        return firstName + " " + (middleName.isEmpty() ? "" : middleName + " ") + lastName;
    }

    // --- GETTERS AND SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}