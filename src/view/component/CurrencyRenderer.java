package view.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import config.AppConfig;

public class CurrencyRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public CurrencyRenderer() {
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 14));
		setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Long amount) {
			setText(formatCurrency(amount));
			setForeground(AppConfig.Colors.SUCCESS_GREEN);
		} else {
			setText(value != null ? value.toString() : "");
			setForeground(new Color(33, 33, 33));
		}

		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(row % 2 == 0 ? AppConfig.Colors.CARD_WHITE : new Color(250, 250, 250));
		}

		return this;
	}

	private String formatCurrency(long value) {
		if (value >= 1000000000) {
			return String.format("%.1fB VND", value / 1000000000.0);
		} else if (value >= 1000000) {
			return String.format("%.1fM VND", value / 1000000.0);
		} else {
			return String.format("%,d VND", value);
		}
	}
}
