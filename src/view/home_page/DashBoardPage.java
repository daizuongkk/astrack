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
import view.component.UICardFactory;
import util.FormatUtils;

public class DashBoardPage extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel statsPanel;
	private JTable recentTable;
	private DefaultTableModel tableModel;
	private AssetService assetService;

	public DashBoardPage() {
		setLayout(new BorderLayout());
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
		welcomeLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 28));
		welcomeLabel.setForeground(AppConfig.Colors.PRIMARY_GREEN);

		JLabel subtitleLabel = new JLabel("Quản lý tài sản của bạn một cách hiệu quả và chuyên nghiệp");
		subtitleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 15));
		subtitleLabel.setForeground(new Color(96, 96, 96));

		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(subtitleLabel, BorderLayout.CENTER);

		add(welcomePanel, BorderLayout.NORTH);
	}

	private void createStatsOverview() {
		statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		statsPanel.setBackground(AppConfig.Colors.LIGHT_BG);
		statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		updateStatsCards();

		add(statsPanel, BorderLayout.CENTER);
	}

	private void updateStatsCards() {
		statsPanel.removeAll();

		int totalAssets = 0;
		long totalValue = 0;

		if (assetService != null) {
			List<Asset> assets = assetService.getAllAssets();
			totalAssets = assets.size();
			for (Asset asset : assets) {
				totalValue += (long) asset.getValue();
			}
		}

		statsPanel.add(createStatCard("📊 Tổng tài sản", String.valueOf(totalAssets), "Tài sản",
				AppConfig.Colors.PRIMARY_GREEN, new Color(227, 242, 253)));
		statsPanel.add(createStatCard("💰 Tổng giá trị", FormatUtils.formatCurrency(totalValue), "VND",
				AppConfig.Colors.SUCCESS_GREEN, new Color(232, 245, 233)));

		statsPanel.revalidate();
		statsPanel.repaint();
	}

	private JPanel createStatCard(String title, String value, String unit, Color accentColor, Color bgColor) {
		JPanel card = UICardFactory.createCard(bgColor);
		card.setLayout(new BorderLayout(0, 12));
		card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		card.setBackground(bgColor);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 15));
		titleLabel.setForeground(AppConfig.Colors.TEXT_PRIMARY);

		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 32));
		valueLabel.setForeground(accentColor);

		JLabel unitLabel = new JLabel(unit);
		unitLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 13));
		unitLabel.setForeground(AppConfig.Colors.TEXT_SECONDARY);

		JPanel valuePanel = new JPanel(new BorderLayout(0, 5));
		valuePanel.setOpaque(false);
		valuePanel.add(valueLabel, BorderLayout.NORTH);
		valuePanel.add(unitLabel, BorderLayout.CENTER);

		card.add(titleLabel, BorderLayout.NORTH);
		card.add(valuePanel, BorderLayout.CENTER);

		return card;
	}

	private void createRecentActivities() {
		JPanel activitiesPanel = UICardFactory.createWhiteCard();
		activitiesPanel.setLayout(new BorderLayout(0, 15));
		activitiesPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		JLabel titleLabel = new JLabel("📋 Hoạt động gần đây");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 20));
		titleLabel.setForeground(new Color(33, 33, 33));
		activitiesPanel.add(titleLabel, BorderLayout.NORTH);

		String[] columnNames = { "Tên tài sản", "Loại", "Giá trị", "Ngày thêm", "Ngày sửa", "Ngày xóa" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		recentTable = new JTable(tableModel);
		recentTable.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 13));
		recentTable.setRowHeight(42);
		recentTable.setShowGrid(false);
		recentTable.setIntercellSpacing(new Dimension(0, 0));

		// Modern table header
		recentTable.getTableHeader().setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		// Header uses primary (dark green) with high contrast text
		recentTable.getTableHeader().setBackground(AppConfig.Colors.PRIMARY_GREEN);
		recentTable.getTableHeader().setForeground(AppConfig.Colors.TEXT_WHITE);
		recentTable.getTableHeader().setReorderingAllowed(false);
		recentTable.getTableHeader().setResizingAllowed(false);
		recentTable.getTableHeader().setPreferredSize(new Dimension(0, 45));
		recentTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		Color recentSelBg = new Color(AppConfig.Colors.PRIMARY_GREEN.getRed(), AppConfig.Colors.PRIMARY_GREEN.getGreen(),
				AppConfig.Colors.PRIMARY_GREEN.getBlue(), 20);
		recentTable.setSelectionBackground(recentSelBg);
		recentTable.setSelectionForeground(AppConfig.Colors.TEXT_WHITE);
		recentTable.setBackground(Color.WHITE);
		recentTable.setBorder(BorderFactory.createEmptyBorder());

		recentTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				javax.swing.table.DefaultTableCellRenderer renderer = (javax.swing.table.DefaultTableCellRenderer) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					renderer.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
				}
				renderer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
				return renderer;
			}
		});

		JScrollPane scrollPane = new JScrollPane(recentTable);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(Color.WHITE);

		activitiesPanel.add(scrollPane, BorderLayout.CENTER);

		add(activitiesPanel, BorderLayout.SOUTH);
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
			List<Activity> activities = assetService.getRecentActivities(10);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			for (int i = activities.size() - 1; i >= 0; i--) {
				Activity activity = activities.get(i);
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

				Object[] rowData = {
						activity.getPropertyName(),
						activity.getPropertyType(),
						FormatUtils.formatCurrency(activity.getPropertyValue()),
						addedDate,
						updateDate,
						deleteDate
				};
				tableModel.addRow(rowData);
			}
		}
	}

}
