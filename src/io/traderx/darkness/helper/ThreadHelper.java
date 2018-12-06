package io.traderx.darkness.helper;

public class ThreadHelper {
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
