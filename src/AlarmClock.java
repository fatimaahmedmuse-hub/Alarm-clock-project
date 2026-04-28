import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AlarmClock extends JFrame {
    private JLabel dateLabel, currentTimeLabel;
    private JTextField alarmTimeField;
    private JButton setAlarmButton, clearAlarmButton, themeButton;
    private String alarmTime = "";
    private boolean isAlarmSet = false;
    private boolean isDarkMode = false;

    public AlarmClock() {
        setTitle("Swing Alarm Clock");
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Display Panel for stacking Date and Time
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setOpaque(false);

        dateLabel = new JLabel("Loading Date...");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        currentTimeLabel = new JLabel("Loading Time...");
        currentTimeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        currentTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        displayPanel.add(dateLabel);
        displayPanel.add(Box.createVerticalStrut(10));
        displayPanel.add(currentTimeLabel);

        // UI Components
        alarmTimeField = new JTextField(5);
        setAlarmButton = new JButton("Set Alarm");
        clearAlarmButton = new JButton("Clear");
        themeButton = new JButton("Toggle Dark Mode");

        add(displayPanel); 
        add(new JLabel("Set Alarm (HH:mm):"));
        add(alarmTimeField);
        add(setAlarmButton);
        add(clearAlarmButton);
        add(themeButton);

        // --- Logic Sections ---

        themeButton.addActionListener(e -> toggleTheme());

        setAlarmButton.addActionListener(e -> {
            String input = alarmTimeField.getText().trim();
            
            // 1. Check if Empty
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a time!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Check Format (HH:mm using Regex)
            if (!input.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                JOptionPane.showMessageDialog(this, "Invalid format! Use HH:mm (e.g., 14:30)", "Format Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            alarmTime = input;
            isAlarmSet = true;
            JOptionPane.showMessageDialog(this, "Alarm set for " + alarmTime);
        });

        clearAlarmButton.addActionListener(e -> {
            isAlarmSet = false;
            alarmTime = "";
            alarmTimeField.setText("");
            JOptionPane.showMessageDialog(this, "Alarm Cleared");
        });

        Timer timer = new Timer(1000, e -> updateDisplay());
        timer.start();

        applyTheme();
        setVisible(true);
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyTheme();
    }

    private void applyTheme() {
        Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
        Color textColor = isDarkMode ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bgColor);
        dateLabel.setForeground(textColor);
        currentTimeLabel.setForeground(textColor);
        themeButton.setText(isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
    }

    private void updateDisplay() {
        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")));
        LocalTime now = LocalTime.now();
        currentTimeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        if (isAlarmSet && now.format(DateTimeFormatter.ofPattern("HH:mm")).equals(alarmTime)) {
            isAlarmSet = false;
            triggerAlarm();
        }
    }

    private void triggerAlarm() {
        String[] options = {"Dismiss", "Snooze (5 mins)"};
        int choice = JOptionPane.showOptionDialog(this, "ALARM!", "Wake Up", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options);

        if (choice == 1) {
            alarmTime = LocalTime.now().plusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"));
            isAlarmSet = true;
            JOptionPane.showMessageDialog(this, "Snoozed until " + alarmTime);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlarmClock::new);
    }
}
