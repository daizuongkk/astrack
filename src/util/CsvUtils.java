package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for simple CSV read/write operations.
 */
public final class CsvUtils {

	private CsvUtils() {
	}

	public static List<String[]> readAll(Path path) throws IOException {
		List<String[]> rows = new ArrayList<>();
		if (Files.notExists(path)) {
			return rows;
		}
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) {
					continue;
				}
				rows.add(line.split(",", -1));
			}
		}
		return rows;
	}

	public static void writeAll(Path path, List<String[]> rows) throws IOException {
		Files.createDirectories(path.getParent());
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (String[] row : rows) {
				writer.write(String.join(",", row));
				writer.newLine();
			}
		}
	}

	public static void append(Path path, String[] row) throws IOException {
		Files.createDirectories(path.getParent());
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
				java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND)) {
			writer.write(String.join(",", row));
			writer.newLine();
		}
	}
}
