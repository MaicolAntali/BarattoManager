package com.barattoManager.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {

	public static String hash(String str) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		var hashText = new StringBuilder(
				new BigInteger(
						1,
						digest.digest(str.getBytes(StandardCharsets.UTF_8))
				).toString(16)
		);

		while (hashText.length() < 64)
		{
			hashText.insert(0, '0');
		}

		return hashText.toString();
	}
}
