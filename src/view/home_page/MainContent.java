package view.home_page;

import javax.swing.JPanel;
import java.awt.CardLayout;

public class MainContent extends JPanel {
	private static final long serialVersionUID = 1L;
	private final CardLayout cardLayout;

	public MainContent() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
	}

	public void showPage(String pageName) {
		cardLayout.show(this, pageName);
	}

	public void addPage(String pageName, JPanel page) {
		add(page, pageName);
	}
}
