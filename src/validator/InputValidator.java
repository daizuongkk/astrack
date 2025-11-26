package validator;

/**
 * Utility class for validating user inputs.
 */
public final class InputValidator {

	private InputValidator() {
	}

	/**
	 * Validates email format.
	 * 
	 * @param email the email to validate
	 * @return true if email is valid, false otherwise
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

	/**
	 * Validates Vietnamese phone number format.
	 * 
	 * @param phone the phone number to validate
	 * @return true if phone is valid, false otherwise
	 */
	public static boolean isValidPhone(String phone) {
		if (phone == null || phone.isBlank()) {
			return false;
		}
		return phone.matches("^(0|\\+84)[0-9]{9,10}$");
	}

	/**
	 * Validates username length.
	 * 
	 * @param username  the username to validate
	 * @param minLength minimum required length
	 * @return true if username is valid, false otherwise
	 */
	public static boolean isValidUsername(String username, int minLength) {
		return username != null && username.length() >= minLength;
	}

	/**
	 * Validates password length.
	 * 
	 * @param password  the password to validate
	 * @param minLength minimum required length
	 * @return true if password is valid, false otherwise
	 */
	public static boolean isValidPassword(String password, int minLength) {
		return password != null && password.length() >= minLength;
	}

	/**
	 * Checks if two passwords match.
	 * 
	 * @param password        the password
	 * @param confirmPassword the confirmation password
	 * @return true if passwords match, false otherwise
	 */
	public static boolean passwordsMatch(String password, String confirmPassword) {
		return password != null && password.equals(confirmPassword);
	}

	/**
	 * Validates that a string is not blank.
	 * 
	 * @param value the value to validate
	 * @return true if value is not blank, false otherwise
	 */
	public static boolean isNotBlank(String value) {
		return value != null && !value.trim().isEmpty();
	}
}
