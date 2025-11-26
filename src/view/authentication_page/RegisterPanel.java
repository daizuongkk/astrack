package view.authentication_page;

import config.AppConfig;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.MouseEvent;

public class RegisterPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public interface RegisterHandler {
		void onRegister(String username, String password, String confirmPassword);
	}

	private final JTextField usernameField = new JTextField();
	private final JPasswordField passwordField = new JPasswordField();
	private final JPasswordField confirmField = new JPasswordField();

	private RegisterHandler registerHandler;
	private Runnable switchToLogin;

	private final JButton registerButton = new JButton("SIGN UP");
	private final JLabel switchLabel = new JLabel("Already have an account? Login");
	private final JPanel registerWrapper = new JPanel(null);

	public RegisterPanel() {
		initUI();
		initActions();
	}

	private void initUI() {
		int panelWidth = 480;
		int panelHeight = 720;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setLayout(null);
		setBackground(new Color(248, 250, 252));

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, panelWidth, panelHeight);
		panel.setBackground(new Color(248, 250, 252));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(panel);

		JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
		title.setForeground(config.AppConfig.Colors.PRIMARY_GREEN);
		title.setFont(new Font("Segoe UI", Font.BOLD, 28));
		title.setBounds(20, 60, panelWidth - 40, 40);
		panel.add(title);

		JLabel subtitle = new JLabel("Tạo tài khoản mới để quản lý tài sản", SwingConstants.CENTER);
		subtitle.setForeground(new Color(100, 100, 100));
		subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		subtitle.setBounds(20, 105, panelWidth - 40, 25);
		panel.add(subtitle);

		JLabel lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblUser.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		lblUser.setBounds(30, 155, 100, 16);
		panel.add(lblUser);

		usernameField.setBounds(30, 175, panelWidth - 60, 45);
		usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		usernameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		usernameField.setForeground(new Color(33, 33, 33));
		addFieldFocusListener(usernameField);
		panel.add(usernameField);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblPassword.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		lblPassword.setBounds(30, 235, 100, 16);
		panel.add(lblPassword);

		passwordField.setBounds(30, 255, panelWidth - 60, 45);
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		passwordField.setForeground(new Color(33, 33, 33));
		addFieldFocusListener(passwordField);
		panel.add(passwordField);

		JLabel lblPasswordConfirm = new JLabel("Confirm Password");
		lblPasswordConfirm.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblPasswordConfirm.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		lblPasswordConfirm.setBounds(30, 315, 150, 16);
		panel.add(lblPasswordConfirm);

		confirmField.setBounds(30, 335, panelWidth - 60, 45);
		confirmField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		confirmField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		confirmField.setForeground(new Color(33, 33, 33));
		addFieldFocusListener(confirmField);
		panel.add(confirmField);

		registerWrapper.setBounds(30, 415, panelWidth - 60, 52);
		registerWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
		registerWrapper.setOpaque(true);

		registerButton.setBounds(0, 0, registerWrapper.getWidth(), registerWrapper.getHeight());
		registerButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		registerButton.setFocusPainted(false);

		registerButton.setOpaque(false);
		registerButton.setContentAreaFilled(false);

		registerButton.setForeground(Color.WHITE);

		registerButton.setBorder(BorderFactory.createLineBorder(
				AppConfig.Colors.PRIMARY_GREEN,
				2, false));
		registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		registerWrapper.add(registerButton);
		panel.add(registerWrapper);

		switchLabel.setBounds(30, 490, panelWidth - 60, 20);
		switchLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchLabel.setForeground(config.AppConfig.Colors.PRIMARY_GREEN);
		switchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		switchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(switchLabel);
	}

	private void initActions() {
		registerButton.addMouseListener(new MouseAdapter() {
			private boolean inside = false;

			@Override
			public void mouseEntered(MouseEvent e) {
				inside = true;
				registerWrapper.setBackground(AppConfig.Colors.DARK_GREEN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				inside = false;
				registerWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				registerWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				registerWrapper.setBackground(inside ? AppConfig.Colors.DARK_GREEN : AppConfig.Colors.PRIMARY_GREEN);
			}
		});
		registerButton.addActionListener(e -> {
			if (registerHandler != null) {
				registerHandler.onRegister(getUsername(), getPassword(), getConfirmPassword());
			}
		});

		switchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (switchToLogin != null) {
					switchToLogin.run();
				}
			}
		});
	}

	public String getUsername() {
		return usernameField.getText().trim();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public String getConfirmPassword() {
		return new String(confirmField.getPassword());
	}

	public void setOnRegister(RegisterHandler registerHandler) {
		this.registerHandler = registerHandler;
	}

	public void setSwitchToLogin(Runnable switchToLogin) {
		this.switchToLogin = switchToLogin;
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JPasswordField getConfirmField() {
		return confirmField;
	}

	public void clearFields() {
		usernameField.setText("");
		passwordField.setText("");
		confirmField.setText("");
	}

	private void addFieldFocusListener(JTextField field) {
		field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}

			@Override
			public void focusLost(FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}
		});
	}

	private void addFieldFocusListener(JPasswordField field) {
		field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}

			@Override
			public void focusLost(FocusEvent e) {
				field.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}
		});
	}
}
