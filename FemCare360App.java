import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.List;

public class FemCare360App {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Integer loggedUserId = null;
    private String loggedUsername = null;

    // Vibrant gradient colors
    private final Color GRADIENT_START = new Color(138, 35, 135);
    private final Color GRADIENT_END = new Color(233, 64, 87);
    private final Color ACCENT_PURPLE = new Color(168, 85, 247);
    private final Color ACCENT_PINK = new Color(236, 72, 153);
    private final Color ACCENT_ORANGE = new Color(251, 146, 60);
    private final Color ACCENT_GREEN = new Color(52, 211, 153);
    private final Color CARD_BG = new Color(255, 255, 255, 240);
    private final Color TEXT_DARK = new Color(17, 24, 39);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new FemCare360App().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("FemCare360");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 850);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createAuthPanel(), "Auth");
        mainPanel.add(createDashboardPanel(), "Dashboard");
        mainPanel.add(createProfilePanel(), "Profile");
        mainPanel.add(createCyclePanel(), "Cycle");
        mainPanel.add(createSymptomHubPanel(), "SymptomHub");
        mainPanel.add(createWellnessPanel(), "Wellness");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "Auth");
    }

    // ----------- GRADIENT BACKGROUND PANEL ------------
    class GradientPanel extends JPanel {
        private Color startColor, endColor;

        public GradientPanel(Color start, Color end) {
            this.startColor = start;
            this.endColor = end;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, w, h, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    // ----------- GLASSMORPHISM CARD ------------
    class GlassPanel extends JPanel {
        public GlassPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);

            super.paintComponent(g);
        }
    }

    // ----------- AUTH PANEL ------------
    private JPanel createAuthPanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new GridBagLayout());

        GlassPanel card = new GlassPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(50, 60, 50, 60));

        JLabel logo = new JLabel("FemCare360");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        logo.setForeground(GRADIENT_START);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your wellness, beautifully managed");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabs.setPreferredSize(new Dimension(450, 420));

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setOpaque(false);
        loginPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JTextField loginUser = createGlassTextField("Username");
        JPasswordField loginPass = createGlassPasswordField("Password");
        JButton loginBtn = createGradientButton("Sign In", ACCENT_PURPLE, ACCENT_PINK);

        loginPanel.add(createFieldLabel("Username"));
        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginPanel.add(loginUser);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(createFieldLabel("Password"));
        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginPanel.add(loginPass);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(loginBtn);
        loginPanel.add(Box.createVerticalGlue());

        // Signup Panel
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setOpaque(false);
        signupPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JTextField signupUser = createGlassTextField("Username");
        JPasswordField signupPass = createGlassPasswordField("Password");
        JPasswordField confirmPass = createGlassPasswordField("Confirm Password");
        JButton signupBtn = createGradientButton("Create Account", ACCENT_PINK, ACCENT_ORANGE);

        signupPanel.add(createFieldLabel("Username"));
        signupPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        signupPanel.add(signupUser);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        signupPanel.add(createFieldLabel("Password"));
        signupPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        signupPanel.add(signupPass);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        signupPanel.add(createFieldLabel("Confirm Password"));
        signupPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        signupPanel.add(confirmPass);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        signupPanel.add(signupBtn);
        signupPanel.add(Box.createVerticalGlue());

        tabs.addTab("Login", loginPanel);
        tabs.addTab("Sign Up", signupPanel);

        card.add(logo);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(tabs);

        GridBagConstraints gbc = new GridBagConstraints();
        panel.add(card, gbc);

        // Actions
        loginBtn.addActionListener(e -> {
            String u = loginUser.getText().trim();
            String p = new String(loginPass.getPassword());
            
            Integer userId = UserDAO.loginUser(u, p);
            if(userId != null){
                loggedUserId = userId;
                loggedUsername = u;
                mainPanel.remove(1);
                mainPanel.add(createDashboardPanel(), "Dashboard", 1);
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signupBtn.addActionListener(e -> {
            String u = signupUser.getText().trim();
            String p = new String(signupPass.getPassword());
            String c = new String(confirmPass.getPassword());

            if(u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!p.equals(c)) {
                JOptionPane.showMessageDialog(panel, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = UserDAO.registerUser(u, p);
            if(success) {
                JOptionPane.showMessageDialog(panel, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                tabs.setSelectedIndex(0);
                signupUser.setText("");
                signupPass.setText("");
                confirmPass.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // ----------- DASHBOARD ------------
    private JPanel createDashboardPanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(30, 40, 20, 40));

        String displayName = loggedUsername;
        if(loggedUserId != null) {
            ProfileDAO.UserProfile profile = ProfileDAO.getProfile(loggedUserId);
            if(profile != null && profile.name != null && !profile.name.isEmpty()) {
                displayName = profile.name;
            }
        }

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome back, " + displayName + "!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(Color.WHITE);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Let's take care of your health today");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        welcomePanel.add(welcome);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomePanel.add(subtitle);

        header.add(welcomePanel, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        // Cards
        JPanel cardsPanel = new JPanel(new GridBagLayout());
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(new EmptyBorder(30, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(18, 18, 18, 18);
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0; gbc.gridy = 0;
        cardsPanel.add(createEnhancedDashboardCard("Profile", "Manage your health info", "", ACCENT_PURPLE,
                e -> cardLayout.show(mainPanel, "Profile")), gbc);

        gbc.gridx = 1;
        cardsPanel.add(createEnhancedDashboardCard("Cycle Tracker", "Track your period cycle", "", ACCENT_PINK,
                e -> cardLayout.show(mainPanel, "Cycle")), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        cardsPanel.add(createEnhancedDashboardCard("Symptom Hub", "Find remedies & tips", "", ACCENT_ORANGE,
                e -> cardLayout.show(mainPanel, "SymptomHub")), gbc);

        gbc.gridx = 1;
        cardsPanel.add(createEnhancedDashboardCard("Wellness Tips", "Daily inspiration", "", ACCENT_GREEN,
                e -> cardLayout.show(mainPanel, "Wellness")), gbc);

        panel.add(cardsPanel, BorderLayout.CENTER);

        return panel;
    }

    // ----------- PROFILE PANEL ------------
    private JPanel createProfilePanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new BorderLayout());

        panel.add(createHeader("Your Profile", "Dashboard"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(30, 50, 50, 50));

        GlassPanel card = new GlassPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 50, 40, 50));

        JTextField nameField = createGlassTextField("Full Name");
        JTextField weightField = createGlassTextField("Weight (kg)");
        JTextField heightField = createGlassTextField("Height (cm)");
        JButton saveBtn = createGradientButton("Save Profile", ACCENT_PURPLE, ACCENT_PINK);

        // Load from database
        if(loggedUserId != null) {
            ProfileDAO.UserProfile profile = ProfileDAO.getProfile(loggedUserId);
            if(profile != null) {
                if(profile.name != null && !profile.name.isEmpty()) nameField.setText(profile.name);
                if(profile.weight != null && !profile.weight.isEmpty()) weightField.setText(profile.weight);
                if(profile.height != null && !profile.height.isEmpty()) heightField.setText(profile.height);
            }
        }

        card.add(createFieldLabel("Full Name"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(nameField);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(createFieldLabel("Weight"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(weightField);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(createFieldLabel("Height"));
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(heightField);
        card.add(Box.createRigidArea(new Dimension(0, 35)));
        card.add(saveBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        content.add(card, gbc);
        panel.add(content, BorderLayout.CENTER);

        saveBtn.addActionListener(e -> {
            if(loggedUserId != null){
                boolean success = ProfileDAO.saveProfile(loggedUserId, 
                    nameField.getText().trim(),
                    weightField.getText().trim(), 
                    heightField.getText().trim());
                
                if(success) {
                    JOptionPane.showMessageDialog(panel, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    mainPanel.remove(1);
                    mainPanel.add(createDashboardPanel(), "Dashboard", 1);
                }
            }
        });

        return panel;
    }

    // ----------- CYCLE TRACKER ------------
    private JPanel createCyclePanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new BorderLayout());

        panel.add(createHeader("Cycle Tracker", "Dashboard"), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(20, 40, 40, 40));

        GlassPanel inputCard = new GlassPanel();
        inputCard.setLayout(new GridBagLayout());
        inputCard.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(5, 10, 5, 10);
        inputGbc.anchor = GridBagConstraints.CENTER;

        JLabel periodLabel = new JLabel("Last Period:");
        periodLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        periodLabel.setForeground(TEXT_DARK);

        JTextField lastPeriod = new JTextField("yyyy-MM-dd");
        lastPeriod.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lastPeriod.setBackground(Color.WHITE);
        lastPeriod.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(168, 85, 247), 2),
                new EmptyBorder(12, 15, 12, 15)
        ));
        lastPeriod.setForeground(TEXT_DARK);
        lastPeriod.setCaretColor(TEXT_DARK);
        lastPeriod.setPreferredSize(new Dimension(200, 50));

        JLabel cycleLabel = new JLabel("Cycle Length:");
        cycleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cycleLabel.setForeground(TEXT_DARK);

        JTextField cycleLen = new JTextField("28");
        cycleLen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cycleLen.setBackground(Color.WHITE);
        cycleLen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(168, 85, 247), 2),
                new EmptyBorder(12, 15, 12, 15)
        ));
        cycleLen.setForeground(TEXT_DARK);
        cycleLen.setCaretColor(TEXT_DARK);
        cycleLen.setPreferredSize(new Dimension(100, 50));

        JButton generateBtn = createGradientButton("Generate Calendar", ACCENT_PINK, ACCENT_ORANGE);
        generateBtn.setPreferredSize(new Dimension(220, 50));

        // Load existing cycle data
        if(loggedUserId != null) {
            CycleDAO.CycleInfo cycle = CycleDAO.getCycle(loggedUserId);
            if(cycle != null) {
                lastPeriod.setText(cycle.lastPeriodDate);
                cycleLen.setText(String.valueOf(cycle.cycleLength));
            }
        }

        inputGbc.gridx = 0; inputGbc.gridy = 0;
        inputCard.add(periodLabel, inputGbc);
        inputGbc.gridx = 1;
        inputCard.add(lastPeriod, inputGbc);
        inputGbc.gridx = 2;
        inputCard.add(cycleLabel, inputGbc);
        inputGbc.gridx = 3;
        inputCard.add(cycleLen, inputGbc);
        inputGbc.gridx = 4;
        inputCard.add(generateBtn, inputGbc);

        content.add(inputCard, BorderLayout.NORTH);

        JPanel calendarContainer = new JPanel();
        calendarContainer.setLayout(new BoxLayout(calendarContainer, BoxLayout.Y_AXIS));
        calendarContainer.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(calendarContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        content.add(scrollPane, BorderLayout.CENTER);
        panel.add(content, BorderLayout.CENTER);

        generateBtn.addActionListener(e -> {
            if(loggedUserId != null) {
                // Save to database
                CycleDAO.saveCycle(loggedUserId, lastPeriod.getText().trim(), 
                    Integer.parseInt(cycleLen.getText().trim()));
                generateCalendar(calendarContainer, lastPeriod, cycleLen, panel);
            }
        });

        return panel;
    }

    private void generateCalendar(JPanel container, JTextField lastPeriodField, JTextField cycleLenField, JPanel parent) {
        try {
            LocalDate lastPeriod = LocalDate.parse(lastPeriodField.getText().trim());
            int cycleLen = Integer.parseInt(cycleLenField.getText().trim());

            LocalDate startMonth = lastPeriod.withDayOfMonth(1);
            LocalDate endMonth = lastPeriod.plusMonths(5);

            List<LocalDate> periodDays = new ArrayList<>();
            List<LocalDate> fertileDays = new ArrayList<>();
            List<LocalDate> ovulationDays = new ArrayList<>();

            LocalDate ref = lastPeriod;
            LocalDate endCalc = endMonth.plusMonths(1);

            while(!ref.isAfter(endCalc)){
                for(int d=0; d<5; d++) periodDays.add(ref.plusDays(d));
                LocalDate ovulationDay = ref.plusDays(14);
                ovulationDays.add(ovulationDay);
                for(int d=10; d<=17; d++) {
                    LocalDate fertileDay = ref.plusDays(d);
                    if(!ovulationDays.contains(fertileDay)) {
                        fertileDays.add(fertileDay);
                    }
                }
                ref = ref.plusDays(cycleLen);
            }

            container.removeAll();

            JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
            legendPanel.setOpaque(false);
            legendPanel.add(createColorLegend("Period Days", new Color(239, 68, 68)));
            legendPanel.add(createColorLegend("Ovulation", new Color(168, 85, 247)));
            legendPanel.add(createColorLegend("Fertile Window", new Color(252, 165, 165)));
            container.add(legendPanel);
            container.add(Box.createRigidArea(new Dimension(0, 20)));

            LocalDate tempMonth = startMonth;

            while(!tempMonth.isAfter(endMonth)){
                GlassPanel monthCard = new GlassPanel();
                monthCard.setLayout(new BorderLayout());
                monthCard.setBorder(new EmptyBorder(20, 20, 20, 20));

                JLabel monthLabel = new JLabel(tempMonth.getMonth() + " " + tempMonth.getYear(), SwingConstants.CENTER);
                monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
                monthLabel.setForeground(GRADIENT_START);
                monthLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
                monthCard.add(monthLabel, BorderLayout.NORTH);

                String[] cols = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
                Object[][] tableData = new Object[6][7];
                LocalDate firstDay = tempMonth.withDayOfMonth(1);
                int startCol = firstDay.getDayOfWeek().getValue() % 7;
                int row = 0, col = startCol;
                YearMonth ym = YearMonth.from(firstDay);
                for(int day=1; day<=ym.lengthOfMonth(); day++){
                    tableData[row][col] = day;
                    col++;
                    if(col>6){ col=0; row++; }
                }

                final LocalDate monthForRenderer = tempMonth;

                JTable monthTable = new JTable(tableData, cols);
                monthTable.setRowHeight(50);
                monthTable.setFont(new Font("Segoe UI", Font.BOLD, 14));
                monthTable.setShowGrid(true);
                monthTable.setGridColor(new Color(200, 200, 200));
                monthTable.setOpaque(false);

                monthTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus,
                                                                   int row, int col) {
                        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(TEXT_DARK);
                        setHorizontalAlignment(SwingConstants.CENTER);
                        setFont(new Font("Segoe UI", Font.BOLD, 14));

                        if(value != null){
                            int day = (int)value;
                            LocalDate curDate = monthForRenderer.withDayOfMonth(day);

                            if(periodDays.contains(curDate)) {
                                cell.setBackground(new Color(239, 68, 68));
                                cell.setForeground(Color.WHITE);
                            }
                            else if(ovulationDays.contains(curDate)) {
                                cell.setBackground(new Color(168, 85, 247));
                                cell.setForeground(Color.WHITE);
                            }
                            else if(fertileDays.contains(curDate)) {
                                cell.setBackground(new Color(252, 165, 165));
                                cell.setForeground(TEXT_DARK);
                            }
                        }
                        return cell;
                    }
                });

                monthCard.add(monthTable, BorderLayout.CENTER);
                container.add(monthCard);
                container.add(Box.createRigidArea(new Dimension(0, 20)));
                tempMonth = tempMonth.plusMonths(1);
            }

            container.revalidate();
            container.repaint();

        } catch(Exception ex){
            JOptionPane.showMessageDialog(parent, "Invalid input! Use yyyy-MM-dd format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ----------- SYMPTOM HUB (same as original) ------------
    private JPanel createSymptomHubPanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new BorderLayout());

        panel.add(createHeader("Symptom Hub", "Dashboard"), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(20, 50, 40, 50));

        String[] symptoms = {"Select a symptom...","Acne","Cold","Headache","Menstrual Cramps",
                "Nausea","Bloating","Fatigue","Back Pain","Mood Swings","Migraine"};
        JComboBox<String> combo = new JComboBox<>(symptoms);
        combo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        combo.setPreferredSize(new Dimension(300, 50));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboPanel.setOpaque(false);
        comboPanel.setBorder(new EmptyBorder(10, 0, 30, 0));
        comboPanel.add(combo);
        content.add(comboPanel, BorderLayout.NORTH);

        JPanel tipsContainer = new JPanel();
        tipsContainer.setLayout(new BoxLayout(tipsContainer, BoxLayout.Y_AXIS));
        tipsContainer.setOpaque(false);

        JScrollPane sp = new JScrollPane(tipsContainer);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(null);
        content.add(sp, BorderLayout.CENTER);

        Map<String, String[][]> allTips = new HashMap<>();

        allTips.put("Acne", new String[][]{
                {"Apply turmeric and sandalwood paste with rose water for 15 minutes",
                        "Neem leaves paste helps reduce inflammation and kills bacteria",
                        "Use multani mitti (Fuller's earth) mixed with milk as face mask"},
                {"Salicylic acid cleanser helps unclog pores",
                        "Benzoyl peroxide cream reduces acne-causing bacteria",
                        "Regular exfoliation and non-comedogenic moisturizer"}
        });

        allTips.put("Cold", new String[][]{
                {"Ginger tea with honey and tulsi leaves boosts immunity",
                        "Steam inhalation with eucalyptus oil clears nasal passages",
                        "Warm turmeric milk before bed soothes throat"},
                {"Vitamin C supplements boost immune system",
                        "Saline nasal spray relieves congestion",
                        "Rest and stay hydrated with warm fluids"}
        });

        allTips.put("Headache", new String[][]{
                {"Cold compress on forehead and temples for instant relief",
                        "Peppermint oil massage on temples reduces pain",
                        "Warm jeera water helps with digestive headaches"},
                {"Over-the-counter pain relievers like ibuprofen",
                        "Rest in dark, quiet room to reduce stimulation",
                        "Stay hydrated and maintain regular sleep schedule"}
        });

        allTips.put("Menstrual Cramps", new String[][]{
                {"Warm ajwain water reduces cramping naturally",
                        "Hot water bottle on lower abdomen provides relief",
                        "Jaggery with warm milk helps with period pain"},
                {"Heating pad on abdomen relaxes muscles",
                        "Ibuprofen or naproxen for pain relief",
                        "Light yoga or walking improves blood flow"}
        });

        allTips.put("Nausea", new String[][]{
                {"Fresh ginger water or chewing raw ginger settles stomach",
                        "Smell fresh lemon or drink lemon water",
                        "Coconut water replenishes electrolytes"},
                {"Small, frequent meals prevent empty stomach",
                        "Avoid strong odors and greasy foods",
                        "Peppermint tea or antiemetic medication if severe"}
        });

        allTips.put("Bloating", new String[][]{
                {"Warm jeera (cumin) water on empty stomach aids digestion",
                        "Fennel seeds after meals reduce gas",
                        "Avoid heavy, oily foods and carbonated drinks"},
                {"Probiotics improve gut health",
                        "Light walking after meals aids digestion",
                        "Avoid gas-producing foods like beans and cabbage"}
        });

        allTips.put("Fatigue", new String[][]{
                {"Warm milk with saffron and almonds energizes",
                        "Dates and dry fruits provide natural energy",
                        "Morning sunlight for 15 minutes boosts vitamin D"},
                {"Ensure 7-8 hours quality sleep nightly",
                        "Check iron and B12 vitamin levels",
                        "Regular exercise actually improves energy levels"}
        });

        allTips.put("Back Pain", new String[][]{
                {"Warm sesame oil massage relieves muscle tension",
                        "Hot water compress on affected area",
                        "Gentle yoga stretches improve flexibility"},
                {"Alternate hot and cold compress therapy",
                        "Anti-inflammatory medication as needed",
                        "Maintain proper posture while sitting"}
        });

        allTips.put("Mood Swings", new String[][]{
                {"Ashwagandha tea reduces stress and anxiety",
                        "Warm milk with nutmeg promotes calm",
                        "Daily meditation and deep breathing exercises"},
                {"Regular exercise releases endorphins",
                        "Maintain consistent sleep routine",
                        "Talk therapy or counseling for persistent issues"}
        });

        allTips.put("Migraine", new String[][]{
                {"Sandalwood paste on forehead cools and soothes",
                        "Rest in dark, cool, quiet room",
                        "Coriander seed water reduces inflammation"},
                {"Take prescribed migraine medication early",
                        "Ice pack on forehead and neck",
                        "Identify and avoid triggers like bright lights"}
        });

        combo.addActionListener(e -> {
            String selected = (String) combo.getSelectedItem();
            tipsContainer.removeAll();

            if(selected != null && !selected.equals("Select a symptom...") && allTips.containsKey(selected)){
                String[][] tips = allTips.get(selected);

                JPanel centerPanel = new JPanel();
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
                centerPanel.setOpaque(false);
                centerPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

                centerPanel.add(Box.createHorizontalGlue());
                centerPanel.add(createTipCard("Paati's Tips", tips[0], ACCENT_GREEN));
                centerPanel.add(Box.createRigidArea(new Dimension(30, 0)));
                centerPanel.add(createTipCard("Modern Medicine", tips[1], ACCENT_PINK));
                centerPanel.add(Box.createHorizontalGlue());

                tipsContainer.add(centerPanel);
                tipsContainer.add(Box.createRigidArea(new Dimension(0, 30)));

                JLabel warning = new JLabel("Consult a healthcare professional if symptoms persist or worsen");
                warning.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                warning.setForeground(Color.WHITE);
                warning.setAlignmentX(Component.CENTER_ALIGNMENT);
                tipsContainer.add(warning);
            }

            tipsContainer.revalidate();
            tipsContainer.repaint();
        });

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    // ----------- WELLNESS PANEL ------------
    private JPanel createWellnessPanel() {
        GradientPanel panel = new GradientPanel(GRADIENT_START, GRADIENT_END);
        panel.setLayout(new BorderLayout());

        panel.add(createHeader("Daily Wellness", "Dashboard"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(40, 60, 60, 60));

        GlassPanel tipCard = new GlassPanel();
        tipCard.setLayout(new BoxLayout(tipCard, BoxLayout.Y_AXIS));
        tipCard.setBorder(new EmptyBorder(50, 60, 50, 60));
        tipCard.setPreferredSize(new Dimension(700, 400));

        JLabel categoryLabel = new JLabel();
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        categoryLabel.setForeground(ACCENT_PURPLE);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea tipText = new JTextArea();
        tipText.setEditable(false);
        tipText.setLineWrap(true);
        tipText.setWrapStyleWord(true);
        tipText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        tipText.setForeground(TEXT_DARK);
        tipText.setOpaque(false);
        tipText.setBorder(new EmptyBorder(25, 20, 25, 20));
        tipText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newTipBtn = createGradientButton("Get New Tip", ACCENT_PINK, ACCENT_ORANGE);
        newTipBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        tipCard.add(categoryLabel);
        tipCard.add(tipText);
        tipCard.add(Box.createRigidArea(new Dimension(0, 20)));
        tipCard.add(newTipBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        content.add(tipCard, gbc);
        panel.add(content, BorderLayout.CENTER);

        Map<String, List<String>> wellnessTips = new HashMap<>();

        wellnessTips.put("Hydration", Arrays.asList(
                "Drink at least 2-3 liters of water daily to keep your body hydrated and energized.",
                "Start your day with warm water and lemon to kickstart your metabolism and flush toxins.",
                "Herbal teas like chamomile and green tea count towards daily water intake and provide antioxidants."
        ));

        wellnessTips.put("Exercise", Arrays.asList(
                "Aim for 30 minutes of moderate activity daily to maintain cardiovascular health and boost mood.",
                "Include yoga for flexibility, stress relief, and strengthening the mind-body connection.",
                "Walk 10,000 steps per day to improve your overall cardiovascular fitness and mental clarity."
        ));

        wellnessTips.put("Sleep", Arrays.asList(
                "Maintain 7-8 hours of quality sleep every night for optimal physical and mental health.",
                "Keep consistent sleep and wake times, even on weekends, to regulate your circadian rhythm.",
                "Avoid screens 1 hour before bedtime as blue light disrupts melatonin production."
        ));

        wellnessTips.put("Nutrition", Arrays.asList(
                "Eat balanced meals with colorful fruits and vegetables to get a variety of nutrients.",
                "Include iron-rich foods like spinach, lentils, and dates especially important for women's health.",
                "Consume calcium sources like dairy, leafy greens, and fortified foods for strong bone health."
        ));

        wellnessTips.put("Mental Health", Arrays.asList(
                "Practice 10 minutes of meditation daily to reduce stress, anxiety, and improve focus.",
                "Take short breaks from screens every hour to prevent eye strain and mental fatigue.",
                "Maintain social connections with friends and family for emotional wellbeing and support."
        ));

        wellnessTips.put("Women's Health", Arrays.asList(
                "Track your menstrual cycle regularly to understand your body's patterns and plan accordingly.",
                "Schedule annual gynecological check-ups for preventive care and early detection of issues.",
                "Practice good menstrual hygiene by changing sanitary products regularly every 4-6 hours."
        ));

        Runnable showRandomTip = () -> {
            List<String> categories = new ArrayList<>(wellnessTips.keySet());
            String randomCategory = categories.get(new Random().nextInt(categories.size()));
            List<String> categoryTips = wellnessTips.get(randomCategory);
            String randomTip = categoryTips.get(new Random().nextInt(categoryTips.size()));

            categoryLabel.setText(randomCategory);
            tipText.setText(randomTip);
        };

        showRandomTip.run();
        newTipBtn.addActionListener(e -> showRandomTip.run());

        return panel;
    }

    // ----------- HELPER METHODS ------------

    private JTextField createGlassTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.BOLD, 15));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(168, 85, 247), 2),
                new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setForeground(TEXT_DARK);
        field.setCaretColor(TEXT_DARK);
        return field;
    }

    private JPasswordField createGlassPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.BOLD, 15));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(168, 85, 247), 2),
                new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setForeground(TEXT_DARK);
        field.setCaretColor(TEXT_DARK);
        return field;
    }

    private JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, startColor, getWidth(), 0, endColor);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            }
        });
        return button;
    }

    private JPanel createEnhancedDashboardCard(String title, String desc, String icon, Color accentColor, ActionListener action) {
        GlassPanel card = new GlassPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(40, 35, 40, 35));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(320, 220));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descLabel.setForeground(new Color(75, 85, 99));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        contentPanel.add(descLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 27));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
            }
        });

        return card;
    }

    private JPanel createHeader(String title, String backTo) {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(30, 40, 20, 40));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);

        JButton backBtn = new JButton("< Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, backTo));

        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            }
        });

        header.add(backBtn, BorderLayout.EAST);
        return header;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createColorLegend(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setOpaque(false);

        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(30, 30));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);

        panel.add(colorBox);
        panel.add(label);
        return panel;
    }

    private JPanel createTipCard(String title, String[] tips, Color accentColor) {
        GlassPanel card = new GlassPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        card.setPreferredSize(new Dimension(420, 380));
        card.setMaximumSize(new Dimension(420, 380));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        for(String tip : tips) {
            JPanel tipPanel = new JPanel(new BorderLayout(8, 0));
            tipPanel.setOpaque(false);
            tipPanel.setMaximumSize(new Dimension(360, 80));
            tipPanel.setPreferredSize(new Dimension(360, 80));

            JLabel bullet = new JLabel("•");
            bullet.setFont(new Font("Segoe UI", Font.BOLD, 18));
            bullet.setForeground(accentColor);
            bullet.setVerticalAlignment(SwingConstants.TOP);

            JTextArea tipText = new JTextArea(tip);
            tipText.setEditable(false);
            tipText.setLineWrap(true);
            tipText.setWrapStyleWord(true);
            tipText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tipText.setForeground(TEXT_DARK);
            tipText.setOpaque(false);
            tipText.setBorder(null);

            tipPanel.add(bullet, BorderLayout.WEST);
            tipPanel.add(tipText, BorderLayout.CENTER);

            card.add(tipPanel);
            card.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        return card;
    }
}