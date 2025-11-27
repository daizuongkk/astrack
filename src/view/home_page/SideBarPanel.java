package view.home_page;

import config.AppConfig;
import view.component.SideBarButton;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public final class SideBarPanel extends JPanel {
	private SideBarButton btnLogOut;
	private SideBarButton btnMenu;
	private SideBarButton btnDashBoard;
	private SideBarButton btnAssets;
	private SideBarButton btnReport;
	private SideBarButton btnProfile;

	public SideBarPanel() {
		super();
		initUI();
	}

	private void initUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(AppConfig.Colors.SIDEBAR_BG);
		this.setPreferredSize(new Dimension(88, 0));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));

		btnMenu = new SideBarButton(new ImageIcon("src/images/menu_icon.png"));
		btnDashBoard = new SideBarButton(new ImageIcon("src/images/dashboard_icon.png"));
		btnAssets = new SideBarButton(new ImageIcon("src/images/goal_icon.png"));
		btnReport = new SideBarButton(new ImageIcon("src/images/report_icon.png"));
		btnProfile = new SideBarButton(new ImageIcon("src/images/profile_icon.png"));
		btnLogOut = new SideBarButton(new ImageIcon("src/images/logout_icon.png"));

		btnMenu.addLabel("Menu");
		btnDashBoard.addLabel("TỔNG QUAN");
		btnAssets.addLabel("TÀI SẢN");
		btnReport.addLabel("THỐNG KÊ");
		btnProfile.addLabel("HỒ SƠ");
		btnLogOut.addLabel("ĐĂNG XUẤT");

		onlyIcon();

		add(Box.createVerticalStrut(15));
		this.add(btnMenu);
		add(Box.createVerticalStrut(8));
		this.add(btnDashBoard);
		add(Box.createVerticalStrut(8));
		this.add(btnAssets);
		add(Box.createVerticalStrut(8));
		this.add(btnReport);
		add(Box.createVerticalGlue());
		this.add(btnProfile);
		add(Box.createVerticalStrut(15));
		this.add(btnLogOut);
		add(Box.createVerticalStrut(10));
	}

	public void onlyIcon() {
		btnLogOut.getTxtLabel().setVisible(false);
		btnDashBoard.getTxtLabel().setVisible(false);
		btnMenu.getTxtLabel().setVisible(false);
		btnAssets.getTxtLabel().setVisible(false);
		btnReport.getTxtLabel().setVisible(false);
		btnProfile.getTxtLabel().setVisible(false);

		btnLogOut.hideGlue();
		btnAssets.hideGlue();
		btnDashBoard.hideGlue();
		btnMenu.hideGlue();
		btnReport.hideGlue();
		btnProfile.hideGlue();
	}

	public void iconText() {
		btnLogOut.getTxtLabel().setVisible(true);
		btnDashBoard.getTxtLabel().setVisible(true);
		btnMenu.getTxtLabel().setVisible(true);
		btnAssets.getTxtLabel().setVisible(true);
		btnReport.getTxtLabel().setVisible(true);
		btnProfile.getTxtLabel().setVisible(true);

		btnAssets.visibleGlue();
		btnMenu.visibleGlue();
		btnReport.visibleGlue();
		btnDashBoard.visibleGlue();
		btnLogOut.visibleGlue();
		btnProfile.visibleGlue();
	}

	// Getters
	public SideBarButton getBtnLogOut() {
		return btnLogOut;
	}

	public SideBarButton getBtnDashBoard() {
		return btnDashBoard;
	}

	public SideBarButton getBtnReport() {
		return btnReport;
	}

	public SideBarButton getBtnAssets() {
		return btnAssets;
	}

	public SideBarButton getBtnProfile() {
		return btnProfile;
	}

	public SideBarButton getBtnMenu() {
		return btnMenu;
	}

}
