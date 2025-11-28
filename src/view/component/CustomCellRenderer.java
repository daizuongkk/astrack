package view.component;

import config.AppConfig;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (isSelected) {
			c.setBackground(new Color(216, 255, 218));
			c.setForeground(AppConfig.Colors.DARK_GREEN);
		} else {
			c.setBackground(Color.WHITE);
			c.setForeground(AppConfig.Colors.DARK_GREEN);
		}

		c.setHorizontalAlignment(SwingConstants.CENTER);

		c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, AppConfig.Colors.PRIMARY_GREEN));

		return c;
	}

}
