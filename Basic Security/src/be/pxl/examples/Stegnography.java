package be.pxl.examples;// for encoding

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stegnography {

	private BufferedImage image = null, new_image = null;
	private int pixels[][];
	private char Data[];
	private int Width, Height;
	private int Binary[];
	private int Length = 0;

	public Stegnography() {
		File fileName = new File("keys/face.bmp");

		try {
			new_image = ImageIO.read(fileName);

			image = new BufferedImage(new_image.getWidth(), new_image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

			Graphics2D graphics = image.createGraphics();
			graphics.drawRenderedImage(new_image, null);

			graphics.dispose();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		pixels = new int[image.getWidth()][image.getHeight()];

		Binary = new int[8];

		Width = image.getWidth();
		Height = image.getHeight();

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				pixels[i][j] = image.getRGB(i, j);
			}
		}

		String name = "Test om te testen";
		Length = name.length();

		Data = name.toCharArray();

		WriteLSB();

		try {
			ImageIO.write(image, "png", fileName);
		}
		catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("Encoding Completed");
	}

	private void WriteLSB() {
		int temp, tempText, x = 0, p = 0, i, j = 0;
		Binary((int) Data[x]);
		x++;

		for (i = 0; (i < Width && x < Data.length); i++) {
			for (j = 0; (j < Height && x < Data.length); j++) {

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

					if (Binary[p] == 0) {
						pixels[i][j] = pixels[i][j] & ~change; // ~1 ki all bits 1 hoti ha except LSB
					} else if (Binary[p] == 1) {
						pixels[i][j] = pixels[i][j] | change; // only LSB ko 1 krna ha
					}
					p++;

					if (p == 8) {
						p = 0;
						Binary((int) Data[x]);
						x++;
					}
				}

			}
		}

		for (i = 0; i < Width; i++) {
			for (int h = 0; h < Height; h++) {
				image.setRGB(i, h, pixels[i][h]);
			}
		}

	}

	private void Binary(int temp) {
		Binary = null;
		Binary = new int[8];

		for (int i = 7; i >= 0; i--)// cuz character is of 1 byte
		{
			Binary[i] = temp % 2;

			System.out.print(Binary[i]);
			temp /= 2;
		}
		System.out.println();
	}
}