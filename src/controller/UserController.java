package controller;

import java.awt.Frame;

import javax.swing.SwingUtilities;

import exception.ValidationException;
import java.awt.HeadlessException;
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
		// Field-level save is handled by the view via setOnSave; controller wires
		// change-password and persistence callback.
		view.getBtnChangePassword().addActionListener(e -> showChangePasswordDialog());

		// Persist profile when the view triggers a field-level save
		view.setOnSave(profile -> {
			try {
				String username = view.getUserId();
				profileService.saveProfile(username, profile);
				CustomNotification.showSuccess(view, "Thành công", "Thay đổi thông tin thành công");
			} catch (ValidationException | HeadlessException ve) {
				CustomNotification.showError(view, "Lỗi", "Email không hợp lệ");
			}
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