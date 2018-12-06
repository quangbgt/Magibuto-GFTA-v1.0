package io.traderx.darkness.base;

public abstract class Table {
	
	protected String name;
	
	protected int smallX, smallY, oddX, oddY, bigX, bigY, evenX, evenY, tripleX, tripleY;
	
	protected int chip10x, chip10y, chip25x, chip25y, chip100x, chip100y, chip500x, chip500y, chip1000x, chip1000y;
	
	protected int confirmButtonX, confirmButtonY;
	
	protected int color1x, color1y, color2x, color2y;
	
	protected String colorCode1, colorCode2;
	
	protected int dice1x, dice1y, dice2x, dice2y, dice3x, dice3y, diceWidth, diceHeight;
	
	protected int mouseStandX, mouseStandY, fullscreenX, fullscreenY, closeChromeX, closeChromeY;
	
	public Table() {
		super();
	}
	
	public Table(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSmallX() {
		return smallX;
	}

	public void setSmallX(int smallX) {
		this.smallX = smallX;
	}

	public int getSmallY() {
		return smallY;
	}

	public void setSmallY(int smallY) {
		this.smallY = smallY;
	}

	public int getOddX() {
		return oddX;
	}

	public void setOddX(int oddX) {
		this.oddX = oddX;
	}

	public int getOddY() {
		return oddY;
	}

	public void setOddY(int oddY) {
		this.oddY = oddY;
	}

	public int getBigX() {
		return bigX;
	}

	public void setBigX(int bigX) {
		this.bigX = bigX;
	}

	public int getBigY() {
		return bigY;
	}

	public void setBigY(int bigY) {
		this.bigY = bigY;
	}

	public int getEvenX() {
		return evenX;
	}

	public void setEvenX(int evenX) {
		this.evenX = evenX;
	}

	public int getEvenY() {
		return evenY;
	}

	public void setEvenY(int evenY) {
		this.evenY = evenY;
	}

	public int getTripleX() {
		return tripleX;
	}

	public void setTripleX(int tripleX) {
		this.tripleX = tripleX;
	}

	public int getTripleY() {
		return tripleY;
	}

	public void setTripleY(int tripleY) {
		this.tripleY = tripleY;
	}

	public int getChip10x() {
		return chip10x;
	}

	public void setChip10x(int chip10x) {
		this.chip10x = chip10x;
	}

	public int getChip10y() {
		return chip10y;
	}

	public void setChip10y(int chip10y) {
		this.chip10y = chip10y;
	}

	public int getChip25x() {
		return chip25x;
	}

	public void setChip25x(int chip25x) {
		this.chip25x = chip25x;
	}

	public int getChip25y() {
		return chip25y;
	}

	public void setChip25y(int chip25y) {
		this.chip25y = chip25y;
	}

	public int getChip100x() {
		return chip100x;
	}

	public void setChip100x(int chip100x) {
		this.chip100x = chip100x;
	}

	public int getChip100y() {
		return chip100y;
	}

	public void setChip100y(int chip100y) {
		this.chip100y = chip100y;
	}

	public int getChip500x() {
		return chip500x;
	}

	public void setChip500x(int chip500x) {
		this.chip500x = chip500x;
	}

	public int getChip500y() {
		return chip500y;
	}

	public void setChip500y(int chip500y) {
		this.chip500y = chip500y;
	}

	public int getChip1000x() {
		return chip1000x;
	}

	public void setChip1000x(int chip1000x) {
		this.chip1000x = chip1000x;
	}

	public int getChip1000y() {
		return chip1000y;
	}

	public void setChip1000y(int chip1000y) {
		this.chip1000y = chip1000y;
	}

	public int getConfirmButtonX() {
		return confirmButtonX;
	}

	public void setConfirmButtonX(int confirmButtonX) {
		this.confirmButtonX = confirmButtonX;
	}

	public int getConfirmButtonY() {
		return confirmButtonY;
	}

	public void setConfirmButtonY(int confirmButtonY) {
		this.confirmButtonY = confirmButtonY;
	}

	public int getColor1x() {
		return color1x;
	}

	public void setColor1x(int color1x) {
		this.color1x = color1x;
	}

	public int getColor1y() {
		return color1y;
	}

	public void setColor1y(int color1y) {
		this.color1y = color1y;
	}

	public int getColor2x() {
		return color2x;
	}

	public void setColor2x(int color2x) {
		this.color2x = color2x;
	}

	public int getColor2y() {
		return color2y;
	}

	public void setColor2y(int color2y) {
		this.color2y = color2y;
	}

	public String getColorCode1() {
		return colorCode1;
	}

	public void setColorCode1(String colorCode1) {
		this.colorCode1 = colorCode1;
	}

	public String getColorCode2() {
		return colorCode2;
	}

	public void setColorCode2(String colorCode2) {
		this.colorCode2 = colorCode2;
	}

	public int getDice1x() {
		return dice1x;
	}

	public void setDice1x(int dice1x) {
		this.dice1x = dice1x;
	}

	public int getDice1y() {
		return dice1y;
	}

	public void setDice1y(int dice1y) {
		this.dice1y = dice1y;
	}

	public int getDice2x() {
		return dice2x;
	}

	public void setDice2x(int dice2x) {
		this.dice2x = dice2x;
	}

	public int getDice2y() {
		return dice2y;
	}

	public void setDice2y(int dice2y) {
		this.dice2y = dice2y;
	}

	public int getDice3x() {
		return dice3x;
	}

	public void setDice3x(int dice3x) {
		this.dice3x = dice3x;
	}

	public int getDice3y() {
		return dice3y;
	}

	public void setDice3y(int dice3y) {
		this.dice3y = dice3y;
	}

	public int getDiceWidth() {
		return diceWidth;
	}

	public void setDiceWidth(int diceWidth) {
		this.diceWidth = diceWidth;
	}

	public int getDiceHeight() {
		return diceHeight;
	}

	public void setDiceHeight(int diceHeight) {
		this.diceHeight = diceHeight;
	}

	public int getMouseStandX() {
		return mouseStandX;
	}

	public void setMouseStandX(int mouseStandX) {
		this.mouseStandX = mouseStandX;
	}

	public int getMouseStandY() {
		return mouseStandY;
	}

	public void setMouseStandY(int mouseStandY) {
		this.mouseStandY = mouseStandY;
	}
	
	public int getFullscreenX() {
		return fullscreenX;
	}

	public void setFullscreenX(int fullscreenX) {
		this.fullscreenX = fullscreenX;
	}

	public int getFullscreenY() {
		return fullscreenY;
	}

	public void setFullscreenY(int fullscreenY) {
		this.fullscreenY = fullscreenY;
	}

	public int getCloseChromeX() {
		return closeChromeX;
	}

	public void setCloseChromeX(int closeChromeX) {
		this.closeChromeX = closeChromeX;
	}

	public int getCloseChromeY() {
		return closeChromeY;
	}

	public void setCloseChromeY(int closeChromeY) {
		this.closeChromeY = closeChromeY;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ",\nsmallX=" + smallX + ",\nsmallY=" + smallY + ",\noddX=" + oddX + ",\noddY=" + oddY
				+ ",\nbigX=" + bigX + ",\nbigY=" + bigY + ",\nevenX=" + evenX + ",\nevenY=" + evenY + ",\ntripleX=" + tripleX
				+ ",\ntripleY=" + tripleY + ",\nchip10x=" + chip10x + ",\nchip10y=" + chip10y + ",\nchip25x=" + chip25x
				+ ",\nchip25y=" + chip25y + ",\nchip100x=" + chip100x + ",\nchip100y=" + chip100y + ",\nchip500x="
				+ chip500x + ",\nchip500y=" + chip500y + ",\nchip1000x=" + chip1000x + ",\nchip1000y=" + chip1000y
				+ ",\nconfirmButtonX=" + confirmButtonX + ",\nconfirmButtonY=" + confirmButtonY + ",\ncolor1x=" + color1x
				+ ",\ncolor1y=" + color1y + ",\ncolor2x=" + color2x + ",\ncolor2y=" + color2y + ",\ncolorCode1="
				+ colorCode1 + ",\ncolorCode2=" + colorCode2 + ",\ndice1x=" + dice1x + ",\ndice1y=" + dice1y + ",\ndice2x="
				+ dice2x + ",\ndice2y=" + dice2y + ",\ndice3x=" + dice3x + ",\ndice3y=" + dice3y + ",\ndiceWidth="
				+ diceWidth + ",\ndiceHeight=" + diceHeight + ",\nmouseStandX=" + mouseStandX + ",\nmouseStandY="
				+ mouseStandY + "]";
	}
}
