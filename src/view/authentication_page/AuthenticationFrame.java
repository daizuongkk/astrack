package view.authentication_page;

import controller.AuthController;
import javax.swing.*;

import config.AppConfig;

import java.awt.*;

public class AuthenticationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String LOGIN_CARD = "LOGIN";
	private static final String REGISTER_CARD = "REGISTER";

	private final CardLayout cardLayout = new CardLayout();
	private final LoginPanel loginPanel = new LoginPanel();
	private final RegisterPanel registerPanel = new RegisterPanel();
	private final JPanel rightPanelContainer = new JPanel(cardLayout);

	@SuppressWarnings("unused")
	private final AuthController authController;

	public AuthenticationFrame() {

		setTitle("AssetTrack - Đăng nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1080, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setIconImage(new ImageIcon("src/images/app_logo.jpg").getImage());

		JLabel lblImage = new JLabel();

		java.net.URL imgUrl = getClass().getResource("/images/app_logo.jpg");
		if (imgUrl != null) {
			ImageIcon icon = new ImageIcon(imgUrl);
			Image scaled = icon.getImage().getScaledInstance(600, 680,
					Image.SCALE_SMOOTH);
			lblImage.setIcon(new ImageIcon(scaled));
		} else {
			System.err.println("Image NOT found via getResource. Working dir: " +
					System.getProperty("user.dir"));
			lblImage.setOpaque(true);
			lblImage.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			lblImage.setText("AssetTrack");
			lblImage.setFont(new Font("Segoe UI", Font.BOLD, 32));
			lblImage.setForeground(Color.WHITE);
			lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		add(lblImage, BorderLayout.WEST);

		rightPanelContainer.setPreferredSize(new Dimension(480, 720));
		rightPanelContainer.setBackground(new Color(248, 250, 252));

		rightPanelContainer.add(loginPanel, LOGIN_CARD);
		rightPanelContainer.add(registerPanel, REGISTER_CARD);

		cardLayout.show(rightPanelContainer, "LOGIN");

		add(rightPanelContainer, BorderLayout.CENTER);
		this.authController = new AuthController(this, loginPanel, registerPanel);

		setVisible(true);

	}

	public void showLogin() {
		loginPanel.clearFields();
		cardLayout.show(rightPanelContainer, LOGIN_CARD);
	}

	public void showRegister() {
		registerPanel.clearFields();
		cardLayout.show(rightPanelContainer, REGISTER_CARD);
	}

}
