package view.component;

import config.AppConfig;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;

public class TableHeaderRenderer extends DefaultTableCellRenderer {

	public TableHeaderRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setFont(new Font("Segoe UI", Font.BOLD, 20));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		lbl.setBackground(AppConfig.Colors.PRIMARY_GREEN);
		lbl.setForeground(AppConfig.Colors.TEXT_LIGHT);
		lbl.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		return lbl;
	}
}