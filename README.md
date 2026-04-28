# Java Swing Alarm Clock

A sleek, functional desktop alarm clock application built with Java Swing. This project features a real-time display of the date and time, theme customization, and a robust alarm system with snooze capabilities.

## Features

- **Real-time Clock:** Displays current date and time (HH:mm:ss) updated every second.
- **Smart Alarm:** Set alarms using the 24-hour format (`HH:mm`).
- **Input Validation:**
  - Prevents empty alarm entries.
  - Enforces correct time formatting via Regex.
- **Snooze & Dismiss:** Postpone your alarm by 5 minutes or dismiss it entirely.
- **Dark Mode:** Toggle between Light and Dark themes for a personalized experience.
- **Responsive Layout:** Uses nested layout managers for a clean, vertical display.

## Technologies Used

- **Language:** Java
- **Library:** Java Swing (GUI)
- **Time Management:** `java.time` API (`LocalTime`, `LocalDate`)

## Prerequisites

To run this project, you need to have the **Java Development Kit (JDK) 8** or higher installed on your machine.

## How to Run

1. **Clone or Download:** Save the `AlarmClock.java` file to your local machine.
2. **Compile:**
   ```bash
   javac AlarmClock.java
   ```
3. **Run:**
   ```bash
   java AlarmClock
   ```

## Usage

1. **Launch** the application.
2. **Set the time** in the input field (e.g., `13:45`).
3. Click **Set Alarm**.
4. Use the **Toggle Dark Mode** button to switch the UI theme.
5. When the alarm triggers, select **Snooze** to add 5 minutes or **Dismiss** to stop it.
