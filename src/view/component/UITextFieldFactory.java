package view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import config.AppConfig;

public final class UITextFieldFactory {

	private UITextFieldFactory() {
	}

	public static JTextField createStyledTextField() {
		JTextField field = new JTextField() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (hasFocus()) {
					Graphics2D g2d = (Graphics2D) g.create();
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setColor(new Color(13, 110, 253, 10));
					g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
					g2d.dispose();
				}
			}
		};

		field.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		field.setPreferredSize(new Dimension(280, 42));
		field.setForeground(AppConfig.Colors.TEXT_PRIMARY);
		field.setCaretColor(AppConfig.Colors.PRIMARY_GREEN);
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		field.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(9, 14, 9, 14)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(10, 15, 10, 15)));
			}
		});

		return field;
	}

	public static JTextField createStyledTextField(int width) {
		JTextField field = createStyledTextField();
		field.setPreferredSize(new Dimension(width, 42));
		return field;
	}
}
