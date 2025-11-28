package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Central place for data folder definitions.
 */
public final class PathsConfig {
	public static final Path DATA_ROOT = Paths.get("data");
	public static final Path USER_DATA = DATA_ROOT.resolve("user_data");
	public static final Path PROFILE_DATA = DATA_ROOT.resolve("profile");
	public static final Path ASSET_DATA = DATA_ROOT.resolve("property_data");
	public static final Path USER_CREDENTIAL_FILE = USER_DATA.resolve("user.csv");

	private PathsConfig() {
	}

	public static void ensureBaseStructure() {
		try {
			Files.createDirectories(USER_DATA);
			Files.createDirectories(PROFILE_DATA);
			Files.createDirectories(ASSET_DATA);
			if (Files.notExists(USER_CREDENTIAL_FILE)) {
				Files.createFile(USER_CREDENTIAL_FILE);
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Cannot initialize data folders", ex);
		}
	}

	public static Path profileFile(String username) {
		return PROFILE_DATA.resolve(username + "_profile.csv");
	}

	public static Path assetFile(String username) {
		return ASSET_DATA.resolve(username + "_data.csv");
	}

	public static Path activitiesFile(String username) {
		return ASSET_DATA.resolve(username + "_activities.csv");
	}
}
