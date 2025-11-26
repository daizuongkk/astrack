package view.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import config.AppConfig;

public class ActionsRenderer extends JPanel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private final JButton editButton;
	private final JButton deleteButton;

	public ActionsRenderer() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
		setOpaque(false);

		editButton = UIButtonFactory.createIconButton("src/images/add_icon.png", 40);
		deleteButton = UIButtonFactory.createIconButton("src/images/delete_icon.png", 40);

		editButton.setFocusPainted(false);
		deleteButton.setFocusPainted(false);

		add(editButton);
		add(deleteButton);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setBackground(new Color(25, 118, 210, 20));
		} else {
			setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
		}
		editButton.setEnabled(true);
		deleteButton.setEnabled(true);
		return this;
	}
}
