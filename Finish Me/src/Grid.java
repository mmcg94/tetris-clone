public class Grid {

	private int maxX;
	private int maxY;
	private int cellSize;
	private int screenXOffset;
	private int screenYOffset;
	private int[][] coordinateGrid;
	private final int numTokenCells = 4;
	public int currentTokenX = 0;
	public int currentTokenY = 0;
	private int[] currentToken = { 0, 0 };
	private String currentColor;
	private int currentStationaryNumber;

	public Grid(int maxX, int maxY, int screenXOffset, int screenYOffset, int cellSize) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.cellSize = cellSize;
		this.screenXOffset = screenXOffset;
		this.screenYOffset = screenYOffset;
		coordinateGrid = new int[maxX][maxY];
	}	
	
	public boolean checkValidTokenPostion(int xOffset, int yOffset) {
		for (int i = 0; i < numTokenCells; i++) { 
			int x = Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX + xOffset; 
			int y = Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY + yOffset; 
			if (x > maxX - 1 || x< 0 || 
					y  > maxY - 1 || y < 0 ||
					coordinateGrid[x][y] >= 2) 
			{
				return false;			
			}
		}
		return true;
	}
	
	public void drawRandomToken() {
		currentToken[0] = (int) (Math.random() * 7);
		currentToken[1] = (int) (Math.random() * 4);
		setCurrentColor();
		System.out.print(currentStationaryNumber);
		setCurrentStationaryNumber();
		resetCurrentTokenCoords();
		for (int i = 0; i < numTokenCells; i++) {
			occupyTokenCell(
					Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX,
					Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY);
		}
	}
	
	public void reDrawTokens() {;
		for (int i = 0; i < numTokenCells; i++) {
			occupyTokenCell(
					Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX,
					Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY);
		}
	}

	private void occupyTokenCell(int x, int y){
		if (x > maxX || x < 0 || y < 0 || y > maxY) {
			System.out.print("Failed to occupy token cell.");
			return;
		}
		coordinateGrid[x][y] = 1;
	}

	public void saveToken() {
		for (int i = 0; i < numTokenCells; i++) {
			occupyNonTokenCell(
					Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX,
					Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY);
		}
	}

	private void occupyNonTokenCell(int x, int y){
		if (x > maxX || x < 0 || y < 0 || y > maxY) {
			System.out.print("Failed to occupy non token cell.");
			return;
		}
		coordinateGrid[x][y] = currentStationaryNumber;
	}

	private void setCurrentStationaryNumber() {
		this.currentStationaryNumber = Token.tokenStationaryNumber[currentToken[0]];	
	}

	private void setCurrentColor() {
		this.currentColor = Token.tokenColor[currentToken[0]];
	}

	public int getMaxX() { return maxX; }
	public int getMaxY() { return maxY; }
	public int getCellSize() { return cellSize; }
	public int getXOffset() { return screenXOffset; }
	public int getYOffset() { return screenYOffset; }
	public int[][] getGrid() { return coordinateGrid; }

	public void changeCurrentTokenCoords(int x, int y) {
		currentTokenX = currentTokenX + x;
		currentTokenY = currentTokenY + y;
	}
	
	private void resetCurrentTokenCoords() {
		currentTokenX = getMaxX() / 2 - 1;
		currentTokenY = 0;
	}
	
	public String getColor(int i) {
		if (i == 1) {
			return getCurrentColor();
		} else if (i >= 2) {
			return Token.tokenColor[i-2];
		} else {			
			return null;
		}
	}
	
	public String getCurrentColor() {
		return currentColor;
	}



}
