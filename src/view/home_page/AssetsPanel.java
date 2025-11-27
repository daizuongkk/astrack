package view.home_page;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import model.Asset;
import service.AssetService;
import config.AppConfig;
import view.component.UICardFactory;
import view.component.UIButtonFactory;
import view.component.UITextFieldFactory;
import view.component.ActionsRenderer;
import view.component.TableHeaderRenderer;

public final class AssetsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable assetsTable;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JButton addAssetButton;
	private final AssetService assetService;
	private final service.TypeService typeService;
	private ReportPanel reportPanel;
	private DashBoardPage dashboardContent;

	private final Color BACKGROUND_LIGHT = new Color(248, 250, 252);

	public AssetsPanel(AssetService assetService, service.TypeService typeService) {
		this.assetService = assetService;
		this.typeService = typeService;
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_LIGHT);

		createHeaderPanel();
		createTable();
		loadAssetsData();
	}

	public void setReportPanel(ReportPanel reportPanel) {
		this.reportPanel = reportPanel;
	}

	public void setDashBoardContent(DashBoardPage dashboardContent) {
		this.dashboardContent = dashboardContent;
	}

	private void createHeaderPanel() {
		JPanel headerPanel = UICardFactory.createWhiteCard();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlePanel.setOpaque(false);
		JLabel titleLabel = new JLabel("Quản lý tài sản");
		titleLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 24));
		titleLabel.setForeground(AppConfig.Colors.DARK_GREEN);
		titlePanel.add(titleLabel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonPanel.setOpaque(false);

		addAssetButton = UIButtonFactory.createPrimaryButton("Thêm tài sản");
		addAssetButton.addActionListener(e -> showAddAssetDialog(null));

		buttonPanel.add(addAssetButton);

		headerPanel.add(titlePanel, BorderLayout.WEST);
		headerPanel.add(buttonPanel, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);
	}

	private void createTable() {
		String[] columnNames = { "Tên tài sản", "Ngày", "Số lượng", "Đơn vị", "Loại", "Ghi chú", "Sửa/ Xóa" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		assetsTable = new JTable(tableModel);
		assetsTable.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 17));
		assetsTable.setRowHeight(50);
		assetsTable.setShowGrid(false);
		assetsTable.setIntercellSpacing(new Dimension(0, 0));

		assetsTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());

		Color selBg = new Color(AppConfig.Colors.PRIMARY_GREEN.getRed(), AppConfig.Colors.PRIMARY_GREEN.getGreen(),
				AppConfig.Colors.PRIMARY_GREEN.getBlue(), 20);
		assetsTable.setSelectionBackground(selBg);
		assetsTable.setSelectionForeground(AppConfig.Colors.TEXT_WHITE);
		assetsTable.setBackground(Color.WHITE);
		assetsTable.setForeground(AppConfig.Colors.TEXT_PRIMARY);

		assetsTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				javax.swing.table.DefaultTableCellRenderer renderer = (javax.swing.table.DefaultTableCellRenderer) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					renderer.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
					renderer.setForeground(new Color(40, 40, 40));
				} else {
					renderer.setForeground(Color.WHITE);
				}
				renderer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
				return renderer;
			}
		});

		assetsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		assetsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		assetsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		assetsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		assetsTable.getColumnModel().getColumn(4).setPreferredWidth(120);
		assetsTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		assetsTable.getColumnModel().getColumn(6).setPreferredWidth(120);

		assetsTable.getColumnModel().getColumn(6).setCellRenderer(new ActionsRenderer());

		assetsTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = assetsTable.rowAtPoint(evt.getPoint());
				int col = assetsTable.columnAtPoint(evt.getPoint());

				if (col == 6 && row >= 0) {
					try {
						Object idValue = tableModel.getValueAt(row, 6);
						if (idValue != null) {
							String assetId = idValue.toString();
							Asset asset = assetService.getAssetById(assetId);

							if (asset != null) {
								java.awt.Rectangle cellRect = assetsTable.getCellRect(row, col, true);
								int x = evt.getX() - cellRect.x;
								int cellWidth = cellRect.width;
								int midPoint = cellWidth / 2;

								if (x < midPoint) {
									showAddAssetDialog(asset);
								} else {
									int choice = JOptionPane.showConfirmDialog(AssetsPanel.this,
											"Bạn có chắc chắn muốn xóa tài sản này?", "Xác nhận xóa",
											JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

									if (choice == JOptionPane.YES_OPTION) {
										assetService.deleteAssetById(assetId);
										loadAssetsData();
										JOptionPane.showMessageDialog(AssetsPanel.this, "Xóa tài sản thành công!");

										if (reportPanel != null) {
											reportPanel.refreshData();
										}

										if (dashboardContent != null) {
											dashboardContent.refreshData();
										}
									}
								}
							}
						}
					} catch (HeadlessException ex) {
						System.err.println("Error handling action click: " + ex.getMessage());
					}
				}
			}
		});

		JPanel tableContainer = UICardFactory.createWhiteCard();
		tableContainer.setLayout(new BorderLayout());
		tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		scrollPane = new JScrollPane(assetsTable);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scrollPane.getViewport().setBackground(Color.WHITE);

		tableContainer.add(scrollPane, BorderLayout.CENTER);
		tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(tableContainer, BorderLayout.CENTER);
	}

	public void loadAssetsData() {
		if (assetsTable != null && assetsTable.isEditing()) {
			assetsTable.getCellEditor().stopCellEditing();
		}

		tableModel.setRowCount(0);

		List<Asset> assets = assetService.getAllAssets();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (Asset asset : assets) {
			String assetId = asset.getId();
			if (assetId == null || assetId.isEmpty()) {
				continue;
			}

			Object[] rowData = {
					asset.getName(),
					asset.getAcquiredDate() != null ? dateFormat.format(Date.valueOf(asset.getAcquiredDate())) : "",
					asset.getQuantity(),
					asset.getUnit() != null ? asset.getUnit() : "",
					asset.getCategory(),
					asset.getNotes() != null ? asset.getNotes() : "",
					assetId
			};
			tableModel.addRow(rowData);
		}

		tableModel.fireTableDataChanged();
		assetsTable.revalidate();
		assetsTable.repaint();
	}

	private void showAddAssetDialog(Asset existingAsset) {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
				existingAsset == null ? "Thêm tài sản" : "✏️ Chỉnh sửa tài sản", true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(550, 650);
		dialog.setLocationRelativeTo(this);
		dialog.setResizable(false);
		dialog.getContentPane().setBackground(AppConfig.Colors.BACKGROUND_LIGHT);

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(AppConfig.Colors.BORDER_LIGHT, 1),
				BorderFactory.createEmptyBorder(30, 30, 30, 30)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;

		// Form fields
		JTextField nameField = UITextFieldFactory.createStyledTextField();
		JTextField quantityField = UITextFieldFactory.createStyledTextField();
		JTextField unitField = UITextFieldFactory.createStyledTextField();
		JTextField valueField = UITextFieldFactory.createStyledTextField();
		// Build type combo based on available types (persisted + sensible defaults)
		java.util.List<String> existingTypes = new java.util.ArrayList<>();
		existingTypes.addAll(typeService.getAllTypes());
		// sensible defaults to ensure good UX when no custom types yet
		String[] defaults = new String[] { "Tiền mặt",
				"Sổ tiết kiệm",
				"Bất động sản",
				"Phương tiện đi lại",
				"Đồ điện tử & Công nghệ",
				"Trang sức",
				"Cho vay",
				"Tài sản khác" };
		for (String d : defaults) {
			if (!existingTypes.contains(d))
				existingTypes.add(d);
		}

		JComboBox<String> typeComboBox = new JComboBox<>(existingTypes.toArray(String[]::new));
		typeComboBox.setEditable(true); // allow typing a new type directly

		// small add button beside the combo that adds current typed value to saved
		// types
		JButton addTypeBtn = UIButtonFactory.createPrimaryButton("+ Loại");
		addTypeBtn.setPreferredSize(new Dimension(90, 34));
		addTypeBtn.setFont(new java.awt.Font(AppConfig.Fonts.FONT_FAMILY, java.awt.Font.BOLD, 13));
		// add typed type into persisted list and update UI
		addTypeBtn.addActionListener(ae -> {
			Object sel = typeComboBox.getEditor() != null ? typeComboBox.getEditor().getItem()
					: typeComboBox.getSelectedItem();
			if (sel != null) {
				String candidate = sel.toString().trim();
				if (!candidate.isBlank()) {
					typeService.addType(candidate);
					// refresh combo model
					java.util.List<String> updated = new java.util.ArrayList<>(typeService.getAllTypes());
					for (String d : defaults)
						if (!updated.contains(d))
							updated.add(d);
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(updated.toArray(String[]::new));
					typeComboBox.setModel(model);
					typeComboBox.setSelectedItem(candidate);
					if (reportPanel != null)
						reportPanel.refreshTypes();
				}
			}
		});

		// description area (restored)
		JTextArea descriptionArea = new JTextArea(3, 20);
		descriptionArea.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		descriptionArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(10, 12, 10, 12)));
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setBackground(Color.WHITE);

		descriptionArea.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				descriptionArea.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(9, 11, 9, 11)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				descriptionArea.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(10, 12, 10, 12)));
			}
		});

		JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
		descriptionScroll.setPreferredSize(new Dimension(300, 80));
		descriptionScroll.setBorder(BorderFactory.createEmptyBorder());

		// Fill existing data if editing
		if (existingAsset != null) {
			nameField.setText(existingAsset.getName());
			quantityField.setText(String.valueOf(existingAsset.getQuantity()));
			unitField.setText(existingAsset.getUnit() != null ? existingAsset.getUnit() : "");
			valueField.setText(String.valueOf((long) existingAsset.getValue()));
			typeComboBox.setSelectedItem(existingAsset.getCategory());
			descriptionArea.setText(existingAsset.getNotes() != null ? existingAsset.getNotes() : "");
		}

		// Style combo box
		typeComboBox.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.PLAIN, 14));
		typeComboBox.setPreferredSize(new Dimension(280, 42));
		typeComboBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		typeComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				typeComboBox.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(AppConfig.Colors.PRIMARY_GREEN, 2),
						BorderFactory.createEmptyBorder(7, 11, 7, 11)));
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				typeComboBox.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));
			}
		});

		// Helper method to create styled labels
		java.util.function.Function<String, JLabel> createLabel = (text) -> {
			JLabel label = new JLabel(text);
			label.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
			label.setForeground(AppConfig.Colors.TEXT_PRIMARY);
			return label;
		};

		// Add form components
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(createLabel.apply("Tên tài sản:"), gbc);
		gbc.gridx = 1;
		formPanel.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(createLabel.apply("Số lượng:"), gbc);
		gbc.gridx = 1;
		formPanel.add(quantityField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(createLabel.apply("Đơn vị:"), gbc);
		gbc.gridx = 1;
		formPanel.add(unitField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		formPanel.add(createLabel.apply("Giá trị (VND):"), gbc);
		gbc.gridx = 1;
		formPanel.add(valueField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		formPanel.add(createLabel.apply("Loại tài sản:"), gbc);
		gbc.gridx = 1;
		JPanel typeBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		typeBoxPanel.setBackground(Color.WHITE);
		typeBoxPanel.add(typeComboBox);
		typeBoxPanel.add(addTypeBtn);
		formPanel.add(typeBoxPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		formPanel.add(createLabel.apply("Ghi chú:"), gbc);
		gbc.gridx = 1;
		formPanel.add(descriptionScroll, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		JButton saveButton = UIButtonFactory.createPrimaryButton(
				existingAsset == null ? "Thêm" : "Cập nhật");
		JButton cancelButton = UIButtonFactory.createSecondaryButton("Hủy");

		saveButton.setPreferredSize(new Dimension(130, 45));
		cancelButton.setPreferredSize(new Dimension(130, 45));

		saveButton.addActionListener(e -> {
			try {
				String name = nameField.getText().trim();
				int quantity = Integer.parseInt(quantityField.getText().trim());
				String unit = unitField.getText().trim();
				long value = Long.parseLong(valueField.getText().trim());
				String type = ((String) typeComboBox.getSelectedItem());
				String description = descriptionArea.getText().trim();

				if (name.isEmpty() || unit.isEmpty() || value <= 0) {
					JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin và giá trị phải > 0!");
					return;
				}

				Date currentDate = new Date(System.currentTimeMillis());

				if (existingAsset == null) {
					Asset newAsset = assetService.createNewAsset();
					newAsset.setName(name);
					newAsset.setQuantity(quantity);
					newAsset.setUnit(unit);
					newAsset.setValue(value);
					newAsset.setCategory(type);
					newAsset.setNotes(description);
					newAsset.setAcquiredDate(currentDate.toLocalDate());

					assetService.addNewAsset(newAsset);
					if (type != null && !type.isBlank()) {
						typeService.addType(type);
						if (reportPanel != null)
							reportPanel.refreshTypes();
					}
				} else {
					String assetId = existingAsset.getId();
					assetService.updateAsset(assetId, name, quantity, unit, currentDate, type, value, description);
					if (type != null && !type.isBlank()) {
						typeService.addType(type);
						if (reportPanel != null)
							reportPanel.refreshTypes();
					}
				}

				if (assetsTable.isEditing()) {
					assetsTable.getCellEditor().stopCellEditing();
				}

				loadAssetsData();
				dialog.dispose();

				JOptionPane.showMessageDialog(this,
						existingAsset == null ? "Thêm tài sản thành công!" : "Cập nhật tài sản thành công!",
						"Thành công",
						JOptionPane.INFORMATION_MESSAGE);

				if (reportPanel != null) {
					reportPanel.refreshData();
				}

				if (dashboardContent != null) {
					dashboardContent.refreshData();
				}

				assetsTable.clearSelection();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(dialog, "Số lượng và giá trị phải là giá trị nguyên dương!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(e -> dialog.dispose());

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		dialog.add(formPanel, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

}
