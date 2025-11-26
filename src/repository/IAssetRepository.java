package repository;

import java.util.List;
import model.Asset;

/**
 * Interface for asset repository operations.
 */
public interface IAssetRepository {
	
	/**
	 * Loads all assets for a user.
	 * 
	 * @param username the username
	 * @return list of assets
	 * @throws RepositoryException if loading fails
	 */
	List<Asset> loadAssets(String username);
	
	/**
	 * Saves assets for a user.
	 * 
	 * @param username the username
	 * @param assets the assets to save
	 * @throws RepositoryException if saving fails
	 */
	void saveAssets(String username, List<Asset> assets);
	
	/**
	 * Creates a new asset with generated ID.
	 * 
	 * @param username the username
	 * @return a new asset instance
	 */
	Asset createNew(String username);
}

