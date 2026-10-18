package bantaybaryo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {
    // Made public so the separated panels can use your exact color theme!
    public static final Color PRIMARY = Color.decode("#1E3A8A");
    public static final Color SECONDARY = Color.decode("#0284C7");
    public static final Color BACKGROUND = Color.decode("#F8FAFC");
    public static final Color TEXT = Color.decode("#0F172A");
    public static final Color MUTED = Color.decode("#6B7280");
    public static final Color BORDER = Color.decode("#E2E8F0");
    public static final Color PENDING = Color.decode("#F59E0B");
    public static final Color PROGRESS = Color.decode("#2563EB");
    public static final Color RESOLVED = Color.decode("#10B981");

    private final CardLayout mainLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(mainLayout);

    // Shared Data
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Report> reports = new ArrayList<>();

    private User currentUser;
    private DashboardPanel dashboardPanel;

    public Main() {
        setTitle("Bantay Baryo - Community Incident Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 760);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);

        seedData();

        // 1. Initialize the Dashboard first so we can reference it
        dashboardPanel = new DashboardPanel(mainLayout, mainPanel, this);

        // 2. Add all your newly separated panels!
        mainPanel.add(new HomePanel(mainLayout, mainPanel, this), "home");
        mainPanel.add(new LoginPanel(mainLayout, mainPanel, this), "login");
        mainPanel.add(dashboardPanel, "dashboard");

        add(mainPanel);
        mainLayout.show(mainPanel, "home");
    }

    private void seedData() {
        users.add(new Resident("R-1001", "Maria", "Dela", "Cruz", "user", "user123", "Purok 2", "Bahay ng Lahat"));
        
        // NORMAL STAFF account
        users.add(new BarangayStaff("S-2001", "Jose", "", "Santos", "staff", "staff123", "Community Officer"));
        
        // This is your ADMIN account
        users.add(new Admin("A-9001", "Super", "", "Admin", "admin", "admin123", "Barangay Captain"));

        reports.add(new IncidentReport("IR-3001", "R-1001", "Public Safety", "Barangay Hall",
                "A noise disturbance and fight occurred near the covered court.",
                "2026-10-07", "PENDING", "Maria Cruz and Juan Dela Torre"));
        reports.add(new ComplaintReport("CR-3002", "R-1001", "Garbage Disposal", "Purok 5",
                "Uncollected trash has been piling up near the drainage canal.",
                "2026-10-07", "IN_PROGRESS", "Barangay Waste Team"));
    }

    // --- Core Data & Logic Methods ---
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)
                    && "ACTIVE".equalsIgnoreCase(user.getStatus())) return user;
        }
        return null;
    }

    // Bridges the LoginPanel to the DashboardPanel securely
    public void updateDashboardForUser() {
        if (dashboardPanel != null) {
            dashboardPanel.updateDashboardForUser();
        }
    }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
    public ArrayList<Report> getReports() { return reports; }

    // --- Shared UI Components (Made Public) ---
    public JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(BORDER));
        return panel;
    }

    public JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public JButton createSecondaryButton(String text) {
        JButton button = createPrimaryButton(text);
        button.setBackground(SECONDARY);
        return button;
    }

    public JButton createNavGhostButton(String text) {
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
                button.setForeground(new Color(191, 219, 254));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    public JButton createNavOutlineButton(String text) {
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

    public JLabel centeredLabel(String text, int size, Color color, int style) {
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
                return logoImage;
            }
        }
        java.net.URL url = getClass().getResource("/resources/logo.png");
        if (url != null) {
            logoImage = new ImageIcon(url).getImage();
            return logoImage;
        }
        return null;
    }

    public ImageIcon getLogoIcon(int maxWidth, int maxHeight) {
        Image img = getLogoImage();
        if (img == null) return null;

        int srcW = img.getWidth(null);
        int srcH = img.getHeight(null);
        if (srcW <= 0 || srcH <= 0) return null;

        double scale = Math.min((double) maxWidth / srcW, (double) maxHeight / srcH);
        int drawW = (int) Math.round(srcW * scale);
        int drawH = (int) Math.round(srcH * scale);

        while (srcW > drawW * 2 && srcH > drawH * 2) {
            srcW /= 2;
            srcH /= 2;
            BufferedImage stepDown = new BufferedImage(srcW, srcH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2step = stepDown.createGraphics();
            g2step.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2step.drawImage(img, 0, 0, srcW, srcH, null);
            g2step.dispose();
            img = stepDown;
        }

        BufferedImage scaled = new BufferedImage(drawW, drawH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.drawImage(img, 0, 0, drawW, drawH, null);
        g2.dispose();

        return new ImageIcon(scaled);
    }

    public ImageIcon createLogoIconSmall() {
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

        public ArrayList<User> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}