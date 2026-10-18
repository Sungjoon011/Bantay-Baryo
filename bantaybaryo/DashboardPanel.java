package bantaybaryo;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JPanel {
    private final Main mainApp;

    private final  JLabel dashboardWelcome;
    private final JLabel dashboardRole;
    private JPanel dashboardContent;
    private CardLayout contentLayout;
    private JLabel pendingValue;
    private JLabel progressValue;
    private JLabel resolvedValue;
    private JLabel totalValue;
    private DefaultListModel<String> reportListModel;
    private JList<String> reportList;
    private DefaultListModel<String> overviewReportListModel;
    private JList<String> overviewReportList;
    private JLabel summaryLabel;

    private JLabel overviewTitle;
    private javax.swing.table.DefaultTableModel residentReportTableModel;
    
    // Sidebar Buttons
    private JButton overviewNavigation;
    private JButton residentNavigation;
    private JButton myReportsNavigation;
    private JButton staffNavigation;
    private JButton profileNavigation;
    private JButton adminNavigation;
    
    private JButton activeSidebarButton;
    private String overviewStatusFilter;
    private javax.swing.table.DefaultTableModel overviewTableModel;
    private JPanel statsPanel;


    public DashboardPanel(CardLayout mainLayout, JPanel mainPanel, Main mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setBackground(Main.BACKGROUND);

        // ==========================================
        // 1. TOP BAR (Brand on left, Role + Name on right)
        // ==========================================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Main.PRIMARY); // Dark civic blue
        topBar.setBorder(new EmptyBorder(12, 28, 12, 28));

        // LEFT: Branding (Logo + Bantay Baryo)
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandPanel.setOpaque(false);
        
        ImageIcon navLogo = mainApp.getLogoIcon(35, 35);
        JLabel brandLogo = new JLabel(navLogo);
        
        JLabel brand = new JLabel("Bantay Baryo");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        brandPanel.add(brandLogo);
        brandPanel.add(brand);

        // RIGHT: Role + Name side-by-side
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); 
        userInfoPanel.setOpaque(false);

        // Role (Left side of the right panel)
        dashboardRole = new JLabel("Resident");
        dashboardRole.setFont(new Font("SansSerif", Font.BOLD, 13));
        dashboardRole.setForeground(new Color(191, 219, 254)); // Soft light blue
        dashboardRole.setVerticalAlignment(SwingConstants.CENTER);

        // Name (Right side of the right panel)
        dashboardWelcome = new JLabel("Welcome, Maria");
        dashboardWelcome.setFont(new Font("SansSerif", Font.BOLD, 18));
        dashboardWelcome.setForeground(Color.WHITE);
        dashboardWelcome.setVerticalAlignment(SwingConstants.CENTER);

        userInfoPanel.add(dashboardRole);
        userInfoPanel.add(dashboardWelcome);

        topBar.add(brandPanel, BorderLayout.WEST);
        topBar.add(userInfoPanel, BorderLayout.EAST);

        // ==========================================
        // 2. SIDEBAR (Navigation + Logout at bottom)
        // ==========================================
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


        // --- Create Sidebar Buttons ---
        overviewNavigation = createSidebarButton("Dashboard");
        residentNavigation = createSidebarButton("File a Report");
        myReportsNavigation = createSidebarButton("My Reports");
        staffNavigation = createSidebarButton("Report Queue");
        profileNavigation = createSidebarButton("My Profile");
        adminNavigation = createSidebarButton("Manage Users");

        // --- Sidebar Button Actions ---
        overviewNavigation.addActionListener(e -> {
            activateSidebarButton(overviewNavigation);
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
        profileNavigation.addActionListener(e -> {
            activateSidebarButton(profileNavigation);
            dashboardContent.add(buildProfilePanel(), "profile");
            contentLayout.show(dashboardContent, "profile");
        });
        adminNavigation.addActionListener(e -> {
            activateSidebarButton(adminNavigation);
            contentLayout.show(dashboardContent, "admin");
        });

        // --- Add Buttons to Sidebar ---
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(profileNavigation);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(overviewNavigation);
        sidebar.add(Box.createVerticalStrut(10));
        
        // Swapped the order here!
        sidebar.add(myReportsNavigation); 
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(residentNavigation);
        
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(staffNavigation);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(adminNavigation);
        
        // Push the Logout button to the very bottom
        sidebar.add(Box.createVerticalGlue()); 
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setMaximumSize(new Dimension(175, 44));
        logoutBtn.setPreferredSize(new Dimension(175, 44));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // 👈 Centers the button
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Soft Red Styling
        logoutBtn.setBackground(new Color(254, 226, 226)); 
        logoutBtn.setForeground(new Color(220, 38, 38)); 
        logoutBtn.setOpaque(true);
        logoutBtn.setContentAreaFilled(true);
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(252, 165, 165), 1));
        
        logoutBtn.addActionListener(e -> {
            mainApp.setCurrentUser(null);
            mainLayout.show(mainPanel, "login");
        });
        sidebar.add(logoutBtn);

        activateSidebarButton(overviewNavigation);

        // --- Add Panels to CardLayout ---
        dashboardContent.add(buildOverviewPanel(), "overview");
        dashboardContent.add(buildResidentPanel(), "resident");
        dashboardContent.add(buildMyReportsPanel(), "myReports");
        dashboardContent.add(buildStaffPanel(), "staff");
        dashboardContent.add(buildAdminPanel(), "admin");
        // (We build the profile panel dynamically when the button is clicked)

        body.add(sidebar, BorderLayout.WEST);
        body.add(dashboardContent, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    // ==========================================
    // 3. UPDATE LOGIC (Called after login)
    // ==========================================
    public void updateDashboardForUser() {
        User currentUser = mainApp.getCurrentUser();
        if (currentUser == null) return;
        
        // Just the role (e.g., "Resident", "Barangay Staff")
        dashboardRole.setText(currentUser.getRoleType());
        
        // Just the full name (e.g., "Maria Dela Cruz")
        dashboardWelcome.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        
        // 1. Determine who is logged in
        boolean isResident = currentUser instanceof Resident;
        boolean isStaff = currentUser instanceof BarangayStaff;
        boolean isAdmin = currentUser.getRoleType().equals("Admin"); 

        // 2. Show/Hide the sidebar buttons based on their role
        overviewNavigation.setVisible(!isResident); // Hide for Residents
        residentNavigation.setVisible(isResident);
        myReportsNavigation.setVisible(isResident);
        
        staffNavigation.setVisible(isStaff || isAdmin); 
        adminNavigation.setVisible(isAdmin);            
        profileNavigation.setVisible(true);             

        // 3. Reset the view based on the role
        overviewStatusFilter = null;
        overviewTitle.setText(isResident ? "My Report Overview" : "Dashboard");
        
        if (isResident) {
            // Residents land on "My Reports" and see it active
            updateResidentReportList();
            activateSidebarButton(myReportsNavigation);
            contentLayout.show(dashboardContent, "myReports");
        } else {
            // Staff and Admin land on "Overview"
            activateSidebarButton(overviewNavigation);
            contentLayout.show(dashboardContent, "overview");
        }
        
        // 4. Refresh the sidebar and data
        residentNavigation.getParent().revalidate();
        residentNavigation.getParent().repaint();
        updateReportList();
        updateResidentReportList();
        updateOverviewText();

                // Rebuild the profile panel so it's ready for the new user
        dashboardContent.add(buildProfilePanel(), "profile");
    }

    // ==========================================
    // 4. PROFILE PANEL (Sectioned UI)
    // ==========================================
    private JPanel buildProfilePanel() {
        JPanel mainWrapper = new JPanel();
        mainWrapper.setLayout(new BoxLayout(mainWrapper, BoxLayout.Y_AXIS));
        mainWrapper.setBackground(Main.BACKGROUND);
        mainWrapper.setBorder(new EmptyBorder(30, 40, 30, 40));

        User user = mainApp.getCurrentUser();
        if (user == null) return mainWrapper;

        // 1. HEADER
        JLabel title = new JLabel("Your Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Main.PRIMARY);
        
        JLabel subtitle = new JLabel("Keep your personal and account information up to date.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Main.MUTED);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createVerticalStrut(20));

        // 2. PERSONAL INFORMATION SECTION
        JPanel personalCard = mainApp.createCard();
        personalCard.setLayout(new GridBagLayout());
        personalCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER),
                new EmptyBorder(25, 30, 25, 30)
        ));
        personalCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 15);

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel infoTitle = new JLabel("Personal Information");
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        infoTitle.setForeground(Main.TEXT);
        personalCard.add(infoTitle, gbc);

        // Row 1 Labels
        gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.gridx = 0; personalCard.add(createFieldLabel("First Name"), gbc);
        gbc.gridx = 1; personalCard.add(createFieldLabel("Middle Name (Optional)"), gbc);
        
        // Row 1 Fields
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 15, 15);
        JTextField firstNameField = createModernTextField(user.getFirstName());
        gbc.gridx = 0; personalCard.add(firstNameField, gbc);
        
        // Middle Name Optional Logic
        JTextField middleNameField;
        String mName = user.getMiddleName();
        if (mName == null || mName.isEmpty()) {
            middleNameField = createModernTextField("Optional");
            middleNameField.setForeground(Color.GRAY);
        } else {
            middleNameField = createModernTextField(mName);
        }
        gbc.gridx = 1; personalCard.add(middleNameField, gbc);

        // Row 2 Labels
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 5, 15);
        gbc.gridx = 0; personalCard.add(createFieldLabel("Last Name"), gbc);
        gbc.gridx = 1; personalCard.add(createFieldLabel("Contact Number"), gbc);

        // Row 2 Fields
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 15, 15);
        JTextField lastNameField = createModernTextField(user.getLastName());
        gbc.gridx = 0; personalCard.add(lastNameField, gbc);
        
        // Contact Number (Assuming you added getContactNumber/setContactNumber to User.java)
        JTextField contactField = createModernTextField(user.getContactNumber());
        gbc.gridx = 1; personalCard.add(contactField, gbc);

        // Row 3: Purok & Sitio (Only for Resident)
        JTextField purokField = null;
        JTextField sitioField = null;
        
        if (user instanceof Resident) {
            Resident res = (Resident) user;
            
            gbc.gridy = 5; gbc.insets = new Insets(0, 0, 5, 15);
            gbc.gridx = 0; personalCard.add(createFieldLabel("Purok"), gbc);
            gbc.gridx = 1; personalCard.add(createFieldLabel("Sitio (Optional)"), gbc);

            gbc.gridy = 6; gbc.insets = new Insets(0, 0, 20, 15);
            
            purokField = createModernTextField(res.getPurok());
            gbc.gridx = 0; personalCard.add(purokField, gbc);
            
            // Sitio Optional Logic
            String sName = res.getSitio();
            if (sName == null || sName.isEmpty()) {
                sitioField = createModernTextField("Optional");
                sitioField.setForeground(Color.GRAY);
            } else {
                sitioField = createModernTextField(sName);
            }
            gbc.gridx = 1; personalCard.add(sitioField, gbc);
        }

        // Save Button
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        
        JButton saveBtn = mainApp.createPrimaryButton("Save Changes");
        saveBtn.setPreferredSize(new Dimension(150, 40));
        personalCard.add(saveBtn, gbc);

        // --- SAVE LOGIC ---
        // We must declare final variables to use them inside the lambda
        JTextField finalPurok = purokField;
        JTextField finalSitio = sitioField;
        
        saveBtn.addActionListener(e -> {
            user.setFirstName(firstNameField.getText().trim());
            user.setLastName(lastNameField.getText().trim());
            user.setContactNumber(contactField.getText().trim());
            
            // Handle optional Middle Name
            String newMiddle = middleNameField.getText().trim();
            if (newMiddle.equals("Optional")) newMiddle = "";
            user.setMiddleName(newMiddle);
            
            // Handle Resident fields
            if (user instanceof Resident) {
                Resident res = (Resident) user;
                res.setPurok(finalPurok.getText().trim());
                
                // Handle optional Sitio
                String newSitio = finalSitio.getText().trim();
                if (newSitio.equals("Optional")) newSitio = "";
                res.setSitio(newSitio);
            }
            
            // Update the top bar text
            mainApp.updateDashboardForUser(); 
            
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // 3. ACCOUNT & SECURITY SECTION
        JPanel securityCard = mainApp.createCard();
        securityCard.setLayout(new GridBagLayout());
        securityCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER),
                new EmptyBorder(20, 25, 20, 25)
        ));
        securityCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.insets = new Insets(8, 8, 8, 8);
        sgbc.fill = GridBagConstraints.HORIZONTAL;
        sgbc.weightx = 1.0;
        sgbc.anchor = GridBagConstraints.WEST;

        sgbc.gridx = 0; sgbc.gridy = 0; sgbc.gridwidth = 2;
        JLabel secTitle = new JLabel("Account & Security");
        secTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        secTitle.setForeground(Main.TEXT);
        securityCard.add(secTitle, sgbc);

        // Read-only Info
        sgbc.gridx = 0; sgbc.gridy = 1; sgbc.gridwidth = 1;
        securityCard.add(new JLabel("Username:"), sgbc);
        sgbc.gridx = 1;
        JLabel userLabel = new JLabel(user.getUsername());
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        securityCard.add(userLabel, sgbc);

        sgbc.gridx = 0; sgbc.gridy = 2;
        securityCard.add(new JLabel("Role:"), sgbc);
        sgbc.gridx = 1;
        JLabel roleLabel = new JLabel(user.getRoleType());
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        securityCard.add(roleLabel, sgbc);

        // Password Fields
        sgbc.gridx = 0; sgbc.gridy = 3;
        securityCard.add(new JLabel("New Password:"), sgbc);
        sgbc.gridx = 1;
        JPasswordField newPassField = new JPasswordField();
        newPassField.setPreferredSize(new Dimension(250, 32));
        securityCard.add(newPassField, sgbc);

        sgbc.gridx = 0; sgbc.gridy = 4;
        securityCard.add(new JLabel("Confirm Password:"), sgbc);
        sgbc.gridx = 1;
        JPasswordField confirmPassField = new JPasswordField();
        confirmPassField.setPreferredSize(new Dimension(250, 32));
        securityCard.add(confirmPassField, sgbc);

        // Update Button
        sgbc.gridx = 1; sgbc.gridy = 5; sgbc.anchor = GridBagConstraints.EAST;
        sgbc.fill = GridBagConstraints.NONE;
        JButton updatePassBtn = mainApp.createSecondaryButton("Update Password");
        updatePassBtn.setPreferredSize(new Dimension(180, 36));
        updatePassBtn.addActionListener(e -> {
            String newPass = new String(newPassField.getPassword());
            String confirmPass = new String(confirmPassField.getPassword());
            if (newPass.isEmpty() || !newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match or are empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            user.setPassword(newPass);
            newPassField.setText("");
            confirmPassField.setText("");
            JOptionPane.showMessageDialog(this, "Password updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        securityCard.add(updatePassBtn, sgbc);


        // 4. ASSEMBLE EVERYTHING (Stacked vertically)
        mainWrapper.add(headerPanel);
        mainWrapper.add(personalCard);
        mainWrapper.add(Box.createVerticalStrut(20)); // Space between sections
        mainWrapper.add(securityCard);

        // Wrap it in a ScrollPane so it doesn't cut off on small screens
        JScrollPane scrollPane = new JScrollPane(mainWrapper);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBackground(Main.BACKGROUND);

        // Return a panel that holds the scroll pane
        JPanel finalPanel = new JPanel(new BorderLayout());
        finalPanel.setBackground(Main.BACKGROUND);
        finalPanel.add(scrollPane, BorderLayout.CENTER);
        
        return finalPanel;
    }

    // ==========================================
    // 5. ADMIN PANEL (User Management)
    // ==========================================
    private JPanel buildAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("User Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Main.TEXT);
        headerPanel.add(title, BorderLayout.WEST);

        JButton registerBtn = mainApp.createPrimaryButton("+ Register New User");
            registerBtn.addActionListener(e -> showRegisterUserModal());
            registerBtn.setPreferredSize(new Dimension(200, 40));
            headerPanel.add(registerBtn, BorderLayout.EAST);
            panel.add(headerPanel, BorderLayout.NORTH);

        // 2. TABLE CONFIGURATION
        String[] columns = {"USER ID", "FULL NAME", "ROLE", "STATUS", "ACTIONS"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(55);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);

        // --- CUSTOM HEADER RENDERER ---
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setBorder(null);
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(248, 250, 252));
                label.setForeground(new Color(71, 85, 105));
                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 15, 0, 15));
                return label;
            }
        });

        // --- CUSTOM CELL RENDERER ---
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 15, 0, 15)); 
                label.setFont(new Font("SansSerif", Font.PLAIN, 14));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                
                if (isSelected) {
                    label.setBackground(new Color(241, 245, 249));
                } else {
                    label.setBackground(Color.WHITE);
                }

                if (column == 3) { // STATUS COLUMN
                    String status = value != null ? value.toString() : "";
                    
                    JPanel pillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    pillPanel.setBackground(isSelected ? new Color(241, 245, 249) : Color.WHITE);
                    
                    JLabel pill = new JLabel(status, SwingConstants.CENTER);
                    pill.setOpaque(true);
                    pill.setFont(new Font("SansSerif", Font.BOLD, 11));
                    pill.setBorder(new EmptyBorder(6, 12, 6, 12));
                    
                    if (status.equals("ACTIVE")) {
                        pill.setBackground(new Color(209, 250, 229));
                        pill.setForeground(new Color(4, 120, 87));   
                    } else {
                        pill.setBackground(new Color(241, 245, 249));
                        pill.setForeground(new Color(100, 116, 139));   
                    }
                    
                    pill.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(pill.getBackground(), 4, true),
                            new EmptyBorder(1, 7, 1, 7)
                    ));
                    
                    pillPanel.add(pill);
                    return pillPanel;
                    
                } else if (column == 4) { // ACTIONS COLUMN
                    String status = (String) table.getValueAt(row, 3);
                    if ("ACTIVE".equals(status)) {
                        label.setText("Deactivate Account");
                        label.setForeground(new Color(220, 38, 38));
                    } else {
                        label.setText("Activated");
                        label.setForeground(Main.MUTED);
                    }
                    label.setFont(new Font("SansSerif", Font.BOLD, 13));
                } else {
                    label.setForeground(Main.TEXT);
                }
                
                return label;
            }
        });

        // --- MOUSE LISTENER (Click to Deactivate) ---
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                
                if (row != -1 && col == 4) {
                    String userId = (String) table.getValueAt(row, 0);
                    User selectedUser = null;
                    
                    // Get users from Main
                    for (User u : mainApp.getUsers()) {
                        if (u.getId().equals(userId)) {
                            selectedUser = u;
                            break;
                        }
                    }
                    
                    if (selectedUser != null && "ACTIVE".equals(selectedUser.getStatus())) {
                        int confirm = JOptionPane.showConfirmDialog(null, 
                            "Are you sure you want to deactivate " + selectedUser.getFullName() + "?", 
                            "Confirm Deactivation", JOptionPane.YES_NO_OPTION);
                            
                        if (confirm == JOptionPane.YES_OPTION) {
                            selectedUser.setStatus("INACTIVE");
                            
                            // 👇 YOUR GROUPMATE WILL ADD THE FILE SAVING HERE LATER 👇
                            // mainApp.getUserManager().saveUsers();
                            
                            refreshAdminTable(table);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        panel.add(tableCard, BorderLayout.CENTER);

        JLabel footer = new JLabel("Soft deactivation preserves historical report references.");
        footer.setForeground(Main.MUTED);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(footer, BorderLayout.SOUTH);

        refreshAdminTable(table);
        return panel;
    }

    private void refreshAdminTable(JTable table) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Just load from Main's ArrayList for now
        for (User u : mainApp.getUsers()) {
            model.addRow(new Object[]{
                u.getId(),
                u.getFullName(),
                u.getRoleType(),
                u.getStatus(),
                "" 
            });
        }
    }

    // ==========================================
    // 6. OVERVIEW PANEL (Dashboard)
    // ==========================================
    private JPanel buildOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. STAT CARDS (Top Row)
        statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setOpaque(false);
        
        pendingValue = createStatCard("Pending", Main.PENDING);
        progressValue = createStatCard("In Progress", Main.PROGRESS);
        resolvedValue = createStatCard("Resolved", Main.RESOLVED);
        totalValue = createStatCard("Total Reports", new Color(71, 85, 105));

        statsPanel.add(pendingValue.getParent());
        statsPanel.add(progressValue.getParent());
        statsPanel.add(resolvedValue.getParent());
        statsPanel.add(totalValue.getParent());

        // Make cards clickable
        makeOverviewCardClickable(pendingValue.getParent(), "PENDING");
        makeOverviewCardClickable(progressValue.getParent(), "IN_PROGRESS");
        makeOverviewCardClickable(resolvedValue.getParent(), "RESOLVED");
        makeOverviewCardClickable(totalValue.getParent(), null);
        
        panel.add(statsPanel, BorderLayout.NORTH);

        // 2. MAIN CONTENT CARD (Header + Table + Footer)
        JPanel mainCard = new JPanel(new BorderLayout(0, 15));
        mainCard.setBackground(Color.WHITE);
        mainCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));

        // --- HEADER AREA (Title, Active Filter, Hint) ---
        JPanel headerArea = new JPanel(new BorderLayout());
        headerArea.setOpaque(false);
        headerArea.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        
        overviewTitle = new JLabel("Dashboard");
        overviewTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        overviewTitle.setForeground(Main.TEXT);
        
        summaryLabel = new JLabel("Showing: All Reports"); // 👈 This is the new active filter text
        summaryLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        summaryLabel.setForeground(Main.MUTED); 
        
        titlePanel.add(overviewTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(summaryLabel);

        // The hint on the right side
        JLabel hint = new JLabel("Double-click a report to view complete details.");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 13));
        hint.setForeground(Main.MUTED);
        hint.setVerticalAlignment(SwingConstants.BOTTOM);

        headerArea.add(titlePanel, BorderLayout.WEST);
        headerArea.add(hint, BorderLayout.EAST);
        mainCard.add(headerArea, BorderLayout.NORTH);

        // Table (Using the same modern table style as My Reports)
        String[] columns = {"REPORT ID", "TYPE", "CATEGORY", "STATUS"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);

        // Custom Header Renderer
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(null);
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Color.WHITE);
                label.setForeground(new Color(71, 85, 105));
                label.setFont(new Font("SansSerif", Font.BOLD, 11));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 10, 0, 10));
                return label;
            }
        });

        // Custom Cell Renderer (Pills)
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 10, 0, 10)); 
                label.setFont(new Font("SansSerif", Font.PLAIN, 13));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                
                if (isSelected) {
                    label.setBackground(new Color(248, 250, 252));
                } else {
                    label.setBackground(Color.WHITE);
                }

                if (column == 3) { // STATUS COLUMN
                    String status = value != null ? value.toString() : "";
                    
                    JPanel pillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    pillPanel.setBackground(isSelected ? new Color(248, 250, 252) : Color.WHITE);
                    
                    JLabel pill = new JLabel(status, SwingConstants.CENTER);
                    pill.setOpaque(true);
                    pill.setFont(new Font("SansSerif", Font.BOLD, 11));
                    pill.setBorder(new EmptyBorder(5, 10, 5, 10));
                    
                    if (status.equals("PENDING")) {
                        pill.setBackground(new Color(254, 243, 199)); pill.setForeground(new Color(180, 83, 9));
                    } else if (status.equals("IN PROGRESS")) {
                        pill.setBackground(new Color(219, 234, 254)); pill.setForeground(new Color(30, 64, 175));
                    } else if (status.equals("RESOLVED")) {
                        pill.setBackground(new Color(209, 250, 229)); pill.setForeground(new Color(4, 120, 87));
                    }
                    
                    pill.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(pill.getBackground(), 4, true),
                            new EmptyBorder(1, 6, 1, 6)
                    ));
                    
                    pillPanel.add(pill);
                    return pillPanel;
                }
                
                return label;
            }
        });

        // Double-click to view
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String reportId = (String) table.getValueAt(table.getSelectedRow(), 0);
                    for (Report report : mainApp.getReports()) {
                        if (report.getReportId().equals(reportId)) {
                            showReportDetails(report);
                            break;
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainCard.add(scrollPane, BorderLayout.CENTER);


        panel.add(mainCard, BorderLayout.CENTER);

        // Keep references to the table model so we can update it
        overviewReportListModel = null; // Not used anymore
        overviewTableModel = model;

        // Set default active card (Total Reports)
        updateActiveStatCard();

        return panel;
    }

        private void updateActiveStatCard() {
        if (statsPanel == null) return;
        
        // Map the filter status to the corresponding card
        Component[] cards = statsPanel.getComponents();
        String[] statuses = {"PENDING", "IN_PROGRESS", "RESOLVED", null};
        
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] instanceof JPanel) {
                JPanel card = (JPanel) cards[i];
                
                // Check if this card is the active one
                if ((overviewStatusFilter == null && statuses[i] == null) || 
                    (overviewStatusFilter != null && overviewStatusFilter.equals(statuses[i]))) {
                    
                    // ACTIVE STATE: Soft blue background and blue border
                    card.setBackground(new Color(239, 246, 255));
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Main.SECONDARY, 2), // Blue border
                            new EmptyBorder(19, 24, 19, 24) // Slightly less padding to fit the thicker border
                    ));
                } else {
                    // INACTIVE STATE: White background, grey border
                    card.setBackground(Color.WHITE);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                            new EmptyBorder(20, 25, 20, 25)
                    ));
                }
            }
        }
    }

    // ==========================================
    // 7. RESIDENT SUBMIT REPORT PANEL (Matches Profile Style)
    // ==========================================
    private JPanel buildResidentPanel() {
        JPanel mainWrapper = new JPanel();
        mainWrapper.setLayout(new BoxLayout(mainWrapper, BoxLayout.Y_AXIS));
        mainWrapper.setBackground(Main.BACKGROUND);
        mainWrapper.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. HEADER
        JLabel title = new JLabel("File a Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Main.PRIMARY);
        
        JLabel subtitle = new JLabel("Submit an incident or complaint to the barangay.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Main.MUTED);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createVerticalStrut(20));

        // 2. FORM CARD
        JPanel card = mainApp.createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(750, 750));

        // --- REPORT TYPE ---
        card.add(createFieldLabel("Report Type"));
        
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        typePanel.setOpaque(false);
        typePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JRadioButton rdoIncident = createStyledRadio("Incident Report", true);
        JRadioButton rdoComplaint = createStyledRadio("Complaint Report", false);
        ButtonGroup bg = new ButtonGroup();

        bg.add(rdoIncident);
        bg.add(rdoComplaint);

        typePanel.add(rdoIncident);
        typePanel.add(Box.createHorizontalStrut(15));
        typePanel.add(rdoComplaint);
        
        card.add(typePanel);
        card.add(Box.createVerticalStrut(15));

        // --- CATEGORY ---
        card.add(createFieldLabel("Category"));
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{
                "Noise Disturbance", "Sanitation", "Infrastructure", 
                "Boundary Dispute", "Public Safety", "Others"
        });
        categoryBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        categoryBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(categoryBox);
        card.add(Box.createVerticalStrut(15));

        // --- DYNAMIC FIELD ---
        JLabel dynamicLabel = createFieldLabel("Incident Date & Time");
        card.add(dynamicLabel);

        JPanel dynamicPanel = new JPanel(new CardLayout());
        dynamicPanel.setOpaque(false);
        dynamicPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dynamicPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JTextField dateField = createModernTextField("mm/dd/yyyy --:-- --");
        JTextField respondentField = createModernTextField("e.g., neighbor or store name");

        dynamicPanel.add(dateField, "INCIDENT");
        dynamicPanel.add(respondentField, "COMPLAINT");
        card.add(dynamicPanel);
        card.add(Box.createVerticalStrut(15));

        // --- PERSONS INVOLVED ---
        JLabel personsLabel = createFieldLabel("Persons Involved");
        card.add(personsLabel);
        JTextField personsField = createModernTextField("e.g., 2 unknown males");
        card.add(personsField);
        card.add(Box.createVerticalStrut(15));

        // --- LOCATION ---
        card.add(createFieldLabel("Location Description"));
        JTextField locationField = createModernTextField("e.g., Near Purok 3 Chapel");
        card.add(locationField);
        card.add(Box.createVerticalStrut(15));

        // --- DESCRIPTION ---
        card.add(createFieldLabel("Detailed Description"));
        JTextArea descArea = new JTextArea(5, 20);
        descArea.setText("Describe the incident/complaint in detail...");
        descArea.setForeground(Color.GRAY);
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        descScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.add(descScroll);
        card.add(Box.createVerticalStrut(25));

        // --- SUBMIT BUTTON (Centered) ---
        JButton submitBtn = mainApp.createPrimaryButton("Submit");
        submitBtn.setPreferredSize(new Dimension(180, 45));
        submitBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        // Create a wrapper panel to center the button
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.setAlignmentX(Component.LEFT_ALIGNMENT); // Keep the wrapper full-width
        buttonWrapper.add(submitBtn);
        
        // Add the wrapper to the card instead of the button directly
        card.add(buttonWrapper);

        submitBtn.addActionListener(e -> {
            // Pass the incident radio button to check if it's selected
            submitReport(rdoIncident, categoryBox, locationField, personsField, descArea);
        });
        // --- LOGIC FOR SWAPPING FIELDS ---
        CardLayout cl = (CardLayout) dynamicPanel.getLayout();
        rdoIncident.addActionListener(e -> {
            dynamicLabel.setText("Incident Date & Time");
            cl.show(dynamicPanel, "INCIDENT");
            personsLabel.setVisible(true);
            personsField.setVisible(true);
        });
        
        rdoComplaint.addActionListener(e -> {
            dynamicLabel.setText("Respondent Name / Entity");
            cl.show(dynamicPanel, "COMPLAINT");
            personsLabel.setVisible(false);
            personsField.setVisible(false);
        });

        // 3. ASSEMBLE
        mainWrapper.add(headerPanel);
        mainWrapper.add(card);

        JScrollPane scrollPane = new JScrollPane(mainWrapper);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Main.BACKGROUND);
        scrollPane.getViewport().setBackground(Main.BACKGROUND);

        JPanel finalPanel = new JPanel(new BorderLayout());
        finalPanel.setBackground(Main.BACKGROUND);
        finalPanel.add(scrollPane, BorderLayout.CENTER);
        
        return finalPanel;
    }

    // --- HELPER METHODS ---

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(Main.TEXT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(0, 45)); // 👈 THIS FORCES THE HEIGHT
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setAlignmentX(Component.LEFT_ALIGNMENT); 

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                new EmptyBorder(0, 12, 0, 12)
        ));
        return field;
    }

    private JRadioButton createStyledRadio(String text, boolean selected) {
        JRadioButton rb = new JRadioButton(text, selected);
        rb.setOpaque(true);
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                new EmptyBorder(8, 15, 8, 15)
        ));
        return rb;
    }


    // ==========================================
    // 8. STAFF REPORT QUEUE PANEL
    // ==========================================
    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. HEADER & ACTIONS
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        
        JLabel title = new JLabel("Report Queue");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Main.TEXT);
        
        JLabel subtitle = new JLabel("Manage and update the status of community reports.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Main.MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        // Action Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        
        JButton progressBtn = mainApp.createSecondaryButton("Mark In Progress");
        progressBtn.setPreferredSize(new Dimension(160, 40));
        
        JButton resolvedBtn = mainApp.createPrimaryButton("Mark Resolved");
        resolvedBtn.setPreferredSize(new Dimension(140, 40));
        
        actions.add(progressBtn);
        actions.add(resolvedBtn);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(actions, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // 2. TABLE CONFIGURATION
        String[] columns = {"REPORT ID", "REPORTER", "TYPE", "CATEGORY", "DATE SUBMITTED", "STATUS"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);

        // --- CUSTOM HEADER RENDERER ---
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setBorder(null);
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(248, 250, 252));
                label.setForeground(new Color(71, 85, 105));
                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 15, 0, 15));
                return label;
            }
        });

        // --- CUSTOM CELL RENDERER (Status Pills) ---
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 15, 0, 15)); 
                label.setFont(new Font("SansSerif", Font.PLAIN, 14));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                
                if (isSelected) {
                    label.setBackground(new Color(241, 245, 249));
                } else {
                    label.setBackground(Color.WHITE);
                }

                if (column == 5) { // STATUS COLUMN
                    String status = value != null ? value.toString() : "";
                    
                    JPanel pillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    pillPanel.setBackground(isSelected ? new Color(241, 245, 249) : Color.WHITE);
                    
                    JLabel pill = new JLabel(status, SwingConstants.CENTER);
                    pill.setOpaque(true);
                    pill.setFont(new Font("SansSerif", Font.BOLD, 12));
                    pill.setBorder(new EmptyBorder(6, 12, 6, 12));
                    
                    if (status.equals("PENDING")) {
                        pill.setBackground(new Color(254, 243, 199)); 
                        pill.setForeground(new Color(180, 83, 9));   
                    } else if (status.equals("IN PROGRESS")) {
                        pill.setBackground(new Color(219, 234, 254)); 
                        pill.setForeground(new Color(30, 64, 175));  
                    } else if (status.equals("RESOLVED")) {
                        pill.setBackground(new Color(209, 250, 229)); 
                        pill.setForeground(new Color(4, 120, 87));   
                    }
                    
                    pill.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(pill.getBackground(), 5, true),
                            new EmptyBorder(1, 7, 1, 7)
                    ));
                    
                    pillPanel.add(pill);
                    return pillPanel;
                }
                
                return label;
            }
        });

        // --- MOUSE LISTENER (Double click to view) ---
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String reportId = (String) table.getValueAt(row, 0);
                        for (Report report : mainApp.getReports()) {
                            if (report.getReportId().equals(reportId)) {
                                showReportDetails(report);
                                break;
                            }
                        }
                    }
                }
            }
        });

        // Wrap in ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        panel.add(tableCard, BorderLayout.CENTER);

        // --- ACTION BUTTON LOGIC ---
        progressBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a report first.", "No Report Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String reportId = (String) table.getValueAt(row, 0);
            updateSelectedReportStatus(reportId, "IN_PROGRESS");
            refreshStaffTable(table);
        });

        resolvedBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a report first.", "No Report Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String reportId = (String) table.getValueAt(row, 0);
            updateSelectedReportStatus(reportId, "RESOLVED");
            refreshStaffTable(table);
        });

        // Populate table initially
        refreshStaffTable(table);

        return panel;
    }

    // Helper method to refresh the staff table data
    private void refreshStaffTable(JTable table) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Report report : mainApp.getReports()) {
            model.addRow(new Object[]{
                report.getReportId(),
                report.getReporterId(), // Or reporter name if you have it
                report.getReportType(),
                report.getCategory(),
                report.getDateSubmitted(),
                report.getStatus().replace('_', ' ')
            });
        }
    }

    // Helper method to update the status by Report ID
    private void updateSelectedReportStatus(String reportId, String newStatus) {
        for (Report report : mainApp.getReports()) {
            if (report.getReportId().equals(reportId)) {
                String currentStatus = report.getStatus();
                
                if (currentStatus.equals(newStatus)) {
                    JOptionPane.showMessageDialog(this, "This report is already marked as " + newStatus.replace('_', ' ') + ".", "No Change", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (currentStatus.equals("RESOLVED") && !newStatus.equals("RESOLVED")) {
                    JOptionPane.showMessageDialog(this, "Cannot change a resolved report.", "Not Allowed", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                report.setStatus(newStatus);
                updateOverviewText();
                updateResidentReportList();
                JOptionPane.showMessageDialog(this, "Report " + reportId + " updated to " + newStatus.replace('_', ' ') + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateActiveStatCard();
                return;
            }
        }
    }

    // ==========================================
    // 9. MY REPORTS PANEL (Resident only)
    // ==========================================
    private JPanel buildMyReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Main.BACKGROUND);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 1. HEADER
        JLabel title = new JLabel("My Reports");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Main.TEXT);
        
        JLabel subtitle = new JLabel("Click any row to view details and action history.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Main.MUTED);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitle);
        panel.add(headerPanel, BorderLayout.NORTH);

        // 2. TABLE CONFIGURATION
        String[] columns = {"REPORT ID", "TYPE", "CATEGORY", "DATE SUBMITTED", "STATUS", "ACTIONS"};
        residentReportTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(residentReportTableModel);
        table.setRowHeight(50); // Taller rows
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249)); // Very faint line between rows
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);

        // --- CUSTOM HEADER RENDERER ---
        // This makes the header look like the HTML version (flat, grey, no borders)
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setBorder(null);
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(248, 250, 252)); // Soft grey background
                label.setForeground(new Color(71, 85, 105));   // Dark grey text
                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 15, 0, 15)); // Padding
                return label;
            }
        });

        // --- CUSTOM CELL RENDERER (The Magic) ---
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 15, 0, 15)); 
                label.setFont(new Font("SansSerif", Font.PLAIN, 14));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                
                if (isSelected) {
                    label.setBackground(new Color(241, 245, 249));
                } else {
                    label.setBackground(Color.WHITE);
                }

                if (column == 4) { // STATUS COLUMN
                    String status = value != null ? value.toString() : "";
                    
                    // To make a "pill", we actually return a JPanel containing a JLabel
                    JPanel pillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    pillPanel.setBackground(isSelected ? new Color(241, 245, 249) : Color.WHITE);
                    
                    JLabel pill = new JLabel(status, SwingConstants.CENTER);
                    pill.setOpaque(true);
                    pill.setFont(new Font("SansSerif", Font.BOLD, 12));
                    pill.setBorder(new EmptyBorder(6, 12, 6, 12)); // Padding inside the pill
                    
                    if (status.equals("PENDING")) {
                        pill.setBackground(new Color(254, 243, 199)); // Soft yellow
                        pill.setForeground(new Color(180, 83, 9));   
                    } else if (status.equals("IN PROGRESS")) {
                        pill.setBackground(new Color(219, 234, 254)); // Soft blue
                        pill.setForeground(new Color(30, 64, 175));  
                    } else if (status.equals("RESOLVED")) {
                        pill.setBackground(new Color(209, 250, 229)); // Soft green
                        pill.setForeground(new Color(4, 120, 87));   
                    }
                    
                    // Since JLabel is square, we use a trick: a rounded border
                    pill.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(pill.getBackground(), 5, true),
                            new EmptyBorder(1, 7, 1, 7)
                    ));
                    
                    pillPanel.add(pill);
                    return pillPanel;
                    
                } else if (column == 5) { // ACTIONS COLUMN
                    label.setForeground(new Color(2, 132, 199));
                    label.setFont(new Font("SansSerif", Font.BOLD, 14));
                } else {
                    label.setForeground(Main.TEXT);
                }
                
                return label;
            }
        });

        // Mouse Listener for Action
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String reportId = (String) table.getValueAt(row, 0);
                    for (Report report : mainApp.getReports()) {
                        if (report.getReportId().equals(reportId)) {
                            showReportDetails(report);
                            break;
                        }
                    }
                }
            }
        });

        // Wrap in a styled ScrollPane with a rounded card look
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Add some padding around the table inside the card
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        panel.add(tableCard, BorderLayout.CENTER);
        
        return panel;
    }

    // ==========================================
    // 10. HELPER METHODS
    // ==========================================


    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(175, 44));
        button.setPreferredSize(new Dimension(175, 44));
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // 👈 THIS FIXES THE LEFT SPACING!
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

     // ==========================================
    // 11. REGISTER NEW USER MODAL
    // ==========================================
    private void showRegisterUserModal() {
        JDialog dialog = new JDialog(mainApp, "Register New User", true);
        dialog.setSize(850, 600); // Slightly taller to fit everything comfortably
        dialog.setLocationRelativeTo(mainApp);
        dialog.setUndecorated(true); 

        // Main Wrapper (Clean White Card)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(15, 23, 42), 1), 
                new EmptyBorder(30, 40, 30, 40)
        ));

        // 1. HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Register New User");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Main.TEXT);

        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        closeBtn.setForeground(Main.MUTED);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dialog.dispose());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(closeBtn, BorderLayout.EAST);

        // 2. FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Row 1: First Name & Middle Name ---
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 5, 20); // 5px below label
        
        gbc.gridx = 0; formPanel.add(createFieldLabel("First Name"), gbc);
        gbc.gridx = 1; formPanel.add(createFieldLabel("Middle Name(Optional)"), gbc);

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 20, 20); // 20px below field
        
        JTextField firstNameField = createModernTextField("");
        gbc.gridx = 0; formPanel.add(firstNameField, gbc);
        
        JTextField middleNameField = createModernTextField("");
        gbc.gridx = 1; formPanel.add(middleNameField, gbc);

        // --- Row 2: Last Name (Full Width) ---
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(createFieldLabel("Last Name"), gbc);

        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 20, 0);
        JTextField lastNameField = createModernTextField("");
        formPanel.add(lastNameField, gbc);

        // --- Row 3: Username & Contact ---
        gbc.gridy = 4; gbc.gridwidth = 1; gbc.insets = new Insets(0, 0, 5, 20);
        gbc.gridx = 0; formPanel.add(createFieldLabel("Username"), gbc);
        gbc.gridx = 1; formPanel.add(createFieldLabel("Contact"), gbc);

        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 20, 20);
        JTextField usernameField = createModernTextField("");
        gbc.gridx = 0; formPanel.add(usernameField, gbc);
        
        JTextField contactField = createModernTextField("");
        gbc.gridx = 1; formPanel.add(contactField, gbc);

        // --- Row 4: Role & Password ---
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 5, 20);
        gbc.gridx = 0; formPanel.add(createFieldLabel("Role"), gbc);
        gbc.gridx = 1; formPanel.add(createFieldLabel("Password"), gbc);

        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 0, 20);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Resident", "Barangay Staff", "Admin"});
        roleCombo.setPreferredSize(new Dimension(0, 45)); // Make it as tall as the text fields
        roleCombo.setBackground(Color.WHITE);
        gbc.gridx = 0; formPanel.add(roleCombo, gbc);
        
        JTextField passwordField = createModernTextField("pass");
        gbc.gridx = 1; formPanel.add(passwordField, gbc);

        // 3. SUBMIT BUTTON
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 20px padding top
        
        JButton submitBtn = mainApp.createPrimaryButton("Create User");
        submitBtn.setPreferredSize(new Dimension(0, 50));
        submitBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        buttonPanel.add(submitBtn, BorderLayout.CENTER);

        // 4. ASSEMBLE
        wrapper.add(headerPanel, BorderLayout.NORTH);
        wrapper.add(formPanel, BorderLayout.CENTER);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(wrapper);
        dialog.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 15, 15));

        // 5. CREATE LOGIC
        submitBtn.addActionListener(e -> {
            String fName = firstNameField.getText().trim();
            String mName = middleNameField.getText().trim();
            String lName = lastNameField.getText().trim();
            String uname = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();

            if (fName.isEmpty() || lName.isEmpty() || uname.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newId = "U" + System.currentTimeMillis();
            
            User newUser = null;
            if ("Resident".equals(role)) {
                newUser = new Resident(newId, fName, mName, lName, uname, pass, "Purok 1", "Sitio Central");
            } else if ("Barangay Staff".equals(role)) {
                newUser = new BarangayStaff(newId, fName, mName, lName, uname, pass, "Community Officer");
            } else if ("Admin".equals(role)) {
                newUser = new Admin(newId, fName, mName, lName, uname, pass, "Barangay Captain");
            }

            if (newUser != null) {
                mainApp.getUsers().add(newUser);
                
                dashboardContent.add(buildAdminPanel(), "admin");
                contentLayout.show(dashboardContent, "admin");
                
                JOptionPane.showMessageDialog(dialog, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
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

    private void updateReportList() {
        if (reportListModel == null) return;
        reportListModel.clear();
        for (Report report : mainApp.getReports()) {
            reportListModel.addElement(report.getReportId() + "  |  " + report.getReportType()
                    + "  |  " + report.getCategory() + "  |  " + report.getStatus());
        }
    }

    private void updateResidentReportList() {
        if (residentReportTableModel == null) return;
        
        residentReportTableModel.setRowCount(0); // Clear existing rows
        for (Report report : getVisibleReports()) {
            residentReportTableModel.addRow(new Object[]{
                report.getReportId(),
                report.getReportType(),
                report.getCategory(),
                report.getDateSubmitted(),
                report.getStatus().replace('_', ' '),
                "View →"
            });
        }
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
        
        // Update the stat cards
        if (pendingValue != null) pendingValue.setText(String.valueOf(pending));
        if (progressValue != null) progressValue.setText(String.valueOf(progress));
        if (resolvedValue != null) resolvedValue.setText(String.valueOf(resolved));
        if (totalValue != null) totalValue.setText(String.valueOf(visibleReports.size()));
        
        // Update the Header Filter text
        if (summaryLabel != null) {
            if (overviewStatusFilter == null) {
                summaryLabel.setText("Showing: All Reports");
            } else {
                // e.g. "Showing: IN PROGRESS"
                summaryLabel.setText("Showing: " + overviewStatusFilter.replace('_', ' '));
            }
        }
        
        // Update the table
        if (overviewTableModel != null) {
            overviewTableModel.setRowCount(0);
            for (Report report : getOverviewReports()) {
                overviewTableModel.addRow(new Object[]{
                    report.getReportId(),
                    report.getReportType(),
                    report.getCategory(),
                    report.getStatus().replace('_', ' ')
                });
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
                updateActiveStatCard(); // 👈 New method to highlight the active card
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                // Only highlight if it's NOT the active card
                if (overviewStatusFilter != status) {
                    card.setBackground(new Color(248, 250, 252)); // Very soft grey on hover
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                if (overviewStatusFilter != status) {
                    card.setBackground(Color.WHITE); // Revert to white
                }
            }
        });
    }

    private JLabel createStatCard(String label, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel value = new JLabel("0");
        value.setName(label);
        value.setFont(new Font("SansSerif", Font.BOLD, 32)); // Large bold number
        value.setForeground(color);
        
        JLabel caption = new JLabel(label);
        caption.setFont(new Font("SansSerif", Font.BOLD, 14));
        caption.setForeground(new Color(100, 116, 139));
        
        card.add(value, BorderLayout.NORTH);
        card.add(caption, BorderLayout.SOUTH);
        
        // Return the value label so we can update it, but keep the card as parent
        return value; 
    }

    private void showReportDetails(Report report) {
        if (report == null) return;
        
        JDialog dialog = new JDialog(mainApp, "Report Details", true);
        dialog.setSize(750, 550);
        dialog.setLocationRelativeTo(mainApp);
        dialog.setUndecorated(true); 

        // Main Wrapper (Clean White Card with thin border)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(15, 23, 42), 1), 
                new EmptyBorder(25, 30, 25, 30)
        ));

        // 1. HEADER (Title + Close Button)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel(report.getReportId() + " • " + report.getReportType());
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Main.TEXT);

        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        closeBtn.setForeground(Main.MUTED);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dialog.dispose());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(closeBtn, BorderLayout.EAST);

        // 2. ALL CONTENT
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 10, 0);

        // --- METADATA (Two Columns) ---
        JPanel metaPanel = new JPanel(new GridLayout(3, 4, 10, 15));
        metaPanel.setOpaque(false);
        
        metaPanel.add(createMetaLabel("Category:"));
        metaPanel.add(createMetaValue(report.getCategory()));
        
        // Status Pill
        JPanel statusWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusWrapper.setOpaque(false);
        JLabel statusPill = new JLabel(report.getStatus().replace('_', ' '), SwingConstants.CENTER);
        statusPill.setOpaque(true);
        statusPill.setFont(new Font("SansSerif", Font.BOLD, 11));
        statusPill.setBorder(new EmptyBorder(4, 10, 4, 10));
        if ("PENDING".equals(report.getStatus())) {
            statusPill.setBackground(new Color(254, 243, 199)); statusPill.setForeground(new Color(180, 83, 9));
        } else if ("IN_PROGRESS".equals(report.getStatus())) {
            statusPill.setBackground(new Color(219, 234, 254)); statusPill.setForeground(new Color(30, 64, 175));
        } else {
            statusPill.setBackground(new Color(209, 250, 229)); statusPill.setForeground(new Color(4, 120, 87));
        }
        statusWrapper.add(statusPill);
        
        metaPanel.add(createMetaLabel("Status:"));
        metaPanel.add(statusWrapper);

        metaPanel.add(createMetaLabel("Location:"));
        metaPanel.add(createMetaValue(report.getLocation()));
        
        metaPanel.add(createMetaLabel("Reporter:"));
        metaPanel.add(createMetaValue(report.getReporterId()));

        metaPanel.add(createMetaLabel("Date:"));
        metaPanel.add(createMetaValue(report.getDateSubmitted()));

        metaPanel.add(createMetaLabel("Involved:"));
        String involved = "N/A";
        if (report instanceof IncidentReport) involved = ((IncidentReport) report).getPersonsInvolved();
        else if (report instanceof ComplaintReport) involved = ((ComplaintReport) report).getRespondentName();
        JLabel involvedLabel = new JLabel("<html><div style='width:150px'>" + involved + "</div></html>");
        involvedLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        involvedLabel.setForeground(Main.TEXT);
        metaPanel.add(involvedLabel);
        
        contentPanel.add(metaPanel, gbc);

        // --- DESCRIPTION ---
        gbc.gridy = 1;
        JLabel descLabel = createMetaLabel("DESCRIPTION");
        descLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        contentPanel.add(descLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0);
        gbc.fill = GridBagConstraints.BOTH; // Allow it to stretch both ways
        gbc.weighty = 1.0; // Give it extra vertical space if available
        
        JTextArea descText = new JTextArea(report.getDescription());
        descText.setEditable(false);
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setFont(new Font("SansSerif", Font.PLAIN, 15));
        descText.setForeground(Main.TEXT);
        descText.setBackground(new Color(248, 250, 252));
        descText.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Use a normal JScrollPane, but we style it so it doesn't look ugly
        JScrollPane descScroll = new JScrollPane(descText);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        descScroll.getVerticalScrollBar().setUnitIncrement(16);
        descScroll.setPreferredSize(new Dimension(0, 120)); // Force a clean height
        
        contentPanel.add(descScroll, gbc);

        // --- ACTION HISTORY ---
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0; // Stop stretching here
        JLabel historyTitle = new JLabel("Action History Trail");
        historyTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        historyTitle.setForeground(Main.TEXT);
        contentPanel.add(historyTitle, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel historyEntry = new JLabel("<html><span style='color:#64748B; font-family:monospace;'>Oct 5, 2026, 08:00 AM</span> &nbsp; Initial assessment done.</html>");
        historyEntry.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentPanel.add(historyEntry, gbc);

        // 3. ASSEMBLE
        wrapper.add(headerPanel, BorderLayout.NORTH);
        wrapper.add(contentPanel, BorderLayout.CENTER);

        dialog.add(wrapper);
        
        // Round the corners of the dialog
        dialog.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 15, 15));
        
        dialog.setVisible(true);
    }

    private JLabel createMetaLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(new Color(100, 116, 139)); 
        return lbl;
    }

    private JLabel createMetaValue(String text) {
        JLabel lbl = new JLabel(text != null ? text : "N/A");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(Main.TEXT);
        return lbl;
    }


    private void submitReport(JRadioButton rdoIncident, JComboBox<String> category, JTextField location,
                              JTextField detail, JTextArea description) {
        User currentUser = mainApp.getCurrentUser();
        if (!(currentUser instanceof Resident)) return;

        String categoryValue = (String) category.getSelectedItem();
        String locationValue = location.getText().trim();
        String detailValue = detail.getText().trim();
        String descriptionValue = description.getText().trim();

        // Check if the description field still has the placeholder text
        if (descriptionValue.equals("Describe the incident/complaint in detail...")) {
            descriptionValue = "";
        }

        if (categoryValue.isEmpty() || locationValue.isEmpty() || descriptionValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Missing Details", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Determine if it's an Incident or Complaint based on the radio button
        boolean isIncident = rdoIncident.isSelected();
        
        int newIdNum = mainApp.getReports().size() + 1001;
        String id = (isIncident ? "IR-" : "CR-") + newIdNum;

        Report report = isIncident
                ? new IncidentReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue)
                : new ComplaintReport(id, currentUser.getId(), categoryValue, locationValue, descriptionValue,
                LocalDate.now().toString(), "PENDING", detailValue);

        mainApp.getReports().add(report);

        JOptionPane.showMessageDialog(this, "Report submitted successfully!\nID: " + id,
                "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear the fields after submission
        category.setSelectedIndex(0); 
        location.setText(""); 
        detail.setText(""); 
        description.setText("");
        description.setForeground(Color.GRAY); // Reset the placeholder color

        // Refresh the tables
        updateReportList();
        updateResidentReportList();
        updateOverviewText();
        
        // Automatically switch to the "My Reports" tab to show the new report
        activateSidebarButton(myReportsNavigation);
        contentLayout.show(dashboardContent, "myReports");
    }
}