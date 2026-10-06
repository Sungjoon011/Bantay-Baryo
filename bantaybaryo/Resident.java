package bantaybaryo;

public class Resident extends User {
    // Resident-specific encapsulated variables
    private String purok;
    private String sitio;

    // Constructor calls super() to pass base data up to the User class
    public Resident(String id, String firstName, String middleName, String lastName, String username, String password, String purok, String sitio) {
        super(id, firstName, middleName, lastName, username, password);
        this.purok = purok;
        this.sitio = sitio;
    }

    // Polymorphism/Abstraction: Fulfilling the abstract method
    @Override
    public String getRoleType() {
        return "Resident";
    }

    // --- GETTERS AND SETTERS ---
    public String getPurok() { return purok; }
    public void setPurok(String purok) { this.purok = purok; }

    public String getSitio() { return sitio; }
    public void setSitio(String sitio) { this.sitio = sitio; }
}