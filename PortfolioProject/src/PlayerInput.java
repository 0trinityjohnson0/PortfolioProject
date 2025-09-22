
public class PlayerInput {
    private String lastAction; // "reveal" or "flag"
    private int lastRow;
    private int lastCol;

    // constructor
    public PlayerInput() {
        this.lastAction = "";
        this.lastRow = -1;
        this.lastCol = -1;
    }

    // getters
    public String getLastAction() { return this.lastAction; }
    public int getLastRow() { return this.lastRow; }
    public int getLastCol() { return this.lastCol; }

    // setters
    public void setLastAction(String action) { this.lastAction = action; }
    public void setLastRow(int row) { this.lastRow = row; }
    public void setLastCol(int col) { this.lastCol = col; }

    // methods
    public void revealTile(int row, int col) { /* to-do later */ }
    public void flagTile(int row, int col) { /* to-do later */ }
}
