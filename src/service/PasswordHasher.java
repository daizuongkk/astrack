package service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {
	private static final String ALGORITHM = "SHA-256";

	private PasswordHasher() {
	}

	public static String hash(String rawPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
			StringBuilder builder = new StringBuilder();
			for (byte b : hash) {
				builder.append(String.format("%02x", b));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Missing hash algorithm", e);
		}
	}
}
