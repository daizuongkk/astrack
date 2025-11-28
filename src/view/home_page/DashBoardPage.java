package view.home_page;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import service.AssetService;
import model.Asset;
import model.Activity;
import config.AppConfig;

import view.component.CustomCellRenderer;
import view.component.TableHeaderRenderer;
import view.component.UICardFactory;
import util.FormatUtils;

public class DashBoardPage extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel statsPanel;
	private JTable recentTable;
	private DefaultTableModel tableModel;
	private AssetService assetService;

	public DashBoardPage() {
		setLayout(new GridBagLayout());
		setBackground(AppConfig.Colors.LIGHT_BG);
		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		createWelcomeSection();
		createStatsOverview();
		createRecentActivities();
	}

	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
		refreshData();
	}

	private void createWelcomeSection() {
		JPanel welcomePanel = UICardFactory.createWhiteCard();
		welcomePanel.setLayout(new BorderLayout(0, 10));
		welcomePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		JLabel welcomeLabel = new JLabel("Chào mừng bạn đến với Asset Track!");
		welcomeLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 33));
		welcomeLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);

		JLabel subtitleLabel = new JLabel("Quản lý tài sản của bạn một cách hiệu quả và chuyên nghiệp");
		subtitleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 20));
		subtitleLabel.setForeground(new Color(96, 96, 96));

		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(subtitleLabel, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(welcomePanel, gbc);
	}

	private void createStatsOverview() {
		statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(1, 2, 20, 0));
		statsPanel.setBackground(AppConfig.Colors.LIGHT_BG);
		statsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		updateStatsCards();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(statsPanel, gbc);
	}

	private void updateStatsCards() {
		statsPanel.removeAll();

		int totalAssets = 0;
		long totalValue = 0;

		if (assetService != null) {
			List<Asset> assets = assetService.getAllAssets();
			totalAssets = assets.stream()
					.mapToInt(a -> a.getQuantity())
					.sum();
			for (Asset asset : assets) {
				totalValue += (long) asset.getValue() * (long) asset.getQuantity();
			}
		}

		JPanel card1 = createStatCard("Tổng tài sản".toUpperCase(), String.valueOf(totalAssets), "Tài sản".toUpperCase(),
				AppConfig.Colors.PRIMARY_GREEN, new Color(227, 242, 253));
		JPanel card2 = createStatCard("Tổng giá trị".toUpperCase(), FormatUtils.formatCurrency(totalValue), "VND",
				AppConfig.Colors.SUCCESS_GREEN, new Color(232, 245, 233));

		statsPanel.add(card1);
		statsPanel.add(card2);

		statsPanel.revalidate();
		statsPanel.repaint();
	}

	private JPanel createStatCard(String title, String value, String unit, Color accentColor, Color bgColor) {
		JPanel card = UICardFactory.createCard(bgColor);
		card.setLayout(new BorderLayout());
		card.setBorder(BorderFactory.createEmptyBorder(0, 53, 0, 24));
		card.setBackground(bgColor);

		JPanel leftContainer = new JPanel();
		leftContainer.setOpaque(false);
		leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.X_AXIS));

		JLabel titleLabel = new JLabel(title + ": ");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 30));
		titleLabel.setForeground(AppConfig.Colors.DARK_GREEN);

		JLabel valueLabel = new JLabel(value + " ");
		valueLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 30));
		valueLabel.setForeground(accentColor);

		JLabel unitLabel = new JLabel(unit);
		unitLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 30));
		unitLabel.setForeground(AppConfig.Colors.TEXT_SECONDARY);

		leftContainer.add(titleLabel);
		leftContainer.add(valueLabel);
		leftContainer.add(unitLabel);

		card.add(leftContainer, BorderLayout.WEST);

		card.setPreferredSize(new Dimension(0, 100));
		card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
		card.setMinimumSize(new Dimension(200, 80));

		return card;
	}

	private void createRecentActivities() {
		JPanel activitiesPanel = UICardFactory.createWhiteCard();
		activitiesPanel.setLayout(new BorderLayout(0, 15));
		activitiesPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		JLabel titleLabel = new JLabel("Hoạt động gần đây");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 20));
		titleLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);
		activitiesPanel.add(titleLabel, BorderLayout.NORTH);

		String[] columnNames = { "STT", "Tên tài sản", "Loại", "Giá trị(VND)", "Ngày thêm", "Ngày sửa", "Ngày xóa" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		recentTable = new JTable(tableModel);
		recentTable.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 17));
		recentTable.setRowHeight(42);

		recentTable.getTableHeader().setReorderingAllowed(false);
		recentTable.getTableHeader().setResizingAllowed(false);
		recentTable.setIntercellSpacing(new Dimension(0, 0));
		recentTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
		recentTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		recentTable.getColumnModel().getColumn(0).setMaxWidth(100);

		recentTable.getColumnModel().getColumn(1).setPreferredWidth(217);
		recentTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		recentTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		recentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		recentTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		recentTable.getColumnModel().getColumn(6).setPreferredWidth(100);

		recentTable.setBackground(Color.WHITE);
		recentTable.setBorder(BorderFactory.createEmptyBorder());

		recentTable.setDefaultRenderer(Object.class, new CustomCellRenderer());

		JScrollPane scrollPane = new JScrollPane(recentTable);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(Color.WHITE);

		activitiesPanel.add(scrollPane, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(activitiesPanel, gbc);
	}

	public void refreshData() {
		if (assetService != null) {
			assetService.reloadFromFile();
		}
		updateStatsCards();
		updateRecentActivities();
		revalidate();
		repaint();
	}

	private void updateRecentActivities() {
		tableModel.setRowCount(0);

		if (assetService != null) {
			List<Activity> activities = assetService.getRecentActivities(15);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			for (int i = activities.size() - 1; i >= 0; i--) {
				Activity activity = activities.get(i);
				if (activities.get(i) == null)
					break;
				String activityType = activity.getActivityType();

				String addedDate = "";
				String updateDate = "";
				String deleteDate = "";

				switch (activityType) {
					case "ADD" -> addedDate = dateFormat.format(activity.getAddedDate());
					case "UPDATE" -> updateDate = dateFormat.format(activity.getActivityDate());
					case "DELETE" -> deleteDate = dateFormat.format(activity.getActivityDate());
					default -> {
					}
				}

				Object[] rowData = { i + 1, activity.getPropertyName(), activity.getPropertyType(),
						FormatUtils.formatCurrency(activity.getPropertyValue()), addedDate, updateDate, deleteDate };
				tableModel.insertRow(0, rowData);
			}
		}
	}
}
