package be.pxl.app.keyMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateRSA {

	// Public keys
	public final String PUBLIC_KEY_A = System.getProperty("user.home") + "/documents/Security App Files/Keys/public_A";
	public final String PUBLIC_KEY_B = System.getProperty("user.home") + "/documents/Security App Files/Keys/public_B";

	// Private keys
	public final String PRIVATE_KEY_A = System.getProperty("user.home") + "/documents/Security App Files/Keys/private_A";
	public final String PRIVATE_KEY_B = System.getProperty("user.home") + "/documents/Security App Files/Keys/private_B";

	// File directories
	private String mainDir = System.getProperty("user.home") + "/documents/Security App Files";
	private String keyDir = System.getProperty("user.home") + "/documents/Security App Files/Keys";
	private String encrDir = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files";
	private String decrDir = System.getProperty("user.home") + "/documents/Security App Files/Decrypted Files";
	private String stegaEncrDir = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Steganography Files";
	private String stegaDecrDir = System.getProperty("user.home") + "/documents/Security App Files/Decrypted Steganography Files";

	@SuppressWarnings("unused")
	private boolean success = false;

	public GenerateRSA() {
		generateRSA();
		makeDirs();
	}

	public void generateRSA() {
		try {
			// RSA part
			final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			final KeyPair keyA = keyPairGen.generateKeyPair();
			final KeyPair keyB = keyPairGen.generateKeyPair();

			PublicKey pubA = keyA.getPublic();
			PublicKey pubB = keyB.getPublic();
			PrivateKey privA = keyA.getPrivate();
			PrivateKey privB = keyB.getPrivate();

			File publicKeyA = new File(PUBLIC_KEY_A);
			File publicKeyB = new File(PUBLIC_KEY_B);

			File privateKeyA = new File(PRIVATE_KEY_A);
			File privateKeyB = new File(PRIVATE_KEY_B);

			// Generate public key files for A and B
			if (publicKeyA.getParentFile() != null) {
				publicKeyA.getParentFile().mkdirs();
			} else {
				publicKeyA.createNewFile();
			}

			if (publicKeyB.getParentFile() != null) {
				publicKeyB.getParentFile().mkdirs();
			} else {
				publicKeyB.createNewFile();
			}

			// Generate private key files for A and B
			if (privateKeyA.getParentFile() != null) {
				privateKeyA.getParentFile().mkdirs();
			} else {
				privateKeyA.createNewFile();
			}

			if (privateKeyB.getParentFile() != null) {
				privateKeyB.getParentFile().mkdirs();
			} else {
				privateKeyB.createNewFile();
			}

			if (!publicKeyA.exists()) {
				// Saving the Public key of A in a file
				ObjectOutputStream publicKeyOSA = new ObjectOutputStream(new FileOutputStream(publicKeyA));
				publicKeyOSA.writeObject(pubA);
				publicKeyOSA.close();
			}

			if (!publicKeyB.exists()) {
				// Saving the Public key of B in a file
				ObjectOutputStream publicKeyOSB = new ObjectOutputStream(new FileOutputStream(publicKeyB));
				publicKeyOSB.writeObject(pubB);
				publicKeyOSB.close();
			}

			if (!privateKeyA.exists()) {
				// Saving the Private key of A in a file
				ObjectOutputStream privateKeyOSA = new ObjectOutputStream(new FileOutputStream(privateKeyA));
				privateKeyOSA.writeObject(privA);
				privateKeyOSA.close();
			}

			if (!privateKeyB.exists()) {
				// Saving the Private key of B in a file
				ObjectOutputStream privateKeyOSB = new ObjectOutputStream(new FileOutputStream(privateKeyB));
				privateKeyOSB.writeObject(privB);
				privateKeyOSB.close();
			}

			// System.out.println("Public A Key: " + pubA);
			// System.out.println("Public B Key: " + pubB);
			// System.out.println("Private A Key: " + privA);
			// System.out.println("Private B Key: " + privB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method generates the directories where the encrypted and decrypted files are stored, if these do not exist
	public void makeDirs() {
		File mainDirectory = new File(mainDir);
		File keyDirectory = new File(keyDir);
		File encrDirectory = new File(encrDir);
		File decrDirectory = new File(decrDir);
		File stegaEncrDirectory = new File(stegaEncrDir);
		File stegaDecrDirectory = new File(stegaDecrDir);

		if (!mainDirectory.exists()) {
			success = mainDirectory.mkdir();
		}

		if (!keyDirectory.exists()) {
			success = keyDirectory.mkdir();
		}

		if (!encrDirectory.exists()) {
			success = encrDirectory.mkdir();
		}

		if (!decrDirectory.exists()) {
			success = decrDirectory.mkdir();
		}

		if (!stegaEncrDirectory.exists()) {
			success = stegaEncrDirectory.mkdir();
		}

		if (!stegaDecrDirectory.exists()) {
			success = stegaDecrDirectory.mkdir();
		}
	}

}
