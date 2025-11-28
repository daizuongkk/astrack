package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import config.AppConfig;
import service.UserRepository;

public class ChangePasswordDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPasswordField currentPasswordField;
	private final JPasswordField newPasswordField;
	private final JPasswordField confirmPasswordField;
	private final JButton okButton;
	private final JButton cancelButton;
	private final String username;
	private Consumer<String> onPasswordChanged;

	public ChangePasswordDialog(Frame parent, String username) {
		super(parent, "Đổi mật khẩu", true);
		this.username = username;

		currentPasswordField = createStyledPasswordField();
		newPasswordField = createStyledPasswordField();
		confirmPasswordField = createStyledPasswordField();
		okButton = createStyledButton("Đổi", AppConfig.Colors.PRIMARY_GREEN);
		cancelButton = createStyledButton("Hủy", new Color(200, 200, 200));

		setLayout(new BorderLayout());
		setSize(450, 350);
		setLocationRelativeTo(parent);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		RoundedPanel mainPanel = new RoundedPanel(12, new Color(248, 250, 252));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel contentPanel = createContentPanel();
		JPanel buttonPanel = createButtonPanel();

		mainPanel.add(contentPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);

		setupActions();
	}

	private JPasswordField createStyledPasswordField() {
		JPasswordField field = new JPasswordField() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (hasFocus()) {
					Graphics2D g2d = (Graphics2D) g.create();
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setColor(new Color(13, 110, 253, 10));
					g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
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

	private JButton createStyledButton(String text, Color bgColor) {
		JButton btn = new JButton(text);
		btn.setPreferredSize(new Dimension(110, 40));
		btn.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		btn.setBackground(bgColor);
		btn.setForeground(bgColor.equals(AppConfig.Colors.PRIMARY_GREEN) ? Color.WHITE : Color.BLACK);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		return btn;
	}

	private JPanel createContentPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);

		JLabel currentLabel = new JLabel("Mật khẩu hiện tại");
		currentLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		currentLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		panel.add(currentLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(currentPasswordField);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel newLabel = new JLabel("Mật khẩu mới");
		newLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		newLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		panel.add(newLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(newPasswordField);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel confirmLabel = new JLabel("Xác nhận mật khẩu");
		confirmLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		confirmLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		panel.add(confirmLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(confirmPasswordField);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panel.setOpaque(false);
		panel.add(cancelButton);
		panel.add(okButton);
		return panel;
	}

	private void setupActions() {
		cancelButton.addActionListener(e -> dispose());

		okButton.addActionListener(e -> {
			if (validateAndChangePassword()) {
				dispose();
			}
		});
	}

	private boolean validateAndChangePassword() {
		String current = new String(currentPasswordField.getPassword());
		String newPassword = new String(newPasswordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());

		if (current.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
			CustomNotification.showWarning(this, "Cảnh báo", "Vui lòng nhập đầy đủ thông tin");
			return false;
		}

		if (!newPassword.equals(confirmPassword)) {
			CustomNotification.showError(this, "Lỗi", "Mật khẩu xác nhận không khớp");
			return false;
		}

		if (newPassword.length() < 6) {
			CustomNotification.showError(this, "Lỗi", "Mật khẩu mới phải có ít nhất 6 ký tự");
			return false;
		}

		UserRepository repo = new UserRepository();
		if (!repo.authenticate(username, current)) {
			CustomNotification.showError(this, "Lỗi", "Mật khẩu hiện tại không đúng");
			return false;
		}

		repo.updatePassword(username, newPassword);
		CustomNotification.showSuccess(this, "Thành công", "Đổi mật khẩu thành công");

		if (onPasswordChanged != null) {
			onPasswordChanged.accept(newPassword);
		}

		return true;
	}

	public void setOnPasswordChanged(Consumer<String> callback) {
		this.onPasswordChanged = callback;
	}

	public static String showDialog(Frame parent, String username) {
		ChangePasswordDialog dialog = new ChangePasswordDialog(parent, username);
		dialog.setVisible(true);
		return null;
	}

	// Inner class for rounded panel
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
