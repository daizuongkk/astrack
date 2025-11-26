import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.PathsConfig;

import view.authentication_page.AuthenticationFrame;

public final class Main {

	private Main() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException ex) {
				System.err.println("Failed to set look and feel: " + ex.getMessage());
			}
			PathsConfig.ensureBaseStructure();
			new AuthenticationFrame().setVisible(true);
		});
	}
}
