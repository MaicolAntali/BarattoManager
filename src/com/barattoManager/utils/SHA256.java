package com.barattoManager.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

	public static byte[] hash(String str) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		return digest.digest(str.getBytes(StandardCharsets.UTF_8));
	}


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
