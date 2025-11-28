package controller;

import exception.AuthenticationException;
import exception.ValidationException;
import java.awt.HeadlessException;
import repository.IAssetRepository;
import repository.IActivityRepository;
import repository.IProfileRepository;
import service.ActivityRepository;
import service.AssetRepository;
import service.AuthenticationService;
import service.ProfileRepository;
import service.SessionManager;
import service.UserRepository;
import view.authentication_page.AuthenticationFrame;
import view.authentication_page.LoginPanel;
import view.authentication_page.RegisterPanel;
import view.home_page.HomePage;
import view.dialog.CustomNotification;

public class AuthController {
	private final AuthenticationFrame frame;
	private final LoginPanel loginPanel;
	private final RegisterPanel registerPanel;
	private final AuthenticationService authService;
	private final IProfileRepository profileRepository;
	private final IAssetRepository assetRepository;
	private final IActivityRepository activityRepository;

	public AuthController(AuthenticationFrame frame, LoginPanel loginPanel, RegisterPanel registerPanel) {
		this.frame = frame;
		this.loginPanel = loginPanel;
		this.registerPanel = registerPanel;
		this.authService = new AuthenticationService(new UserRepository());
		this.profileRepository = new ProfileRepository();
		this.assetRepository = new AssetRepository();
		this.activityRepository = new ActivityRepository();
		wireEvents();
	}

	private void wireEvents() {
		loginPanel.setOnLogin(this::handleLogin);
		registerPanel.setOnRegister(this::handleRegister);
		loginPanel.setSwitchToRegister(frame::showRegister);
		registerPanel.setSwitchToLogin(frame::showLogin);
	}

	private void handleLogin(String username, String password) {
		try {
			authService.authenticate(username, password);
			SessionManager.setCurrentUser(username);
			frame.dispose();

			new HomePage(username, profileRepository, assetRepository, activityRepository,
					password == null ? 0 : password.length())
					.setVisible(true);
		} catch (ValidationException | AuthenticationException e) {
			CustomNotification.showError(frame, "Lỗi", e.getMessage());
		} catch (Exception e) {
			CustomNotification.showError(frame, "Lỗi", "Đã xảy ra lỗi không mong muốn");
		}
	}

	private void handleRegister(String username, String password, String confirmPassword) {
		try {
			authService.register(username, password, confirmPassword);
			CustomNotification.showSuccess(frame, "Thành công", "Đăng ký thành công. Chuyển đến trang đăng nhập");
			frame.showLogin();
		} catch (ValidationException e) {
			CustomNotification.showError(frame, "Lỗi", e.getMessage());
		} catch (HeadlessException e) {
			CustomNotification.showError(frame, "Lỗi", "Đã xảy ra lỗi không mong muốn");
		}
	}
}