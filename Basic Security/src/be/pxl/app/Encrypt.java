package be.pxl.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encrypt {

	// Public keys
	public final String PUBLIC_KEY_A = "keys/public_A.key";
	public final String PUBLIC_KEY_B = "keys/public_B.key";

	// Private keys
	public final String PRIVATE_KEY_A = "keys/private_A.key";
	public final String PRIVATE_KEY_B = "keys/private_B.key";

	// DES symmetric key
	//public final String SYMMETRIC_KEY = "keys/symmetric.key";

	public Encrypt() {
		//generateKeys();
	}

	public void generateKeys() {
		try {
			// RSA part
			final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			final KeyPair keyA = keyPairGen.generateKeyPair();
			final KeyPair keyB = keyPairGen.generateKeyPair();

			File publicKeyA = new File(PUBLIC_KEY_A);
			File publicKeyB = new File(PUBLIC_KEY_B);

			File privateKeyA = new File(PRIVATE_KEY_A);
			File privateKeyB = new File(PRIVATE_KEY_B);

			// Generate public keys for A and B
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

			// Generate private keys for A and B
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

			// Saving the Public key of A in a file
			ObjectOutputStream publicKeyOSA = new ObjectOutputStream(new FileOutputStream(publicKeyA));
			publicKeyOSA.writeObject(keyA.getPublic());
			publicKeyOSA.close();

			// Saving the Public key of B in a file
			ObjectOutputStream publicKeyOSB = new ObjectOutputStream(new FileOutputStream(publicKeyB));
			publicKeyOSB.writeObject(keyB.getPublic());
			publicKeyOSB.close();

			// Saving the Private key of A in a file
			ObjectOutputStream privateKeyOSA = new ObjectOutputStream(new FileOutputStream(privateKeyA));
			privateKeyOSA.writeObject(keyA.getPrivate());
			privateKeyOSA.close();

			// Saving the Private key of B in a file
			ObjectOutputStream privateKeyOSB = new ObjectOutputStream(new FileOutputStream(privateKeyB));
			privateKeyOSB.writeObject(keyB.getPrivate());
			privateKeyOSB.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateSymmetric() {
		try {
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
