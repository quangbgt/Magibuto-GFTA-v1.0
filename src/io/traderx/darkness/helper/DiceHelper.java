package io.traderx.darkness.helper;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import io.traderx.darkness.base.Table;


public class DiceHelper {

	public static int[] fetchDices(Table table, Robot robot, String[] dicePathArray, double diffPercent) {
		
		int[] dices = new int[3];
		
		Rectangle rectArea1 = new Rectangle(table.getDice1x(), table.getDice1y(), table.getDiceWidth(), table.getDiceHeight());
		BufferedImage buffer1 = robot.createScreenCapture(rectArea1);
		dices[0] = match(buffer1, dicePathArray, diffPercent);

		Rectangle rectArea2 = new Rectangle(table.getDice2x(), table.getDice2y(), table.getDiceWidth(), table.getDiceHeight());
		BufferedImage buffer2 = robot.createScreenCapture(rectArea2);
		dices[1] = match(buffer2, dicePathArray, diffPercent);

		Rectangle rectArea3 = new Rectangle(table.getDice3x(), table.getDice3y(), table.getDiceWidth(), table.getDiceHeight());
		BufferedImage buffer3 = robot.createScreenCapture(rectArea3);
		dices[2] = match(buffer3, dicePathArray, diffPercent);
		
		return dices;
	}
	
	public static void saveDice(int x, int y, int width, int height, Robot robot, String saveTo) {
		try {
			Rectangle rectArea = new Rectangle(x, y, width, height);
			BufferedImage screenFullImage = robot.createScreenCapture(rectArea);
			//SAVE_DICES_DIR + "capturred_" + System.currentTimeMillis() + "_.bmp"
			ImageIO.write(screenFullImage, "bmp", new File(saveTo));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static int match(BufferedImage image, String[] dicePathArray, double diffPercent) {
		int result = 0;
		try {
			BufferedImage buffer = null;
			double diff = 0;
			for (int i = 0; i < dicePathArray.length; i++) {
				buffer = ImageIO.read(new File(dicePathArray[i]));
				diff = getDifferencePercent(image, buffer);
				if (diff < diffPercent) {
					result = i + 1;
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	private static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
		int width = img1.getWidth();
		int height = img1.getHeight();
		int width2 = img2.getWidth();
		int height2 = img2.getHeight();
		if (width != width2 || height != height2) {
			throw new IllegalArgumentException(String.format(
					"Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
		}

		long diff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
			}
		}
		long maxDiff = 3L * 255 * width * height;

		return 100.0 * diff / maxDiff;
	}
	
	private static int pixelDiff(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}
}
