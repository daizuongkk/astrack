package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.authentication_page.AuthenticationFrame;
import view.home_page.HomePage;
import view.dialog.CustomNotification;

public class HomePageController implements ActionListener {
	private final HomePage view;
	private boolean onClickMenuBtn;
	private view.component.SideBarButton currentSelectedButton;

	public HomePageController(HomePage view) {
		this.view = view;
		onClickMenuBtn = false;
		currentSelectedButton = view.getSideBarPanel().getBtnDashBoard();
		currentSelectedButton.setSelected(true);
		initializeListeners();
	}

	private void initializeListeners() {
		view.getSideBarPanel().getBtnLogOut().addActionListener(this);
		view.getSideBarPanel().getBtnMenu().addActionListener(this);
		view.getSideBarPanel().getBtnProfile().addActionListener(this);
		view.getSideBarPanel().getBtnDashBoard().addActionListener(this);
		view.getSideBarPanel().getBtnAssets().addActionListener(this);
		view.getSideBarPanel().getBtnReport().addActionListener(this);
	}

	private void btnLogOutClicked() {
		int choosed = CustomNotification.showConfirm(view, "Xác nhận đăng xuất", "Bạn có chắc chắn đăng xuất!");
		if (choosed == 0) {
			view.clearSession();
			this.view.dispose();
			new AuthenticationFrame().setVisible(true);
		}
	}

	private void btnMenuClicked() {
		if (!onClickMenuBtn) {
			view.getSideBarPanel().setPreferredSize(new Dimension(250, 0));
			view.getSideBarPanel().iconText();
		} else {
			view.getSideBarPanel().setPreferredSize(new Dimension(88, 0));
			view.getSideBarPanel().onlyIcon();
		}
		onClickMenuBtn = !onClickMenuBtn;
		view.getSideBarPanel().revalidate();
		view.getSideBarPanel().repaint();
	}

	private void btnDashBoardClicked() {
		setSelectedButton(view.getSideBarPanel().getBtnDashBoard());
		view.getMainContent().showPage("DASHBOARD");
	}

	private void btnAssetsClicked() {
		setSelectedButton(view.getSideBarPanel().getBtnAssets());
		view.getMainContent().showPage("ASSETS");
	}

	private void btnReportClicked() {
		setSelectedButton(view.getSideBarPanel().getBtnReport());
		view.getMainContent().showPage("REPORT");
	}

	private void btnProfileClicked() {
		setSelectedButton(view.getSideBarPanel().getBtnProfile());
		view.getMainContent().showPage("PROFILE");
	}

	private void setSelectedButton(view.component.SideBarButton button) {
		if (currentSelectedButton != null) {
			currentSelectedButton.setSelected(false);
		}
		currentSelectedButton = button;
		if (button != null) {
			button.setSelected(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getSideBarPanel().getBtnLogOut()) {
			btnLogOutClicked();
		}
		if (e.getSource() == view.getSideBarPanel().getBtnMenu()) {
			btnMenuClicked();
		}
		if (e.getSource() == view.getSideBarPanel().getBtnDashBoard()) {
			btnDashBoardClicked();
		}
		if (e.getSource() == view.getSideBarPanel().getBtnAssets()) {
			btnAssetsClicked();
		}
		if (e.getSource() == view.getSideBarPanel().getBtnReport()) {
			btnReportClicked();
		}
		if (e.getSource() == view.getSideBarPanel().getBtnProfile()) {
			btnProfileClicked();
		}
	}

}