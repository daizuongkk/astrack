package service;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import exception.RepositoryException;
import java.text.ParseException;
import model.Activity;
import repository.IActivityRepository;
import util.CsvUtils;
import util.PathsConfig;

public class ActivityRepository implements IActivityRepository {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<Activity> loadActivities(String username) {
		var path = PathsConfig.activitiesFile(username);
		List<Activity> activities = new ArrayList<>();
		try {
			Files.createDirectories(path.getParent());
			if (Files.notExists(path)) {
				Files.createFile(path);
				return activities;
			}
			List<String[]> rows = CsvUtils.readAll(path);
			boolean isFirstLine = true;
			for (String[] row : rows) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				if (row.length < 7) {
					continue;
				}
				try {
					String id = row[0].trim();
					String name = row[1].trim();
					String type = row[2].trim();
					long value = Long.parseLong(row[3].trim());
					String activityType = row[4].trim();
					Date activityDate = new Date(DATE_FORMAT.parse(row[5].trim()).getTime());
					Date addedDate = new Date(DATE_FORMAT.parse(row[6].trim()).getTime());

					activities.add(new Activity(id, name, type, value, activityType, activityDate, addedDate));
				} catch (NumberFormatException | ParseException e) {
					System.err.println("Error parsing activity: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			throw new RepositoryException("Cannot read activity file", e);
		}
		return activities;
	}

	@Override
	public void saveActivities(String username, List<Activity> activities) {
		var path = PathsConfig.activitiesFile(username);
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[] { "ID", "Tên", "Loại", "Giá trị", "Hoạt động", "Ngày hoạt động", "Ngày thêm" });

		for (Activity activity : activities) {
			rows.add(new String[] {
					activity.getPropertyId(),
					safe(activity.getPropertyName()),
					safe(activity.getPropertyType()),
					Long.toString(activity.getPropertyValue()),
					safe(activity.getActivityType()),
					activity.getActivityDate() != null ? DATE_FORMAT.format(activity.getActivityDate()) : "",
					activity.getAddedDate() != null ? DATE_FORMAT.format(activity.getAddedDate()) : ""
			});
		}
		try {
			CsvUtils.writeAll(path, rows);
		} catch (IOException e) {
			throw new RepositoryException("Cannot write activity file", e);
		}
	}

	@Override
	public void addActivity(String username, Activity activity) {
		List<Activity> activities = loadActivities(username);
		activities.add(activity);
		// Keep only last 50 activities
		if (activities.size() > 50) {
			activities.remove(0);
		}
		saveActivities(username, activities);
	}

	private static String safe(String value) {
		return value == null ? "" : value;
	}
}