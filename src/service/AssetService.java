package service;

import java.util.ArrayList;
import java.util.List;

import model.Asset;
import model.Activity;
import repository.IAssetRepository;
import repository.IActivityRepository;

public class AssetService {
	private final IAssetRepository assetRepository;
	private final IActivityRepository activityRepository;
	private final String username;

	public AssetService(IAssetRepository assetRepository, IActivityRepository activityRepository) {
		this.assetRepository = assetRepository;
		this.activityRepository = activityRepository;
		this.username = SessionManager.getCurrentUser();
	}

	public List<Asset> getAllAssets() {
		if (username == null) {
			return new ArrayList<>();
		}
		return assetRepository.loadAssets(username);
	}

	public void addNewAsset(Asset asset) {
		if (username == null) {
			return;
		}
		List<Asset> assets = assetRepository.loadAssets(username);

		if (asset.getId() == null || asset.getId().isEmpty()) {
			asset.setId(assetRepository.createNew(username).getId());
		}

		assets.add(asset);

		assetRepository.saveAssets(username, assets);

		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		Activity activity = new Activity(
				asset.getId(),
				asset.getName(),
				asset.getCategory(),
				(long) asset.getValue(),
				"ADD",
				currentDate,
				currentDate);
		activityRepository.addActivity(username, activity);
	}

	public void deleteAssetById(String assetId) {
		if (username == null) {
			return;
		}
		List<Asset> assets = assetRepository.loadAssets(username);
		Asset toDelete = null;
		for (Asset asset : assets) {
			if (asset.getId().equals(assetId)) {
				toDelete = asset;
				break;
			}
		}

		if (toDelete != null) {
			assets.remove(toDelete);
			assetRepository.saveAssets(username, assets);

			java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
			Activity activity = new Activity(
					toDelete.getId(),
					toDelete.getName(),
					toDelete.getCategory(),
					(long) toDelete.getValue(),
					"DELETE",
					currentDate,
					toDelete.getAcquiredDate() != null ? java.sql.Date.valueOf(toDelete.getAcquiredDate()) : currentDate);
			activityRepository.addActivity(username, activity);
		}
	}

	public Asset getAssetById(String assetId) {
		if (username == null) {
			return null;
		}
		List<Asset> assets = assetRepository.loadAssets(username);
		for (Asset asset : assets) {
			if (asset.getId().equals(assetId)) {
				return asset;
			}
		}
		return null;
	}

	public void updateAsset(String assetId, String newAssetName, int newQuantity,
			String newUnit, java.sql.Date newAddedDate, String newAssetType, long newValue, String newDescription) {
		if (username == null) {
			return;
		}
		List<Asset> assets = assetRepository.loadAssets(username);
		Asset originalAsset = null;

		for (Asset asset : assets) {
			if (asset.getId().equals(assetId)) {
				originalAsset = asset;
				asset.setName(newAssetName);
				asset.setQuantity(newQuantity);
				asset.setUnit(newUnit);
				asset.setAcquiredDate(newAddedDate != null ? newAddedDate.toLocalDate() : null);
				asset.setCategory(newAssetType);
				asset.setValue(newValue);
				asset.setNotes(newDescription);
				break;
			}
		}

		if (originalAsset != null) {
			assetRepository.saveAssets(username, assets);

			// Record activity
			java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
			Activity activity = new Activity(
					assetId,
					newAssetName,
					newAssetType,
					newValue,
					"UPDATE",
					currentDate,
					originalAsset.getAcquiredDate() != null ? java.sql.Date.valueOf(originalAsset.getAcquiredDate())
							: currentDate);
			activityRepository.addActivity(username, activity);
		}
	}

	public int getAssetsCount() {
		return getAllAssets().size();
	}

	public List<Activity> getAllActivities() {
		if (username == null) {
			return new ArrayList<>();
		}
		return activityRepository.loadActivities(username);
	}

	public List<Activity> getRecentActivities(int count) {
		List<Activity> allActivities = getAllActivities();
		int size = allActivities.size();
		int start = Math.max(0, size - count);
		List<Activity> recent = new ArrayList<>();
		for (int i = size - 1; i >= start; i--) {
			recent.add(allActivities.get(i));
		}
		return recent;
	}

	public void addActivity(Activity activity) {
		if (username == null) {
			return;
		}
		activityRepository.addActivity(username, activity);
	}

	public Asset createNewAsset() {
		if (username == null) {
			return null;
		}
		Asset asset = assetRepository.createNew(username);
		// Use repository's ID generation (UUID-based, more reliable)
		return asset;
	}
}
