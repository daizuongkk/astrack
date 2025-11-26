package view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import config.AppConfig;

public class DatePickerField extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField dateField;
	private JButton calendarButton;
	private final Calendar selectedDate;

	public DatePickerField() {
		setLayout(new BorderLayout(20, 0));
		setBackground(Color.WHITE);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, AppConfig.Spacing.FIELD_HEIGHT + 13));

		selectedDate = Calendar.getInstance();

		JLabel label = createLabel();
		JPanel datePanel = createDatePanel();

		add(label, BorderLayout.WEST);
		add(datePanel, BorderLayout.CENTER);

		updateDateField();
	}

	private JLabel createLabel() {
		JLabel label = new JLabel("Ngày sinh");
		label.setFont(AppConfig.Fonts.BODY);
		label.setForeground(AppConfig.Colors.TEXT_PRIMARY);
		label.setPreferredSize(new Dimension(AppConfig.Spacing.FIELD_LABEL_WIDTH, AppConfig.Spacing.FIELD_HEIGHT));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		return label;
	}

	private JPanel createDatePanel() {
		JPanel datePanel = new JPanel(new BorderLayout(0, 0));
		datePanel.setBackground(Color.WHITE);
		datePanel.setMaximumSize(new Dimension(350, AppConfig.Spacing.FIELD_HEIGHT));
		datePanel.setPreferredSize(new Dimension(350, AppConfig.Spacing.FIELD_HEIGHT));

		dateField = new JTextField();
		dateField.setFont(AppConfig.Fonts.BODY);
		dateField.setEditable(true);
		dateField.setBackground(Color.WHITE);
		dateField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(AppConfig.Colors.BORDER_LIGHT, 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 8)));

		dateField.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				dateField.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.BORDER_FOCUS, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 8)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				dateField.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.BORDER_LIGHT, 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 8)));
				validateAndFormatDate();
			}
		});

		dateField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
					validateAndFormatDate();
				}
			}
		});

		calendarButton = new JButton(new ImageIcon(AppConfig.Resources.CALENDAR_ICON));
		calendarButton.setPreferredSize(new Dimension(AppConfig.Spacing.FIELD_HEIGHT, AppConfig.Spacing.FIELD_HEIGHT));
		calendarButton.setBackground(Color.WHITE);
		calendarButton.setFocusPainted(false);
		calendarButton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 1, 1, AppConfig.Colors.BORDER_LIGHT),
				BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		calendarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		calendarButton.addActionListener(e -> showCalendarDialog());

		datePanel.add(dateField, BorderLayout.CENTER);
		datePanel.add(calendarButton, BorderLayout.EAST);

		return datePanel;
	}

	private void validateAndFormatDate() {
		String input = dateField.getText().trim();
		SimpleDateFormat standardFormat = new SimpleDateFormat(AppConfig.DateFormats.DISPLAY_FORMAT);

		for (String formatStr : AppConfig.DateFormats.ACCEPTED_FORMATS) {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			format.setLenient(false);
			try {
				Date date = format.parse(input);
				selectedDate.setTime(date);
				dateField.setText(standardFormat.format(selectedDate.getTime()));
				dateField.setForeground(AppConfig.Colors.TEXT_PRIMARY);
				return;
			} catch (ParseException e) {
				// Continue to next format
			}
		}

		// If no format matched, show error
		dateField.setForeground(AppConfig.Colors.ERROR);
		updateDateField();
	}

	private void updateDateField() {
		SimpleDateFormat sdf = new SimpleDateFormat(AppConfig.DateFormats.DISPLAY_FORMAT);
		dateField.setText(sdf.format(selectedDate.getTime()));
		dateField.setForeground(AppConfig.Colors.TEXT_PRIMARY);
	}

	private void showCalendarDialog() {
		updateDateField();
	}

	public String getDate() {
		return dateField.getText();
	}

	public void setDate(String date) {
		if (date != null && !date.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(AppConfig.DateFormats.DISPLAY_FORMAT);
				Date d = sdf.parse(date);
				selectedDate.setTime(d);
				updateDateField();
			} catch (ParseException e) {
			}
		}
	}

	public void setEditable(boolean editable) {
		dateField.setEditable(editable);
		calendarButton.setEnabled(editable);
		Color bgColor = editable ? AppConfig.Colors.BACKGROUND_WHITE : AppConfig.Colors.BACKGROUND_LIGHT;
		dateField.setBackground(bgColor);
	}
}
