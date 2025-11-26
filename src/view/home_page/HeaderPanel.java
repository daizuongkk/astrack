package view.home_page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public final class HeaderPanel extends JPanel {

	private final JLabel title;

	public HeaderPanel() {
		// tạo và add label 1 lần duy nhất
		title = new JLabel("ASSETTRACK - QUẢN LÍ TÀI SẢN", JLabel.CENTER);
		initUI();
	}

	public void initUI() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(0, 70));
		this.setOpaque(true); // vì ta vẽ background nên để true cho nhất quán

		title.setFont(new Font("Segoe UI", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		this.add(title, BorderLayout.CENTER);

		// nếu muốn font thay đổi theo resize window:
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustTitleFont();
			}
		});
	}

	private void adjustTitleFont() {
		// tính font size dựa trên chiều cao panel (tinh tế nhẹ)
		int height = Math.max(24, getHeight()); // tránh quá nhỏ
		int newSize = Math.max(12, height / 3); // chỉnh tỉ lệ tùy ý
		Font base = title.getFont();
		if (base.getSize() != newSize) {
			title.setFont(new Font(base.getName(), base.getStyle(), newSize));
			revalidate();
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Solid background – project primary dark green
		g2d.setColor(config.AppConfig.Colors.SIDEBAR_BG);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Shadow bottom
		g2d.setColor(new Color(0, 0, 0, 20));
		g2d.fillRect(0, getHeight() - 2, getWidth(), 2);

		g2d.dispose();
	}
}
