
public class Tile {
	private int row;
	private int col;
	private boolean isMine;
	private boolean isRevealed;
	private boolean isFlagged;
	private int adjacentMines;

	// constructor
	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		this.isMine = false;
		this.isFlagged = false;
		this.adjacentMines = 0;
	}

	// getters
	public int getRow() { return this.row; }
	public int getCol() { return this.col; }
	public boolean isMine() { return this.isMine; }
	public boolean isRevealed() { return this.isRevealed; }
	public boolean isFlagged() { return this.isFlagged; }
	public int getAdjacentMines() { return this.adjacentMines; }
 
	// setters
	public void setMine(boolean isMine) { this.isMine = isMine; }
	public void setRevealed(boolean revealed) { this.isRevealed = revealed; }
	public void setFlagged(boolean flagged) { this.isFlagged = flagged; }
	public void setAdjacentMines(int count) { this.adjacentMines = count; }

	// methods
	public void reveal() { /* do later */ }
	public void toggleFlag() { /* do later */ }
}
