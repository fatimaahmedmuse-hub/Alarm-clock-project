import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AlarmClock extends JFrame {
    private JLabel dateLabel;
    private JLabel currentTimeLabel;
    private JTextField alarmTimeField;
    private JButton setAlarmButton, clearAlarmButton;
    private String alarmTime = "";
    private boolean isAlarmSet = false;

    public AlarmClock() {
        setTitle("Java Swing Alarm Clock");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        dateLabel = new JLabel("Loading date...");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(dateLabel);

        currentTimeLabel = new JLabel("Loading Time...");
        currentTimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(currentTimeLabel);

        add(new JLabel("Set Alarm (HH:mm):"));
        alarmTimeField = new JTextField(5);
        add(alarmTimeField);

        setAlarmButton = new JButton("Set Alarm");
        add(setAlarmButton);

        clearAlarmButton = new JButton("Clear");
        add(clearAlarmButton);

        // Set Alarm Logic
        setAlarmButton.addActionListener(e -> {
            alarmTime = alarmTimeField.getText();
            isAlarmSet = true;
            JOptionPane.showMessageDialog(this, "Alarm set for " + alarmTime);
        });

        // Clear Alarm Logic
        clearAlarmButton.addActionListener(e -> {
            isAlarmSet = false;
            alarmTime = "";
            alarmTimeField.setText("");
            JOptionPane.showMessageDialog(this, "Alarm Cleared");
        });

        // Timer to update clock and check alarm
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

        setVisible(true);
    }

    private void updateTime() {
        LocalDate today = LocalDate.now();
        String displayDate = today.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
        dateLabel.setText(displayDate);
        LocalTime now = LocalTime.now();
        String displayTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        currentTimeLabel.setText(displayTime);

        String compareTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        if (isAlarmSet && compareTime.equals(alarmTime)) {
            isAlarmSet = false;
            triggerAlarm();
        }
    }

    private void triggerAlarm() {
        String[] options = {"Dismiss", "Snooze (5 mins)"};
        int choice = JOptionPane.showOptionDialog(this, 
                "ALARM! It is now " + alarmTime, 
                "Alarm Triggered", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, 
                null, options, options[0]);

        if (choice == 1) { // User clicked Snooze
            LocalTime snoozeTime = LocalTime.now().plusMinutes(5);
            alarmTime = snoozeTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            isAlarmSet = true;
            JOptionPane.showMessageDialog(this, "Snoozed until " + alarmTime);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlarmClock::new);
    }
}
