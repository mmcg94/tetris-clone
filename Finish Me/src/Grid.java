
public class Grid {

	private int maxX;
	private int maxY;
	private int cellSize;
	private int screenXOffset;
	private int screenYOffset;
	private int[][] coordinateGrid;

	public Grid(int maxX, int maxY, int screenXOffset, int screenYOffset, int cellSize) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.cellSize = cellSize;
		this.screenXOffset = screenXOffset;
		this.screenYOffset = screenYOffset;
		coordinateGrid = new int[maxX][maxY];
	}

	public void occupyTokenCell(int x, int y){
		coordinateGrid[x][y] = 1;
	}
	
	public void occupyNonTokenCell(int x, int y){
		coordinateGrid[x][y] = 2;
	}
	
	public int getMaxX() { return maxX; }
	public int getMaxY() { return maxY; }
	public int getCellSize() { return cellSize; }
	public int getXOffset() { return screenXOffset; }
	public int getYOffset() { return screenYOffset; }
	public int[][] getGrid() { return coordinateGrid; }

}
