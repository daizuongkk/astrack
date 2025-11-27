package view.component;

import config.AppConfig;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ActionsRenderer extends JPanel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private final JButton editButton;
	private final JButton deleteButton;

	public ActionsRenderer() {
		setLayout(new java.awt.GridLayout(1, 2, 0, 0));
		setOpaque(true);

		editButton = new JButton(new ImageIcon("src/images/add_icon.png"));
		editButton.setBackground(AppConfig.Colors.PRIMARY_GREEN);
		editButton.setForeground(Color.WHITE);
		editButton.setFocusPainted(false);
		editButton.setBorderPainted(false);
		editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		editButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				editButton.setBackground(AppConfig.Colors.PRIMARY_GREEN.darker());
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				editButton.setBackground(AppConfig.Colors.PRIMARY_GREEN);
			}
		});
		deleteButton = new JButton(new ImageIcon("src/images/delete_icon.png"));
		deleteButton.setBackground(new Color(220, 53, 69));
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFocusPainted(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				deleteButton.setBackground(new Color(200, 30, 50));
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				deleteButton.setBackground(new Color(220, 53, 69));
			}
		});
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
