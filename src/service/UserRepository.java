package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.RepositoryException;
import model.UserCredentials;
import repository.IUserRepository;
import util.CsvUtils;
import util.PathsConfig;

public class UserRepository implements IUserRepository {

	public UserRepository() {
		PathsConfig.ensureBaseStructure();
	}

	@Override
	public boolean usernameExists(String username) {
		return loadAll().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
	}

	@Override
	public void register(String username, String rawPassword) {
		String hash = PasswordHasher.hash(rawPassword);
		try {
			CsvUtils.append(PathsConfig.USER_CREDENTIAL_FILE, new String[] { username, hash });
		} catch (IOException e) {
			throw new RepositoryException("Cannot save user credentials", e);
		}
	}

	@Override
	public boolean authenticate(String username, String rawPassword) {
		Optional<UserCredentials> credentials = loadAll().stream()
				.filter(u -> u.getUsername().equalsIgnoreCase(username))
				.findFirst();
		if (credentials.isEmpty()) {
			return false;
		}
		String hash = PasswordHasher.hash(rawPassword);
		return hash.equals(credentials.get().getPasswordHash());
	}

	@Override
	public List<UserCredentials> loadAll() {
		List<UserCredentials> result = new ArrayList<>();
		try {
			for (String[] row : CsvUtils.readAll(PathsConfig.USER_CREDENTIAL_FILE)) {
				if (row.length < 2) {
					continue;
				}

				result.add(new UserCredentials(row[0].trim(), row[1].trim()));
			}
		} catch (IOException e) {
			throw new RepositoryException("Cannot read credentials", e);
		}
		return result;
	}

	/**
	 * Update the stored password hash for the given username.
	 * If the username does not exist, this method does nothing.
	 */
	@Override
	public void updatePassword(String username, String rawPassword) {
		List<String[]> rows;
		try {
			rows = CsvUtils.readAll(PathsConfig.USER_CREDENTIAL_FILE);
		} catch (IOException e) {
			throw new RepositoryException("Cannot read credentials", e);
		}

		String newHash = PasswordHasher.hash(rawPassword);
		boolean changed = false;
		for (String[] row : rows) {
			if (row.length >= 2 && row[0].equalsIgnoreCase(username)) {
				row[1] = newHash;
				changed = true;
			}
		}

		if (changed) {
			try {
				CsvUtils.writeAll(PathsConfig.USER_CREDENTIAL_FILE, rows);
			} catch (IOException e) {
				throw new RepositoryException("Cannot write credentials", e);
			}
		}
	}
}
