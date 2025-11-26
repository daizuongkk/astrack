package view.home_page;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import service.AssetService;
import model.Asset;
import config.AppConfig;
import view.component.UICardFactory;
import view.component.CurrencyRenderer;
import util.FormatUtils;

public class ReportPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> assetTypeComboBox;
	private JTable assetTable;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JPanel statsPanel;

	private final AssetService assetService;

	// Modern color scheme - using centralized AppConfig
	private final Color PRIMARY_GREEN = AppConfig.Colors.PRIMARY_GREEN;
	private final Color BACKGROUND_LIGHT = AppConfig.Colors.BACKGROUND_LIGHT;
	private final Color CARD_WHITE = AppConfig.Colors.CARD_WHITE;
	private final Color TEXT_DARK = AppConfig.Colors.TEXT_PRIMARY;
	private final Color TEXT_GRAY = AppConfig.Colors.TEXT_SECONDARY;
	private final Color BORDER_COLOR = AppConfig.Colors.BORDER_LIGHT;

	public ReportPanel(AssetService assetService) {
		this.assetService = assetService;
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_LIGHT);

		// Create components
		createHeaderPanel();
		createTable();

		// Set initial filter to show all assets
		filterAssetsByType("Tất cả");
	}

	public void refreshData() {
		if (assetService != null) {
			assetService.reloadFromFile();
			filterAssetsByType((String) assetTypeComboBox.getSelectedItem());
			updateStatisticsDisplay();
		}
	}

	private void createHeaderPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(BACKGROUND_LIGHT);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// Main header card
		JPanel headerCard = UICardFactory.createWhiteCard();
		headerCard.setLayout(new BorderLayout(20, 20));
		headerCard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// Title section
		JPanel titleSection = new JPanel(new BorderLayout());
		titleSection.setBackground(CARD_WHITE);

		JLabel titleLabel = new JLabel("📊 Báo cáo thống kê tài sản");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 28));
		titleLabel.setForeground(PRIMARY_GREEN);

		JLabel subtitleLabel = new JLabel("Theo dõi và phân tích danh mục tài sản của bạn");
		subtitleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 15));
		subtitleLabel.setForeground(TEXT_GRAY);

		titleSection.add(titleLabel, BorderLayout.NORTH);
		titleSection.add(subtitleLabel, BorderLayout.SOUTH);

		// Filter section
		JPanel filterSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterSection.setBackground(CARD_WHITE);

		// Stats cards
		statsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
		statsPanel.setBackground(CARD_WHITE);

		updateStatisticsDisplay();

		// Filter controls
		JPanel filterControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterControls.setBackground(CARD_WHITE);

		JLabel filterLabel = new JLabel("🔍 Lọc theo loại:");
		filterLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		filterLabel.setForeground(TEXT_DARK);

		// Asset type combo box with modern styling
		String[] assetTypes = { "Tất cả", "Bất động sản", "Đồ điện tử", "Phương tiện", "Khác" };
		assetTypeComboBox = new JComboBox<>(assetTypes);
		assetTypeComboBox.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		assetTypeComboBox.setPreferredSize(new Dimension(200, 42));
		assetTypeComboBox.setBackground(CARD_WHITE);
		assetTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(BORDER_COLOR, 1),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)));

		// Focus effect for combo box
		assetTypeComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				assetTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(7, 14, 7, 14)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				assetTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(BORDER_COLOR, 1),
						BorderFactory.createEmptyBorder(8, 15, 8, 15)));
			}
		});

		assetTypeComboBox.addActionListener(e -> {
			String selectedType = (String) assetTypeComboBox.getSelectedItem();
			filterAssetsByType(selectedType);
		});

		filterControls.add(filterLabel);
		filterControls.add(Box.createHorizontalStrut(10));
		filterControls.add(assetTypeComboBox);

		filterSection.add(statsPanel);
		filterSection.add(Box.createHorizontalStrut(30));
		filterSection.add(filterControls);

		headerCard.add(titleSection, BorderLayout.NORTH);
		headerCard.add(filterSection, BorderLayout.SOUTH);

		headerPanel.add(headerCard, BorderLayout.CENTER);

		add(headerPanel, BorderLayout.NORTH);
	}

	private void updateStatisticsDisplay() {
		if (statsPanel == null)
			return;

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

		statsPanel.add(createStatCard("🏠 Tổng tài sản", String.valueOf(totalAssets), "Tài sản"));
		String valueText = FormatUtils.formatCurrency(totalValue);
		statsPanel.add(createStatCard("💰 Tổng giá trị", valueText, "VND"));

		statsPanel.revalidate();
		statsPanel.repaint();
	}

	private JPanel createStatCard(String title, String value, String unit) {
		JPanel card = new JPanel(new BorderLayout(0, 8));
		card.setBackground(new Color(248, 250, 252));
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		card.setPreferredSize(new Dimension(150, 90));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 13));
		titleLabel.setForeground(TEXT_GRAY);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 24));
		valueLabel.setForeground(PRIMARY_GREEN);
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel unitLabel = new JLabel(unit);
		unitLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 11));
		unitLabel.setForeground(TEXT_GRAY);
		unitLabel.setHorizontalAlignment(SwingConstants.CENTER);

		card.add(titleLabel, BorderLayout.NORTH);
		card.add(valueLabel, BorderLayout.CENTER);
		card.add(unitLabel, BorderLayout.SOUTH);

		return card;
	}

	private void createTable() {
		// Table model with columns: Tên tài sản, Ngày, Giá trị, Ghi chú
		String[] columnNames = { "🏷️ Tên tài sản", "📅 Ngày", "💰 Giá trị", "📝 Ghi chú" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		assetTable = new JTable(tableModel);
		assetTable.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		assetTable.setRowHeight(50);
		assetTable.setShowGrid(false);
		assetTable.setIntercellSpacing(new Dimension(0, 0));

		// Modern table header
		assetTable.getTableHeader().setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		assetTable.getTableHeader().setBackground(PRIMARY_GREEN);
		assetTable.getTableHeader().setForeground(AppConfig.Colors.TEXT_WHITE);
		assetTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
		assetTable.getTableHeader().setPreferredSize(new Dimension(0, 55));
		assetTable.getTableHeader().setReorderingAllowed(false);
		assetTable.getTableHeader().setResizingAllowed(false);

		// Table styling
		Color selBg = new Color(AppConfig.Colors.PRIMARY_GREEN.getRed(), AppConfig.Colors.PRIMARY_GREEN.getGreen(),
				AppConfig.Colors.PRIMARY_GREEN.getBlue(), 20);
		assetTable.setSelectionBackground(selBg);
		assetTable.setSelectionForeground(AppConfig.Colors.TEXT_WHITE);
		assetTable.setBackground(CARD_WHITE);
		assetTable.setBorder(BorderFactory.createEmptyBorder());

		// Alternating row colors
		assetTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				javax.swing.table.DefaultTableCellRenderer renderer = (javax.swing.table.DefaultTableCellRenderer) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					renderer.setBackground(row % 2 == 0 ? CARD_WHITE : new Color(250, 250, 250));
				}
				renderer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
				return renderer;
			}
		});

		// Set column widths
		assetTable.getColumnModel().getColumn(0).setPreferredWidth(250);
		assetTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		assetTable.getColumnModel().getColumn(2).setPreferredWidth(180);
		assetTable.getColumnModel().getColumn(3).setPreferredWidth(300);

		// Custom renderer for value column (format as currency)
		assetTable.getColumnModel().getColumn(2).setCellRenderer(new CurrencyRenderer());

		JPanel tableCard = UICardFactory.createWhiteCard();
		tableCard.setLayout(new BorderLayout());
		tableCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		scrollPane = new JScrollPane(assetTable);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scrollPane.getViewport().setBackground(CARD_WHITE);

		tableCard.add(scrollPane, BorderLayout.CENTER);
		tableCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel tableContainer = new JPanel(new BorderLayout());
		tableContainer.setBackground(BACKGROUND_LIGHT);
		tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
		tableContainer.add(tableCard, BorderLayout.CENTER);

		add(tableContainer, BorderLayout.CENTER);
	}

	private void filterAssetsByType(String selectedType) {
		tableModel.setRowCount(0);

		List<Asset> assets = assetService.getAllAssets();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (Asset asset : assets) {
			boolean shouldAdd = false;

			if ("Tất cả".equals(selectedType)) {
				shouldAdd = true;
			} else if ("Bất động sản".equals(selectedType) && asset.getCategory().equals("Bất động sản")) {
				shouldAdd = true;
			} else if ("Đồ điện tử".equals(selectedType) && asset.getCategory().equals("Đồ điện tử")) {
				shouldAdd = true;
			} else if ("Phương tiện".equals(selectedType) && asset.getCategory().equals("Phương tiện")) {
				shouldAdd = true;
			} else if ("Khác".equals(selectedType) && asset.getCategory().equals("Khác")) {
				shouldAdd = true;
			}

			if (shouldAdd) {
				Object[] rowData = {
						asset.getName(),
						asset.getAcquiredDate() != null ? dateFormat.format(java.sql.Date.valueOf(asset.getAcquiredDate())) : "",
						(long) asset.getValue(),
						asset.getNotes() != null ? asset.getNotes() : ""
				};
				tableModel.addRow(rowData);
			}
		}
	}

}
