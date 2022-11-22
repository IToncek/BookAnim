package me.itoncek;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

public class Main {
	public static void main(String[] args) throws Exception {
		File parent = new File("C:\\Users\\user\\Music\\rick");
		char c = 'a';
		String pref = "";
		for (File file : Objects.requireNonNull(parent.listFiles())) {
			if(file.getAbsolutePath().endsWith("png")) {
				try(FileWriter fw = new FileWriter("C:\\Users\\user\\AppData\\Roaming\\.minecraft\\saves\\New World (27)\\datapacks\\example\\data\\example\\functions\\" + mult(c, pref) + ".mcfunction")) {
					BufferedImage src = rotate(ImageIO.read(file));
					for (int y = 0; y < src.getHeight(); y = y+2) {
						for (int x = 0; x < src.getWidth(); x = x+3) {
							boolean b0 = Brightness(src.getRGB(x, y));
							boolean b1 = Brightness(src.getRGB(x + 1, y));
							boolean b2 = Brightness(src.getRGB(x + 2, y));
							boolean b3 = Brightness(src.getRGB(x, y + 1));
							boolean b4 = Brightness(src.getRGB(x + 1, y + 1));
							boolean b5 = Brightness(src.getRGB(x + 2, y + 1));
							int trux = (x/3)+5;
							int truy = (y/2)+64;
							fw.write("setblock " + trux + " " + truy +" 11 minecraft:chiseled_bookshelf[slot_0_occupied=" + stringify(b0) + ",slot_1_occupied=" + stringify(b1) + ",slot_2_occupied=" + stringify(b2) + ",slot_3_occupied=" + stringify(b3) + ",slot_4_occupied=" + stringify(b4) + ",slot_5_occupied=" + stringify(b5) + "]\n");
						}
					}
					fw.write("schedule function example:" + next(c, pref) + " 1t");
					if(c == 'z') {
						pref = pref + "z";
						c = 'a';
					} else {
						c++;
					}
				}
			}
		}
	}
	
	public static BufferedImage rotate(BufferedImage img) {
		
		// Getting Dimensions of image
		int width = img.getWidth();
		int height = img.getHeight();
		
		// Creating a new buffered image
		BufferedImage newImage = new BufferedImage(
				img.getWidth(), img.getHeight(), img.getType());
		
		// creating Graphics in buffered image
		Graphics2D g2 = newImage.createGraphics();
		
		// Rotating image by degrees using toradians()
		// method
		// and setting new dimension t it
		g2.rotate(Math.toRadians(180), width / 2.0,
		          height / 2.0);
		g2.drawImage(img, null, 0, 0);
		
		// Return rotated buffer image
		return newImage;
	}
	
	private static String stringify(boolean in) {
		if (in) {
			return "true";
		}
		return "false";
	}
	private static String next(char c, String pref){
		if(c == 'z') {
			return pref + "za";
		} else {
			char d = c;
			d++;
			return pref + d;
		}
	}
	private static String mult(char c, String pref) {
		return pref + c;
	}
	private static boolean Brightness(int rgb) {
		Color c = new Color(rgb);
		return (int)Math.sqrt(
				c.getRed() * c.getRed() * .241 +
						c.getGreen() * c.getGreen() * .691 +
						c.getBlue() * c.getBlue() * .068) > 127;
	}
}