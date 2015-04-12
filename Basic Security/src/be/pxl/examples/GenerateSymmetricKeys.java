package be.pxl.examples;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class GenerateSymmetricKeys {

	private static void generateKey(String keyAlgorithm) {

		try {

			KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgorithm);
			SecretKey key = keyGen.generateKey();

			System.out.println("\n" + "Generating symmetric key using " + key.getAlgorithm() + " algorithm");

			// Get the bytes of the key
			byte[] keyBytes = key.getEncoded();
			int numBytes = keyBytes.length;

			System.out.println("  The number of bytes in the key = " + numBytes + ".");

			// The bytes can be converted back to a SecretKey
			SecretKey key2 = new SecretKeySpec(keyBytes, keyAlgorithm);
			System.out.println("  Are both symmetric keys equal? " + key.equals(key2));
			//System.out.println(keyBytes.toString());
		}
		catch (NoSuchAlgorithmException e) {

			System.out.println("Exception");
			System.out.println("No such algorithm: " + keyAlgorithm);

		}

	}

	public static void main(String[] args) {

		// Generate a DES key
		generateKey("DES");

		// Generate a Blowfish key
		generateKey("Blowfish");

		// Generate a DESede key
		generateKey("DESede");
	}

}
