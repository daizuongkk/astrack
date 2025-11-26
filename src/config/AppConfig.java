package config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Centralized configuration for application constants.
 */
public final class AppConfig {

	private AppConfig() {
	}

	// Application Info
	public static final String APP_NAME = "AssetTrack";
	public static final String APP_VERSION = "1.0.0";

	// Window Dimensions
	public static final Dimension AUTH_WINDOW_SIZE = new Dimension(700, 538);
	public static final Dimension MAIN_WINDOW_SIZE = new Dimension(1400, 800);
	public static final Dimension SIDEBAR_COLLAPSED_WIDTH = new Dimension(90, 0);
	public static final Dimension SIDEBAR_EXPANDED_WIDTH = new Dimension(250, 0);

	// Colors - Professional Color Palette
	public static final class Colors {
		private Colors() {
		}

		// Primary Colors (Light green theme)
		// Chosen light-green primary for the project (professional, accessible)
		public static final Color PRIMARY_GREEN = new Color(102, 187, 106); // Light green (primary)
		public static final Color PRIMARY_DARK = new Color(67, 160, 71);
		public static final Color PRIMARY_LIGHT = new Color(167, 235, 142);

		// Secondary Colors
		public static final Color SECONDARY = new Color(255, 140, 0); // Vibrant orange
		public static final Color SECONDARY_DARK = new Color(204, 110, 0);
		public static final Color SECONDARY_LIGHT = new Color(255, 167, 38);

		// Status Colors
		public static final Color SUCCESS_GREEN = new Color(40, 167, 69);
		public static final Color SUCCESS = new Color(34, 139, 34);
		public static final Color ERROR_RED = new Color(220, 53, 69);
		public static final Color ERROR = new Color(198, 40, 40);
		public static final Color WARNING_YELLOW = new Color(255, 193, 7);
		public static final Color INFO_CYAN = new Color(23, 162, 184);

		// Background Colors
		public static final Color BACKGROUND_LIGHT = new Color(248, 250, 252);
		public static final Color BACKGROUND_WHITE = Color.WHITE;
		public static final Color LIGHT_BG = new Color(248, 250, 252);
		public static final Color CARD_WHITE = Color.WHITE;
		public static final Color DARK_GREEN = new Color(30, 80, 50);

		// Text Colors
		public static final Color TEXT_PRIMARY = new Color(33, 33, 33); // Very dark gray
		public static final Color TEXT_SECONDARY = new Color(100, 100, 100);
		public static final Color TEXT_MUTED = new Color(150, 150, 150);
		public static final Color TEXT_LIGHT = new Color(255, 255, 255);
		public static final Color TEXT_WHITE = Color.WHITE;

		// Border Colors
		public static final Color BORDER_LIGHT = new Color(220, 220, 220);
		public static final Color BORDER_MEDIUM = new Color(200, 200, 200);
		public static final Color BORDER_FOCUS = PRIMARY_GREEN;
		public static final Color BORDER_ERROR = new Color(220, 53, 69);

		// Sidebar Colors
		// Sidebar: darker background with light-green active accent
		public static final Color SIDEBAR_BG = new Color(46, 125, 50);
		public static final Color SIDEBAR_HOVER = new Color(56, 125, 78);
		public static final Color SIDEBAR_ACTIVE = PRIMARY_GREEN; // use primary light-green when active

		// Legacy (kept for compatibility)
		public static Color backgroundColor = new Color(248, 250, 252);
		public static Color hoverColor = new Color(230, 235, 240);
		public static Color pressColor = new Color(220, 225, 230);
		public static Color borderColor = new Color(109, 136, 102);
		public static Color textColor = new Color(33, 33, 33);
	}

	// Fonts
	public static final class Fonts {
		private Fonts() {
		}

		public static final String FONT_FAMILY = "Segoe UI";
		public static final Font TITLE = new Font(FONT_FAMILY, Font.BOLD, 28);
		public static final Font SUBTITLE = new Font(FONT_FAMILY, Font.PLAIN, 14);
		public static final Font BODY = new Font(FONT_FAMILY, Font.PLAIN, 14);
		public static final Font BUTTON = new Font(FONT_FAMILY, Font.BOLD, 14);
		public static final Font BUTTON_PLAIN = new Font(FONT_FAMILY, Font.PLAIN, 13);
	}

	// Validation Rules
	public static final class Validation {
		private Validation() {
		}

		public static final int MIN_USERNAME_LENGTH = 4;
		public static final int MIN_PASSWORD_LENGTH = 6;
		public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
		public static final String PHONE_PATTERN = "^(0|\\+84)[0-9]{9,10}$";
	}

	// Date Formats
	public static final class DateFormats {
		private DateFormats() {
		}

		public static final String DISPLAY_FORMAT = "dd/MM/yyyy";
		public static final String[] ACCEPTED_FORMATS = {
				"dd/MM/yyyy",
				"dd-MM-yyyy",
				"dd.MM.yyyy",
				"dd MM yyyy",
				"ddMMyyyy",
				"d/M/yyyy",
				"d-M-yyyy"
		};
	}

	// UI Spacing
	public static final class Spacing {
		private Spacing() {
		}

		public static final int SMALL = 6;
		public static final int MEDIUM = 8;
		public static final int LARGE = 20;
		public static final int XLARGE = 30;
		public static final int FIELD_HEIGHT = 42;
		public static final int FIELD_LABEL_WIDTH = 180;
	}

	// Resource Paths
	public static final class Resources {
		private Resources() {
		}

		public static final String IMAGE_BASE_PATH = "/images/";
		public static final String LOGO = IMAGE_BASE_PATH + "app_logo.jpg";
		public static final String CALENDAR_ICON = "src/images/calendar_icon.png";
	}
}
