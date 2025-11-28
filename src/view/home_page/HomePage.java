package view.home_page;

import config.AppConfig;
import controller.HomePageController;
import controller.UserController;
import repository.IAssetRepository;
import repository.IActivityRepository;
import repository.IProfileRepository;
import service.AssetService;
import service.UserProfileService;

import java.awt.*;

import javax.swing.*;
import model.UserProfile;
import service.SessionManager;
import view.dialog.CustomNotification;

public class HomePage extends JFrame {
	private final SideBarPanel sideBarPanel = new SideBarPanel();
	private final HeaderPanel headerPanel = new HeaderPanel();
	private final MainContent mainContent = new MainContent();
	private final String username;
	private final IProfileRepository profileRepository;
	private final UserProfilePage userProfilePage = new UserProfilePage();
	private final DashBoardPage dashBoardPage = new DashBoardPage();
	private final AssetsPanel assetsPanel;
	private final ReportPanel reportPanel;
	private final AssetService assetService;
	@SuppressWarnings("unused")
	private final HomePageController homePageController;
	@SuppressWarnings("unused")
	private final UserController userController;

	public HomePage(String username, IProfileRepository profileRepository, IAssetRepository assetRepository,
			IActivityRepository activityRepository, int passwordLength) {
		setTitle("Asset Track");
		this.username = username;
		this.profileRepository = profileRepository;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1500, 900));

		setSize(1500, 900);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setIconImage(new ImageIcon("src/images/app_logo.jpg").getImage());
		assetService = new AssetService(assetRepository, activityRepository);

		repository.TypeRepository typeRepository = new repository.TypeRepository();
		service.TypeService typeService = new service.TypeService(typeRepository, username);

		assetsPanel = new AssetsPanel(assetService, typeService);
		reportPanel = new ReportPanel(assetService, typeService);

		assetsPanel.setReportPanel(reportPanel);
		assetsPanel.setDashBoardContent(dashBoardPage);
		dashBoardPage.setAssetService(assetService);
		mainContent.addPage("DASHBOARD", dashBoardPage);
		mainContent.addPage("ASSETS", assetsPanel);
		mainContent.addPage("REPORT", reportPanel);
		mainContent.addPage("PROFILE", userProfilePage);

		mainContent.showPage("DASHBOARD");

		add(headerPanel, BorderLayout.NORTH);
		add(sideBarPanel, BorderLayout.WEST);
		add(mainContent, BorderLayout.CENTER);
		getContentPane().setBackground(AppConfig.Colors.LIGHT_BG);
		loadData(passwordLength);
		userProfilePage.setOnSave(this::onSaveProfile);
		this.homePageController = new HomePageController(this);
		this.userController = new UserController(userProfilePage, new UserProfileService(profileRepository));

	}

	private void loadData(int passwordLength) {

		UserProfile profile = profileRepository.loadProfile(username);
		userProfilePage.setProfile(profile);
		userProfilePage.setUserid(username);

		if (passwordLength > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < passwordLength; i++) {
				sb.append('•');
			}
			userProfilePage.setPassworField(sb.toString());
		}
	}

	private void onSaveProfile(UserProfile profile) {
		try {
			profileRepository.saveProfile(username, profile);
			CustomNotification.showSuccess(this, "Thành công", "Đã lưu hồ sơ");
		} catch (HeadlessException e) {
			CustomNotification.showError(this, "Lỗi", "Lỗi khi lưu hồ sơ: " + e.getMessage());
		}
	}

	public SideBarPanel getSideBarPanel() {
		return sideBarPanel;
	}

	public HeaderPanel getHeaderPanel() {
		return headerPanel;
	}

	public MainContent getHomeContent() {
		return mainContent;
	}

	public MainContent getMainContent() {
		return mainContent;
	}

	public void clearSession() {
		SessionManager.clear();
	}

	public AssetService getAssetService() {
		return assetService;
	}

}