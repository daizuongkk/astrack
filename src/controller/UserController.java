
package controller;

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import exception.ValidationException;
import java.awt.HeadlessException;
import service.UserProfileService;
import view.dialog.ChangePasswordDialog;
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
				JOptionPane.showMessageDialog(view,
						"Lưu thông tin thành công!",
						"Thành công",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (ValidationException ve) {
				JOptionPane.showMessageDialog(view, ve.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException ex) {
				JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi khi lưu hồ sơ", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void showChangePasswordDialog() {
		String username = view.getUserId();
		if (username == null || username.isBlank()) {
			JOptionPane.showMessageDialog(view, "Không có tên người dùng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(view);
		ChangePasswordDialog dialog = new ChangePasswordDialog(parentFrame, username);
		dialog.setOnPasswordChanged(newPassword -> view.setPassworField(newPassword));
		dialog.setVisible(true);
	}
}