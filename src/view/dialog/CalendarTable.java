// package view.dialog;

// import java.text.SimpleDateFormat;
// import java.util.Calendar;

// import javax.swing.BorderFactory;
// import javax.swing.JButton;
// import javax.swing.JDialog;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.SwingConstants;
// import javax.swing.SwingUtilities;
// import javax.swing.border.EmptyBorder;

// import org.w3c.dom.events.MouseEvent;

// import config.AppConfig;

// public class CalendarTable {
// private void showCalendarDialog() {
// JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
// "Chọn ngày sinh", false);
// dialog.setLayout(new BorderLayout());
// dialog.setSize(380, 420);
// dialog.setLocationRelativeTo(this);
// dialog.setResizable(false);

// JPanel calendarPanel = new JPanel(new BorderLayout(0, 0));
// calendarPanel.setBackground(Color.WHITE);

// JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
// headerPanel.setBackground(Color.WHITE);
// headerPanel.setBorder(BorderFactory.createCompoundBorder(
// BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
// new EmptyBorder(15, 15, 15, 15)));

// Calendar tempCal = (Calendar) selectedDate.clone();

// JLabel monthYearLabel = new JLabel();
// monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
// monthYearLabel.setForeground(new Color(40, 40, 40));
// monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
// updateMonthYearLabel(monthYearLabel, tempCal);

// JButton prevButton = new JButton("‹");
// JButton nextButton = new JButton("›");
// styleNavButton(prevButton);
// styleNavButton(nextButton);

// JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
// navPanel.setBackground(Color.WHITE);
// navPanel.add(prevButton);
// navPanel.add(monthYearLabel);
// navPanel.add(nextButton);

// headerPanel.add(navPanel, BorderLayout.CENTER);

// JPanel daysPanel = new JPanel(new GridLayout(7, 7, 8, 8));
// daysPanel.setBackground(Color.WHITE);
// daysPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

// String[] dayNames = { "CN", "T2", "T3", "T4", "T5", "T6", "T7" };
// Font dayHeaderFont = new Font("Segoe UI", Font.BOLD, 12);
// for (String day : dayNames) {
// JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
// dayLabel.setFont(dayHeaderFont);
// dayLabel.setForeground(new Color(120, 120, 120));
// daysPanel.add(dayLabel);
// }

// JButton[] dayButtons = new JButton[42];
// for (int i = 0; i < 42; i++) {
// dayButtons[i] = new JButton();
// dayButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
// dayButtons[i].setBackground(Color.WHITE);
// dayButtons[i].setForeground(new Color(60, 60, 60));
// dayButtons[i].setFocusPainted(false);
// dayButtons[i].setBorderPainted(true);
// dayButtons[i].setBorder(BorderFactory.createLineBorder(new Color(240, 240,
// 240), 1));
// dayButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
// dayButtons[i].setPreferredSize(new Dimension(40, 40));
// daysPanel.add(dayButtons[i]);

// final int index = i;
// dayButtons[i].addActionListener(e -> {
// String text = dayButtons[index].getText();
// if (!text.isEmpty() && dayButtons[index].isEnabled()) {
// int day = Integer.parseInt(text);
// tempCal.set(Calendar.DAY_OF_MONTH, day);
// selectedDate = (Calendar) tempCal.clone();
// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
// dateField.setText(sdf.format(selectedDate.getTime()));
// onEditDateField(); // Ghi nhận là có chỉnh sửa
// dialog.dispose();
// }
// });
// }

// Runnable updateCalendar = () -> {
// updateMonthYearLabel(monthYearLabel, tempCal);

// Calendar cal = (Calendar) tempCal.clone();
// cal.set(Calendar.DAY_OF_MONTH, 1);
// int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
// int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

// for (JButton btn : dayButtons) {
// btn.setText("");
// btn.setBackground(Color.WHITE);
// btn.setForeground(new Color(60, 60, 60));
// btn.setEnabled(false);
// btn.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
// for (MouseListener ml : btn.getMouseListeners()) {
// btn.removeMouseListener(ml);
// }
// }

// Calendar today = Calendar.getInstance();
// int todayDay = today.get(Calendar.DAY_OF_MONTH);
// int todayMonth = today.get(Calendar.MONTH);
// int todayYear = today.get(Calendar.YEAR);

