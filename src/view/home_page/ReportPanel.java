package view.home_page;

import config.AppConfig;
import model.Asset;
import service.AssetService;
import service.TypeService;
import util.FormatUtils;
import view.component.CurrencyRenderer;
import view.component.UICardFactory;
import view.component.UIButtonFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ReportPanel - provides type and date-range filtering and two embedded charts
 * (category distribution, monthly trend).
 */
public final class ReportPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final AssetService assetService;
	private final TypeService typeService;

	private JComboBox<String> assetTypeComboBox;
	private JTextField startDateField;
	private JTextField endDateField;
	private JPanel statsPanel;
	private JPanel chartsRow;
	private JTable assetTable;
	private DefaultTableModel tableModel;

	private final Color PRIMARY_GREEN = AppConfig.Colors.PRIMARY_GREEN;
	private final Color CARD_WHITE = AppConfig.Colors.CARD_WHITE;
	private final Color BACKGROUND_LIGHT = AppConfig.Colors.BACKGROUND_LIGHT;
	private final Color TEXT_DARK = AppConfig.Colors.TEXT_PRIMARY;

	public ReportPanel(AssetService assetService, TypeService typeService) {
		this.assetService = assetService;
		this.typeService = typeService;

		setLayout(new BorderLayout());
		setBackground(BACKGROUND_LIGHT);

		createHeaderPanel();
		createTable();

		refreshTypes();
		filterAssetsByType("Tất cả", null, null);
		updateCharts("Tất cả", null, null);
	}

	public void refreshData() {
		assetService.reloadFromFile();
		filterAssetsByType((String) assetTypeComboBox.getSelectedItem(), null, null);
		updateStatisticsDisplay();
		updateCharts((String) assetTypeComboBox.getSelectedItem(), null, null);
	}

	private void createHeaderPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(BACKGROUND_LIGHT);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		JPanel headerCard = UICardFactory.createWhiteCard();
		headerCard.setLayout(new BorderLayout(20, 20));
		headerCard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		JPanel titleSection = new JPanel(new BorderLayout());
		titleSection.setBackground(CARD_WHITE);
		JLabel titleLabel = new JLabel("Báo cáo thống kê tài sản");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 28));
		titleLabel.setForeground(PRIMARY_GREEN);
		JLabel subtitle = new JLabel("Theo dõi và phân tích danh mục tài sản của bạn");
		subtitle.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		subtitle.setForeground(AppConfig.Colors.TEXT_SECONDARY);
		titleSection.add(titleLabel, BorderLayout.NORTH);
		titleSection.add(subtitle, BorderLayout.SOUTH);

		JPanel filterSection = new JPanel(new BorderLayout());
		filterSection.setBackground(CARD_WHITE);

		statsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
		statsPanel.setBackground(CARD_WHITE);
		updateStatisticsDisplay();

		JPanel controlBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		controlBar.setBackground(CARD_WHITE);

		JLabel filterLabel = new JLabel("🔍 Lọc theo loại:");
		filterLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		filterLabel.setForeground(TEXT_DARK);

		assetTypeComboBox = new JComboBox<>();
		assetTypeComboBox.setPreferredSize(new Dimension(200, 40));
		assetTypeComboBox.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		assetTypeComboBox.setBackground(CARD_WHITE);
		assetTypeComboBox.addActionListener(e -> {
			String sel = (String) assetTypeComboBox.getSelectedItem();
			filterAssetsByType(sel, null, null);
			updateCharts(sel, null, null);
		});

		JLabel dateLabel = new JLabel("  📅 Thời gian thêm:");
		dateLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		dateLabel.setForeground(TEXT_DARK);

		startDateField = new JTextField(8);
		startDateField.setToolTipText("dd/MM/yyyy");
		endDateField = new JTextField(8);
		endDateField.setToolTipText("dd/MM/yyyy");

		JButton applyBtn = UIButtonFactory.createPrimaryButton("Áp dụng");
		JButton clearBtn = UIButtonFactory.createSecondaryButton("Xóa");
		applyBtn.setPreferredSize(new Dimension(90, 36));
		clearBtn.setPreferredSize(new Dimension(90, 36));

		applyBtn.addActionListener(e -> {
			String sel = (String) assetTypeComboBox.getSelectedItem();
			LocalDate start = parseDateOrNull(startDateField.getText().trim());
			LocalDate end = parseDateOrNull(endDateField.getText().trim());
			filterAssetsByType(sel, start, end);
			updateCharts(sel, start, end);
		});

		clearBtn.addActionListener(e -> {
			startDateField.setText("");
			endDateField.setText("");
			String sel = (String) assetTypeComboBox.getSelectedItem();
			filterAssetsByType(sel, null, null);
			updateCharts(sel, null, null);
		});

		controlBar.add(filterLabel);
		controlBar.add(Box.createHorizontalStrut(8));
		controlBar.add(assetTypeComboBox);
		controlBar.add(Box.createHorizontalStrut(10));
		controlBar.add(dateLabel);
		controlBar.add(startDateField);
		controlBar.add(new JLabel(" — "));
		controlBar.add(endDateField);
		controlBar.add(Box.createHorizontalStrut(6));
		controlBar.add(applyBtn);
		controlBar.add(Box.createHorizontalStrut(6));
		controlBar.add(clearBtn);

		filterSection.add(statsPanel, BorderLayout.WEST);
		filterSection.add(controlBar, BorderLayout.EAST);

		headerCard.add(titleSection, BorderLayout.NORTH);
		headerCard.add(filterSection, BorderLayout.SOUTH);

		chartsRow = new JPanel(new GridLayout(1, 2, 20, 0));
		chartsRow.setBackground(CARD_WHITE);
		chartsRow.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		headerCard.add(chartsRow, BorderLayout.CENTER);

		headerPanel.add(headerCard, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
	}

	private void updateStatisticsDisplay() {
		if (statsPanel == null)
			return;
		statsPanel.removeAll();
		List<Asset> assets = assetService.getAllAssets();
		int total = assets.size();
		long sum = assets.stream().mapToLong(a -> (long) a.getValue()).sum();
		statsPanel.add(createStatCard("🏠 Tổng tài sản", String.valueOf(total), "Tài sản"));
		statsPanel.add(createStatCard("💰 Tổng giá trị", FormatUtils.formatCurrency(sum), "VND"));
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
		titleLabel.setForeground(AppConfig.Colors.TEXT_SECONDARY);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 24));
		valueLabel.setForeground(PRIMARY_GREEN);
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel unitLabel = new JLabel(unit);
		unitLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 11));
		unitLabel.setForeground(AppConfig.Colors.TEXT_SECONDARY);
		unitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(titleLabel, BorderLayout.NORTH);
		card.add(valueLabel, BorderLayout.CENTER);
		card.add(unitLabel, BorderLayout.SOUTH);
		return card;
	}

	private void createTable() {
		String[] cols = { "🏷️ Tên tài sản", "📅 Ngày", "💰 Giá trị", "📝 Ghi chú" };
		tableModel = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		assetTable = new JTable(tableModel);
		assetTable.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		assetTable.setRowHeight(50);
		assetTable.setIntercellSpacing(new Dimension(0, 0));
		assetTable.getTableHeader().setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		assetTable.getTableHeader().setBackground(PRIMARY_GREEN);
		assetTable.getColumnModel().getColumn(2).setCellRenderer(new CurrencyRenderer());

		JScrollPane sc = new JScrollPane(assetTable);
		sc.setBorder(BorderFactory.createEmptyBorder());
		JPanel tableCard = UICardFactory.createWhiteCard();
		tableCard.setLayout(new BorderLayout());
		tableCard.add(sc, BorderLayout.CENTER);
		tableCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(BACKGROUND_LIGHT);
		container.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
		container.add(tableCard, BorderLayout.CENTER);
		add(container, BorderLayout.CENTER);
	}

	private LocalDate parseDateOrNull(String s) {
		if (s == null || s.isBlank())
			return null;
		try {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return LocalDate.parse(s, fmt);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (dd/MM/yyyy)", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private void filterAssetsByType(String selectedType, LocalDate start, LocalDate end) {
		tableModel.setRowCount(0);
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		for (Asset a : assetService.getAllAssets()) {
			boolean okType = "Tất cả".equals(selectedType) || selectedType == null
					|| selectedType.equals(a.getCategory());
			if (!okType)
				continue;
			if (start != null || end != null) {
				if (a.getAcquiredDate() == null)
					continue;
				LocalDate d = a.getAcquiredDate();
				if (start != null && d.isBefore(start))
					continue;
				if (end != null && d.isAfter(end))
					continue;
			}
			Object[] row = { a.getName(),
					a.getAcquiredDate() != null ? fmt.format(java.sql.Date.valueOf(a.getAcquiredDate())) : "",
					(long) a.getValue(), a.getNotes() != null ? a.getNotes() : "" };
			tableModel.addRow(row);
		}
	}

	public void refreshTypes() {
		List<String> list = new ArrayList<>();
		list.add("Tất cả");
		list.addAll(typeService.getAllTypes());
		String[] defaults = new String[] { "Tiền mặt",
				"Sổ tiết kiệm",
				"Bất động sản",
				"Phương tiện đi lại",
				"Đồ điện tử & Công nghệ",
				"Trang sức",
				"Cho vay",
				"Tài sản khác" };
		for (String d : defaults)
			if (!list.contains(d))
				list.add(d);
		assetTypeComboBox.setModel(new DefaultComboBoxModel<>(list.toArray(String[]::new)));
	}

	private JPanel createCategoryDistributionChartFor(List<Asset> assets) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(CARD_WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		JLabel title = new JLabel("Thống kê theo nhóm tài sản");
		title.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		title.setForeground(TEXT_DARK);
		panel.add(title, BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(CARD_WHITE);

		Map<String, Integer> counts = new java.util.HashMap<>();
		for (Asset a : assets)
			counts.put(a.getCategory(), counts.getOrDefault(a.getCategory(), 0) + 1);
		int max = counts.values().stream().mapToInt(Integer::intValue).max().orElse(1);
		counts.forEach((k, v) -> {
			JPanel row = new JPanel(new BorderLayout(6, 0));
			row.setBackground(CARD_WHITE);
			JLabel name = new JLabel(k == null || k.isBlank() ? "Không xác định" : k + " (" + v + ")");
			name.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 13));
			row.add(name, BorderLayout.WEST);
			int percent = (int) ((v * 100.0) / (double) max);
			JProgressBar bar = new JProgressBar(0, 100);
			bar.setValue(percent);
			bar.setBackground(new Color(240, 240, 240));
			bar.setForeground(PRIMARY_GREEN);
			bar.setPreferredSize(new Dimension(180, 16));
			row.add(bar, BorderLayout.CENTER);
			content.add(row);
			content.add(Box.createVerticalStrut(8));
		});
		panel.add(content, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createMonthlyTrendChartFor(List<Asset> assets) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(CARD_WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		JLabel title = new JLabel("Biến động tài sản (6 tháng)");
		title.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		title.setForeground(TEXT_DARK);
		panel.add(title, BorderLayout.NORTH);

		LocalDate now = LocalDate.now();
		Map<String, Long> monthVals = new LinkedHashMap<>();
		for (int i = 5; i >= 0; i--) {
			LocalDate m = now.minusMonths(i);
			monthVals.put(m.getMonth().name().substring(0, 3) + " " + m.getYear(), 0L);
		}
		for (Asset a : assets)
			if (a.getAcquiredDate() != null) {
				String k = a.getAcquiredDate().getMonth().name().substring(0, 3) + " "
						+ a.getAcquiredDate().getYear();
				monthVals.computeIfPresent(k, (kk, vv) -> vv + (long) a.getValue());
			}
		long max = monthVals.values().stream().mapToLong(Long::longValue).max().orElse(1L);
		JPanel content = new JPanel(new GridLayout(1, monthVals.size(), 6, 0));
		content.setBackground(CARD_WHITE);
		for (var e : monthVals.entrySet()) {
			JPanel col = new JPanel(new BorderLayout());
			col.setBackground(CARD_WHITE);
			double ratio = max == 0 ? 0 : e.getValue() / (double) max;
			int h = (int) (ratio * 140) + 10;
			JPanel bar = new JPanel();
			bar.setBackground(PRIMARY_GREEN);
			bar.setPreferredSize(new Dimension(40, h));
			JPanel barWrap = new JPanel(new GridBagLayout());
			barWrap.setBackground(new Color(250, 250, 250));
			barWrap.add(bar);
			JLabel lbl = new JLabel(e.getKey());
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 11));
			col.add(barWrap, BorderLayout.CENTER);
			col.add(lbl, BorderLayout.SOUTH);
			content.add(col);
		}
		panel.add(content, BorderLayout.CENTER);
		return panel;
	}

	private void updateCharts(String selectedType, LocalDate start, LocalDate end) {
		List<Asset> filtered = new ArrayList<>();
		for (Asset a : assetService.getAllAssets()) {
			boolean okType = (selectedType == null || "Tất cả".equals(selectedType))
					|| (a.getCategory() != null && a.getCategory().equals(selectedType));
			if (!okType)
				continue;
			if (start != null || end != null) {
				if (a.getAcquiredDate() == null)
					continue;
				if (start != null && a.getAcquiredDate().isBefore(start))
					continue;
				if (end != null && a.getAcquiredDate().isAfter(end))
					continue;
			}
			filtered.add(a);
		}
		chartsRow.removeAll();
		chartsRow.add(createCategoryDistributionChartFor(filtered));
		chartsRow.add(createMonthlyTrendChartFor(filtered));
		chartsRow.revalidate();
		chartsRow.repaint();
	}
}
