package com.barattoManager.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to hash a {@link String} into a SHA-256
 */
public class SHA256 {

	/**
	 * Method used to a a {@link String}
	 *
	 * @param str {@link String} to hash
	 * @return {@code byte[]} that represent the hashed {@link String}
	 */
	public static byte[] hash(String str) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		return digest.digest(str.getBytes(StandardCharsets.UTF_8));
	}


	/**
	 * Method used to convert an {@code byte[]} into {@link String}
	 *
	 * @param hash {@code byte[]} to convert into {@link String}
	 * @return {@link String}
	 */
	public static String bytesToString(byte[] hash) {

		var hexString = new StringBuilder(2 * hash.length);

		for (byte b : hash) {
			var hex = Integer.toHexString(0xff & b);

			if (hex.length() == 1)
				hexString.append(0);

			hexString.append(hex);
		}


		return hexString.toString();
	}
}
