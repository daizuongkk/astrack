package controller;

import controller.UserProfilePanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileController implements ActionListener {
	private final UserProfilePanel view;

	// Lưu trữ thông tin người dùng (sau này có thể thay bằng model/database)
	private String savedName = "";
	private String savedEmail = "";
	private String savedPhone = "";
	private String savedDob = "";
	private String savedAddress = "";
	private String savedJob = "";

	public UserProfileController(UserProfilePanel view) {
		this.view = view;
		initializeListeners();
	}

	private void initializeListeners() {
		view.getBtnSave().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getBtnSave()) {
			handleSaveOrEdit();
		}
	}

	private void handleSaveOrEdit() {
		if (view.isEditMode()) {
			if (validateInputs()) {
				saveUserInfo();
				view.toggleEditMode();
				JOptionPane.showMessageDialog(view,
						"Lưu thông tin thành công!",
						"Thành công",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			view.toggleEditMode();
		}
	}

	private boolean validateInputs() {
		String name = view.getUserName().trim();
		String email = view.getEmail().trim();
		String phone = view.getPhone().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(view,
					"Vui lòng nhập họ và tên!",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(view,
					"Vui lòng nhập email!",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			JOptionPane.showMessageDialog(view,
					"Email không hợp lệ!",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (phone.isEmpty()) {
			JOptionPane.showMessageDialog(view,
					"Vui lòng nhập số điện thoại!",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!phone.matches("^(0|\\+84)[0-9]{9,10}$")) {
			JOptionPane.showMessageDialog(view,
					"Số điện thoại không hợp lệ!",
					"Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void saveUserInfo() {
		savedName = view.getUserName();
		savedEmail = view.getEmail();
		savedPhone = view.getPhone();
		savedDob = view.getDob();
		savedAddress = view.getAddress();
		savedJob = view.getJob();

		// TODO: Sau này có thể lưu vào database hoặc file
		System.out.println("=== Thông tin đã lưu ===");
		System.out.println("Tên: " + savedName);
		System.out.println("Email: " + savedEmail);
		System.out.println("SĐT: " + savedPhone);
		System.out.println("Ngày sinh: " + savedDob);
		System.out.println("Địa chỉ: " + savedAddress);
		System.out.println("Nghề nghiệp: " + savedJob);
	}

	// Getters for saved info (dùng khi cần load lại thông tin)
	public String getSavedName() {
		return savedName;
	}

	public String getSavedEmail() {
		return savedEmail;
	}

	public String getSavedPhone() {
		return savedPhone;
	}

	public String getSavedDob() {
		return savedDob;
	}

	public String getSavedAddress() {
		return savedAddress;
	}

	public String getSavedJob() {
		return savedJob;
	}
}