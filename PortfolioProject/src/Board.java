
public class Board {
    private int rows;
    private int cols;
    private int numMines;
    private Tile[][] tiles;

    // constructor
    public Board(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.tiles = new Tile[rows][cols];

        // initialize the tiles 
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                this.tiles[r][c] = new Tile(r, c);
            }
        }
    }

    // getters
    public int getRows() { return this.rows; }
    public int getCols() { return this.cols; }
    public int getNumMines() { return this.numMines; }
    public Tile getTile(int row, int col) { return this.tiles[row][col]; }

    // methods
    public void generateBoard() { /* place mines + calculate numbers */ }
    public void placeMines() { /* to-do later */ }
    public void calculateAdjacentMines() { /* to-do later */ }
    public void revealTile(int row, int col) { /* to-do later */ }
}