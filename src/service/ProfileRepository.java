package service;

import java.io.IOException;

import exception.RepositoryException;
import model.UserProfile;
import repository.IProfileRepository;
import util.CsvUtils;
import util.PathsConfig;

public class ProfileRepository implements IProfileRepository {

	@Override
	public UserProfile loadProfile(String username) {
		var path = PathsConfig.profileFile(username);
		try {
			var rows = CsvUtils.readAll(path);
			if (rows.isEmpty()) {
				return new UserProfile();
			}
			String[] data = rows.get(0);
			return mapToProfile(data);
		} catch (IOException e) {
			return new UserProfile();
		}
	}

	@Override
	public void saveProfile(String username, UserProfile profile) {
		var row = new String[] {

				defaultString(profile.getFullName()),
				defaultString(profile.getEmail()),
				defaultString(profile.getPhone()),
				defaultString(profile.getDob()),
				defaultString(profile.getAddress()),
				defaultString(profile.getOccupation())
		};
		try {
			CsvUtils.writeAll(PathsConfig.profileFile(username), java.util.Collections.singletonList(row));
		} catch (IOException e) {
			throw new RepositoryException("Cannot save profile data", e);
		}
	}

	private static UserProfile mapToProfile(String[] row) {
		String name = row.length > 0 ? row[0].trim() : "";
		String email = row.length > 1 ? row[1].trim() : "";
		String phone = row.length > 2 ? row[2].trim() : "";
		String dob = row.length > 3 ? row[3].trim() : "";
		String address = row.length > 4 ? row[4].trim() : "";
		String occupation = row.length > 5 ? row[5].trim() : "";
		return new UserProfile(name, email, phone, dob, address, occupation);
	}

	private static String defaultString(String value) {
		return value == null ? "" : value;
	}
}
