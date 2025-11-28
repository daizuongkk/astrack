package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import config.AppConfig;
import javax.swing.ImageIcon;

public class CustomNotification extends JDialog {
	private static final long serialVersionUID = 1L;

	// Notification types
	public static final int INFO = 0;
	public static final int SUCCESS = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 3;
	public static final int CONFIRM = 4;

	private int result = -1; // For confirm dialogs

	public CustomNotification(Frame parent, String title, String message, int type) {
		super(parent, title, true);
		initComponents(message, type, false, null);
	}

	public CustomNotification(Frame parent, String title, String message, int type, boolean autoClose) {
		super(parent, title, true);
		initComponents(message, type, autoClose, null);
	}

	public CustomNotification(Frame parent, String title, String message, int type, Runnable onConfirm,
			Runnable onCancel) {
		super(parent, title, true);
		initComponents(message, type, false, new Runnable[] { onConfirm, onCancel });
	}

	private void initComponents(String message, int type, boolean autoClose, Runnable[] callbacks) {
		setLayout(new BorderLayout());
		setSize(450, 180);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		RoundedPanel mainPanel = new RoundedPanel(15, getTypeBackgroundColor(type));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
		messagePanel.setOpaque(false);

		JLabel iconLabel = new JLabel(getTypeIcon(type));
		iconLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 24));
		iconLabel.setForeground(getTypeColor(type));
		messagePanel.add(iconLabel);
		messagePanel.add(Box.createHorizontalStrut(15));

		JLabel messageLabel = new JLabel("<html>" + message + "</html>");
		messageLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		messageLabel.setForeground(AppConfig.Colors.TEXT_PRIMARY);
		messageLabel.setVerticalAlignment(SwingConstants.TOP);
		messagePanel.add(messageLabel);
		messagePanel.add(Box.createHorizontalGlue());

		mainPanel.add(messagePanel, BorderLayout.CENTER);

		JPanel buttonPanel = createButtonPanel(type, callbacks);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		if (autoClose && type != CONFIRM) {
			Timer timer = new Timer(3000, e -> dispose());
			timer.setRepeats(false);
			timer.start();
		}
	}

	private JPanel createButtonPanel(int type, Runnable[] callbacks) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panel.setOpaque(false);

		JButton okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(100, 38));
		okButton.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		okButton.setBackground(getTypeColor(type));
		okButton.setForeground(Color.WHITE);
		okButton.setBorderPainted(false);
		okButton.setFocusPainted(false);
		okButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		okButton.addActionListener(e -> {
			result = 0;
			if (callbacks != null && callbacks[0] != null) {
				callbacks[0].run();
			}
			dispose();
		});

		panel.add(okButton);

		if (type == CONFIRM) {
			JButton cancelButton = new JButton("Hủy");
			cancelButton.setPreferredSize(new Dimension(100, 38));
			cancelButton.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
			cancelButton.setBackground(new Color(200, 200, 200));
			cancelButton.setForeground(Color.BLACK);
			cancelButton.setBorderPainted(false);
			cancelButton.setFocusPainted(false);
			cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			cancelButton.addActionListener(e -> {
				result = 1;
				if (callbacks != null && callbacks[1] != null) {
					callbacks[1].run();
				}
				dispose();
			});

			panel.add(cancelButton);
		}

		return panel;
	}

	private ImageIcon getTypeIcon(int type) {
		return switch (type) {
			case SUCCESS -> new ImageIcon("src/images/success_icon.png");
			case WARNING -> new ImageIcon("src/images/warning_icon.png");
			case ERROR -> new ImageIcon("src/images/error_icon.png");
			case CONFIRM -> new ImageIcon("src/images/warning_icon.png");
			default -> new ImageIcon("src/images/warning_icon.png");
		};
	}

	private Color getTypeColor(int type) {
		return switch (type) {
			case SUCCESS -> AppConfig.Colors.SUCCESS;
			case WARNING -> AppConfig.Colors.WARNING_YELLOW;
			case ERROR -> AppConfig.Colors.ERROR_RED;
			case CONFIRM -> AppConfig.Colors.PRIMARY_GREEN;
			default -> AppConfig.Colors.PRIMARY_GREEN;
		};
	}

	private Color getTypeBackgroundColor(int type) {
		return switch (type) {
			case SUCCESS -> new Color(240, 255, 240);
			case WARNING -> new Color(255, 251, 235);
			case ERROR -> new Color(255, 240, 240);
			case CONFIRM -> new Color(255, 251, 235);
			default -> new Color(240, 248, 255);
		};
	}

	public int getResult() {
		return result;
	}

	public static void showInfo(Component parent, String title, String message) {
		CustomNotification dialog = new CustomNotification((Frame) SwingUtilities.getWindowAncestor(parent), title,
				message, INFO);
		dialog.setVisible(true);
	}

	public static void showSuccess(Component parent, String title, String message) {
		CustomNotification dialog = new CustomNotification((Frame) SwingUtilities.getWindowAncestor(parent), title,
				message, SUCCESS);
		dialog.setVisible(true);
	}

	public static void showWarning(Component parent, String title, String message) {
		CustomNotification dialog = new CustomNotification((Frame) SwingUtilities.getWindowAncestor(parent), title,
				message, WARNING);
		dialog.setVisible(true);
	}

	public static void showError(Component parent, String title, String message) {
		CustomNotification dialog = new CustomNotification((Frame) SwingUtilities.getWindowAncestor(parent), title,
				message, ERROR);
		dialog.setVisible(true);
	}

	public static int showConfirm(Component parent, String title, String message) {
		CustomNotification dialog = new CustomNotification((Frame) SwingUtilities.getWindowAncestor(parent), title,
				message, CONFIRM);
		dialog.setVisible(true);
		return dialog.getResult();
	}

	private static class RoundedPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final int radius;
		private final Color backgroundColor;

		public RoundedPanel(int radius, Color backgroundColor) {
			this.radius = radius;
			this.backgroundColor = backgroundColor;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.setColor(backgroundColor);
			g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

			g2d.setColor(new Color(220, 220, 220));
			g2d.setStroke(new java.awt.BasicStroke(1));
			g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

			super.paintComponent(g);
		}
	}
}
