package io.traderx.darkness.helper;

import java.awt.Color;
import java.awt.Robot;

public class MiscHelper {
	
	public static String getColor(int x, int y, Robot robot) {
		Color color = robot.getPixelColor(x, y);
		return "" + color.getRed() + color.getGreen() + color.getBlue();
	}
}
