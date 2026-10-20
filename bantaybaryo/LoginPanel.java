package bantaybaryo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginPanel extends JPanel {

    public LoginPanel(CardLayout mainLayout, JPanel mainPanel, Main mainApp) {
        setLayout(new BorderLayout());
        setBackground(Main.BACKGROUND);

        // ---------- NAVBAR ----------
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Main.PRIMARY);
        nav.setBorder(new EmptyBorder(12, 32, 12, 32));

        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brandPanel.setOpaque(false);

        ImageIcon navLogo = mainApp.getLogoIcon(40, 40);
        JLabel brandLogo = new JLabel();
        if (navLogo != null) {
            brandLogo.setIcon(navLogo);
        } else {
            brandLogo.setText("🛡");
            brandLogo.setFont(new Font("SansSerif", Font.PLAIN, 24));
            brandLogo.setForeground(Color.WHITE);
        }

        JLabel brand = new JLabel("Bantay Baryo");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 22));

        brandPanel.add(brandLogo);
        brandPanel.add(brand);

        JPanel navActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navActions.setOpaque(false);

        JButton homeButton = mainApp.createNavGhostButton("Home");
        homeButton.addActionListener(e -> mainLayout.show(mainPanel, "home"));

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Main.PRIMARY);
        loginButton.setBackground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(110, 40));

        navActions.add(homeButton);
        navActions.add(loginButton);

        nav.add(brandPanel, BorderLayout.WEST);
        nav.add(navActions, BorderLayout.EAST);

        // ---------- LOGIN FORM ----------
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Main.BACKGROUND);

        JPanel card = mainApp.createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.BORDER),
                new EmptyBorder(35, 40, 35, 40)));
        card.setPreferredSize(new Dimension(420, 440));

        JLabel title = mainApp.centeredLabel("Welcome Back!", 26, Main.PRIMARY, Font.BOLD);
        JLabel subtitle = mainApp.centeredLabel("Log in to your account", 14, Main.MUTED, Font.PLAIN);

        JPanel userGroup = new JPanel();
        userGroup.setLayout(new BoxLayout(userGroup, BoxLayout.Y_AXIS));
        userGroup.setOpaque(false);
        userGroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        userGroup.setMaximumSize(new Dimension(320, 60));

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        userLabel.setForeground(Main.TEXT);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField username = new JTextField();
        username.setFont(new Font("SansSerif", Font.PLAIN, 14));
        username.setMaximumSize(new Dimension(320, 38));
        username.setPreferredSize(new Dimension(320, 38));
        username.setAlignmentX(Component.LEFT_ALIGNMENT);

        userGroup.add(userLabel);
        userGroup.add(Box.createVerticalStrut(6));
        userGroup.add(username);

        JPanel passGroup = new JPanel();
        passGroup.setLayout(new BoxLayout(passGroup, BoxLayout.Y_AXIS));
        passGroup.setOpaque(false);
        passGroup.setAlignmentX(Component.CENTER_ALIGNMENT);
        passGroup.setMaximumSize(new Dimension(320, 60));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        passLabel.setForeground(Main.TEXT);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField password = new JPasswordField();
        password.setFont(new Font("SansSerif", Font.PLAIN, 14));
        password.setMaximumSize(new Dimension(320, 38));
        password.setPreferredSize(new Dimension(320, 38));
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        passGroup.add(passLabel);
        passGroup.add(Box.createVerticalStrut(6));
        passGroup.add(password);

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setOpaque(false);
        showPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel checkBoxWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkBoxWrapper.setOpaque(false);
        checkBoxWrapper.setMaximumSize(new Dimension(320, 30));
        checkBoxWrapper.add(showPassword);

        showPassword.addActionListener(e -> password.setEchoChar(showPassword.isSelected() ? (char) 0 : '•'));

        JButton logIn = mainApp.createPrimaryButton("Log in");
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.setPreferredSize(new Dimension(320, 44));
        logIn.setMaximumSize(new Dimension(320, 44));
        logIn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Main.PRIMARY, 1),
                new EmptyBorder(10, 24, 10, 24)));

        logIn.addActionListener(e -> {
            String userStr = username.getText().trim();
            String passStr = new String(password.getPassword());

            // 👇 1. CHECK FOR EMPTY FIELDS FIRST 👇
            if (userStr.isEmpty() || passStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter both username and password.", 
                    "Required Fields", 
                    JOptionPane.WARNING_MESSAGE);
                return; // Stop right here
            }

            // 👇 2. THEN CHECK CREDENTIALS 👇
            User user = mainApp.authenticate(userStr, passStr);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                password.setText("");
                return;
            }

            // 👇 3. SUCCESSFUL LOGIN 👇
            mainApp.setCurrentUser(user);
            mainApp.updateDashboardForUser();
            mainLayout.show(mainPanel, "dashboard");

            username.setText("");
            password.setText("");
        });

        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        card.add(userGroup);
        card.add(Box.createVerticalStrut(16));
        card.add(passGroup);
        card.add(Box.createVerticalStrut(10));
        card.add(checkBoxWrapper);
        card.add(Box.createVerticalStrut(24));
        card.add(logIn);

        centerWrapper.add(card);

        add(nav, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }
}