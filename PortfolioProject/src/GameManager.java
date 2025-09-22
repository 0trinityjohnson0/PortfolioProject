
public class GameManager {
    private Board board;
    private Timer timer;
    private boolean isGameOver;

    // constructor
    public GameManager(int rows, int cols, int numMines) {
        this.board = new Board(rows, cols, numMines);
        this.timer = new Timer();
        this.isGameOver = false;
    }

    // getters
    public boolean getIsGameOver() { return this.isGameOver; }
    public Board getBoard() { return this.board; }

    // methods
    public void startGame() { /* to-do later */ }
    public void handleInput(String action, int row, int col) { /* to-do later */ }
    public void checkWinCondition() { /* to-do later */ }
    public void endGame(boolean won) { /* to-do later */ }
}
