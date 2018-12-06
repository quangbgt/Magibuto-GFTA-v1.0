package io.traderx.darkness.sicbo.simulator;

import java.io.File;
import java.util.ArrayList;

import io.traderx.darkness.helper.IOHelper;
import io.traderx.darkness.helper.MiscHelper;
import io.traderx.darkness.helper.ThreadHelper;

public class FollowineSim {
	
	//Bet options
	public static final String BIG = "BIG";
	public static final String SMALL = "SMALL";
	public static final String EVEN = "EVEN";
	public static final String ODD = "ODD";
	public static final String TRIPLE = "TRIPLE";
	//Entry options
	public static final String BS = "BS";
	public static final String EO = "EO";
	public static final String DUAL = "DUAL";
	
	private int profit;
	private int baseBet = 10;
	private int dice1, dice2, dice3;
	private int resultSum;
	private String resultString = null;
	
	private int counter;
	private int offset;
	private String entryOption; // BS EO DUAL
	private String bsBetOption = "bsf";
	private String eoBetOption = "eof";
	private int bsBetValue, eoBetValue;
	private String bsPattern, eoPattern;
	private int bigLevel, smallLevel, evenLevel, oddLevel;
	
	private ArrayList<String> data;
	
	private int sl, tp;
	private int currentBsUpset=0;
	private int currentEoUpset=0;
	private String source, path;
	private int triggerLevel = 0;
	private boolean enableLog = true;
	private String bofp, boap, sefp, seap;
	
	public FollowineSim(String path, String entryOption, String bofp, String boap, String sefp, String seap, int offset, int triggerLevel, int sl, int tp, boolean enableLog) {
		super();
		this.path = path;
		this.source = (path.replace("\\", "/").split("/")[1]).replace(".txt", "");
		this.entryOption = entryOption;
		this.bofp = bofp;
		this.boap = boap;
		this.sefp = sefp;
		this.seap = seap;
		this.offset = offset;
		this.triggerLevel = triggerLevel;
		this.sl = sl;
		this.tp = tp;
		this.enableLog = enableLog;
	}
	
