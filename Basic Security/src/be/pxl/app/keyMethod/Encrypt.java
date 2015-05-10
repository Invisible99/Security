package be.pxl.app.keyMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Encrypt {

	public final String ENCRYPTED_SYMMETRIC = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files/File_2";
	public final String ENCRYPTED_HASH = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files/File_3";

	// File
	private File file;

	// Secret Key
	private SecretKey key;

	// AES key size
	public final int AES_Key_Size = 128;

	// Byte aesKey
	private byte[] aesKey;
	private byte[] hashKey;

	// Cipher
	private Cipher pkCipher;
	private Cipher aesCipher;

	// Private and public key
	private PublicKey pub;
	private PrivateKey priv;
	
	//Error Strings
	//private String errorMsg;
	//private String errorTitle;

	// SecretKeySpec
	private SecretKeySpec aeskeySpec;
	
	public Encrypt(String fileName) throws NoSuchAlgorithmException, NoSuchPaddingException {
		// create RSA public key cipher
		pkCipher = Cipher.getInstance("RSA");

		// create AES shared key cipher
		aesCipher = Cipher.getInstance("AES");

		file = new File(fileName);
	}

	@SuppressWarnings("resource")
	// Method reads public and private key and stores them in the variables
	// (tested and works)
	public void readKeys(String publicKeyPath, String privateKeyPath) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = null;

		// Read the public key
		inputStream = new ObjectInputStream(new FileInputStream(publicKeyPath));
		pub = (PublicKey) inputStream.readObject();

		// Read the private key
		inputStream = new ObjectInputStream(new FileInputStream(privateKeyPath));
		priv = (PrivateKey) inputStream.readObject();

		// System.out.println("Keys loaded");
		// System.out.println("Public Key: " + pub);
		// System.out.println("Private Key: " + priv);
	}

	// Method generates AES session key and stores it in a SecretKey variable
	// (tested and works)
	public void generateAES() throws NoSuchAlgorithmException {
		// Returns a KeyGenerator object that generates secret keys for the specified algorithm.
		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		// Initializes this key generator for a certain keysize.
		kgen.init(AES_Key_Size);

		// Generates a secret key.
		key = kgen.generateKey();

		// Returns the key in its primary encoding format, or null if this key does not support encoding.
		aesKey = key.getEncoded();

		// Constructs a secret key from the given byte array.
		aeskeySpec = new SecretKeySpec(aesKey, "AES");
		// System.out.println("AES: " + key);
	}

	// Method encrypts file using AES key
	// (tested and works)
	public void encryptFile() throws IOException, InvalidKeyException {
		int dot = file.getPath().lastIndexOf(".");
		aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);

		FileInputStream is;
		is = new FileInputStream(file.getAbsolutePath());
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files/File_1." + file.getPath().substring(dot + 1)), aesCipher);

		int i;
		byte[] b = new byte[1024];
		while ((i = is.read(b)) != -1) {
			os.write(b, 0, i);
		}
		// System.out.println("File encrypted");
		is.close();
		os.close();
	}

	// Method encrypts AES key using public key (if having the right one)
	// (tested and works)
	public void encryptAES() throws InvalidKeyException, IOException {
		pkCipher.init(Cipher.ENCRYPT_MODE, pub);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(ENCRYPTED_SYMMETRIC), pkCipher);
		os.write(aesKey);
		os.close();
		// System.out.println("AES encrypted");
	}

	@SuppressWarnings("resource")
	// Method generates a hash of the file you're encrypting
	// (tested and works)
	public String generateHash(String datafile) {
		StringBuffer sb = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(datafile);
			byte[] dataBytes = new byte[1024];

			int nread = 0;

			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}

			byte[] mdbytes = md.digest();

			// convert the byte to hex format

			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// System.out.println("Digest(in hex format):: " + sb.toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Failed to generate hash for file", "Hash generation error", JOptionPane.ERROR_MESSAGE);
			// e.printStackTrace();
		}
		return sb.toString();
	}

	// Method encrypts the hash
	// (tested and works)
	public void encryptHash(String hash, String privateKeyFile) throws InvalidKeyException, IOException {
		pkCipher.init(Cipher.ENCRYPT_MODE, priv);
		hashKey = hash.getBytes();
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(ENCRYPTED_HASH), pkCipher);
		os.write(hashKey);
		os.close();
		// System.out.println("Hash encrypted");
	}
}
