package repository;

import java.util.List;
import model.UserCredentials;

/**
 * Interface for user repository operations.
 */
public interface IUserRepository {
	
	/**
	 * Checks if a username exists.
	 * 
	 * @param username the username to check
	 * @return true if username exists, false otherwise
	 */
	boolean usernameExists(String username);
	
	/**
	 * Registers a new user.
	 * 
	 * @param username the username
	 * @param rawPassword the raw password (will be hashed)
	 * @throws RepositoryException if registration fails
	 */
	void register(String username, String rawPassword);
	
	/**
	 * Authenticates a user.
	 * 
	 * @param username the username
	 * @param rawPassword the raw password
	 * @return true if authentication succeeds, false otherwise
	 */
	boolean authenticate(String username, String rawPassword);
	
	/**
	 * Loads all user credentials.
	 * 
	 * @return list of user credentials
	 * @throws RepositoryException if loading fails
	 */
	List<UserCredentials> loadAll();
	
	/**
	 * Updates the password for a user.
	 * 
	 * @param username the username
	 * @param rawPassword the new raw password (will be hashed)
	 * @throws RepositoryException if update fails
	 */
	void updatePassword(String username, String rawPassword);
}

