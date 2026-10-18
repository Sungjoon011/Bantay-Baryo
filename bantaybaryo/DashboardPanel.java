package bantaybaryo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardPanel extends JPanel {
    private final Main mainApp;

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

    public DashboardPanel(CardLayout mainLayout, JPanel mainPanel, Main mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setBackground(Main.BACKGROUND);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(16, 28, 16, 28));

        JPanel heading = new JPanel(new BorderLayout(12, 0));
        heading.setOpaque(false);
        heading.add(new JLabel(mainApp.createLogoIconSmall()), BorderLayout.WEST);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        dashboardWelcome = new JLabel("Welcome");
        dashboardWelcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        dashboardWelcome.setForeground(Main.TEXT);

        dashboardRole = new JLabel("Community overview");
        dashboardRole.setForeground(Main.MUTED);

        headingText.add(dashboardWelcome);
        headingText.add(dashboardRole);
        heading.add(headingText, BorderLayout.CENTER);

        JButton logout = mainApp.createSecondaryButton("Logout");
        logout.addActionListener(e -> {
            mainApp.setCurrentUser(null);
            mainLayout.show(mainPanel, "login");
        });

        topBar.add(heading, BorderLayout.WEST);
        topBar.add(logout, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Main.BACKGROUND);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, Main.BORDER),
                new EmptyBorder(16, 14, 16, 14)));
        sidebar.setPreferredSize(new Dimension(205, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        dashboardContent = new JPanel();
        contentLayout = new CardLayout();
        dashboardContent.setLayout(contentLayout);
        dashboardContent.setBackground(Main.BACKGROUND);

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

        add(topBar, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    public void updateDashboardForUser() {
        User currentUser = mainApp.getCurrentUser();
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

    private JPanel buildOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        pendingValue = createStatCard("Pending", Main.PENDING);
        progressValue = createStatCard("In Progress", Main.PROGRESS);
        resolvedValue = createStatCard("Resolved", Main.RESOLVED);
        totalValue = createStatCard("Total Reports", Main.MUTED);

        stats.add(pendingValue.getParent());
        stats.add(progressValue.getParent());
        stats.add(resolvedValue.getParent());
        stats.add(totalValue.getParent());

        makeOverviewCardClickable(pendingValue.getParent(), "PENDING");
        makeOverviewCardClickable(progressValue.getParent(), "IN_PROGRESS");
        makeOverviewCardClickable(resolvedValue.getParent(), "RESOLVED");
        makeOverviewCardClickable(totalValue.getParent(), null);
        panel.add(stats, BorderLayout.NORTH);

        JPanel recent = mainApp.createCard();
        recent.setLayout(new BorderLayout(0, 12));
        recent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER, 1),
                new EmptyBorder(20, 22, 20, 22)));

        overviewTitle = new JLabel("Overview");
        overviewTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        overviewTitle.setForeground(Main.TEXT);

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
        summaryLabel.setForeground(Main.MUTED);
        recent.add(overviewTitle, BorderLayout.NORTH);

        JPanel reportArea = new JPanel(new BorderLayout(0, 6));
        reportArea.setOpaque(false);
        JLabel hint = new JLabel("Double-click a report to view complete details.");
        hint.setForeground(Main.MUTED);
        reportArea.add(hint, BorderLayout.NORTH);
        reportArea.add(new JScrollPane(overviewReportList), BorderLayout.CENTER);

        recent.add(reportArea, BorderLayout.CENTER);
        recent.add(summaryLabel, BorderLayout.SOUTH);
        panel.add(recent, BorderLayout.CENTER);

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
                card.setBorder(BorderFactory.createLineBorder(Main.SECONDARY, 2));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                card.setBorder(new EmptyBorder(15, 18, 15, 18));
            }
        });
    }

    private JLabel createStatCard(String label, Color color) {
        JPanel card = mainApp.createCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 18, 15, 18));
        JLabel value = new JLabel("0");
        value.setName(label);
        value.setFont(new Font("SansSerif", Font.BOLD, 28));
        value.setForeground(color);
        JLabel caption = new JLabel(label);
        caption.setForeground(Main.MUTED);
        card.add(value, BorderLayout.NORTH);
        card.add(caption, BorderLayout.SOUTH);
        return value;
    }

    private JPanel buildResidentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel card = mainApp.createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(22, 26, 22, 26));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel title = new JLabel("Submit a Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Main.TEXT);
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

        JButton submit = mainApp.createPrimaryButton("Submit Report");
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
        User currentUser = mainApp.getCurrentUser();
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
        int newIdNum = mainApp.getReports().size() + 1001;
        String id = ("Incident".equals(typeValue) ? "IR-" : "CR-") + newIdNum;

        Report report = "Incident".equals(typeValue)
                ? new IncidentReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue)
                : new ComplaintReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue);

        mainApp.getReports().add(report);

        JOptionPane.showMessageDialog(this, "Report submitted successfully!\nID: " + id,
                "Success", JOptionPane.INFORMATION_MESSAGE);

        category.setSelectedIndex(0); location.setText(""); detail.setText(""); description.setText("");
        updateReportList();
        updateResidentReportList();
        updateOverviewText();
    }

    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Report Queue");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Main.TEXT);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton progress = mainApp.createSecondaryButton("Mark In Progress");
        JButton resolved = mainApp.createPrimaryButton("Mark Resolved");
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
                    showReportDetails(mainApp.getReports().get(reportList.getSelectedIndex()));
                }
            }
        });

        panel.add(top, BorderLayout.NORTH);

        JPanel listArea = new JPanel(new BorderLayout(0, 8));
        listArea.setOpaque(false);
        JLabel hint = new JLabel("Double-click a report to view its complete details.");
        hint.setForeground(Main.MUTED);
        listArea.add(hint, BorderLayout.NORTH);
        listArea.add(new JScrollPane(reportList), BorderLayout.CENTER);
        listArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER, 1),
                new EmptyBorder(12, 14, 14, 14)));

        panel.add(listArea, BorderLayout.CENTER);

        progress.addActionListener(e -> updateSelectedReportStatus("IN_PROGRESS"));
        resolved.addActionListener(e -> updateSelectedReportStatus("RESOLVED"));
        return panel;
    }

    private JPanel buildMyReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("My Submitted Reports");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Main.TEXT);

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
        hint.setForeground(Main.MUTED);
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title, BorderLayout.WEST);
        top.add(hint, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        JPanel listArea = new JPanel(new BorderLayout());
        listArea.setBackground(Color.WHITE);
        listArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER, 1),
                new EmptyBorder(8, 10, 10, 10)));
        listArea.add(new JScrollPane(residentReportList), BorderLayout.CENTER);
        panel.add(listArea, BorderLayout.CENTER);

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
        list.setSelectionForeground(Main.TEXT);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> source, Object value, int index,
                                                          boolean selected, boolean focused) {
                JLabel cell = (JLabel) super.getListCellRendererComponent(
                        source, value, index, selected, focused);
                cell.setBorder(new EmptyBorder(8, 10, 8, 10));
                if (!selected) {
                    cell.setBackground(index % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    cell.setForeground(Main.TEXT);
                }
                return cell;
            }
        });
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(175, 44));
        button.setPreferredSize(new Dimension(175, 44));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER, 1),
                new EmptyBorder(9, 12, 9, 12)));
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Main.TEXT);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                if (button != activeSidebarButton) {
                    button.setBackground(new Color(239, 246, 255));
                    button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Main.SECONDARY, 1),
                            new EmptyBorder(9, 12, 9, 12)));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                if (button != activeSidebarButton) {
                    button.setBackground(Color.WHITE);
                    button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Main.BORDER, 1),
                            new EmptyBorder(9, 12, 9, 12)));
                }
            }
        });
        return button;
    }

    private void activateSidebarButton(JButton selected) {
        if (activeSidebarButton != null) {
            activeSidebarButton.setBackground(Color.WHITE);
            activeSidebarButton.setForeground(Main.TEXT);
            activeSidebarButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Main.BORDER, 1),
                    new EmptyBorder(9, 12, 9, 12)));
        }
        activeSidebarButton = selected;
        selected.setBackground(Main.PRIMARY);
        selected.setForeground(Color.WHITE);
        selected.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.PRIMARY, 1),
                new EmptyBorder(9, 12, 9, 12)));
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
        for (Report report : mainApp.getReports()) {
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
        Report selectedReport = mainApp.getReports().get(index);
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
        User currentUser = mainApp.getCurrentUser();

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
        User currentUser = mainApp.getCurrentUser();

        if (currentUser instanceof Resident) {
            for (Report report : mainApp.getReports()) {
                if (currentUser.getId().equals(report.getReporterId())) {
                    visibleReports.add(report);
                }
            }
        } else {
            visibleReports.addAll(mainApp.getReports());
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
        JDialog dialog = new JDialog(mainApp, "Report Details - " + report.getReportId(), true);
        dialog.setSize(620, 560);
        dialog.setLocationRelativeTo(mainApp);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(Main.BACKGROUND);
        content.setBorder(new EmptyBorder(22, 24, 22, 24));
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);
        JLabel title = new JLabel("<html><b>" + report.getReportType() + " Report</b><br>"
                + "<font size='3'>" + report.getReportId() + "  |  " + report.getCategory() + "</font></html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Main.PRIMARY);
        JLabel status = new JLabel(report.getStatus().replace('_', ' '), SwingConstants.CENTER);
        status.setOpaque(true);
        status.setForeground(Color.WHITE);
        status.setBackground(statusColor(report.getStatus()));
        status.setBorder(new EmptyBorder(8, 14, 8, 14));
        header.add(title, BorderLayout.CENTER);
        header.add(status, BorderLayout.EAST);

        JPanel detailsCard = mainApp.createCard();
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
        details.setForeground(Main.TEXT);
        details.setBackground(Color.WHITE);
        details.setBorder(new EmptyBorder(12, 12, 12, 12));
        details.setText(report.displayDetails());
        detailsCard.add(metadata, BorderLayout.NORTH);
        detailsCard.add(new JScrollPane(details), BorderLayout.CENTER);

        JButton close = mainApp.createSecondaryButton("Close");
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
        labelView.setForeground(Main.MUTED);
        JLabel valueView = new JLabel("<html>" + value + "</html>");
        valueView.setFont(new Font("SansSerif", Font.PLAIN, 13));
        valueView.setForeground(Main.TEXT);
        panel.add(labelView);
        panel.add(Box.createVerticalStrut(3));
        panel.add(valueView);
        return panel;
    }

    private Color statusColor(String status) {
        if ("PENDING".equals(status)) return Main.PENDING;
        if ("IN_PROGRESS".equals(status)) return Main.PROGRESS;
        if ("RESOLVED".equals(status)) return Main.RESOLVED;
        return Main.MUTED;
    }
}