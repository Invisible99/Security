//************************************************
package be.pxl.app.soundStega;

// Stego.java
//************************************************
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.spec.PBEKeySpec;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

class SoundStega {
	public boolean feasible = true;
	private AudioInputStream audioInputStream;
	private byte[] audioBytes;
	private byte[] buff;
	private String outFile;
	char password[];
	PBEKeySpec pbeKeySpec;

	// Constructor for encrypting
	public SoundStega(String sndFile, String ptFile, String oFile) throws UnsupportedAudioFileException, IOException {
		outFile = oFile;
		readSND(sndFile);
		feasible = possible(ptFile);
	}

	// Constructor for decrypting
	public SoundStega(String sndFile, String ptFile) throws UnsupportedAudioFileException, IOException {
		outFile = ptFile;
		readSND(sndFile);
	}

	public void encode() throws IOException {

		int k = 0;
		int i = 1; // start of plaintext in audioBytes
		int pt;
		byte pb;

		// System.out.println("Hiding the ciphertext in AU file ...");
		// First encode the length of the plaintext
		// System.out.println("test");
		pt = buff.length;
		for (int j = 1; j <= 32; j++) {
			if ((pt & 0x80000000) != 0) // MSB of pt is '1'
				audioBytes[i] = (byte) (audioBytes[i] | 0x01);
			else if ((audioBytes[i] & 0x01) != 0) { // MSB of pt '0' and lsb of audio '1'
				audioBytes[i] = (byte) (audioBytes[i] >>> 1);
				audioBytes[i] = (byte) (audioBytes[i] << 1);
			}// if
			pt = (int) (pt << 1);
			i++;
		}

		// Now start encoding the message itself!
		while (k < buff.length) {
			pb = buff[k];
			// System.out.print((char)pt);
			for (int j = 1; j <= 8; j++) {
				if ((pb & 0x80) != 0) // MSB of pb is '1'
					audioBytes[i] = (byte) (audioBytes[i] | 0x01);
				else if ((audioBytes[i] & 0x01) != 0) { // MSB of pt '0' and lsb of audio '1'
					audioBytes[i] = (byte) (audioBytes[i] >>> 1);
					audioBytes[i] = (byte) (audioBytes[i] << 1);
				}
				pb = (byte) (pb << 1);
				i++;
			}
			k++;
		}
		// now write the byte array to an audio file
		File fileOut = new File(outFile);
		ByteArrayInputStream byteIS = new ByteArrayInputStream(audioBytes);
		AudioInputStream audioIS = new AudioInputStream(byteIS, audioInputStream.getFormat(), audioInputStream.getFrameLength());
		if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AU, audioIS)) {
			AudioSystem.write(audioIS, AudioFileFormat.Type.AU, fileOut);
			// System.out.println("Steganographed AU file is written as " + outFile + "...");
		}
	}

	public boolean decode() throws IOException {
		boolean success = true;
		byte out = 0;
		int length = 0;
		int k = 1; // start of plaintext in audioBytes
		// System.out.println("Retrieving the ciphertext from AU file ....");

		// First decode the first 32 samples to find the length of the message text
		length = length & 0x00000000;
		for (int j = 1; j <= 32; j++) {
			length = length << 1;
			if ((audioBytes[k] & 0x01) != 0) {
				length = length | 0x00000001;
			}
			k++;
		}
		buff = new byte[length];
		// System.out.println("Length: " + length);

		// Now decode the message!
		out = (byte) (out & 0x00);
		for (int i = 0; i < length; i++) {
			for (int j = 1; j <= 8; j++) {
				out = (byte) (out << 1);
				if ((audioBytes[k] & 0x01) != 0) {
					out = (byte) (out | 0x01);
				}
				k++;
			}
			buff[i] = out;
			// System.out.print((char)out);
			out = (byte) (out & 0x00);

		}
		// System.out.println("Writing the decrypted hidden message to" + outFile + " ...");
		FileOutputStream outfile = new FileOutputStream(outFile);
		// outfile.write(clearbuff);
		outfile.write(buff);
		outfile.close();

		return success;
	}

	// Method to read the sound file
	private void readSND(String sndf) throws UnsupportedAudioFileException, IOException {
		File sndfile = new File(sndf);
		int totalFramesRead = 0;

		// System.out.println("Reading (AU) sound file ...");
		audioInputStream = AudioSystem.getAudioInputStream(sndfile);
		// sndFormat = audioInputStream.getFormat();
		int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
		// Set an arbitrary buffer size of 1024 frames.
		int numBytes = 154600 * bytesPerFrame;
		audioBytes = new byte[numBytes];
		int numBytesRead = 0;
		int numFramesRead = 0;
		// Try to read numBytes bytes from the file.
		while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
			// Calculate the number of frames actually read.
			numFramesRead = numBytesRead / bytesPerFrame;
			totalFramesRead += numFramesRead;
			// Here, work with the audio data that's
			// now in the audioBytes array...
		}
	}

	// Is it possible to do steganography with current file
	private boolean possible(String pt) throws IOException {
		// accessing the input file
		// System.out.println("Reading the plaintext file ..." + pt);
		FileInputStream fis = new FileInputStream(pt);
		BufferedInputStream bis = new BufferedInputStream(fis);
		int len = bis.available();
		buff = new byte[len];

		while (bis.available() != 0)
			len = bis.read(buff);
		bis.close();
		fis.close();
		return true;
	}

	public boolean isAu(String auFile) {
		boolean match;
		int dot = auFile.lastIndexOf(".");
		String fnm = auFile.substring(dot + 1);
		if (fnm.equals("au")) {
			match = true;
		} else {
			match = false;
		}
		return match;
	}

	public boolean isTxt(String txtFile) {
		boolean match;
		int dot = txtFile.lastIndexOf(".");
		String fnm = txtFile.substring(dot + 1);
		if (fnm.equals("txt")) {
			match = true;
		} else {
			match = false;
		}
		return match;
	}

}// class
