package io.traderx.darkness.main;

import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import io.traderx.darkness.base.W88S2Turbo;
import io.traderx.darkness.config.ConfigLoader;
import io.traderx.darkness.helper.IOHelper;
import io.traderx.darkness.helper.MiscHelper;
import io.traderx.darkness.helper.ThreadHelper;
import io.traderx.darkness.helper.DiceHelper;

public class Agas {
	
	private W88S2Turbo table;
	private Robot robot;
	private Desktop desktop;
	private File clicker;
	private File logFile;
	
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
	
	public static final double DICE_DIFF_PERCENT = 7;

	private static final String CLICKER_PATH = "tools/clicker64.exe";
	public static final String DICES_DIR = "dices_src/";
	public static final String[] SAMPLE_DICES = { DICES_DIR + "1.bmp", DICES_DIR + "2.bmp", DICES_DIR + "3.bmp",
			DICES_DIR + "4.bmp", DICES_DIR + "5.bmp", DICES_DIR + "6.bmp" };
	
	private int profit;
	private int baseBet = 10;
	private int currentChip = 10;
	private int dice1, dice2, dice3;
	private int resultSum;
	private int[] resultDices = null;
	private String resultString = null;
	
	private int counter;
	private int offset;
	private String entryOption; // BS EO DUAL
	private String bsBetOption = "bsf";
	private String eoBetOption = "eof";
	private int bsBetValue, eoBetValue;
	private String bsPattern = "", eoPattern = "";
	private int bigLevel, smallLevel, evenLevel, oddLevel;
	private int bsLevel, eoLevel;
	
	private int sl, tp, limit;
	
	private int triggerLevel = 0;
	private boolean isPlacedBet = false;
	private boolean isFetched = false;
	private boolean isRunnable = true;
	private boolean isResultTriple = false;
	private String bofp, boap, sefp, seap;
	private String mode = "Test";
	private String dealer, gameTable, server;
	private String inputResult;
	
	public Agas(String dealer, String gameTable, String server, String mode, String entryOption, String inputRes, int baseBet, int sl, int tp, int limit) {
		super();
		this.init();
		this.dealer = dealer;
		this.gameTable = gameTable;
		this.server = server;
		this.entryOption = entryOption;
		this.inputResult = inputRes;
		this.baseBet = baseBet;
		this.sl = sl;
		this.tp = tp;
		this.limit = limit;
		this.mode = mode;
		
		this.initPatterns();
	}
	
