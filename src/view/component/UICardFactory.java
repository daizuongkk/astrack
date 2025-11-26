package view.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import config.AppConfig;

public final class UICardFactory {

	private UICardFactory() {
	}

	public static JPanel createCard() {
		return new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.setColor(new Color(0, 0, 0, 12));
				g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 18, 18);

				g2d.setColor(getBackground());
				g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 18, 18);

				g2d.setColor(new Color(220, 220, 220));
				g2d.setStroke(new BasicStroke(1));
				g2d.drawRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 18, 18);

				g2d.dispose();
			}
		};
	}

	public static JPanel createCard(Color backgroundColor) {
		JPanel card = createCard();
		card.setBackground(backgroundColor);
		return card;
	}

	public static JPanel createWhiteCard() {
		return createCard(AppConfig.Colors.CARD_WHITE);
	}
}
