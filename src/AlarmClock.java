import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AlarmClock extends JFrame {
    
    private JLabel currentTimeLabel;
    private JTextField alarmTimeField;
    private JButton setAlarmButton;
    private String alarmTime = "";
    private boolean isAlarmSet = false;

    public AlarmClock() {
        // Setup the Frame
        setTitle("Java Swing Alarm Clock");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // 1. Current Time Label
        currentTimeLabel = new JLabel("Loading...");
        currentTimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(currentTimeLabel);

        // 2. Alarm Input Field (HH:mm)
        add(new JLabel("Set Alarm (HH:mm):"));
        alarmTimeField = new JTextField(5);
        add(alarmTimeField);

        // 3. Set Alarm Button
        setAlarmButton = new JButton("Set Alarm");
        add(setAlarmButton);

        // Button Logic
        setAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmTime = alarmTimeField.getText();
                isAlarmSet = true;
                JOptionPane.showMessageDialog(null, "Alarm set for " + alarmTime);
            }
        });

        // 4. Timer to update every second
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        setVisible(true);
    }

    private void updateTime() {
        // Get current time
        LocalTime now = LocalTime.now();
        
        // Format for display (with seconds)
        String displayTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        currentTimeLabel.setText(displayTime);

        // Format for comparison (matching user input HH:mm)
        String compareTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        // Check if alarm matches
        if (isAlarmSet && compareTime.equals(alarmTime)) {
            isAlarmSet = false; // Reset alarm so it doesn't fire every second for that minute
            JOptionPane.showMessageDialog(this, "ALARM! It is now " + alarmTime);
        }
    }

    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new AlarmClock());
    }
}