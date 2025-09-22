
public class Main {
    public static void main(String[] args) {
        // creates a new game with rows(9), columns(9), and number of mines(10)
        GameManager game = new GameManager(9, 9, 10);

        // start the game
        game.startGame();

        // testing
        System.out.println("Minesweeper game has started!");
    }
}