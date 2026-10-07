package bantaybaryo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private User currentUser;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Report> reports = new ArrayList<>();
    private DefaultListModel<String> reportListModel;
    private JList<String> reportList;
    private JLabel summaryLabel;
    private JLabel welcomeLabel;
    private JLabel subWelcomeLabel;

    public Main() {
        setTitle("Bantay-Baryo System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        seedData();
        buildLoginScreen();
        buildDashboard();

        add(cardPanel);
    }

    private void seedData() {
        users.add(new Resident(
                "R-1001",
                "Maria",
                "Dela",
                "Cruz",
                "mcruz",
                "pass123",
                "Purok 2",
                "Bahay ng Lahat"
        ));

        users.add(new BarangayStaff(
                "S-2001",
                "Jose",
                "",
                "Santos",
                "jsantos",
                "admin123",
                "Community Officer"
        ));

        reports.add(new IncidentReport(
                "IR-3001",
                "R-1001",
                "Public Safety",
                "Barangay Hall",
                "A noise disturbance and fight occurred near the covered court.",
                "2026-10-07",
                "PENDING",
                "Maria Cruz and Juan Dela Torre"
        ));

        reports.add(new ComplaintReport(
                "CR-3002",
                "R-1001",
                "Garbage Disposal",
                "Purok 5",
                "Uncollected trash has been piling up near the drainage canal.",
                "2026-10-07",
                "IN_PROGRESS",
                "Barangay Waste Team"
        ));
    }

    private void buildLoginScreen() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(233, 240, 246));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setPreferredSize(new Dimension(500, 520));
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 229), 1),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel logoLabel = new JLabel(createLogoIcon());
        logoPanel.add(logoLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginCard.add(logoPanel, gbc);

        JLabel appTitle = new JLabel("Bantay-Baryo");
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        appTitle.setHorizontalAlignment(SwingConstants.CENTER);
        appTitle.setForeground(new Color(16, 63, 98));
        gbc.gridy = 1;
        loginCard.add(appTitle, gbc);

        JLabel loginTitle = new JLabel("Welcome");
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        loginCard.add(loginTitle, gbc);

        JLabel subtitle = new JLabel("Sign in to access your barangay account");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setForeground(new Color(98, 112, 124));
        gbc.gridy = 3;
        loginCard.add(subtitle, gbc);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        loginCard.add(userLabel, gbc);

        JTextField usernameField = new JTextField(22);
        usernameField.setPreferredSize(new Dimension(0, 38));
        gbc.gridx = 1;
        loginCard.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        loginCard.add(passLabel, gbc);

        JPasswordField passwordField = new JPasswordField(22);
        passwordField.setPreferredSize(new Dimension(0, 38));
        gbc.gridx = 1;
        loginCard.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(19, 116, 178));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(240, 42));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        loginCard.add(loginButton, gbc);

        JLabel infoLabel = new JLabel("Demo: mcruz / pass123  |  jsantos / admin123");
        infoLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(96, 108, 120));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        loginCard.add(infoLabel, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            User user = authenticate(username, password);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                return;
            }

            currentUser = user;
            updateDashboardForUser();
            cardLayout.show(cardPanel, "dashboard");
            usernameField.setText("");
            passwordField.setText("");
        });

        loginPanel.add(loginCard);
        cardPanel.add(loginPanel, "login");
        cardLayout.show(cardPanel, "login");
    }

    private void buildDashboard() {
        JPanel dashboardPanel = new JPanel(new BorderLayout(14, 14));
        dashboardPanel.setBackground(new Color(239, 244, 249));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout(12, 0));

        JLabel logoMini = new JLabel(createLogoIconSmall());
        titlePanel.add(logoMini, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(15, 45, 70));
        textPanel.add(welcomeLabel);

        subWelcomeLabel = new JLabel("Barangay operations overview");
        subWelcomeLabel.setForeground(new Color(92, 107, 122));
        subWelcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textPanel.add(subWelcomeLabel);

        titlePanel.add(textPanel, BorderLayout.CENTER);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(40, 69, 94));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(110, 38));
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, "login"));
        headerPanel.add(logoutButton, BorderLayout.EAST);

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(255, 255, 255));
        tabbedPane.addTab("Overview", buildOverviewPanel());
        tabbedPane.addTab("Resident", buildResidentPanel());
        tabbedPane.addTab("Staff", buildStaffPanel());
        dashboardPanel.add(tabbedPane, BorderLayout.CENTER);

        cardPanel.add(dashboardPanel, "dashboard");
    }

    private JPanel buildOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(new Color(245, 248, 251));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        statsPanel.setOpaque(false);
        statsPanel.add(createStatCard("Pending", "0", new Color(230, 169, 61)));
        statsPanel.add(createStatCard("In Progress", "0", new Color(41, 128, 185)));
        statsPanel.add(createStatCard("Resolved", "0", new Color(46, 125, 50)));
        statsPanel.add(createStatCard("Total", "0", new Color(94, 108, 122)));
        panel.add(statsPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Community Overview");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(20, 40, 59));
        panel.add(titleLabel, BorderLayout.CENTER);

        JTextArea overviewArea = new JTextArea();
        overviewArea.setEditable(false);
        overviewArea.setBackground(new Color(255, 255, 255));
        overviewArea.setBorder(BorderFactory.createLineBorder(new Color(214, 222, 230), 1));
        overviewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        overviewArea.setLineWrap(true);
        overviewArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(overviewArea), BorderLayout.CENTER);

        summaryLabel = new JLabel();
        summaryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        summaryLabel.setForeground(new Color(37, 58, 75));
        summaryLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        panel.add(summaryLabel, BorderLayout.SOUTH);

        updateOverviewText(overviewArea);
        return panel;
    }

    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 232), 1),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        card.setLayout(new BorderLayout());

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        valueLabel.setForeground(color);

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(100, 114, 126));

        card.add(valueLabel, BorderLayout.NORTH);
        card.add(titleLabel, BorderLayout.SOUTH);
        return card;
    }

    private ImageIcon createLogoIcon() {
        int size = 70;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(19, 116, 178));
        g2.fillRoundRect(10, 10, 50, 50, 16, 16);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();
        String text = "B";
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, x, y);
        g2.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createLogoIconSmall() {
        int size = 42;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(19, 116, 178));
        g2.fillRoundRect(5, 5, 32, 32, 10, 10);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        String text = "B";
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, x, y);
        g2.dispose();
        return new ImageIcon(image);
    }

    private JPanel buildResidentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Submit a Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        JLabel typeLabel = new JLabel("Type:");
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(typeLabel, gbc);

        JComboBox<String> reportTypeBox = new JComboBox<>(new String[]{"Incident", "Complaint"});
        gbc.gridx = 1;
        panel.add(reportTypeBox, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(categoryLabel, gbc);

        JTextField categoryField = new JTextField(25);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(locationLabel, gbc);

        JTextField locationField = new JTextField(25);
        gbc.gridx = 1;
        panel.add(locationField, gbc);

        JLabel detail1Label = new JLabel("Persons Involved / Respondent:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(detail1Label, gbc);

        JTextField detail1Field = new JTextField(25);
        gbc.gridx = 1;
        panel.add(detail1Field, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(descriptionLabel, gbc);

        JTextArea descriptionArea = new JTextArea(6, 25);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(descScroll, gbc);

        JButton submitButton = new JButton("Submit Report");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        reportTypeBox.addActionListener(e -> {
            String selected = (String) reportTypeBox.getSelectedItem();
            detail1Label.setText("Incident".equals(selected) ? "Persons Involved:" : "Respondent Name:");
        });

        submitButton.addActionListener(e -> {
            if (currentUser == null) {
                return;
            }

            String type = (String) reportTypeBox.getSelectedItem();
            String category = categoryField.getText().trim();
            String location = locationField.getText().trim();
            String detail = detail1Field.getText().trim();
            String description = descriptionArea.getText().trim();

            if (category.isEmpty() || location.isEmpty() || detail.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Details", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String reportId = ("Incident".equals(type) ? "IR-" : "CR-") + (reports.size() + 1001);
            Report report;
            if ("Incident".equals(type)) {
                report = new IncidentReport(reportId, currentUser.getId(), category, location, description, java.time.LocalDate.now().toString(), "PENDING", detail);
            } else {
                report = new ComplaintReport(reportId, currentUser.getId(), category, location, description, java.time.LocalDate.now().toString(), "PENDING", detail);
            }

            reports.add(report);
            JOptionPane.showMessageDialog(this, "Report submitted successfully!\nID: " + reportId, "Success", JOptionPane.INFORMATION_MESSAGE);
            updateOverviewText(null);
            updateReportList();

            categoryField.setText("");
            locationField.setText("");
            detail1Field.setText("");
            descriptionArea.setText("");
        });

        return panel;
    }

    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Report Queue");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.WEST);

        JButton inProgressButton = new JButton("Mark In Progress");
        JButton resolvedButton = new JButton("Mark Resolved");

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(inProgressButton);
        actions.add(resolvedButton);
        topPanel.add(actions, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        reportListModel = new DefaultListModel<>();
        reportList = new JList<>(reportListModel);
        reportList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(reportList), BorderLayout.CENTER);

        inProgressButton.addActionListener(e -> updateSelectedReportStatus("IN_PROGRESS"));
        resolvedButton.addActionListener(e -> updateSelectedReportStatus("RESOLVED"));

        updateReportList();
        return panel;
    }

    private User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void updateDashboardForUser() {
        if (currentUser == null) {
            return;
        }

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName());
        }
        if (subWelcomeLabel != null) {
            subWelcomeLabel.setText(currentUser.getRoleType() + " account");
        }

        JTabbedPane tabbedPane = findTabbedPaneInDashboard();
        if (tabbedPane == null) {
            return;
        }

        if (currentUser instanceof Resident) {
            tabbedPane.setEnabledAt(1, true);
            tabbedPane.setEnabledAt(2, false);
        } else if (currentUser instanceof BarangayStaff) {
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setEnabledAt(2, true);
        }

        tabbedPane.setSelectedIndex(0);
        updateReportList();
        updateOverviewText(null);
    }

    private JTabbedPane findTabbedPaneInDashboard() {
        Component content = cardPanel.getComponent(1);
        if (content instanceof Container) {
            for (Component component : ((Container) content).getComponents()) {
                if (component instanceof JTabbedPane) {
                    return (JTabbedPane) component;
                }
            }
        }
        return null;
    }

    private void updateReportList() {
        if (reportListModel == null) {
            return;
        }

        reportListModel.clear();
        for (Report report : reports) {
            reportListModel.addElement(report.getReportId() + " | " + report.getReportType() + " | " + report.getStatus());
        }
    }

    private void updateSelectedReportStatus(String newStatus) {
        if (reportList == null) {
            return;
        }

        int index = reportList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Please select a report to update.", "No Report Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        reports.get(index).setStatus(newStatus);
        updateReportList();
        updateOverviewText(null);
        JOptionPane.showMessageDialog(this, "Status updated to " + newStatus + ".", "Updated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateOverviewText(JTextArea overviewArea) {
        int pending = 0;
        int progress = 0;
        int resolved = 0;

        for (Report report : reports) {
            switch (report.getStatus()) {
                case "PENDING": pending++; break;
                case "IN_PROGRESS": progress++; break;
                case "RESOLVED": resolved++; break;
                default: break;
            }
        }

        String text = "Barangay reports overview\n" +
                "=======================\n" +
                "Pending: " + pending + "\n" +
                "In Progress: " + progress + "\n" +
                "Resolved: " + resolved + "\n\n" +
                "Current user: " + (currentUser != null ? currentUser.getFullName() + " (" + currentUser.getRoleType() + ")" : "Not signed in") + "\n\n" +
                "Latest reports:\n";

        for (Report report : reports) {
            text += report.getReportId() + " - " + report.getReportType() + " | " + report.getStatus() + "\n";
        }

        if (summaryLabel != null) {
            summaryLabel.setText("Total records: " + reports.size() + "   |   Pending: " + pending + "   |   In Progress: " + progress + "   |   Resolved: " + resolved);
        }

        if (overviewArea != null) {
            overviewArea.setText(text);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
