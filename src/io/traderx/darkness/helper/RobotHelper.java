package io.traderx.darkness.helper;

import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;

public class RobotHelper {
	
	private static Robot robot = null;
	private static Desktop desktop = null;
	
	private static final String CLICKER_PATH = "tools/clicker64.exe";
	private static File clicker = null;
	
	public static void moveTo(int x, int y) {
		init();
		robot.mouseMove(x, y);
	}
	
	public static void click() {
		try {
			init();
			desktop.open(clicker);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void click(int x, int y) {
		try {
			init();
			robot.mouseMove(x, y);
			ThreadHelper.sleep(150);
			desktop.open(clicker);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void init() {
		try {
			if(robot == null) {
				robot = new Robot();
			}
			if(desktop == null) {
				desktop = Desktop.getDesktop();
			}
			if(clicker == null) {
				clicker = new File(CLICKER_PATH);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
