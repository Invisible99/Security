package be.pxl.examples;// for decoding

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RetreiveData {

	private BufferedImage image = null;
	private int pixels[][];
	private char Data[];
	private int Width, Height;
	private String myString = "";

	public RetreiveData() {
		try {
			image = ImageIO.read(new File("keys/face.bmp"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		pixels = new int[image.getWidth()][image.getHeight()];

		Width = image.getWidth();
		Height = image.getHeight();

		System.out.println(Width);
		System.out.println(Height);

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				pixels[i][j] = image.getRGB(i, j);
			}
		}

		FileWriter fw = null;
		File file = null;
		BufferedWriter bw = null;

		try {
			file = new File("keys/Retreive.txt");
			fw = new FileWriter(file);

			bw = new BufferedWriter(fw);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		ReadLSB();

		try {
			bw.write(myString);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(myString);
	}

	private void ReadLSB() {
		int temp, tempText = 0, x = 0, p = 0;

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				temp = pixels[i][j];

				int change = 0;

				for (int k = 0; k < 4; k++) {
					if (k == 0) {
						change = 1;
					} else if (k == 1) {
						change = 256;
					} else if (k == 2) {
						change = 65536;
					} else if (k == 3) {
						change = 16777216;
					}

					if ((pixels[i][j] & change) == 1) {
						tempText |= 1;
					}

					tempText <<= 1;

					p++;
					if (p == 8) {
						myString += (char) tempText;// convert int to String and storing in String object
						p = 0;
						// x++;
						if (tempText != 272)
							System.out.println("Temp is " + tempText);
						tempText = 0;

					}
				}

			}
		}
	}
}
