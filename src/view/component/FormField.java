package view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import config.AppConfig;

public class FormField extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel label;
	private final JTextField textField;

	public FormField(String labelText) {
		setLayout(new BorderLayout(20, 0));
		setBackground(Color.WHITE);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, AppConfig.Spacing.FIELD_HEIGHT + 13));

		label = createLabel(labelText);
		textField = createTextField();

		add(label, BorderLayout.WEST);
		add(textField, BorderLayout.CENTER);
	}

	private JLabel createLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(AppConfig.Fonts.BODY);
		lbl.setForeground(AppConfig.Colors.TEXT_PRIMARY);
		lbl.setPreferredSize(new Dimension(AppConfig.Spacing.FIELD_LABEL_WIDTH, AppConfig.Spacing.FIELD_HEIGHT));
		lbl.setHorizontalAlignment(SwingConstants.LEFT);
		return lbl;
	}

	private JTextField createTextField() {
		JTextField field = new JTextField();
		field.setFont(AppConfig.Fonts.BODY);
		field.setPreferredSize(new Dimension(0, AppConfig.Spacing.FIELD_HEIGHT));
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(AppConfig.Colors.BORDER_LIGHT, 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		field.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.BORDER_FOCUS, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent evt) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.BORDER_LIGHT, 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}
		});

		return field;
	}

	public JTextField getTextField() {
		return textField;
	}

	public String getText() {
		return textField.getText().trim();
	}

	public void setText(String text) {
		textField.setText(text);
	}

	public void setEditable(boolean editable) {
		textField.setEditable(editable);
		Color bgColor = editable ? AppConfig.Colors.BACKGROUND_WHITE : AppConfig.Colors.BACKGROUND_LIGHT;
		textField.setBackground(bgColor);
	}
}
