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
        setTitle("Alarm Clock Pro");
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create a Panel specifically for the Date and Time to stack them vertically
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setOpaque(false); // Let the frame background show through

        dateLabel = new JLabel("Loading Date...");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center within panel
        
        currentTimeLabel = new JLabel("Loading Time...");
        currentTimeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        currentTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center within panel

        displayPanel.add(dateLabel);
        displayPanel.add(Box.createVerticalStrut(10)); // Add a small gap between lines
        displayPanel.add(currentTimeLabel);

        // UI Components
        alarmTimeField = new JTextField(5);
        setAlarmButton = new JButton("Set Alarm");
        clearAlarmButton = new JButton("Clear");
        themeButton = new JButton("Toggle Dark Mode");

        // Add components to Frame
        add(displayPanel); 
        add(new JLabel("Set Alarm (HH:mm):"));
        add(alarmTimeField);
        add(setAlarmButton);
        add(clearAlarmButton);
        add(themeButton);

        themeButton.addActionListener(e -> toggleTheme());
        setAlarmButton.addActionListener(e -> {
            alarmTime = alarmTimeField.getText();
            isAlarmSet = true;
            JOptionPane.showMessageDialog(this, "Alarm set for " + alarmTime);
        });

        clearAlarmButton.addActionListener(e -> {
            isAlarmSet = false;
            alarmTime = "";
            alarmTimeField.setText("");
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
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlarmClock::new);
    }
}
