package view.component;

import config.AppConfig;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SideBarButton extends JButton {
	private final JPanel contentPanel;
	private JLabel txtLabel;
	private boolean isSelected = false;
	private Color defaultLabelColor;
	private String labelText;
	private int labelWidth = 120;

	public SideBarButton(ImageIcon icon) {
		contentPanel = new JPanel(new GridBagLayout());
		this.setLayout(new GridBagLayout());
		this.add(contentPanel);
		contentPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 15, 0, 15);
		JLabel iconLabel = new JLabel(icon);
		iconLabel.setPreferredSize(new Dimension(32, 32));
		contentPanel.add(iconLabel, gbc);

		this.setBackground(AppConfig.Colors.SIDEBAR_BG);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setMaximumSize(new Dimension(200, 60));
		this.setMinimumSize(new Dimension(60, 60));
		this.setPreferredSize(new Dimension(60, 60));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setForeground(AppConfig.Colors.TEXT_PRIMARY);

		Color normalBg = AppConfig.Colors.SIDEBAR_BG;
		Color hoverBg = AppConfig.Colors.SIDEBAR_HOVER;
		Color selectedBg = AppConfig.Colors.SIDEBAR_ACTIVE;

		this.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				if (!isSelected) {
					setBackground(hoverBg);
				}
				if (txtLabel != null) {
					txtLabel.setForeground(AppConfig.Colors.PRIMARY_LIGHT);
				}
				repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (!isSelected) {
					setBackground(normalBg);
				}
				if (txtLabel != null && !isSelected) {
					txtLabel.setForeground(defaultLabelColor != null ? defaultLabelColor : AppConfig.Colors.TEXT_PRIMARY);
				}
				repaint();
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				setBackground(selectedBg);
				repaint();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (isSelected) {
					setBackground(selectedBg);
				} else {
					setBackground(hoverBg);
				}
				repaint();
			}
		});
	}

	public void addLabel(String text) {
		labelText = text;
		txtLabel = new JLabel(text);
		txtLabel.setFont(new Font(AppConfig.Fonts.FONT_FAMILY, Font.BOLD, 20));
		txtLabel.setForeground(AppConfig.Colors.TEXT_LIGHT);
		defaultLabelColor = txtLabel.getForeground();

		try {
			int w = AppConfig.SIDEBAR_EXPANDED_WIDTH.width;
			if (w > 0)
				labelWidth = Math.max(labelWidth, w - 80);
		} catch (Exception ex) {
		}
		txtLabel.setPreferredSize(new Dimension(labelWidth, 32));
		txtLabel.setMinimumSize(new Dimension(labelWidth, 32));
		txtLabel.setMaximumSize(new Dimension(labelWidth, 32));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 15);
		contentPanel.add(txtLabel, gbc);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int arc = 12;
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

		if (isSelected) {
			g2d.setColor(new Color(255, 255, 255, 30));
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

			g2d.setColor(AppConfig.Colors.PRIMARY_GREEN);
			g2d.fillRoundRect(0, 0, 4, getHeight(), 2, 2);
		}

		g2d.dispose();
		super.paintComponent(g);
	}

	@Override
	public void setSelected(boolean selected) {
		this.isSelected = selected;
		if (selected) {
			setBackground(AppConfig.Colors.SIDEBAR_ACTIVE);
			if (txtLabel != null) {
				txtLabel.setForeground(AppConfig.Colors.TEXT_LIGHT);
			}
		} else {
			setBackground(AppConfig.Colors.SIDEBAR_BG);
			if (txtLabel != null) {
				txtLabel.setForeground(defaultLabelColor != null ? defaultLabelColor : AppConfig.Colors.TEXT_PRIMARY);
			}
		}
		repaint();
	}

	public void showLabelText(boolean show) {
		if (txtLabel == null)
			return;
		if (show) {
			txtLabel.setText(labelText);
		} else {
			txtLabel.setText(" ");
		}
		revalidate();
		repaint();
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JLabel getTxtLabel() {
		return txtLabel;
	}

	public void hideGlue() {
		Component[] comps = contentPanel.getComponents();
		for (Component c : comps) {
			if (c instanceof Box.Filler) {
				c.setVisible(false);
			}
		}
	}

	public void visibleGlue() {
		Component[] comps = contentPanel.getComponents();
		for (Component c : comps) {
			if (c instanceof Box.Filler) {
				c.setVisible(true);
			}
		}
	}
}