// for (int i = 0; i < daysInMonth; i++) {
// int buttonIndex = firstDayOfWeek + i;
// int dayNum = i + 1;
// dayButtons[buttonIndex].setText(String.valueOf(dayNum));
// dayButtons[buttonIndex].setEnabled(true);

// boolean isToday = (dayNum == todayDay &&
// tempCal.get(Calendar.MONTH) == todayMonth &&
// tempCal.get(Calendar.YEAR) == todayYear);

// boolean isSelected = (dayNum == selectedDate.get(Calendar.DAY_OF_MONTH) &&
// tempCal.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
// tempCal.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR));

// if (isSelected) {
// dayButtons[buttonIndex].setBackground(AppConfig.Colors.PRIMARY_GREEN);
// dayButtons[buttonIndex].setForeground(Color.WHITE);
// dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN,
// 2));
// } else if (isToday) {
// dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN,
// 2));
// dayButtons[buttonIndex].setForeground(AppConfig.Colors.PRIMARY_GREEN);
// }

// final boolean selected = isSelected;

// dayButtons[buttonIndex].addMouseListener(new MouseAdapter() {
// @Override
// public void mouseEntered(MouseEvent e) {
// JButton btn = (JButton) e.getSource();
// if (!selected) {
// btn.setBackground(new Color(240, 248, 255));
// btn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_DARK,
// 1));
// }
// }

// @Override
// public void mouseExited(MouseEvent e) {
// JButton btn = (JButton) e.getSource();
// if (!selected) {
// btn.setBackground(Color.WHITE);
// if (isToday) {
// btn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN,
// 2));
// } else {
// btn.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
// }
// }
// }
// });
// }

// daysPanel.revalidate();
// daysPanel.repaint();
// };

// prevButton.addActionListener(e -> {
// tempCal.add(Calendar.MONTH, -1);
// updateCalendar.run();
// });

// nextButton.addActionListener(e -> {
// tempCal.add(Calendar.MONTH, 1);
// updateCalendar.run();
// });

// updateCalendar.run();

// calendarPanel.add(headerPanel, BorderLayout.NORTH);
// calendarPanel.add(daysPanel, BorderLayout.CENTER);

// JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
// bottomPanel.setBackground(Color.WHITE);
// bottomPanel.setBorder(BorderFactory.createCompoundBorder(
// BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
// new EmptyBorder(15, 15, 15, 15)));

// JButton todayButton = new JButton("Hôm nay");
// todayButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
// todayButton.setPreferredSize(new Dimension(120, 38));
// todayButton.setBackground(Color.WHITE);
// todayButton.setForeground(AppConfig.Colors.PRIMARY_GREEN);
// todayButton.setFocusPainted(false);
// todayButton.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN,
// 1));
// todayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

// todayButton.addMouseListener(new MouseAdapter() {
// @Override
// public void mouseEntered(MouseEvent e) {
// todayButton.setBackground(new Color(240, 248, 255));
// }

// @Override
// public void mouseExited(MouseEvent e) {
// todayButton.setBackground(Color.WHITE);
// }
// });

// todayButton.addActionListener(e -> {
// selectedDate = Calendar.getInstance();
// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
// dateField.setText(sdf.format(selectedDate.getTime()));
// onEditDateField();
// dialog.dispose();
// });

// bottomPanel.add(todayButton);

// calendarPanel.add(bottomPanel, BorderLayout.SOUTH);

// dialog.add(calendarPanel);
// dialog.setVisible(true);
// }

// private void updateMonthYearLabel(JLabel label, Calendar cal) {
// String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
// "Tháng 6",
// "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
// label.setText(months[cal.get(Calendar.MONTH)] + " " +
// cal.get(Calendar.YEAR));
// }

// private void styleNavButton(JButton button) {
// button.setFont(new Font("Segoe UI", Font.BOLD, 20));
// button.setBackground(Color.WHITE);
// button.setForeground(AppConfig.Colors.PRIMARY_GREEN);
// button.setFocusPainted(false);
// button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
// button.setCursor(new Cursor(Cursor.HAND_CURSOR));
// button.setContentAreaFilled(false);

// button.addMouseListener(new MouseAdapter() {
// @Override
// public void mouseEntered(MouseEvent e) {
// button.setForeground(AppConfig.Colors.PRIMARY_DARK);
// }

// @Override
// public void mouseExited(MouseEvent e) {
// button.setForeground(AppConfig.Colors.PRIMARY_GREEN);
// }
// });
// }
// }
