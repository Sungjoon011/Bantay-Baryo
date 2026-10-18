package bantaybaryo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(CardLayout mainLayout, JPanel mainPanel, Main mainApp) {
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

        JButton loginButton = mainApp.createNavOutlineButton("Login");
        loginButton.addActionListener(e -> mainLayout.show(mainPanel, "login"));

        navActions.add(homeButton);
        navActions.add(loginButton);

        nav.add(brandPanel, BorderLayout.WEST);
        nav.add(navActions, BorderLayout.EAST);

        // ---------- HERO CONTENT ----------
        JPanel hero = new JPanel();
        hero.setBackground(Main.BACKGROUND);
        hero.setLayout(new GridBagLayout());

        JPanel heroContent = new JPanel();
        heroContent.setOpaque(false);
        heroContent.setLayout(new BoxLayout(heroContent, BoxLayout.Y_AXIS));

        ImageIcon heroLogo = mainApp.getLogoIcon(220, 220);
        if (heroLogo != null) {
            JLabel heroLogoLabel = new JLabel(heroLogo);
            heroLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            heroContent.add(heroLogoLabel);
            heroContent.add(Box.createVerticalStrut(20));
        }

        JLabel title = new JLabel("Your Community. Your Voice. Your Safety.");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Main.PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("A simple digital platform for reporting and tracking barangay concerns.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(Main.MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel note = new JLabel("Accounts are created and managed by the Barangay Administrator.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(Main.MUTED);
        note.setAlignmentX(Component.CENTER_ALIGNMENT);

        heroContent.add(title);
        heroContent.add(Box.createVerticalStrut(14));
        heroContent.add(subtitle);
        heroContent.add(Box.createVerticalStrut(40));
        heroContent.add(Box.createVerticalStrut(100));
        heroContent.add(note);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        hero.add(heroContent, gbc);

        add(nav, BorderLayout.NORTH);
        add(hero, BorderLayout.CENTER);
    }
}