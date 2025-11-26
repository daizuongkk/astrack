package view.authentication_page;

import config.AppConfig;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.util.function.BiConsumer;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField usernameField = new JTextField();
	private final JPasswordField passwordField = new JPasswordField();
	private BiConsumer<String, String> onLogin;
	private Runnable switchToRegister;

	private final JButton loginButton = new JButton("LOG IN");
	private final JLabel switchLabel = new JLabel("Don't have an account? Signup");
	private final JPanel loginWrapper = new JPanel(null);

	public LoginPanel() {
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

		JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
		title.setForeground(config.AppConfig.Colors.PRIMARY_GREEN);
		title.setFont(new Font("Segoe UI", Font.BOLD, 28));
		title.setBounds(20, 80, panelWidth - 40, 40);
		panel.add(title);

		JLabel subtitle = new JLabel("Đăng nhập vào tài khoản của bạn", SwingConstants.CENTER);
		subtitle.setForeground(new Color(100, 100, 100));
		subtitle.setFont(AppConfig.Fonts.BUTTON_PLAIN);
		subtitle.setBounds(20, 125, panelWidth - 40, 25);
		panel.add(subtitle);

		JLabel lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblUser.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		lblUser.setBounds(30, 180, 100, 16);
		panel.add(lblUser);

		usernameField.setBounds(30, 200, panelWidth - 60, 45);
		usernameField.setFont(AppConfig.Fonts.SUBTITLE);
		usernameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		usernameField.setForeground(new Color(33, 33, 33));
		addFieldFocusListener(usernameField);
		panel.add(usernameField);

		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblPass.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		lblPass.setBounds(30, 260, 100, 16);
		panel.add(lblPass);

		passwordField.setBounds(30, 280, panelWidth - 60, 45);
		passwordField.setFont(AppConfig.Fonts.SUBTITLE);
		passwordField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		passwordField.setForeground(new Color(33, 33, 33));
		addFieldFocusListener(passwordField);
		panel.add(passwordField);

		loginWrapper.setBounds(30, 360, panelWidth - 60, 52);
		loginWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
		loginWrapper.setOpaque(true);

		loginButton.setBounds(0, 0, loginWrapper.getWidth(), loginWrapper.getHeight());
		loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		loginButton.setFocusPainted(false);

		loginButton.setOpaque(false);
		loginButton.setContentAreaFilled(false);

		loginButton.setForeground(Color.WHITE);

		loginButton.setBorder(BorderFactory.createLineBorder(
				AppConfig.Colors.PRIMARY_GREEN,
				2, false));
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		loginWrapper.add(loginButton);
		panel.add(loginWrapper);

		switchLabel.setBounds(30, 435, panelWidth - 60, 20);
		switchLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchLabel.setForeground(config.AppConfig.Colors.PRIMARY_GREEN);
		switchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		switchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(switchLabel);
	}

	private void initActions() {
		loginButton.addMouseListener(new MouseAdapter() {
			private boolean inside = false;

			@Override
			public void mouseEntered(MouseEvent e) {
				inside = true;
				loginWrapper.setBackground(AppConfig.Colors.DARK_GREEN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				inside = false;
				loginWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				loginWrapper.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				loginWrapper.setBackground(inside ? AppConfig.Colors.DARK_GREEN : AppConfig.Colors.PRIMARY_GREEN);
			}
		});
		loginButton.addActionListener(e -> {
			if (onLogin != null) {
				onLogin.accept(getUsername(), getPassword());
			}
		});

		switchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (switchToRegister != null) {
					switchToRegister.run();
				}
			}
		});
	}

	public void setOnLogin(BiConsumer<String, String> onLogin) {
		this.onLogin = onLogin;
	}

	public void setSwitchToRegister(Runnable switchToRegister) {
		this.switchToRegister = switchToRegister;
	}

	public String getUsername() {
		return usernameField.getText().trim();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JLabel getSwitchLabel() {
		return switchLabel;
	}

	public void clearFields() {
		usernameField.setText("");
		passwordField.setText("");
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
