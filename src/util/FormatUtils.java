package util;

/**
 * Utility class for formatting values (currency, dates, etc.).
 */
public final class FormatUtils {

	private FormatUtils() {
	}

	/**
	 * Formats a currency value with appropriate units (B for billions, M for millions).
	 * 
	 * @param value The currency value in VND
	 * @return Formatted string (e.g., "1.5B VND", "500M VND", "1,000,000 VND")
	 */
	public static String formatCurrency(long value) {
		if (value >= 1000000000) {
			return String.format("%.1fB VND", value / 1000000000.0);
		} else if (value >= 1000000) {
			return String.format("%.1fM VND", value / 1000000.0);
		} else {
			return String.format("%,d VND", value);
		}
	}

	/**
	 * Formats a currency value as a simple number with commas.
	 * 
	 * @param value The currency value
	 * @return Formatted string (e.g., "1,000,000")
	 */
	public static String formatNumber(long value) {
		return String.format("%,d", value);
	}
}

