package be.pxl.app;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Steganography {

	private final int MAX_INT_LEN = 4;
	private final int DATA_SIZE = 8;
	
	public final String ENCRYPTED_MESSAGE = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Steganography Files/";
	public final String DECRYPTED_MESSAGE = System.getProperty("user.home") + "/documents/Security App Files/Decrypted Steganography Files/";

	
	// --------------------------- hide a message -----------------------------------
	
	public boolean hide(String textFnm, String imFnm) {
		String inputText = readTextFile(textFnm);
		if ((inputText == null) || (inputText.length() == 0))
			return false;

		byte[] stego = buildStego(inputText);

		BufferedImage im = loadImage(imFnm);
		if (im == null)
			return false;
		byte imBytes[] = accessBytes(im);

		if (!singleHide(imBytes, stego))
			return false;

		File file = new File(imFnm);

		//int dot = file.getName().lastIndexOf(".");
		
		//String fnm = file.getName().substring(0, dot);
		String fnm = file.getName();
		return writeImageToFile(ENCRYPTED_MESSAGE + fnm, im);
	}

	private String readTextFile(String fnm) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try {
			br = new BufferedReader(new FileReader(new File(fnm)));
			String text = null;
			while ((text = br.readLine()) != null)
				sb.append(text + "\n");
		} catch (Exception e) {
			System.out.println("Could not completely read " + fnm);
			return null;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				System.out.println("Problem closing " + fnm);
				return null;
			}
		}
		System.out.println("Read in " + fnm);
		return sb.toString();
	}

	private byte[] buildStego(String inputText) {
		byte[] msgBytes = inputText.getBytes();
		byte[] lenBs = intToBytes(msgBytes.length);

		int totalLen = lenBs.length + msgBytes.length;
		byte[] stego = new byte[totalLen];

		System.arraycopy(lenBs, 0, stego, 0, lenBs.length);
		System.arraycopy(msgBytes, 0, stego, lenBs.length, msgBytes.length);

		return stego;
	}

	private byte[] intToBytes(int i) {

		byte[] integerBs = new byte[MAX_INT_LEN];
		integerBs[0] = (byte) ((i >>> 24) & 0xFF);
		integerBs[1] = (byte) ((i >>> 16) & 0xFF);
		integerBs[2] = (byte) ((i >>> 8) & 0xFF);
		integerBs[3] = (byte) (i & 0xFF);

		return integerBs;
	}

	private BufferedImage loadImage(String imFnm) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File(imFnm));
			System.out.println("Read " + imFnm);
		} catch (IOException e) {
			System.out.println("Could not read image from " + imFnm);
		}

		return im;
	}

	private byte[] accessBytes(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}

	private boolean singleHide(byte[] imBytes, byte[] stego) {
		int imLen = imBytes.length;
		System.out.println("Byte length of image: " + imLen);

		int totalLen = stego.length;
		System.out.println("Total byte length of message: " + totalLen);

		if ((totalLen * DATA_SIZE) > imLen) {
			System.out.println("Image not big enough for message");
			return false;
		}

		hideStego(imBytes, stego, 0);
		return true;
	}

	private void hideStego(byte[] imBytes, byte[] stego, int offset) {
		for (int i = 0; i < stego.length; i++) {
			int byteVal = stego[i];
			for (int j = 7; j >= 0; j--) {
				int bitVal = (byteVal >>> j) & 1;
				imBytes[offset] = (byte) ((imBytes[offset] & 0xFE) | bitVal);
				offset++;
			}
		}
	}

	private boolean writeImageToFile(String outFnm, BufferedImage im) {
		if (!canOverWrite(outFnm))
			return false;

		try {
			ImageIO.write(im, "png", new File(outFnm));
			System.out.println("Image written to PNG file: " + outFnm);
			return true;
		} catch (IOException e) {
			System.out.println("Could not write image to " + outFnm);
			return false;
		}
	}

	private boolean canOverWrite(String fnm) {
		File f = new File(fnm);
		if (!f.exists())
			return true;
		Scanner in = new Scanner(System.in);
		String response;
		System.out.print("File " + fnm + " already exists. ");
		while (true) {
			System.out.print("Overwrite (y|n)? ");
			response = in.nextLine().trim().toLowerCase();
			if (response.startsWith("n")) // no
				return false;
			else if (response.startsWith("y")) // yes
				return true;
		}
	}

	// --------------------------- reveal a message -----------------------------------

	public boolean reveal(String imFnm) {

		BufferedImage im = loadImage(imFnm);
		if (im == null)
			return false;
		byte[] imBytes = accessBytes(im);
		System.out.println("Byte length of image: " + imBytes.length);
		int msgLen = getMsgLength(imBytes, 0);
		if (msgLen == -1)
			return false;
		System.out.println("Byte length of message: " + msgLen);

		String msg = getMessage(imBytes, msgLen, MAX_INT_LEN * DATA_SIZE);
		if (msg != null) {
			File file = new File(imFnm);

			int dot = file.getName().lastIndexOf(".");
			
			String fnm = file.getName().substring(0, dot);
			return writeStringToFile(DECRYPTED_MESSAGE + fnm + ".txt", msg);
		} else {
			System.out.println("No message found");
			return false;
		}
	}

	private int getMsgLength(byte[] imBytes, int offset) {
		byte[] lenBytes = extractHiddenBytes(imBytes, MAX_INT_LEN, offset);
		if (lenBytes == null)
			return -1;

		int msgLen = ((lenBytes[0] & 0xff) << 24) | ((lenBytes[1] & 0xff) << 16) | ((lenBytes[2] & 0xff) << 8) | (lenBytes[3] & 0xff);

		if ((msgLen <= 0) || (msgLen > imBytes.length)) {
			System.out.println("Incorrect message length");
			return -1;
		}

		return msgLen;
	}

	private String getMessage(byte[] imBytes, int msgLen, int offset) {
		byte[] msgBytes = extractHiddenBytes(imBytes, msgLen, offset);
		if (msgBytes == null)
			return null;

		String msg = new String(msgBytes);

		if (isPrintable(msg)) {
			return msg;
		} else
			return null;
	}

	private byte[] extractHiddenBytes(byte[] imBytes, int size, int offset) {
		int finalPosn = offset + (size * DATA_SIZE);
		if (finalPosn > imBytes.length) {
			System.out.println("End of image reached");
			return null;
		}

		byte[] hiddenBytes = new byte[size];

		for (int j = 0; j < size; j++) {
			for (int i = 0; i < DATA_SIZE; i++) {
				hiddenBytes[j] = (byte) ((hiddenBytes[j] << 1) | (imBytes[offset] & 1));
				offset++;
			}
		}
		return hiddenBytes;
	}

	private boolean isPrintable(String str) {
		for (int i = 0; i < str.length(); i++)
			if (!isPrintable(str.charAt(i))) {
				System.out.println("Unprintable character found");
				return false;
			}
		return true;
	}

	private boolean isPrintable(int ch) {
		if (Character.isWhitespace(ch) && (ch < 127))
			return true;
		else if ((ch > 32) && (ch < 127))
			return true;

		return false;
	}

	private boolean writeStringToFile(String outFnm, String msgStr) {
		if (!canOverWrite(outFnm))
			return false;

		try {
			FileWriter out = new FileWriter(new File(outFnm));
			out.write(msgStr);
			out.close();
			System.out.println("Message written to " + outFnm);
			return true;
		} catch (IOException e) {
			System.out.println("Could not write message to " + outFnm);
			return false;
		}
	}
}
