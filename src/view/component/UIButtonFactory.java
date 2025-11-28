package view.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import config.AppConfig;

public final class UIButtonFactory {

	private UIButtonFactory() {
	}

	public static JButton createPlainButton(String text, Color bgColor) {
		text = text.toUpperCase();
		JButton button = new JButton(text);

		button.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 20));
		button.setBackground(bgColor);
		button.setForeground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(true);
		button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addChangeListener(e -> {
			if (button.getModel().isPressed()) {
				button.setBackground(bgColor.darker());
			} else if (button.getModel().isRollover()) {
				button.setBackground(bgColor.brighter());
			} else {
				button.setBackground(bgColor);
			}
		});

		return button;
	}

	public static JButton createIconButton(String iconPath, int size) {
		ImageIcon icon = new ImageIcon(iconPath);

		JButton button = new JButton(icon);

		button.setPreferredSize(new Dimension(size, size));
		button.setOpaque(true);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addChangeListener(e -> {
			if (button.getModel().isPressed()) {
			} else if (button.getModel().isRollover()) {
			} else {
			}
		});

		return button;
	}

	public static JButton createPrimaryButton(String text) {
		return createPlainButton(text, AppConfig.Colors.PRIMARY_GREEN);
	}

	public static JButton createSecondaryButton(String text) {
		return createPlainButton(text, new Color(108, 117, 125));
	}

	public static JButton createDangerButton(String text) {
		return createPlainButton(text, AppConfig.Colors.ERROR_RED);
	}

}
