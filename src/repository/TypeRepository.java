package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import exception.RepositoryException;
import util.CsvUtils;
import util.PathsConfig;

public class TypeRepository implements ITypeRepository {
	@Override
	public List<String> loadTypes(String username) {
		var path = PathsConfig.ASSET_DATA.resolve(username + "_types.csv");
		List<String> types = new ArrayList<>();
		try {
			Files.createDirectories(path.getParent());
			if (Files.notExists(path)) {
				Files.createFile(path);
				return types;
			}
			var rows = CsvUtils.readAll(path);
			for (String[] row : rows) {
				if (row.length > 0 && row[0] != null && !row[0].isBlank()) {
					types.add(row[0].trim());
				}
			}
		} catch (IOException e) {
			throw new RepositoryException("Cannot read types file", e);
		}
		return types;
	}

	@Override
	public void saveTypes(String username, List<String> types) {
		var path = PathsConfig.ASSET_DATA.resolve(username + "_types.csv");
		List<String[]> rows = new ArrayList<>();
		for (String t : types) {
			rows.add(new String[] { t });
		}
		try {
			CsvUtils.writeAll(path, rows);
		} catch (IOException e) {
			throw new RepositoryException("Cannot save types file", e);
		}
	}
}
