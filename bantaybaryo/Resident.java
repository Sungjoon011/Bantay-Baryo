package bantaybaryo;

public class Resident extends User {
    private String purok;
    private String household;
    private String sitio = ""; // Added to support your custom Dashboard UI

    public Resident(String id, String firstName, String middleName, String lastName, String username, String password, String purok, String household) {
        super(id, firstName, middleName, lastName, username, password);
        this.purok = purok;
        this.household = household;
    }

    @Override
    public String getRoleType() {
        return "Resident";
    }

    public String getPurok() { return purok; }
    public void setPurok(String purok) { this.purok = purok; }

    public String getHousehold() { return household; }
    public void setHousehold(String household) { this.household = household; }

    // Added Getters and Setters for Sitio
    public String getSitio() { return sitio; }
    public void setSitio(String sitio) { this.sitio = sitio; }
}