package bantaybaryo;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String USERS_FILE = "users.dat";
    private static final String REPORTS_FILE = "reports.dat";
    private static final String NOTIFICATIONS_FILE = "notifications.dat";

    
    public static void saveUsers(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<User> loadUsers() {
        File file = new File(USERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading users: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    public static void saveReports(ArrayList<Report> reports) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPORTS_FILE))) {
            oos.writeObject(reports);
        } catch (IOException e) {
            System.out.println("Error saving reports: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Report> loadReports() {
        File file = new File(REPORTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Report>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading reports: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

        public static void saveNotifications(ArrayList<Notification> notifications) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOTIFICATIONS_FILE))) {
            oos.writeObject(notifications);
        } catch (IOException e) {
            System.out.println("Error saving notifications: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Notification> loadNotifications() {
        File file = new File(NOTIFICATIONS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Notification>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading notifications: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

}