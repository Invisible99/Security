package be.pxl.examples;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class Test {

	public static void main(String[] args) throws Exception {

		// Generate Symmetric key
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128);
		SecretKey key = generator.generateKey();
		byte[] symmetricKey = key.getEncoded();
		System.out.println("key : " + symmetricKey);

		// Generate private key public key pair
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		// Encrypt Data by symmetric key
		//String encryptedData = encryptWithAESKey("Een hele lange zin die geen betekenis heeft LOL.", symmetricKey);
		//System.out.println("Encrypted Data : " + encryptedData);

		// Encrypt symmetric key by public key
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		//String encryptedkey = Base64.encodeBase64String(cipher.doFinal(symmetricKey));

		// Send message and key to other user having private key

		// Decrypt symmetric Key by private key
		Cipher dipher = Cipher.getInstance("RSA");

		dipher.init(Cipher.DECRYPT_MODE, privateKey);
		//byte[] decryptedSymmetricKey = dipher.doFinal(Base64.decodeBase64(encryptedkey));

		// Decrypt encrypted Data by decrypted symmetric key
		//System.out.println("Decrypted Data : " + decryptWithAESKey(encryptedData, decryptedSymmetricKey));

	}

	/*public static String encryptWithAESKey(String data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		SecretKey secKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] newData = cipher.doFinal(data.getBytes());

		//return Base64.encodeBase64String(newData);
	}*/

	/*public static String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secKey = new SecretKeySpec(key, "AES");

		cipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] newData = cipher.doFinal(Base64.decodeBase64(inputData.getBytes()));
		return new String(newData);

	}*/

}