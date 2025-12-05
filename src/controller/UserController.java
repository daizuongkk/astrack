package controller;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import service.UserProfileService;
import view.dialog.ChangePasswordDialog;
import view.dialog.CustomNotification;
import view.home_page.UserProfilePage;

public class UserController {
	private final UserProfilePage view;
	private final UserProfileService profileService;

	public UserController(UserProfilePage view, UserProfileService profileService) {
		this.view = view;
		this.profileService = profileService;
		initializeListeners();
	}

	private void initializeListeners() {
		view.getBtnChangePassword().addActionListener(e -> showChangePasswordDialog());

		view.setOnSave(profile -> {

			String username = view.getUserId();
			profileService.saveProfile(username, profile);
			System.out.println("succes");
			CustomNotification.showSuccess(view, "Thành công", "Thay đổi thông tin thành công");
		});
	}

	private void showChangePasswordDialog() {
		String username = view.getUserId();
		if (username == null || username.isBlank()) {
			CustomNotification.showError(view, "Lỗi", "Không có tên người dùng");
			return;
		}

		Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(view);
		ChangePasswordDialog dialog = new ChangePasswordDialog(parentFrame, username);
		dialog.setOnPasswordChanged(newPassword -> view.setPassworField(newPassword));
		dialog.setVisible(true);
	}
}