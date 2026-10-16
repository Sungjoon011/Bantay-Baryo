package bantaybaryo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {
    private static final Color PRIMARY = Color.decode("#1E3A8A");
    private static final Color SECONDARY = Color.decode("#0284C7");
    private static final Color BACKGROUND = Color.decode("#F8FAFC");
    private static final Color TEXT = Color.decode("#0F172A");
    private static final Color MUTED = Color.decode("#6B7280");
    private static final Color BORDER = Color.decode("#E2E8F0");
    private static final Color PENDING = Color.decode("#F59E0B");
    private static final Color PROGRESS = Color.decode("#2563EB");
    private static final Color RESOLVED = Color.decode("#10B981");

    private final CardLayout mainLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(mainLayout);
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Report> reports = new ArrayList<>();

    private User currentUser;
    private JLabel dashboardWelcome;
    private JLabel dashboardRole;
    private JPanel dashboardContent;
    private CardLayout contentLayout;
    private JLabel pendingValue;
    private JLabel progressValue;
    private JLabel resolvedValue;
    private JLabel totalValue;
    private DefaultListModel<String> reportListModel;
    private JList<String> reportList;
    private DefaultListModel<String> residentReportListModel;
    private JList<String> residentReportList;
    private DefaultListModel<String> overviewReportListModel;
    private JList<String> overviewReportList;
    private JLabel summaryLabel;
    private JLabel overviewTitle;
    private JButton residentNavigation;
    private JButton myReportsNavigation;
    private JButton staffNavigation;
    private JButton activeSidebarButton;
    private String overviewStatusFilter;

    public Main() {
        setTitle("Bantay Baryo - Community Incident Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 760);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);

        seedData();
        mainPanel.add(buildHomePanel(), "home");
        mainPanel.add(buildLoginPanel(), "login");
        mainPanel.add(buildDashboardPanel(), "dashboard");
        add(mainPanel);
        mainLayout.show(mainPanel, "home");
    }

    private void seedData() {
        users.add(new Resident("R-1001", "Maria", "Dela", "Cruz", "mcruz", "pass123", "Purok 2", "Bahay ng Lahat"));
        users.add(new BarangayStaff("S-2001", "Jose", "", "Santos", "jsantos", "admin123", "Community Officer"));

        reports.add(new IncidentReport("IR-3001", "R-1001", "Public Safety", "Barangay Hall",
                "A noise disturbance and fight occurred near the covered court.",
                "2026-10-07", "PENDING", "Maria Cruz and Juan Dela Torre"));
        reports.add(new ComplaintReport("CR-3002", "R-1001", "Garbage Disposal", "Purok 5",
                "Uncollected trash has been piling up near the drainage canal.",
                "2026-10-07", "IN_PROGRESS", "Barangay Waste Team"));
    }

        private JPanel buildHomePanel() {
    JPanel root = new JPanel(new BorderLayout());
    root.setBackground(BACKGROUND);

    // ---------- NAVBAR ----------
    JPanel nav = new JPanel(new BorderLayout());
    nav.setBackground(PRIMARY);
    nav.setBorder(new EmptyBorder(12, 32, 12, 32));

    // Left: logo + brand
    JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
    brandPanel.setOpaque(false);

    ImageIcon navLogo = getLogoIcon(36, 36);
    JLabel brandLogo = new JLabel();
    if (navLogo != null) {
        brandLogo.setIcon(navLogo);
    } else {
        brandLogo.setText("🛡");
        brandLogo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        brandLogo.setForeground(Color.WHITE);
    }

    JLabel brand = new JLabel("Bantay-Baryo");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 22));

        brandPanel.add(brandLogo);
        brandPanel.add(brand);

    // Right: Home + Login
    JPanel navActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            navActions.setOpaque(false);

    JButton homeButton = createNavGhostButton("Home");
            homeButton.addActionListener(e -> mainLayout.show(mainPanel, "home"));

    JButton loginButton = createNavOutlineButton("Login");
            loginButton.addActionListener(e -> mainLayout.show(mainPanel, "login"));

        navActions.add(homeButton);
        navActions.add(loginButton);

        nav.add(brandPanel, BorderLayout.WEST);
        nav.add(navActions, BorderLayout.EAST);

    // ---------- HERO CONTENT ----------
    JPanel hero = new JPanel();
        hero.setBackground(BACKGROUND);
        hero.setLayout(new GridBagLayout());   // true vertical centering

    JPanel heroContent = new JPanel();
        heroContent.setOpaque(false);
        heroContent.setLayout(new BoxLayout(heroContent, BoxLayout.Y_AXIS));

    // Big centered logo
    ImageIcon heroLogo = getLogoIcon(220, 220);
        if (heroLogo != null) {
            JLabel heroLogoLabel = new JLabel(heroLogo);
                heroLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                heroContent.add(heroLogoLabel);
                heroContent.add(Box.createVerticalStrut(20));
        }

    // Title
    JLabel title = new JLabel("Your Community. Your Voice. Your Safety.");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Subtitle
    JLabel subtitle = new JLabel("A simple digital platform for reporting and tracking barangay concerns.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Call to action
    JButton getStarted = createPrimaryButton("Get Started");
        getStarted.setFont(new Font("SansSerif", Font.BOLD, 14));
        getStarted.setPreferredSize(new Dimension(200, 46));
        getStarted.setMaximumSize(new Dimension(200, 46));
        getStarted.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStarted.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                new EmptyBorder(10, 24, 10, 24)));
        getStarted.addActionListener(e -> mainLayout.show(mainPanel, "login"));
        getStarted.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                getStarted.setBackground(SECONDARY);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                getStarted.setBackground(PRIMARY);
            }
        });

    // Footer note
    JLabel note = new JLabel("Accounts are created and managed by the Barangay Administrator.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(MUTED);
        note.setAlignmentX(Component.CENTER_ALIGNMENT);

        heroContent.add(title);
        heroContent.add(Box.createVerticalStrut(14));
        heroContent.add(subtitle);
        heroContent.add(Box.createVerticalStrut(32));
        heroContent.add(getStarted);
        heroContent.add(Box.createVerticalStrut(26));
        heroContent.add(note);

    GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        hero.add(heroContent, gbc);

        root.add(nav, BorderLayout.NORTH);
        root.add(hero, BorderLayout.CENTER);
        return root;
}

    // Transparent text-only button for the navbar (Home)
    private JButton createNavGhostButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setForeground(new Color(191, 219, 254));   // soft light blue
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    // Outlined button for the navbar (Login)
    private JButton createNavOutlineButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(110, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                new EmptyBorder(6, 18, 6, 18)));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(SECONDARY);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        new EmptyBorder(6, 18, 6, 18)));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        new EmptyBorder(6, 18, 6, 18)));
            }
        });
        return button;
    }

    private JPanel createInfoCard(String number, String heading, String description) {
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numberLabel.setForeground(SECONDARY);
        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        headingLabel.setForeground(TEXT);
        JLabel descriptionLabel = new JLabel("<html><div style='width:220px'>" + description + "</div></html>");
        descriptionLabel.setForeground(MUTED);
        card.add(numberLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(headingLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(descriptionLabel);
        return card;
    }

    private JPanel buildLoginPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BACKGROUND);
        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(30, 38, 30, 38)));
        card.setPreferredSize(new Dimension(420, 450));

        ImageIcon loginLogo = getLogoIcon(90, 90);
        JLabel logo;
            if (loginLogo != null) {
                logo = new JLabel(loginLogo);
            } else {
                logo = new JLabel(createLogoIcon());   // fallback to "B" badge
            }
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = centeredLabel("Bantay-Baryo", 24, PRIMARY, Font.BOLD);
        JLabel subtitle = centeredLabel("Login to your barangay account", 13, MUTED, Font.PLAIN);
        JTextField username = new JTextField("mcruz");
        JPasswordField password = new JPasswordField("pass123");
        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setOpaque(false);
        showPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPassword.addActionListener(e -> password.setEchoChar(showPassword.isSelected() ? (char) 0 : '•'));

        JButton signIn = createPrimaryButton("Sign In");
        signIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signIn.setPreferredSize(new Dimension(260, 44));
        signIn.setMaximumSize(new Dimension(260, 44));
        signIn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                new EmptyBorder(10, 24, 10, 24)));
        signIn.setToolTipText("Sign in to your Bantay-Baryo account");
        signIn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                signIn.setBackground(SECONDARY);
                signIn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY, 2),
                        new EmptyBorder(9, 23, 9, 23)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                signIn.setBackground(PRIMARY);
                signIn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY, 1),
                        new EmptyBorder(10, 24, 10, 24)));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent event) {
                signIn.setBackground(new Color(15, 55, 105));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent event) {
                if (signIn.contains(event.getPoint())) {
                    signIn.setBackground(SECONDARY);
                }
            }
        });
        JButton back = new JButton("← Back to Home");
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(SECONDARY);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(e -> mainLayout.show(mainPanel, "home"));
        signIn.addActionListener(e -> {
            User user = authenticate(username.getText().trim(), new String(password.getPassword()));
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                password.setText("");
                return;
            }
            currentUser = user;
            updateDashboardForUser();
            mainLayout.show(mainPanel, "dashboard");
            username.setText("");
            password.setText("");
        });

        card.add(logo);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(24));
        card.add(createFormRow("Username", username));
        card.add(Box.createVerticalStrut(12));
        card.add(createFormRow("Password", password));
        JLabel demoAccounts = new JLabel("<html><div style='text-align:center'>Demo accounts:<br>"
                + "Resident: mcruz / pass123<br>Staff: jsantos / admin123</div></html>");
        demoAccounts.setFont(new Font("SansSerif", Font.ITALIC, 11));
        demoAccounts.setForeground(MUTED);
        demoAccounts.setHorizontalAlignment(SwingConstants.CENTER);
        demoAccounts.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(6));
        card.add(demoAccounts);
        card.add(Box.createVerticalStrut(8));
        card.add(showPassword);
        card.add(Box.createVerticalStrut(20));
        card.add(signIn);
        card.add(Box.createVerticalStrut(10));
        card.add(back);
        root.add(card);
        return root;
    }

    private JPanel buildDashboardPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(16, 28, 16, 28));
        JPanel heading = new JPanel(new BorderLayout(12, 0));
        heading.setOpaque(false);
        heading.add(new JLabel(createLogoIconSmall()), BorderLayout.WEST);
        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));
        dashboardWelcome = new JLabel("Welcome");
        dashboardWelcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        dashboardWelcome.setForeground(TEXT);
        dashboardRole = new JLabel("Community overview");
        dashboardRole.setForeground(MUTED);
        headingText.add(dashboardWelcome);
        headingText.add(dashboardRole);
        heading.add(headingText, BorderLayout.CENTER);
        JButton logout = createSecondaryButton("Logout");
        logout.addActionListener(e -> {
            currentUser = null;
            mainLayout.show(mainPanel, "login");
        });
        topBar.add(heading, BorderLayout.WEST);
        topBar.add(logout, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BACKGROUND);
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER),
                new EmptyBorder(16, 14, 16, 14)));
        sidebar.setPreferredSize(new Dimension(205, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        dashboardContent = new JPanel();
        contentLayout = new CardLayout();
        dashboardContent.setLayout(contentLayout);
        dashboardContent.setBackground(BACKGROUND);

        JButton overview = createSidebarButton("Overview");
        residentNavigation = createSidebarButton("Submit a Report");
        myReportsNavigation = createSidebarButton("My Reports");
        staffNavigation = createSidebarButton("Report Queue");
        overview.addActionListener(e -> {
            activateSidebarButton(overview);
            contentLayout.show(dashboardContent, "overview");
        });
        residentNavigation.addActionListener(e -> {
            activateSidebarButton(residentNavigation);
            contentLayout.show(dashboardContent, "resident");
        });
        myReportsNavigation.addActionListener(e -> {
            activateSidebarButton(myReportsNavigation);
            updateResidentReportList();
            contentLayout.show(dashboardContent, "myReports");
        });
        staffNavigation.addActionListener(e -> {
            activateSidebarButton(staffNavigation);
            contentLayout.show(dashboardContent, "staff");
        });
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(overview);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(residentNavigation);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(myReportsNavigation);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(staffNavigation);
        activateSidebarButton(overview);

        dashboardContent.add(buildOverviewPanel(), "overview");
        dashboardContent.add(buildResidentPanel(), "resident");
        dashboardContent.add(buildMyReportsPanel(), "myReports");
        dashboardContent.add(buildStaffPanel(), "staff");
        body.add(sidebar, BorderLayout.WEST);
        body.add(dashboardContent, BorderLayout.CENTER);
        root.add(topBar, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        return root;
    }

    private JPanel buildOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        pendingValue = createStatCard("Pending", PENDING);
        progressValue = createStatCard("In Progress", PROGRESS);
        resolvedValue = createStatCard("Resolved", RESOLVED);
        totalValue = createStatCard("Total Reports", MUTED);
        stats.add(pendingValue.getParent());
        stats.add(progressValue.getParent());
        stats.add(resolvedValue.getParent());
        stats.add(totalValue.getParent());
        makeOverviewCardClickable(pendingValue.getParent(), "PENDING");
        makeOverviewCardClickable(progressValue.getParent(), "IN_PROGRESS");
        makeOverviewCardClickable(resolvedValue.getParent(), "RESOLVED");
        makeOverviewCardClickable(totalValue.getParent(), null);
        panel.add(stats, BorderLayout.NORTH);

        JPanel recent = createCard();
        recent.setLayout(new BorderLayout(0, 12));
        recent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(20, 22, 20, 22)));
        overviewTitle = new JLabel("Overview");
        overviewTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        overviewTitle.setForeground(TEXT);
        overviewReportListModel = new DefaultListModel<>();
        overviewReportList = new JList<>(overviewReportListModel);
        overviewReportList.setFixedCellHeight(38);
        overviewReportList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        overviewReportList.setBorder(new EmptyBorder(8, 10, 8, 10));
        styleReportList(overviewReportList);
        overviewReportList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                if (event.getClickCount() == 2 && overviewReportList.getSelectedIndex() >= 0) {
                    showReportDetails(getOverviewReports().get(overviewReportList.getSelectedIndex()));
                }
            }
        });
        summaryLabel = new JLabel();
        summaryLabel.setForeground(MUTED);
        recent.add(overviewTitle, BorderLayout.NORTH);
        JPanel reportArea = new JPanel(new BorderLayout(0, 6));
        reportArea.setOpaque(false);
        JLabel hint = new JLabel("Double-click a report to view complete details.");
        hint.setForeground(MUTED);
        reportArea.add(hint, BorderLayout.NORTH);
        reportArea.add(new JScrollPane(overviewReportList), BorderLayout.CENTER);
        recent.add(reportArea, BorderLayout.CENTER);
        recent.add(summaryLabel, BorderLayout.SOUTH);
        panel.add(recent, BorderLayout.CENTER);
        updateOverviewText();
        return panel;
    }

    private void makeOverviewCardClickable(Component component, String status) {
        if (!(component instanceof JPanel)) return;
        JPanel card = (JPanel) component;
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setToolTipText(status == null ? "Show all reports" : "Show " + status.replace('_', ' ').toLowerCase() + " reports");
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                overviewStatusFilter = status;
                updateOverviewText();
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                card.setBorder(BorderFactory.createLineBorder(SECONDARY, 2));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                card.setBorder(new EmptyBorder(15, 18, 15, 18));
            }
        });
    }

    private JLabel createStatCard(String label, Color color) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 18, 15, 18));
        JLabel value = new JLabel("0");
        value.setName(label);
        value.setFont(new Font("SansSerif", Font.BOLD, 28));
        value.setForeground(color);
        JLabel caption = new JLabel(label);
        caption.setForeground(MUTED);
        card.add(value, BorderLayout.NORTH);
        card.add(caption, BorderLayout.SOUTH);
        return value;
    }

    private JPanel buildResidentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        JPanel card = createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(22, 26, 22, 26));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JLabel title = new JLabel("Submit a Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(TEXT);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        card.add(title, c);

        JComboBox<String> type = new JComboBox<>(new String[]{"Incident", "Complaint"});
        JComboBox<String> category = new JComboBox<>(new String[]{
                "Noise Disturbance", "Sanitation", "Infrastructure",
                "Boundary Dispute", "Public Safety", "Others"
        });
        JTextField location = new JTextField();
        JTextField detail = new JTextField();
        JLabel detailLabel = new JLabel("Persons Involved:");
        JTextArea description = new JTextArea(5, 25);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        c.gridwidth = 1;
        addFormField(card, c, 1, "Report Type:", type);
        addFormField(card, c, 2, "Category:", category);
        addFormField(card, c, 3, "Location:", location);
        c.gridx = 0; c.gridy = 4; card.add(detailLabel, c);
        c.gridx = 1; card.add(detail, c);
        c.gridx = 0; c.gridy = 5; c.anchor = GridBagConstraints.NORTHWEST; card.add(new JLabel("Description:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.BOTH; card.add(new JScrollPane(description), c);
        JButton submit = createPrimaryButton("Submit Report");
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.fill = GridBagConstraints.NONE; c.anchor = GridBagConstraints.CENTER;
        card.add(submit, c);
        type.addActionListener(e -> detailLabel.setText("Incident".equals(type.getSelectedItem())
                ? "Persons Involved:" : "Respondent Name:"));
        submit.addActionListener(e -> submitReport(type, category, location, detail, description));
        panel.add(card);
        return panel;
    }

    private void submitReport(JComboBox<String> type, JComboBox<String> category, JTextField location,
                              JTextField detail, JTextArea description) {
        if (!(currentUser instanceof Resident)) return;
        String categoryValue = (String) category.getSelectedItem();
        String locationValue = location.getText().trim();
        String detailValue = detail.getText().trim();
        String descriptionValue = description.getText().trim();
        if (categoryValue.isEmpty() || locationValue.isEmpty() || detailValue.isEmpty() || descriptionValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Details", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String typeValue = (String) type.getSelectedItem();
        String id = ("Incident".equals(typeValue) ? "IR-" : "CR-") + (reports.size() + 1001);
        Report report = "Incident".equals(typeValue)
                ? new IncidentReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue)
                : new ComplaintReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue);
        reports.add(report);
        JOptionPane.showMessageDialog(this, "Report submitted successfully!\nID: " + id,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        category.setSelectedIndex(0); location.setText(""); detail.setText(""); description.setText("");
        updateReportList();
        updateResidentReportList();
        updateOverviewText();
    }

    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Report Queue");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(TEXT);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton progress = createSecondaryButton("Mark In Progress");
        JButton resolved = createPrimaryButton("Mark Resolved");
        actions.add(progress); actions.add(resolved);
        top.add(title, BorderLayout.WEST); top.add(actions, BorderLayout.EAST);
        reportListModel = new DefaultListModel<>();
        reportList = new JList<>(reportListModel);
        reportList.setFixedCellHeight(34);
        reportList.setBorder(new EmptyBorder(8, 10, 8, 10));
        reportList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        styleReportList(reportList);
        reportList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                if (event.getClickCount() == 2 && reportList.getSelectedIndex() >= 0) {
                    showReportDetails(reports.get(reportList.getSelectedIndex()));
                }
            }
        });
        panel.add(top, BorderLayout.NORTH);
        JPanel listArea = new JPanel(new BorderLayout(0, 8));
        listArea.setOpaque(false);
        JLabel hint = new JLabel("Double-click a report to view its complete details.");
        hint.setForeground(MUTED);
        listArea.add(hint, BorderLayout.NORTH);
        listArea.add(new JScrollPane(reportList), BorderLayout.CENTER);
        listArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(12, 14, 14, 14)));
        panel.add(listArea, BorderLayout.CENTER);
        progress.addActionListener(e -> updateSelectedReportStatus("IN_PROGRESS"));
        resolved.addActionListener(e -> updateSelectedReportStatus("RESOLVED"));
        updateReportList();
        return panel;
    }

    private JPanel buildMyReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("My Submitted Reports");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(TEXT);

        residentReportListModel = new DefaultListModel<>();
        residentReportList = new JList<>(residentReportListModel);
        residentReportList.setFixedCellHeight(38);
        residentReportList.setBorder(new EmptyBorder(8, 10, 8, 10));
        residentReportList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        styleReportList(residentReportList);
        residentReportList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                if (event.getClickCount() == 2 && residentReportList.getSelectedIndex() >= 0) {
                    ArrayList<Report> residentReports = getVisibleReports();
                    showReportDetails(residentReports.get(residentReportList.getSelectedIndex()));
                }
            }
        });

        JLabel hint = new JLabel("Double-click a report to view its complete details.");
        hint.setForeground(MUTED);
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title, BorderLayout.WEST);
        top.add(hint, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);
        JPanel listArea = new JPanel(new BorderLayout());
        listArea.setBackground(Color.WHITE);
        listArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(8, 10, 10, 10)));
        listArea.add(new JScrollPane(residentReportList), BorderLayout.CENTER);
        panel.add(listArea, BorderLayout.CENTER);
        updateResidentReportList();
        return panel;
    }

    private void addFormField(JPanel panel, GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row; c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        panel.add(field, c);
    }

    private void styleReportList(JList<String> list) {
        list.setSelectionBackground(new Color(219, 234, 254));
        list.setSelectionForeground(TEXT);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> source, Object value, int index,
                                                           boolean selected, boolean focused) {
                JLabel cell = (JLabel) super.getListCellRendererComponent(
                        source, value, index, selected, focused);
                cell.setBorder(new EmptyBorder(8, 10, 8, 10));
                if (!selected) {
                    cell.setBackground(index % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    cell.setForeground(TEXT);
                }
                return cell;
            }
        });
    }

    private JPanel createFormRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(new Dimension(340, 36));
        JLabel name = new JLabel(label);
        name.setPreferredSize(new Dimension(72, 28));
        name.setForeground(TEXT);
        row.add(name, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)
                    && "ACTIVE".equalsIgnoreCase(user.getStatus())) return user;
        }
        return null;
    }

    private void updateDashboardForUser() {
        if (currentUser == null) return;
        dashboardWelcome.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName());
        dashboardRole.setText(currentUser.getRoleType() + " account");
        boolean resident = currentUser instanceof Resident;
        overviewStatusFilter = null;
        overviewTitle.setText(resident ? "My Report Overview" : "Community Overview");
        residentNavigation.setVisible(resident);
        myReportsNavigation.setVisible(resident);
        staffNavigation.setVisible(!resident);
        activateSidebarButton(findSidebarButton("Overview"));
        residentNavigation.getParent().revalidate();
        residentNavigation.getParent().repaint();
        contentLayout.show(dashboardContent, "overview");
        updateReportList();
        updateResidentReportList();
        updateOverviewText();
    }

    private JButton findSidebarButton(String text) {
        Container sidebar = residentNavigation.getParent();
        for (Component component : sidebar.getComponents()) {
            if (component instanceof JButton && text.equals(((JButton) component).getText())) {
                return (JButton) component;
            }
        }
        return null;
    }

    private void updateReportList() {
        if (reportListModel == null) return;
        reportListModel.clear();
        for (Report report : reports) {
            reportListModel.addElement(report.getReportId() + "  |  " + report.getReportType()
                    + "  |  " + report.getCategory() + "  |  " + report.getStatus());
        }
    }

    private void updateResidentReportList() {
        if (residentReportListModel == null) return;
        residentReportListModel.clear();
        for (Report report : getVisibleReports()) {
            residentReportListModel.addElement(report.getReportId() + "  |  " + report.getReportType()
                    + "  |  " + report.getCategory() + "  |  " + report.getStatus());
        }
    }

    private void updateSelectedReportStatus(String status) {
        int index = reportList == null ? -1 : reportList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Please select a report to update.",
                    "No Report Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Report selectedReport = reports.get(index);
        String currentStatus = selectedReport.getStatus();
        if ("RESOLVED".equals(currentStatus) && !"RESOLVED".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "This report is already resolved and cannot be moved back to " + status + ".",
                    "Status Update Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ("PENDING".equals(currentStatus) && "RESOLVED".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "A pending report must be marked In Progress before it can be resolved.",
                    "Status Update Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (currentStatus.equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "This report is already marked as " + status + ".",
                    "No Status Change", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selectedReport.setStatus(status);
        updateReportList();
        updateResidentReportList();
        updateOverviewText();
        JOptionPane.showMessageDialog(this, "Status updated to " + status + ".",
                "Updated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateOverviewText() {
        int pending = 0, progress = 0, resolved = 0;
        ArrayList<Report> visibleReports = getVisibleReports();
        for (Report report : visibleReports) {
            if ("PENDING".equals(report.getStatus())) pending++;
            else if ("IN_PROGRESS".equals(report.getStatus())) progress++;
            else if ("RESOLVED".equals(report.getStatus())) resolved++;
        }
        if (summaryLabel != null) {
            String label = currentUser instanceof Resident ? "Your reports: " : "Total reports: ";
            String filterLabel = overviewStatusFilter == null ? "" : "   |   Showing: "
                    + overviewStatusFilter.replace('_', ' ');
            summaryLabel.setText(label + visibleReports.size() + "   |   Pending: " + pending
                    + "   |   In Progress: " + progress + "   |   Resolved: " + resolved + filterLabel);
        }
        if (pendingValue != null) pendingValue.setText(String.valueOf(pending));
        if (progressValue != null) progressValue.setText(String.valueOf(progress));
        if (resolvedValue != null) resolvedValue.setText(String.valueOf(resolved));
        if (totalValue != null) totalValue.setText(String.valueOf(visibleReports.size()));
        if (overviewReportListModel != null) {
            overviewReportListModel.clear();
            for (Report report : getOverviewReports()) {
                overviewReportListModel.addElement(report.getReportId() + "  |  "
                        + report.getReportType() + "  |  " + report.getCategory()
                        + "  |  " + report.getStatus());
            }
        }
    }

    private ArrayList<Report> getVisibleReports() {
        ArrayList<Report> visibleReports = new ArrayList<>();
        if (currentUser instanceof Resident) {
            for (Report report : reports) {
                if (currentUser.getId().equals(report.getReporterId())) {
                    visibleReports.add(report);
                }
            }
        } else {
            visibleReports.addAll(reports);
        }
        return visibleReports;
    }

    private ArrayList<Report> getOverviewReports() {
        ArrayList<Report> overviewReports = new ArrayList<>();
        for (Report report : getVisibleReports()) {
            if (overviewStatusFilter == null || overviewStatusFilter.equals(report.getStatus())) {
                overviewReports.add(report);
            }
        }
        return overviewReports;
    }

    private void showReportDetails(Report report) {
        if (report == null) return;
        JDialog dialog = new JDialog(this, "Report Details - " + report.getReportId(), true);
        dialog.setSize(620, 560);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(22, 24, 22, 24));
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);
        JLabel title = new JLabel("<html><b>" + report.getReportType() + " Report</b><br>"
                + "<font size='3'>" + report.getReportId() + "  |  " + report.getCategory() + "</font></html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(PRIMARY);
        JLabel status = new JLabel(report.getStatus().replace('_', ' '), SwingConstants.CENTER);
        status.setOpaque(true);
        status.setForeground(Color.WHITE);
        status.setBackground(statusColor(report.getStatus()));
        status.setBorder(new EmptyBorder(8, 14, 8, 14));
        header.add(title, BorderLayout.CENTER);
        header.add(status, BorderLayout.EAST);

        JPanel detailsCard = createCard();
        detailsCard.setLayout(new BorderLayout(0, 12));
        detailsCard.setBorder(new EmptyBorder(16, 18, 16, 18));
        JPanel metadata = new JPanel(new GridLayout(2, 3, 12, 10));
        metadata.setOpaque(false);
        metadata.add(detailInfo("Location", report.getLocation()));
        metadata.add(detailInfo("Date Submitted", report.getDateSubmitted()));
        metadata.add(detailInfo("Reporter ID", report.getReporterId()));
        metadata.add(detailInfo("Type", report.getReportType()));
        metadata.add(detailInfo("Category", report.getCategory()));
        metadata.add(detailInfo("Status", report.getStatus().replace('_', ' ')));

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setFont(new Font("SansSerif", Font.PLAIN, 14));
        details.setForeground(TEXT);
        details.setBackground(Color.WHITE);
        details.setBorder(new EmptyBorder(12, 12, 12, 12));
        details.setText(report.displayDetails());
        detailsCard.add(metadata, BorderLayout.NORTH);
        detailsCard.add(new JScrollPane(details), BorderLayout.CENTER);

        JButton close = createSecondaryButton("Close");
        close.addActionListener(e -> dialog.dispose());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.setOpaque(false);
        actions.add(close);
        content.add(header, BorderLayout.NORTH);
        content.add(detailsCard, BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);
        dialog.add(content);
        dialog.setVisible(true);
    }

    private JPanel detailInfo(String label, String value) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel labelView = new JLabel(label.toUpperCase());
        labelView.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelView.setForeground(MUTED);
        JLabel valueView = new JLabel("<html>" + value + "</html>");
        valueView.setFont(new Font("SansSerif", Font.PLAIN, 13));
        valueView.setForeground(TEXT);
        panel.add(labelView);
        panel.add(Box.createVerticalStrut(3));
        panel.add(valueView);
        return panel;
    }

    private Color statusColor(String status) {
        if ("PENDING".equals(status)) return PENDING;
        if ("IN_PROGRESS".equals(status)) return PROGRESS;
        if ("RESOLVED".equals(status)) return RESOLVED;
        return MUTED;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(BORDER));
        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = createPrimaryButton(text);
        button.setBackground(SECONDARY);
        return button;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(175, 44));
        button.setPreferredSize(new Dimension(175, 44));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(9, 12, 9, 12)));
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                if (button != activeSidebarButton) {
                    button.setBackground(new Color(239, 246, 255));
                    button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(SECONDARY, 1),
                            new EmptyBorder(9, 12, 9, 12)));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                if (button != activeSidebarButton) {
                    button.setBackground(Color.WHITE);
                    button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER, 1),
                            new EmptyBorder(9, 12, 9, 12)));
                }
            }
        });
        return button;
    }

    private void activateSidebarButton(JButton selected) {
        if (activeSidebarButton != null) {
            activeSidebarButton.setBackground(Color.WHITE);
            activeSidebarButton.setForeground(TEXT);
            activeSidebarButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    new EmptyBorder(9, 12, 9, 12)));
        }
        activeSidebarButton = selected;
        selected.setBackground(PRIMARY);
        selected.setForeground(Color.WHITE);
        selected.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                new EmptyBorder(9, 12, 9, 12)));
    }

    private JLabel centeredLabel(String text, int size, Color color, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private Image logoImage;

private Image getLogoImage() {
    if (logoImage != null) return logoImage;
    String[] candidates = {
        "resources/logo.png",
        "bantaybaryo/resources/logo.png",
        "src/resources/logo.png",
        "src/main/resources/logo.png"
    };
    for (String path : candidates) {
        java.io.File f = new java.io.File(path);
        if (f.exists()) {
            logoImage = new ImageIcon(f.getAbsolutePath()).getImage();
            System.out.println("✅ Logo loaded from: " + f.getAbsolutePath());
            return logoImage;
        }
    }
    java.net.URL url = getClass().getResource("/resources/logo.png");
    if (url != null) {
        logoImage = new ImageIcon(url).getImage();
        System.out.println("✅ Logo loaded from classpath");
        return logoImage;
    }
    System.err.println("⚠️ Logo not found. Using placeholder.");
    return null;
}

    private ImageIcon getLogoIcon(int maxWidth, int maxHeight) {
        Image img = getLogoImage();
        if (img == null) return null;

        int srcW = img.getWidth(null);
        int srcH = img.getHeight(null);
        if (srcW <= 0 || srcH <= 0) return null;

        // Preserve aspect ratio — fit inside the maxWidth x maxHeight box
        double scale = Math.min((double) maxWidth / srcW, (double) maxHeight / srcH);
        int drawW = (int) Math.round(srcW * scale);
        int drawH = (int) Math.round(srcH * scale);

        BufferedImage scaled = new BufferedImage(drawW, drawH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.drawImage(img, 0, 0, drawW, drawH, null);
        g2.dispose();

        return new ImageIcon(scaled);
    }

    private ImageIcon createLogoIcon() {
        return createLogoIcon(64, 22);
    }

    private ImageIcon createLogoIconSmall() {
        return createLogoIcon(40, 15);
    }

    private ImageIcon createLogoIcon(int size, int fontSize) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(SECONDARY);
        graphics.fillRoundRect(2, 2, size - 4, size - 4, size / 4, size / 4);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        FontMetrics metrics = graphics.getFontMetrics();
        String mark = "B";
        graphics.drawString(mark, (size - metrics.stringWidth(mark)) / 2,
                (size - metrics.getHeight()) / 2 + metrics.getAscent());
        graphics.dispose();
        return new ImageIcon(image);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
