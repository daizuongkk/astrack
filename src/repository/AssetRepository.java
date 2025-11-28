package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exception.RepositoryException;
import model.Asset;
import repository.IAssetRepository;
import util.CsvUtils;
import util.PathsConfig;

public class AssetRepository implements IAssetRepository {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public List<Asset> loadAssets(String username) {
		var path = PathsConfig.assetFile(username);
		List<Asset> assets = new ArrayList<>();
		try {
			Files.createDirectories(path.getParent());
			if (Files.notExists(path)) {
				Files.createFile(path);
				return assets;
			}
			for (String[] row : CsvUtils.readAll(path)) {
				if (row.length < 7) {
					continue;
				}
				Asset asset = new Asset();
				asset.setId(row[0]);
				asset.setName(row[1]);
				asset.setCategory(row[2]);
				asset.setQuantity(parseInt(row[3]));
				asset.setUnit(row.length > 7 ? row[7] : ""); // unit field
				asset.setValue(parseDouble(row[4]));
				asset.setAcquiredDate(parseDate(row[5]));
				asset.setNotes(row[6]);
				assets.add(asset);
			}
		} catch (IOException e) {
			throw new RepositoryException("Cannot read asset file", e);
		}
		return assets;
	}

	@Override
	public void saveAssets(String username, List<Asset> assets) {
		var path = PathsConfig.assetFile(username);
		List<String[]> rows = new ArrayList<>();
		for (Asset asset : assets) {
			rows.add(new String[] {
					asset.getId(),
					safe(asset.getName()),
					safe(asset.getCategory()),
					Integer.toString(asset.getQuantity()),
					Double.toString(asset.getValue()),
					asset.getAcquiredDate() == null ? "" : asset.getAcquiredDate().format(DATE_FORMAT),
					safe(asset.getNotes()),
					safe(asset.getUnit())
			});
		}
		try {
			CsvUtils.writeAll(path, rows);
		} catch (IOException e) {
			throw new RepositoryException("Cannot write asset file", e);
		}
	}

	@Override
	public Asset createNew(String username) {
		Asset asset = new Asset();
		asset.setId(generateId());
		return asset;
	}

	private static String generateId() {
		return "ASSET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}

	private static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	private static double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	private static LocalDate parseDate(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return LocalDate.parse(value, DATE_FORMAT);
	}

	private static String safe(String value) {
		return value == null ? "" : value;
	}
}
