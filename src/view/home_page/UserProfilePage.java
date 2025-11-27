package view.home_page;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.UserProfile;
import config.AppConfig;

public final class UserProfilePage extends JPanel {
	private JTextField userIdField;
	private JPasswordField passwordField;
	private JButton btnChangePassword;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField jobField;
	private JTextField dateField;
	private JButton calendarButton;
	private Calendar selectedDate;
	private JLabel totalAssetLabel;
	private final boolean isEditMode = true;
	private Consumer<UserProfile> onSave;

	// Support editing multiple fields before saving
	private final Set<JTextField> editingFields = new HashSet<>();
	private final Map<JTextField, String> originalValues = new HashMap<>();
	private JPanel btnSavePanel;

	public UserProfilePage() {
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

		JLabel titleLabel = new JLabel("Thông tin cá nhân");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
		titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		titleLabel.setForeground(new Color(33, 33, 33));
		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel subtitleLabel = new JLabel("Quản lý thông tin hồ sơ của bạn");
		subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		subtitleLabel.setForeground(new Color(100, 100, 100));
		subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPanel.add(subtitleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		separator.setForeground(new Color(230, 230, 230));
		contentPanel.add(separator);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		contentPanel.add(createFormField("Tên Đăng Nhập", userIdField = createStyledTextField()));
		userIdField.setEditable(false);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		passwordField = createStyledPasswordField();
		passwordField.setEditable(false);
		btnChangePassword = new JButton("Đổi mật khẩu");
		btnChangePassword.setPreferredSize(new Dimension(140, 42));
		btnChangePassword.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnChangePassword.setBackground(AppConfig.Colors.PRIMARY_GREEN);
		btnChangePassword.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		btnChangePassword.setFocusPainted(true);
		btnChangePassword.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
		btnChangePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPanel.add(createFormFieldWithButton("Mật Khẩu", passwordField, btnChangePassword));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		contentPanel.add(createFormField("Họ và tên", nameField = createStyledTextField(), true));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		contentPanel.add(createFormField("Email", emailField = createStyledTextField(), true));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		contentPanel.add(createFormField("Số điện thoại", phoneField = createStyledTextField(), true));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		selectedDate = Calendar.getInstance();
		contentPanel.add(createDatePickerField());
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		contentPanel.add(createFormField("Địa chỉ", addressField = createStyledTextField(), true));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		contentPanel.add(createFormField("Nghề nghiệp", jobField = createStyledTextField(), true));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel assetPanel = createReadOnlyField("Tổng giá trị tài sản", totalAssetLabel = new JLabel("0 VND"));
		contentPanel.add(assetPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		btnSavePanel = buttonPanel;
		contentPanel.add(buttonPanel);

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
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		field.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}
		});