	public void run() {
		
		this.data =  IOHelper.readLines(path);
		
		while(this.profit < this.baseBet * this.tp) {
			
			this.fetch(this.data.get(this.counter));
			
			if(this.profit >= this.baseBet * this.tp) {
				if(this.enableLog) {
					this.log();
				}
				System.out.println("#DATA=" + source +"\tTP=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter);
				break;
			}
			
			if(this.profit <= -this.baseBet * this.sl) {
				if(this.enableLog) {
					this.log();
				}
				System.out.println("#DATA=" + source +"\tSL=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter);
				break;
			}
			
			if(this.counter == this.data.size()-1) {
				if(this.enableLog) {
					this.log();
				}
				System.out.println("#DATA=" + source +"\tLM=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter);
				break;
			}
			
			this.setupBet();
			if(this.enableLog) {
				this.log();
			}
			this.counter++;
		}
	}
	
	public void fetch(String res) {
		
		if(!res.equals(null) && res.length() == 3) {
			
			this.dice1 = Integer.parseInt(String.valueOf(res.charAt(0)));
			this.dice2 = Integer.parseInt(String.valueOf(res.charAt(1)));
			this.dice3 = Integer.parseInt(String.valueOf(res.charAt(2)));
			
			this.resultSum = this.dice1 + this.dice2 + this.dice3;
			this.resultString = "" + this.dice1 + this.dice2 + this.dice3;
			
			if(this.dice1 == this.dice2 && this.dice1 == this.dice3) {
				
				this.bsPattern += "3";
				this.eoPattern += "3";
				
				this.profit -= (this.bsBetValue + this.eoBetValue);
				
				if(this.bsBetOption.equalsIgnoreCase(BIG)) {
					this.bigLevel--;
				}
				
				if(this.bsBetOption.equalsIgnoreCase(SMALL)) {
					this.smallLevel--;
				}
				
				if(this.eoBetOption.equalsIgnoreCase(EVEN)) {
					this.evenLevel--;
				}
				
				if(this.eoBetOption.equalsIgnoreCase(ODD)) {
					this.oddLevel--;
				}
				
			} else {
				
				this.resultSum = this.dice1 + this.dice2 + this.dice3;
				
				if(this.entryOption.equalsIgnoreCase(BS)) {
					this.updateBS();
				}
				
				if(this.entryOption.equalsIgnoreCase("EO")) {
					this.updateEO();
				}
				
				if(this.entryOption.equalsIgnoreCase("DUAL")) {
					this.updateBS();
					this.updateEO();
				}
			}
		}
	}
	
	private void updateBS() {
		
		if(this.resultSum >= 11) {
			
			this.bsPattern += "1";
			
			if(this.bsBetOption.equalsIgnoreCase(BIG)) {
				this.bigLevel--;
				this.profit += this.bsBetValue;
			}
			if(this.bsBetOption.equalsIgnoreCase(SMALL)) {
				this.smallLevel++;
				this.profit -= this.bsBetValue;
			}
		} else {
			
			this.bsPattern += "0";
			
			if(this.bsBetOption.equalsIgnoreCase(SMALL)) {
				this.smallLevel--;
				this.profit += this.bsBetValue;
			}
			if(this.bsBetOption.equalsIgnoreCase(BIG)){
				this.bigLevel++;
				this.profit -= this.bsBetValue;
			}
		}
		
		this.currentBsUpset = this.count(this.boap, this.bsPattern) - this.count(this.bofp, this.bsPattern);
	}
	
	private void updateEO() {
		
		if(this.resultSum % 2 == 0) {
			
			this.eoPattern += "0";
			
			if(this.eoBetOption.equalsIgnoreCase(EVEN)) {
				this.evenLevel--;
				this.profit += this.eoBetValue;
			}
			if(this.eoBetOption.equalsIgnoreCase(ODD)) {
				this.oddLevel++;
				this.profit -= this.eoBetValue;
			}
		} else {
			
			this.eoPattern += "1";
			
			if(this.eoBetOption.equalsIgnoreCase(ODD)) {
				this.oddLevel--;
				this.profit += this.eoBetValue;
			}
			if(this.eoBetOption.equalsIgnoreCase(EVEN)) {
				this.evenLevel++;
				this.profit -= this.eoBetValue;
			}
		}
		
		this.currentEoUpset = this.count(this.seap, this.eoPattern) - this.count(this.sefp, this.eoPattern);
	}
	
	private void setupBet() {
		
		if(this.entryOption.equalsIgnoreCase(BS)) {
			this.setupBsBet();
		}
		
		if(this.entryOption.equalsIgnoreCase(EO)) {
			this.setupEoBet();
		}
		
		if(this.entryOption.equalsIgnoreCase(DUAL)) {
			this.setupBsBet();
			this.setupEoBet();
		}
	}
	
	private void setupBsBet() {
			
		if(this.resultSum >= 11) {
			
			if(this.bigLevel >= this.triggerLevel && this.isGaleableBig()) {
				this.bsBetValue = this.baseBet * (this.bigLevel + 1);
			}else {
				this.bsBetValue = this.baseBet;
			}
			
			this.bsBetOption = BIG;
			
		} else {
			
			if(this.smallLevel >= this.triggerLevel && this.isGaleableSmall()) {
				this.bsBetValue = this.baseBet * (this.smallLevel + 1);
			}else {
				this.bsBetValue = this.baseBet;
			}
			
			this.bsBetOption = SMALL;
		}
	}
	
	private void setupEoBet() {
		
		if(this.resultSum % 2 == 0) {
			
			if(this.evenLevel >= this.triggerLevel && this.isGaleableEven()) {
				this.eoBetValue = this.baseBet * (this.evenLevel + 1);
			}else {
				this.eoBetValue = this.baseBet;
			}
			
			this.eoBetOption = EVEN;
			
		} else {
			
			if(this.oddLevel >= this.triggerLevel && this.isGaleableOdd()) {
				this.eoBetValue = this.baseBet * (this.oddLevel + 1);
			}else {
				this.eoBetValue = this.baseBet;
			}
			
			this.eoBetOption = ODD;
		}
	}
	
	private void log() {
		System.out.println("@counter=" + this.counter + " [");
		System.out.println("\tresult=" + this.resultString);
		System.out.println("\tprofit=" + this.profit);
		
		if(this.entryOption.equalsIgnoreCase(BS)) {
			System.out.println("\tbs-bet=[" + this.bsBetOption + "," + this.bsBetValue + ", upset=" + this.currentBsUpset+ "]");
		}
		if(this.entryOption.equalsIgnoreCase(EO)) {
			System.out.println("\teo-bet=[" + this.eoBetOption + "," + this.eoBetValue + ", upset=" + this.currentEoUpset+"]");
		}
		if(this.entryOption.equalsIgnoreCase(DUAL)) {
			System.out.println("\tbs-bet=[" + this.bsBetOption + "," + this.bsBetValue + ", upset=" + this.currentBsUpset+"]");
			System.out.println("\teo-bet=[" + this.eoBetOption + "," + this.eoBetValue + ", upset=" + this.currentBsUpset+"]");
		}
		System.out.println("]");
	}
	
	private boolean isGaleableBig() {
		if(!this.bsPattern.endsWith("0101") && this.count(this.bofp, this.bsPattern) <=  this.count(this.boap, this.bsPattern) - this.offset) {
			return true;
		}
		return false;
	}
	
	private boolean isGaleableSmall() {
		if(!this.bsPattern.endsWith("1010") && this.count(this.sefp, this.bsPattern) <=  this.count(this.seap, this.bsPattern) - this.offset) {
			return true;
		}
		return false;
	}
	
	private boolean isGaleableOdd() {
		if(!this.eoPattern.endsWith("0101") && this.count(this.bofp, this.eoPattern) <=  this.count(this.boap, this.eoPattern) - this.offset){
			return true;
		}
		return false;
	}
	
	private boolean isGaleableEven() {
		if(!this.eoPattern.endsWith("1010") && this.count(this.sefp, this.eoPattern) <=  this.count(this.seap, this.eoPattern) - this.offset) {
			return true;
		}
		return false;
	}
	
	
	public int count(String pattern, String input) { 
	    int i = (input.length()-input.replace(pattern, "").length())/pattern.length();
	    return i;
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
	
	@Override
	public String toString() {
		return "FollowineSim [resultString=" + resultString + "]";
	}
}
