package service;

/**
 * Keeps the state of the current logged in user.
 */
public final class SessionManager {
	private static String currentUser;

	private SessionManager() {
	}

	public static void setCurrentUser(String user) {
		currentUser = user;
	}

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void clear() {
		currentUser = null;
	}
}