	public void run() {
		
		while(this.counter <= this.limit && this.isRunnable) {
			
			if(this.isFetchable()) {
				
				this.fetch();
				
				if(this.profit >= this.baseBet * this.tp) {
					this.log();
					System.out.println("#TP=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter + "\t on " + this.getTimeString());
					JOptionPane.showMessageDialog(null, "#TP=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter + "\t on " + this.getTimeString());
					this.isRunnable = false;
					break;
				}
				
				if(this.profit <= -this.baseBet * this.sl) {
					this.log();
					System.out.println("#SL=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter  + "\t on " + this.getTimeString());
					JOptionPane.showMessageDialog(null, "#SL=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter  + "\t on " + this.getTimeString());
					this.isRunnable = false;
					break;
				}
				
				if(this.counter == this.limit) {
					this.log();
					System.out.println("#OVER=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter + "\t on " + this.getTimeString());
					JOptionPane.showMessageDialog(null, "#OVER=" + (this.profit/this.baseBet) + "\t@ counter=" + this.counter + "\t on " + this.getTimeString());
					this.isRunnable = false;
					break;
				}
				
				this.setupBet();
				this.placeBet();
				this.confirmBet();
				this.log();
				this.isFetched = false;
				this.isPlacedBet = false;
				this.counter++;
			}
		}
	}
	
	private void init() {
		try {
			this.desktop = Desktop.getDesktop();
			this.robot = new Robot();
			this.clicker = new File(CLICKER_PATH);
			this.logFile = new File("logs/play_" + this.getTimeString()+ "_.log");
			this.loadTableConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initPatterns() {
		if(!this.inputResult.equals("")) {
			String[] results = this.inputResult.split(",");
			int dice1 = 0;
			int dice2 = 0;
			int dice3 = 0;
			int resSum = 0;
			for(String res : results) {
				dice1 = Integer.parseInt(String.valueOf(res.charAt(0)));
				dice2 = Integer.parseInt(String.valueOf(res.charAt(1)));
				dice3 = Integer.parseInt(String.valueOf(res.charAt(2)));
				
				if(dice1 == dice2 && dice1 == dice3) {
					this.bsPattern += "3";
					this.eoPattern += "3";
				} else {
					resSum = dice1 + dice2 + dice3;
					
					if(resSum >= 11) {
						this.bsPattern += "1";
					}else {
						this.bsPattern += "0";
					}
					
					if(resSum % 2 == 0) {
						this.eoPattern += "0";
					}else {
						this.eoPattern += "1";
					}
				}
			}
		}
	}
	
	public void fetch() {
		
//		this.isResultTriple = false;
		
		if(!this.isFetched) {
			
			this.resultDices = DiceHelper.fetchDices(this.table, this.robot, SAMPLE_DICES, DICE_DIFF_PERCENT);

			this.dice1 = this.resultDices[0];
			this.dice2 = this.resultDices[1];
			this.dice3 = this.resultDices[2];

			this.resultString = "" + this.dice1 + this.dice2 + this.dice3;
			
			if(this.dice1 == this.dice2 && this.dice1 == this.dice3) {
				
				this.isResultTriple = true;
				
				this.bsPattern += "3";
				this.eoPattern += "3";
				
				this.bsBetOption = "triple-no-bet";
				this.eoBetOption = "triple-no-bet";
				
				this.profit -= (this.bsBetValue + this.eoBetValue);
				
				if(this.entryOption.equalsIgnoreCase(BS)) {
					this.bsLevel++;
				} else {
					this.eoLevel++;
				}
				
			} else {
				
				this.isResultTriple = false;
				
				this.resultSum = this.dice1 + this.dice2 + this.dice3;
				
				if(this.entryOption.equalsIgnoreCase(BS)) {
					this.updateBS();
				} else {
					this.updateEO();
				}
			}
			
			this.isFetched = true;
			ThreadHelper.sleep(1500);
		}
	}
	
	private void updateBS() {
		
		if(this.resultSum >= 11) {
			
			this.bsPattern += "1";
			
			if(this.bsBetOption.equalsIgnoreCase(BIG)) {
				this.bsLevel--;
				this.profit += this.bsBetValue;
			}
			if(this.bsBetOption.equalsIgnoreCase(SMALL)) {
				this.bsLevel++;
				this.profit -= this.bsBetValue;
			}
		} else {
			
			this.bsPattern += "0";
			
			if(this.bsBetOption.equalsIgnoreCase(SMALL)) {
				this.bsLevel--;
				this.profit += this.bsBetValue;
			}
			if(this.bsBetOption.equalsIgnoreCase(BIG)){
				this.bsLevel++;
				this.profit -= this.bsBetValue;
			}
		}
	}
	
	private void updateEO() {
		
		if(this.resultSum % 2 == 0) {
			
			this.eoPattern += "0";
			
			if(this.eoBetOption.equalsIgnoreCase(EVEN)) {
				this.eoLevel--;
				this.profit += this.eoBetValue;
			}
			if(this.eoBetOption.equalsIgnoreCase(ODD)) {
				this.eoLevel++;
				this.profit -= this.eoBetValue;
			}
		} else {
			
			this.eoPattern += "1";
			
			if(this.eoBetOption.equalsIgnoreCase(ODD)) {
				this.eoLevel--;
				this.profit += this.eoBetValue;
			}
			if(this.eoBetOption.equalsIgnoreCase(EVEN)) {
				this.eoLevel++;
				this.profit -= this.eoBetValue;
			}
		}
	}
	
	private void setupBet() {
		
		if(this.entryOption.equalsIgnoreCase(BS)) {
			this.setupBsBet();
		} else {
			this.setupEoBet();
		}
	}
	
	private void setupBsBet() {
		
		if(this.resultSum >= 11 && !this.isResultTriple) {
			if(this.profit < 0) {
				this.bsBetValue = -(this.profit) + this.baseBet;
			}else {
				this.bsBetValue = this.baseBet;
			}
			this.bsBetOption = SMALL;
		} 
		
		if(this.resultSum <= 10 && !this.isResultTriple) {
			if(this.profit < 0) {
				this.bsBetValue = -(this.profit) + this.baseBet;
			}else {
				this.bsBetValue = this.baseBet;
			}
			this.bsBetOption = BIG;
		}
	}
	
	private void setupEoBet() {
		
		if(this.resultSum % 2 == 0 && !this.isResultTriple) {
			if(this.profit < 0) {
				this.eoBetValue = -(this.profit) + this.baseBet;
			} else {
				this.eoBetValue = this.baseBet;
			}
			this.eoBetOption = ODD;
		} 
		
		if(this.resultSum % 2 != 0 && !this.isResultTriple) {
			if(this.profit < 0) {
				this.eoBetValue = -(this.profit) + this.baseBet;
			} else {
				this.eoBetValue = this.baseBet;
			}
			this.eoBetOption = EVEN;
		}
	}
	
	private void placeBet() {
		
		this.isPlacedBet = false;
		
		if(this.entryOption.equalsIgnoreCase(BS)) {
			if(!this.bsBetOption.equals("triple-no-bet")) {
				this.bet(this.bsBetOption, this.bsBetValue);
			}
		} else {
			if(!this.eoBetOption.equals("triple-no-bet")) {
				this.bet(this.eoBetOption, this.eoBetValue);
			}
		}
	}
	
	private void bet(String option, int value) {
		
		int optionX = -1;
		int optionY = -1;
		
		if(option.equalsIgnoreCase(BIG)) {
			optionX = this.table.getBigX();
			optionY = this.table.getBigY();
		}
		
		if(option.equalsIgnoreCase(SMALL)) {
			optionX = this.table.getSmallX();
			optionY = this.table.getSmallY();
		}
		
		if(option.equalsIgnoreCase(EVEN)) {
			optionX = this.table.getEvenX();
			optionY = this.table.getEvenY();
		}
		
		if(option.equalsIgnoreCase(ODD)) {
			optionX = this.table.getOddX();
			optionY = this.table.getOddY();
		}
		
		if(value < 100) {
			this.betUnder100(value, optionX, optionY);
		}
		
		if(value == 100) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 200) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 400) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 500) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 600) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 700) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 700) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 900) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 1000) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 1500) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 2000) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 2500) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 3000) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 3500) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 4000) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 4500) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
		}	
		
		if(value == 5000) {
			
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value > 100 && value < 200) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-100, optionX, optionY);
		}
		
		if(value > 200 && value < 300) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-200, optionX, optionY);
		}
		
		if(value > 300 && value < 400) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-300, optionX, optionY);
		}
		
		if(value > 400 && value < 500) {
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-400, optionX, optionY);
		}
		
		if(value > 500 && value < 600) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-500, optionX, optionY);
		}
		
		if(value > 600 && value < 700) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-600, optionX, optionY);
		}
		
		if(value > 700 && value < 800) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-600, optionX, optionY);
		}
		
		if(value > 800 && value < 900) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-600, optionX, optionY);
		}
		
		if(value > 900 && value < 1000) {
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder100(value-600, optionX, optionY);
		}
		
		if(value > 1100 && value < 1500) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder500(value-1000, optionX, optionY);
		}
		
		if(value > 1500 && value < 2000) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			
			this.betUnder500(value-1500, optionX, optionY);
		}
		
		if(value > 2000 && value < 2500) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
						
			this.betUnder500(value-2000, optionX, optionY);
		}
		
		if(value > 2500 && value < 3000) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
						
			this.betUnder500(value-2500, optionX, optionY);
		}
		
		if(value > 3000 && value < 3500) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
									
			this.betUnder500(value-3000, optionX, optionY);
		}
		
		if(value > 3500 && value <4000) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
						
			this.betUnder500(value-3500, optionX, optionY);
		}
		
		if(value > 4000 && value < 4500) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
						
			this.betUnder500(value-4000, optionX, optionY);
		}
		
		if(value > 4500 && value < 5000) {
			this.robot.mouseMove(this.table.getChip1000x(), this.table.getChip1000y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip500x(), this.table.getChip500y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
						
			this.betUnder500(value-4500, optionX, optionY);
		}
		
		this.isPlacedBet = true;
	}
	
	private void betUnder500(int value, int optionX, int optionY) {
		int lim = value / 100;
		int rest = value - lim * 100;
		
		this.robot.mouseMove(this.table.getChip100x(), this.table.getChip100y());
		this.click();
		ThreadHelper.sleep(500);
		
		if(lim > 0) {
			this.robot.mouseMove(optionX, optionY);
			for(int i=0; i < lim; i++) {
				this.click();
				ThreadHelper.sleep(500);
			}
		}
		
		this.betUnder100(rest, optionX, optionY);
	}
	
	private void betUnder100(int value, int optionX, int optionY) {
		
		if(value <= 70) {
			if(value == 25) {
				this.robot.mouseMove(this.table.getChip25x(), this.table.getChip25y());
				this.click();
				ThreadHelper.sleep(500);
				
				this.robot.mouseMove(optionX, optionY);
				this.click();
				ThreadHelper.sleep(500);
				
			}else if(value == 50) {
				this.robot.mouseMove(this.table.getChip25x(), this.table.getChip25y());
				this.click();
				ThreadHelper.sleep(500);
				
				this.robot.mouseMove(optionX, optionY);
				this.click();
				ThreadHelper.sleep(500);
				this.click();
				ThreadHelper.sleep(500);
			} else {
				this.robot.mouseMove(this.table.getChip10x(), this.table.getChip10y());
				this.click();
				ThreadHelper.sleep(500);
				
				this.robot.mouseMove(optionX, optionY);
				
				int lim = value / 10;
				for(int i=0; i< lim; i++) {
					this.click();
					ThreadHelper.sleep(500);
				}
			}
		}
		
		if(value == 75) {
			
			this.robot.mouseMove(this.table.getChip25x(), this.table.getChip25y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 80) {

			this.robot.mouseMove(this.table.getChip25x(), this.table.getChip25y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip10x(), this.table.getChip10y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
		
		if(value == 90) {

			this.robot.mouseMove(this.table.getChip25x(), this.table.getChip25y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(this.table.getChip10x(), this.table.getChip10y());
			this.click();
			ThreadHelper.sleep(500);
			
			this.robot.mouseMove(optionX, optionY);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
			this.click();
			ThreadHelper.sleep(500);
		}
	}
	
	private void confirmBet() {
		
		if (this.isPlacedBet) {
			this.robot.mouseMove(this.table.getConfirmButtonX(), this.table.getConfirmButtonY());
			ThreadHelper.sleep(500);

			if (this.mode.equalsIgnoreCase("real")) {
				this.click();
				ThreadHelper.sleep(1000);
			} else {
				System.out.println("test mode -> no confirm.");
				this.log("test mode -> no confirm.");
			}

			this.robot.mouseMove(this.table.getMouseStandX(), this.table.getMouseStandY());
		}
	}

	private void log() {
		System.out.println("@counter=" + this.counter + " [");
		System.out.println("\tresult=" + this.resultString);
		System.out.println("\tprofit=" + this.profit);
		
		if(this.entryOption.equalsIgnoreCase(BS)) {
			System.out.println("\tbs-bet=[" + this.bsBetOption + "," + this.bsBetValue + "]");
		}
		if(this.entryOption.equalsIgnoreCase(EO)) {
			System.out.println("\teo-bet=[" + this.eoBetOption + "," + this.eoBetValue + "]");
		}
		if(this.entryOption.equalsIgnoreCase(DUAL)) {
			System.out.println("\tbs-bet=[" + this.bsBetOption + "," + this.bsBetValue + "]");
			System.out.println("\teo-bet=[" + this.eoBetOption + "," + this.eoBetValue + "]");
		}
		System.out.println("]");
	}
	
	private void log(String line) {
		IOHelper.writeLine(line, this.logFile.getPath() , true);
	}
	
	private boolean isFetchable() {

		String colorCode1 = MiscHelper.getColor(this.table.getColor1x(), this.table.getColor1y(), this.robot);
		String colorCode2 = MiscHelper.getColor(this.table.getColor2x(), this.table.getColor2y(), this.robot);

		if (colorCode1.equals(this.table.getColorCode1()) && colorCode2.equals(this.table.getColorCode2())) {
			return true;
		}

		return false;
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

	private void loadTableConfig() {

		this.table = new W88S2Turbo("W88-S2-Turbo");

		// dices
		this.table.setDice1x(Integer.parseInt(ConfigLoader.get("dice1x")));
		this.table.setDice1y(Integer.parseInt(ConfigLoader.get("dice1y")));

		this.table.setDice2x(Integer.parseInt(ConfigLoader.get("dice2x")));
		this.table.setDice2y(Integer.parseInt(ConfigLoader.get("dice2y")));

		this.table.setDice3x(Integer.parseInt(ConfigLoader.get("dice3x")));
		this.table.setDice3y(Integer.parseInt(ConfigLoader.get("dice3y")));

		this.table.setDiceWidth(Integer.parseInt(ConfigLoader.get("diceWidth")));
		this.table.setDiceHeight(Integer.parseInt(ConfigLoader.get("diceHeight")));

		// options
		this.table.setSmallX(Integer.parseInt(ConfigLoader.get("smallX")));
		this.table.setSmallY(Integer.parseInt(ConfigLoader.get("smallY")));

		this.table.setOddX(Integer.parseInt(ConfigLoader.get("oddX")));
		this.table.setOddY(Integer.parseInt(ConfigLoader.get("oddY")));

		this.table.setTripleX(Integer.parseInt(ConfigLoader.get("tripleX")));
		this.table.setTripleY(Integer.parseInt(ConfigLoader.get("tripleY")));

		this.table.setEvenX(Integer.parseInt(ConfigLoader.get("evenX")));
		this.table.setEvenY(Integer.parseInt(ConfigLoader.get("evenY")));

		this.table.setBigX(Integer.parseInt(ConfigLoader.get("bigX")));
		this.table.setBigY(Integer.parseInt(ConfigLoader.get("bigY")));

		// chips
		this.table.setChip10x(Integer.parseInt(ConfigLoader.get("chip10x")));
		this.table.setChip10y(Integer.parseInt(ConfigLoader.get("chip10y")));

		this.table.setChip25x(Integer.parseInt(ConfigLoader.get("chip25x")));
		this.table.setChip25y(Integer.parseInt(ConfigLoader.get("chip25y")));

		this.table.setChip100x(Integer.parseInt(ConfigLoader.get("chip100x")));
		this.table.setChip100y(Integer.parseInt(ConfigLoader.get("chip100y")));

		this.table.setChip500x(Integer.parseInt(ConfigLoader.get("chip500x")));
		this.table.setChip500y(Integer.parseInt(ConfigLoader.get("chip500y")));

		this.table.setChip1000x(Integer.parseInt(ConfigLoader.get("chip1000x")));
		this.table.setChip1000y(Integer.parseInt(ConfigLoader.get("chip1000y")));

		// colors
		this.table.setColor1x(Integer.parseInt(ConfigLoader.get("color1x")));
		this.table.setColor1y(Integer.parseInt(ConfigLoader.get("color1y")));

		this.table.setColor2x(Integer.parseInt(ConfigLoader.get("color2x")));
		this.table.setColor2y(Integer.parseInt(ConfigLoader.get("color2y")));

		this.table.setColorCode1(ConfigLoader.get("colorCode1"));
		this.table.setColorCode2(ConfigLoader.get("colorCode2"));

		// confirm button
		this.table.setConfirmButtonX(Integer.parseInt(ConfigLoader.get("confirmButtonX")));
		this.table.setConfirmButtonY(Integer.parseInt(ConfigLoader.get("confirmButtonY")));

		// mousestand
		this.table.setMouseStandX(Integer.parseInt(ConfigLoader.get("mouseStandX")));
		this.table.setMouseStandY(Integer.parseInt(ConfigLoader.get("mouseStandY")));

		this.table.setFullscreenX(Integer.parseInt(ConfigLoader.get("fullscreenX")));
		this.table.setFullscreenY(Integer.parseInt(ConfigLoader.get("fullscreenY")));

		this.table.setCloseChromeX(Integer.parseInt(ConfigLoader.get("closeChromeX")));
		this.table.setCloseChromeY(Integer.parseInt(ConfigLoader.get("closeChromeY")));
	}
	
	private String getTimeString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date).replaceAll(":", "-");
	}

	private void click() {
		try {
			this.desktop.open(this.clicker);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Agas [baseBet=" + baseBet + ", offset=" + offset + ", sl=" + sl + ", tp=" + tp + ", limit=" + limit
				+ ", triggerLevel=" + triggerLevel + ", bofp=" + bofp + ", boap=" + boap + ", sefp=" + sefp + ", seap="
				+ seap + ", mode=" + mode + ", gameTable=" + gameTable + ", server=" + server + "]";
	}

	
}
