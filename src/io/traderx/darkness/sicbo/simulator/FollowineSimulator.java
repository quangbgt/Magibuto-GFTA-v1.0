package io.traderx.darkness.sicbo.simulator;

import java.io.File;
import java.util.ArrayList;

public class FollowineSimulator {
	
	String bofp = "011";
	String boap = "010";
	String sefp = "100";
	String seap = "101";
	
	public FollowineSimulator() {
		
		this.simulate("Full", "DUAL", 1, 1, 250, 10, false);
	}
	
	public void simulate(String source, String option, int offset, int galeTrigger, int sl, int tp, boolean enableLog) {
		
		if(source.equalsIgnoreCase("FULL")) {
			
			ArrayList<String> paths = this.getFilePaths("data/");
			
			for(String path : paths) {
				FollowineSim bot = new FollowineSim(path, option, bofp, boap, sefp, seap, offset,  galeTrigger, sl, tp, enableLog);
				bot.run();
			}
			
		}else {
			FollowineSim bot = new FollowineSim("data/" + source + ".txt", option, bofp, boap, sefp, seap, offset, galeTrigger, sl, tp, enableLog);
			bot.run();
		}
	}
	
	public ArrayList<String> getFilePaths(String dir) {
		
		File file = new File(dir);
		File[] files = null;
		
		ArrayList<String> pathList = new ArrayList<String>();
		
		if(file.isDirectory()) {
			files = file.listFiles();
		}
		
		for(int i = 0; i<files.length; i++) {
			if(!files[i].isDirectory()) {
				pathList.add(files[i].getPath());
			}
		}
		return pathList;
	}
	
	public static void main(String[] args ) {
		new FollowineSimulator();
	}
}
