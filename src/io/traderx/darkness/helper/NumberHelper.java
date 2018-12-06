package io.traderx.darkness.helper;

import java.util.Random;

public class NumberHelper {
	
	public static int getRandomNumber(int max, int min) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}
}
