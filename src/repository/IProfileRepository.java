package repository;

import model.UserProfile;

/**
 * Interface for profile repository operations.
 */
public interface IProfileRepository {
	
	/**
	 * Loads a user profile.
	 * 
	 * @param username the username
	 * @return the user profile, or empty profile if not found
	 * @throws RepositoryException if loading fails
	 */
	UserProfile loadProfile(String username);
	
	/**
	 * Saves a user profile.
	 * 
	 * @param username the username
	 * @param profile the profile to save
	 * @throws RepositoryException if saving fails
	 */
	void saveProfile(String username, UserProfile profile);
}

