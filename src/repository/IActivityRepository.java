package repository;

import java.util.List;
import model.Activity;

/**
 * Interface for activity repository operations.
 */
public interface IActivityRepository {
	
	/**
	 * Loads all activities for a user.
	 * 
	 * @param username the username
	 * @return list of activities
	 */
	List<Activity> loadActivities(String username);
	
	/**
	 * Saves activities for a user.
	 * 
	 * @param username the username
	 * @param activities the activities to save
	 */
	void saveActivities(String username, List<Activity> activities);
	
	/**
	 * Adds a new activity.
	 * 
	 * @param username the username
	 * @param activity the activity to add
	 */
	void addActivity(String username, Activity activity);
}