		return field;
	}

	private JPasswordField createStyledPasswordField() {
		JPasswordField field = new JPasswordField();
		field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		field.setPreferredSize(new Dimension(0, 42));
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		field.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
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
		label.setForeground(new Color(40, 40, 40));
		label.setPreferredSize(new Dimension(180, 42));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(label, BorderLayout.WEST);
		panel.add(textField, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createFormField(String labelText, JTextField textField, boolean editable) {
		if (!editable) {
			return createFormField(labelText, textField);
		}

		JPanel panel = new JPanel(new BorderLayout(20, 0));
		panel.setBackground(Color.WHITE);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		label.setForeground(new Color(40, 40, 40));
		label.setPreferredSize(new Dimension(180, 42));
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JButton editBtn = new JButton(new ImageIcon("src/images/edit_icon.png"));
		editBtn.setOpaque(false);

		editBtn.setPreferredSize(new Dimension(55, 42));
		editBtn.setFocusPainted(false);
		editBtn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
		editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JPanel centerPanel = new JPanel(new BorderLayout(8, 0));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(textField, BorderLayout.CENTER);
		centerPanel.add(editBtn, BorderLayout.EAST);

		panel.add(label, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);

		textField.setEditable(false);
		textField.setBackground(new Color(248, 249, 250));

		editBtn.addActionListener(e -> onEditField(textField));

		return panel;
	}

	private JPanel createFormFieldWithButton(String labelText, JComponent textField, JButton button) {
		JPanel panel = new JPanel(new BorderLayout(20, 0));
		panel.setBackground(Color.WHITE);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		label.setForeground(new Color(40, 40, 40));
		label.setPreferredSize(new Dimension(180, 42));
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel centerPanel = new JPanel(new BorderLayout(8, 0));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(textField, BorderLayout.CENTER);
		centerPanel.add(button, BorderLayout.EAST);

		panel.add(label, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);

		return panel;
	}

	private void validateAndFormatDate() {
		String input = dateField.getText().trim();

		// Allow empty date field
		if (input.isEmpty()) {
			dateField.setForeground(new Color(60, 60, 60));
			return;
		}

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

				SimpleDateFormat standardFormat = new SimpleDateFormat("dd/MM/yyyy");
				dateField.setText(standardFormat.format(selectedDate.getTime()));
				dateField.setForeground(new Color(60, 60, 60));
				return;
			} catch (ParseException e) {
			}
		}

		dateField.setForeground(Color.RED);
		JOptionPane.showMessageDialog(
				this,
				"Định dạng không hợp lệ!",
				"Input Error",
				JOptionPane.ERROR_MESSAGE);

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

		JPanel centerPanel = new JPanel(new BorderLayout(8, 0));
		centerPanel.setBackground(Color.WHITE);

		JPanel datePanel = new JPanel(new BorderLayout(0, 0));
		datePanel.setBackground(Color.WHITE);
		datePanel.setPreferredSize(new Dimension(350, 42));

		dateField = new JTextField();
		dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dateField.setEditable(false);
		dateField.setBackground(new Color(248, 249, 250));
		dateField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 8)));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dateField.setText(sdf.format(selectedDate.getTime()));
		dateField.setForeground(new Color(60, 60, 60));

		dateField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dateField.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 8)));
			}

			@Override
			public void focusLost(FocusEvent e) {
				dateField.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 8)));
				validateAndFormatDate();
			}
		});

		dateField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					validateAndFormatDate();
				}
			}
		});

		calendarButton = new JButton(new ImageIcon("src/images/calendar_icon.png"));
		calendarButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		calendarButton.setPreferredSize(new Dimension(42, 42));
		calendarButton.setBackground(Color.WHITE);
		calendarButton.setForeground(new Color(100, 100, 100));
		calendarButton.setFocusPainted(false);
		calendarButton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 1, 1, new Color(220, 220, 220)),
				BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		calendarButton.setEnabled(false);

		calendarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (calendarButton.isEnabled()) {
					calendarButton.setBackground(new Color(245, 245, 245));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				calendarButton.setBackground(Color.WHITE);
			}
		});

		calendarButton.addActionListener(e -> showCalendarDialog());

		datePanel.add(dateField, BorderLayout.CENTER);
		datePanel.add(calendarButton, BorderLayout.EAST);

		JButton editBtn = new JButton(new ImageIcon("src/images/edit_icon.png"));
		editBtn.setPreferredSize(new Dimension(55, 42));
		editBtn.setOpaque(true);
		editBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		editBtn.setBackground(new Color(255, 255, 255));
		editBtn.setFocusPainted(false);
		editBtn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
		editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		editBtn.addActionListener(e -> onEditDateField());

		centerPanel.add(datePanel, BorderLayout.CENTER);
		centerPanel.add(editBtn, BorderLayout.EAST);

		panel.add(label, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);

		return panel;
	}

	private void showCalendarDialog() {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn ngày sinh", true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(380, 420);
		dialog.setLocationRelativeTo(this);
		dialog.setResizable(false);

		JPanel calendarPanel = new JPanel(new BorderLayout(0, 0));
		calendarPanel.setBackground(Color.WHITE);

		JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
				new EmptyBorder(15, 15, 15, 15)));

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

		JPanel daysPanel = new JPanel(new GridLayout(7, 7, 8, 8));
		daysPanel.setBackground(Color.WHITE);
		daysPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		String[] dayNames = { "CN", "T2", "T3", "T4", "T5", "T6", "T7" };
		Font dayHeaderFont = new Font("Segoe UI", Font.BOLD, 12);
		for (String day : dayNames) {
			JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
			dayLabel.setFont(dayHeaderFont);
			dayLabel.setForeground(new Color(120, 120, 120));
			daysPanel.add(dayLabel);
		}

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

			for (JButton btn : dayButtons) {
				btn.setText("");
				btn.setBackground(Color.WHITE);
				btn.setForeground(new Color(60, 60, 60));
				btn.setEnabled(false);
				btn.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
				for (MouseListener ml : btn.getMouseListeners()) {
					btn.removeMouseListener(ml);
				}
			}

			Calendar today = Calendar.getInstance();
			int todayDay = today.get(Calendar.DAY_OF_MONTH);
			int todayMonth = today.get(Calendar.MONTH);
			int todayYear = today.get(Calendar.YEAR);

			for (int i = 0; i < daysInMonth; i++) {
				int buttonIndex = firstDayOfWeek + i;
				int dayNum = i + 1;
				dayButtons[buttonIndex].setText(String.valueOf(dayNum));
				dayButtons[buttonIndex].setEnabled(true);

				boolean isToday = (dayNum == todayDay &&
						tempCal.get(Calendar.MONTH) == todayMonth &&
						tempCal.get(Calendar.YEAR) == todayYear);

				boolean isSelected = (dayNum == selectedDate.get(Calendar.DAY_OF_MONTH) &&
						tempCal.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
						tempCal.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR));

				if (isSelected) {
					dayButtons[buttonIndex].setBackground(AppConfig.Colors.PRIMARY_GREEN);
					dayButtons[buttonIndex].setForeground(Color.WHITE);
					dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
				} else if (isToday) {
					// Today styling
					dayButtons[buttonIndex].setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
					dayButtons[buttonIndex].setForeground(AppConfig.Colors.PRIMARY_GREEN);
				}

				// Add hover effect
				final boolean selected = isSelected;

				dayButtons[buttonIndex].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						JButton btn = (JButton) e.getSource();
						if (!selected) {
							btn.setBackground(new Color(240, 248, 255));
							btn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_DARK, 1));
						}
					}

					@Override
					public void mouseExited(MouseEvent e) {
						JButton btn = (JButton) e.getSource();
						if (!selected) {
							btn.setBackground(Color.WHITE);
							if (isToday) {
								btn.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2));
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
				new EmptyBorder(15, 15, 15, 15)));

		JButton todayButton = new JButton("Hôm nay");
		todayButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		todayButton.setPreferredSize(new Dimension(120, 38));
		todayButton.setBackground(Color.WHITE);
		todayButton.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		todayButton.setFocusPainted(false);
		todayButton.setBorder(BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 1));
		todayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		todayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				todayButton.setBackground(new Color(240, 248, 255));
			}

			@Override
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
		String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
				"Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
		label.setText(months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
	}

	private void styleNavButton(JButton button) {
		button.setFont(new Font("Segoe UI", Font.BOLD, 20));
		button.setBackground(Color.WHITE);
		button.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(false);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(AppConfig.Colors.PRIMARY_DARK);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(AppConfig.Colors.PRIMARY_GREEN);
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
		label.setForeground(new Color(40, 40, 40)); // Better contrast
		label.setPreferredSize(new Dimension(180, 42));
		label.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		valuePanel.setBackground(new Color(248, 249, 250));
		valuePanel.setPreferredSize(new Dimension(0, 42));
		valuePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		valueLabel.setForeground(AppConfig.Colors.PRIMARY_DARK); // Use theme primary dark for contrast
		valuePanel.add(valueLabel);

		panel.add(label, BorderLayout.WEST);
		panel.add(valuePanel, BorderLayout.CENTER);

		return panel;
	}

	public String getUserName() {
		return nameField.getText();
	}

	public String getEmail() {
		return emailField.getText();
	}

	public String getPhone() {
		return phoneField.getText();
	}

	public String getAddress() {
		return addressField.getText();
	}

	public String getJob() {
		return jobField.getText();
	}

	public JButton getBtnChangePassword() {
		return btnChangePassword;
	}

	public String getDob() {
		return dateField.getText();
	}

	public void setTotalAsset(long amount) {
		totalAssetLabel.setText(String.format("%,d VND", amount));
	}

	public void setUserName(String name) {
		nameField.setText(name);
	}

	public void setEmail(String email) {
		emailField.setText(email);
	}

	public void setPhone(String phone) {
		phoneField.setText(phone);
	}

	public void setAddress(String address) {
		addressField.setText(address);
	}

	public void setJob(String job) {
		jobField.setText(job);
	}

	public String getUserId() {
		return userIdField.getText();
	}

	public String getPassworField() {
		return new String(passwordField.getPassword());
	}

	public void setUserid(String userid) {
		this.userIdField.setText(userid);
	}

	public void setPassworField(String password) {
		this.passwordField.setText(password);
	}

	// Set date from string (format: dd/MM/yyyy)
	public void setDob(String dob) {
		if (dob != null && !dob.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = sdf.parse(dob);
				selectedDate.setTime(date);
				dateField.setText(dob);
			} catch (ParseException e) {
			}
		}
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	private void onEditField(JTextField field) {
		// If not already editing, save original and add to editing set
		if (!editingFields.contains(field)) {
			originalValues.put(field, field.getText());
			editingFields.add(field);
		}

		// Enable editing for this field
		field.setEditable(true);
		field.setBackground(Color.WHITE);
		field.requestFocus();

		// Update Save/Cancel all buttons
		updateSaveCancelPanel();
	}

	private void onEditDateField() {
		// Treat dateField as a regular editable field that can be part of batch edits
		if (!editingFields.contains(dateField)) {
			originalValues.put(dateField, dateField.getText());
			editingFields.add(dateField);
		}

		dateField.setEditable(true);
		dateField.setBackground(Color.WHITE);
		calendarButton.setEnabled(true);
		dateField.requestFocus();

		updateSaveCancelPanel();
	}

	private void saveBtnEdits() {
		if (!editingFields.isEmpty()) {
			// Validate email and phone only if they have values
			if (editingFields.contains(emailField)) {
				String email = emailField.getText().trim();
				if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
					JOptionPane.showMessageDialog(
							this,
							"Email không hợp lệ!",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			if (editingFields.contains(phoneField)) {
				String phone = phoneField.getText().trim();
				if (!phone.isEmpty() && !phone.matches("^(0|\\+84)[0-9]{9,10}$")) {
					JOptionPane.showMessageDialog(
							this,
							"Số điện thoại không hợp lệ!",
							"Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			// finalize date formatting if date field is edited
			if (editingFields.contains(dateField)) {
				validateAndFormatDate();
			}

			// Make all edited fields read-only and reset background
			for (JTextField f : editingFields) {
				f.setEditable(false);
				f.setBackground(new Color(248, 249, 250));
			}

			// disable calendar if date was edited
			if (editingFields.contains(dateField)) {
				calendarButton.setEnabled(false);
			}

			// Clear tracking
			editingFields.clear();
			originalValues.clear();

			// Clear button panel
			JPanel buttonPanel = (JPanel) btnSavePanel;
			buttonPanel.removeAll();
			buttonPanel.revalidate();
			buttonPanel.repaint();

			// Trigger the onSave callback with collected profile
			if (onSave != null) {
				onSave.accept(collectProfile());
			}
		}
	}

	private void cancelBtnEdits() {
		if (!editingFields.isEmpty()) {
			// restore originals
			for (JTextField f : new HashSet<>(editingFields)) {
				String orig = originalValues.getOrDefault(f, "");
				f.setText(orig);
				f.setEditable(false);
				f.setBackground(new Color(248, 249, 250));
			}

			// disable calendar
			calendarButton.setEnabled(false);

			// Clear tracking
			editingFields.clear();
			originalValues.clear();

			// Clear button panel
			JPanel buttonPanel = (JPanel) btnSavePanel;
			buttonPanel.removeAll();
			buttonPanel.revalidate();
			buttonPanel.repaint();
		}
	}

	private void updateSaveCancelPanel() {
		JPanel buttonPanel = (JPanel) btnSavePanel;
		buttonPanel.removeAll();
		if (!editingFields.isEmpty()) {
			JButton saveBtn = new JButton("Lưu Hồ Sơ");
			saveBtn.setPreferredSize(new Dimension(100, 42));
			saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
			saveBtn.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			saveBtn.setForeground(AppConfig.Colors.TEXT_WHITE);
			saveBtn.setFocusPainted(false);
			saveBtn.setBorderPainted(false);

			saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			JButton cancelBtn = new JButton("Hủy Bỏ");
			cancelBtn.setPreferredSize(new Dimension(80, 42));
			cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
			cancelBtn.setBackground(Color.RED);
			cancelBtn.setForeground(Color.WHITE);
			cancelBtn.setFocusPainted(false);
			cancelBtn.setBorderPainted(false);

			cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			buttonPanel.add(Box.createRigidArea(new Dimension(8, 0)));
			buttonPanel.add(cancelBtn);
			buttonPanel.add(Box.createRigidArea(new Dimension(8, 0)));
			buttonPanel.add(saveBtn);

			saveBtn.addActionListener(e -> saveBtnEdits());
			cancelBtn.addActionListener(e -> cancelBtnEdits());
		}
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}

	public void setProfile(UserProfile profile) {
		nameField.setText(nullSafe(profile.getFullName()));
		emailField.setText(nullSafe(profile.getEmail()));
		phoneField.setText(nullSafe(profile.getPhone()));
		dateField.setText(nullSafe(profile.getDob()));
		addressField.setText(nullSafe(profile.getAddress()));
		jobField.setText(nullSafe(profile.getOccupation()));
	}

	public void setOnSave(Consumer<UserProfile> onSave) {
		this.onSave = onSave;
	}

	public UserProfile collectProfile() {
		return new UserProfile(
				nameField.getText().trim(),
				emailField.getText().trim(),
				phoneField.getText().trim(),
				dateField.getText().trim(),
				addressField.getText().trim(),
				jobField.getText().trim());
	}

	private static String nullSafe(String value) {
		return value == null ? "" : value;
	}
}
