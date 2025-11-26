package view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

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

		currentPasswordField = new JPasswordField();
		newPasswordField = new JPasswordField();
		confirmPasswordField = new JPasswordField();
		okButton = new JButton("Đổi");
		cancelButton = new JButton("Hủy");

		setLayout(new BorderLayout());
		setSize(420, 280);
		setLocationRelativeTo(parent);
		setResizable(false);

		JPanel contentPanel = createContentPanel();
		JPanel buttonPanel = createButtonPanel();

		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setupFields();
		setupActions();
	}

	private JPanel createContentPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(12, 12, 12, 12));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(new JLabel("Mật khẩu hiện tại"));
		currentPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(currentPasswordField);
		panel.add(Box.createRigidArea(new Dimension(0, 8)));

		panel.add(new JLabel("Mật khẩu mới"));
		newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(newPasswordField);
		panel.add(Box.createRigidArea(new Dimension(0, 8)));

		panel.add(new JLabel("Xác nhận mật khẩu mới"));
		confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		panel.add(Box.createRigidArea(new Dimension(0, 6)));
		panel.add(confirmPasswordField);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(cancelButton);
		panel.add(okButton);
		return panel;
	}

	private void setupFields() {
		Dimension maxSize = new Dimension(Integer.MAX_VALUE, 36);
		currentPasswordField.setMaximumSize(maxSize);
		newPasswordField.setMaximumSize(maxSize);
		confirmPasswordField.setMaximumSize(maxSize);
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
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!newPassword.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (newPassword.length() < 6) {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 6 ký tự", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		UserRepository repo = new UserRepository();
		if (!repo.authenticate(username, current)) {
			JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		repo.updatePassword(username, newPassword);
		JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công", "Thành công",
				JOptionPane.INFORMATION_MESSAGE);

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
}
