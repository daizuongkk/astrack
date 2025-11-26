package controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserProfilePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField nameField, emailField, phoneField, addressField, jobField;
    private JTextField dateField;
    private JButton calendarButton;
    private Calendar selectedDate;
    private JLabel totalAssetLabel;
    private JButton btnSave;
    private boolean isEditMode = true;

    public UserProfilePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Title section
        JLabel titleLabel = new JLabel("Thông tin cá nhân");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel subtitleLabel = new JLabel("Quản lý thông tin hồ sơ của bạn");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(230, 230, 230));
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Form fields
        contentPanel.add(createFormField("Họ và tên", nameField = createStyledTextField()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFormField("Email", emailField = createStyledTextField()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFormField("Số điện thoại", phoneField = createStyledTextField()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Date of birth with custom calendar picker
        selectedDate = Calendar.getInstance();
        contentPanel.add(createDatePickerField());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFormField("Địa chỉ", addressField = createStyledTextField()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFormField("Nghề nghiệp", jobField = createStyledTextField()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Total asset (read-only)
        JPanel assetPanel = createReadOnlyField("Tổng giá trị tài sản", totalAssetLabel = new JLabel("0 VND"));
        contentPanel.add(assetPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Save button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        btnSave = new JButton("Lưu thay đổi");
        btnSave.setPreferredSize(new Dimension(160, 42));
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(0, 123, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isEditMode) {
                    btnSave.setBackground(new Color(0, 105, 217));
                } else {
                    btnSave.setBackground(new Color(255, 152, 0));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (isEditMode) {
                    btnSave.setBackground(new Color(0, 123, 255));
                } else {
                    btnSave.setBackground(new Color(255, 167, 38));
                }
            }
        });

        buttonPanel.add(btnSave);
        contentPanel.add(buttonPanel);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Add focus effects
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        return field;
    }

    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        label.setPreferredSize(new Dimension(180, 42));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);

        return panel;
    }

    private void validateAndFormatDate() {
        String input = dateField.getText().trim();

        // Try to parse various date formats
        SimpleDateFormat[] formats = {
                new SimpleDateFormat("dd/MM/yyyy"),
                new SimpleDateFormat("dd-MM-yyyy"),
                new SimpleDateFormat("dd.MM.yyyy"),
                new SimpleDateFormat("dd MM yyyy"),
                new SimpleDateFormat("ddMMyyyy"),
                new SimpleDateFormat("d/M/yyyy"),
                new SimpleDateFormat("d-M-yyyy")
        };

        for (SimpleDateFormat format : formats) {
            format.setLenient(false);
            try {
                Date date = format.parse(input);
                selectedDate.setTime(date);

                // Format back to standard format
                SimpleDateFormat standardFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateField.setText(standardFormat.format(selectedDate.getTime()));
                dateField.setForeground(new Color(60, 60, 60));
                return;
            } catch (Exception e) {
                // Continue to next format
            }
        }

        // If no format matched, show error
        dateField.setForeground(Color.RED);
        JOptionPane.showMessageDialog(
                this,
                "Định dạng ngày không hợp lệ!\nVui lòng nhập theo định dạng: dd/MM/yyyy\nVí dụ: 15/03/1990",
                "Lỗi nhập liệu",
                JOptionPane.ERROR_MESSAGE
        );

        // Reset to last valid date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(selectedDate.getTime()));
        dateField.setForeground(new Color(60, 60, 60));
    }

    private JPanel createDatePickerField() {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        JLabel label = new JLabel("Ngày sinh");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        label.setPreferredSize(new Dimension(180, 42));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        // Create date field with calendar button
        JPanel datePanel = new JPanel(new BorderLayout(0, 0));
        datePanel.setBackground(Color.WHITE);
        datePanel.setMaximumSize(new Dimension(350, 42));
        datePanel.setPreferredSize(new Dimension(350, 42));

        dateField = new JTextField();
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setEditable(true);
        dateField.setBackground(Color.WHITE);
        dateField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 8)
        ));

        // Set initial date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(selectedDate.getTime()));
        dateField.setForeground(new Color(60, 60, 60));

        // Add placeholder text behavior
        dateField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                dateField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 8)
                ));
            }
            public void focusLost(FocusEvent e) {
                dateField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 8)
                ));
                validateAndFormatDate();
            }
        });

        // Add key listener for Enter key
        dateField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateAndFormatDate();
                }
            }
        });

        // Calendar button with icon
        calendarButton = new JButton("📅");
        calendarButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        calendarButton.setPreferredSize(new Dimension(42, 42));
        calendarButton.setBackground(Color.WHITE);
        calendarButton.setForeground(new Color(100, 100, 100));
        calendarButton.setFocusPainted(false);
        calendarButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for button
        calendarButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                calendarButton.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(MouseEvent e) {
                calendarButton.setBackground(Color.WHITE);
            }
        });

        calendarButton.addActionListener(e -> showCalendarDialog());

        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(calendarButton, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(datePanel);

        panel.add(label, BorderLayout.WEST);
        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private void showCalendarDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn ngày sinh", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(380, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        // Main calendar panel
        JPanel calendarPanel = new JPanel(new BorderLayout(0, 0));
        calendarPanel.setBackground(Color.WHITE);

        // Header with month/year navigation
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        Calendar tempCal = (Calendar) selectedDate.clone();

        JLabel monthYearLabel = new JLabel();
        monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        monthYearLabel.setForeground(new Color(40, 40, 40));
        monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateMonthYearLabel(monthYearLabel, tempCal);

        JButton prevButton = new JButton("‹");
        JButton nextButton = new JButton("›");
        styleNavButton(prevButton);
        styleNavButton(nextButton);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        navPanel.setBackground(Color.WHITE);
        navPanel.add(prevButton);
        navPanel.add(monthYearLabel);
        navPanel.add(nextButton);

        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Days grid
        JPanel daysPanel = new JPanel(new GridLayout(7, 7, 8, 8));
        daysPanel.setBackground(Color.WHITE);
        daysPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Day labels (header row)
        String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        Font dayHeaderFont = new Font("Segoe UI", Font.BOLD, 12);
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(dayHeaderFont);
            dayLabel.setForeground(new Color(120, 120, 120));
            daysPanel.add(dayLabel);
        }

        // Day buttons
        JButton[] dayButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = new JButton();
            dayButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            dayButtons[i].setBackground(Color.WHITE);
            dayButtons[i].setForeground(new Color(60, 60, 60));
            dayButtons[i].setFocusPainted(false);
            dayButtons[i].setBorderPainted(true);
            dayButtons[i].setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
            dayButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            dayButtons[i].setPreferredSize(new Dimension(40, 40));
            daysPanel.add(dayButtons[i]);

            final int index = i;
            dayButtons[i].addActionListener(e -> {
                String text = dayButtons[index].getText();
                if (!text.isEmpty() && dayButtons[index].isEnabled()) {
                    int day = Integer.parseInt(text);
                    tempCal.set(Calendar.DAY_OF_MONTH, day);
                    selectedDate = (Calendar) tempCal.clone();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dateField.setText(sdf.format(selectedDate.getTime()));
                    dialog.dispose();
                }
            });
        }

        Runnable updateCalendar = () -> {
            updateMonthYearLabel(monthYearLabel, tempCal);

            Calendar cal = (Calendar) tempCal.clone();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
            int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            // Clear all buttons
            for (JButton btn : dayButtons) {
                btn.setText("");
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(60, 60, 60));
                btn.setEnabled(false);
                btn.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
                // Remove all mouse listeners
                for (MouseListener ml : btn.getMouseListeners()) {
                    btn.removeMouseListener(ml);
                }
            }

            // Get today's date
            Calendar today = Calendar.getInstance();
            int todayDay = today.get(Calendar.DAY_OF_MONTH);
            int todayMonth = today.get(Calendar.MONTH);
            int todayYear = today.get(Calendar.YEAR);

            // Fill in the days
            for (int i = 0; i < daysInMonth; i++) {
                int buttonIndex = firstDayOfWeek + i;
                int dayNum = i + 1;
                dayButtons[buttonIndex].setText(String.valueOf(dayNum));
                dayButtons[buttonIndex].setEnabled(true);

                // Check if this is today
                boolean isToday = (dayNum == todayDay &&
                        tempCal.get(Calendar.MONTH) == todayMonth &&
                        tempCal.get(Calendar.YEAR) == todayYear);

                // Check if this is selected date
                boolean isSelected = (dayNum == selectedDate.get(Calendar.DAY_OF_MONTH) &&
                        tempCal.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
                        tempCal.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR));

                if (isSelected) {
                    // Selected date styling
                    dayButtons[buttonIndex].setBackground(new Color(0, 123, 255));
                    dayButtons[buttonIndex].setForeground(Color.WHITE);
                    dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
                } else if (isToday) {
                    // Today styling
                    dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
                    dayButtons[buttonIndex].setForeground(new Color(0, 123, 255));
                }

                // Add hover effect
                final int currentDay = dayNum;
                final boolean selected = isSelected;

                dayButtons[buttonIndex].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();
                        if (!selected) {
                            btn.setBackground(new Color(240, 248, 255));
                            btn.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 1));
                        }
                    }

                    public void mouseExited(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();
                        if (!selected) {
                            btn.setBackground(Color.WHITE);
                            if (isToday) {
                                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
                            } else {
                                btn.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
                            }
                        }
                    }
                });
            }

            daysPanel.revalidate();
            daysPanel.repaint();
        };

        prevButton.addActionListener(e -> {
            tempCal.add(Calendar.MONTH, -1);
            updateCalendar.run();
        });

        nextButton.addActionListener(e -> {
            tempCal.add(Calendar.MONTH, 1);
            updateCalendar.run();
        });

        updateCalendar.run();

        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        // Bottom panel with today button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JButton todayButton = new JButton("Hôm nay");
        todayButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        todayButton.setPreferredSize(new Dimension(120, 38));
        todayButton.setBackground(Color.WHITE);
        todayButton.setForeground(new Color(0, 123, 255));
        todayButton.setFocusPainted(false);
        todayButton.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 1));
        todayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        todayButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                todayButton.setBackground(new Color(240, 248, 255));
            }
            public void mouseExited(MouseEvent e) {
                todayButton.setBackground(Color.WHITE);
            }
        });

        todayButton.addActionListener(e -> {
            selectedDate = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateField.setText(sdf.format(selectedDate.getTime()));
            dialog.dispose();
        });

        bottomPanel.add(todayButton);

        calendarPanel.add(bottomPanel, BorderLayout.SOUTH);

        dialog.add(calendarPanel);
        dialog.setVisible(true);
    }

    private void updateMonthYearLabel(JLabel label, Calendar cal) {
        String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        label.setText(months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 123, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(0, 86, 179));
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0, 123, 255));
            }
        });
    }

    private JPanel createReadOnlyField(String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        label.setPreferredSize(new Dimension(180, 42));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valuePanel.setBackground(new Color(248, 249, 250));
        valuePanel.setPreferredSize(new Dimension(0, 42));
        valuePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(new Color(40, 167, 69));
        valuePanel.add(valueLabel);

        panel.add(label, BorderLayout.WEST);
        panel.add(valuePanel, BorderLayout.CENTER);

        return panel;
    }

    // Getters
    public String getUserName() { return nameField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getPhone() { return phoneField.getText(); }
    public String getAddress() { return addressField.getText(); }
    public String getJob() { return jobField.getText(); }
    public JButton getBtnSave() { return btnSave; }

    // Get date as string in format dd/MM/yyyy
    public String getDob() {
        return dateField.getText();
    }

    // Setters
    public void setTotalAsset(long amount) {
        totalAssetLabel.setText(String.format("%,d VND", amount));
    }

    public void setUserName(String name) { nameField.setText(name); }
    public void setEmail(String email) { emailField.setText(email); }
    public void setPhone(String phone) { phoneField.setText(phone); }
    public void setAddress(String address) { addressField.setText(address); }
    public void setJob(String job) { jobField.setText(job); }

    // Set date from string (format: dd/MM/yyyy)
    public void setDob(String dob) {
        if (dob != null && !dob.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dob);
                selectedDate.setTime(date);
                dateField.setText(dob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEditMode() { return isEditMode; }

    // Toggle between edit and view mode
    public void toggleEditMode() {
        isEditMode = !isEditMode;

        // Enable/disable fields
        nameField.setEditable(isEditMode);
        emailField.setEditable(isEditMode);
        phoneField.setEditable(isEditMode);
        addressField.setEditable(isEditMode);
        jobField.setEditable(isEditMode);

        dateField.setEditable(isEditMode);
        calendarButton.setEnabled(isEditMode);

        // Change appearance
        Color bgColor = isEditMode ? Color.WHITE : new Color(248, 249, 250);
        nameField.setBackground(bgColor);
        emailField.setBackground(bgColor);
        phoneField.setBackground(bgColor);
        addressField.setBackground(bgColor);
        jobField.setBackground(bgColor);
        dateField.setBackground(bgColor);

        // Change button
        if (isEditMode) {
            btnSave.setText("Lưu thay đổi");
            btnSave.setBackground(new Color(0, 123, 255));
        } else {
            btnSave.setText("Sửa thông tin");
            btnSave.setBackground(new Color(255, 167, 38));
        }
    }
}