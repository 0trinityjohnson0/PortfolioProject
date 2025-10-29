// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// MainMenu class -- Builds the visual layout for the game's main menu.
// Contains buttons to start a new game, load a game (future), or exit.
// Button actions delegate to the MainApp class to handle scene switching.

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {

    private VBox layout;

    public MainMenu(MainApp mainApp) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Title
        Text title = new Text("🐾 Welcome to Pet Haven 🐾");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Buttons
        Button newGameBtn = new Button("New Game");
        Button loadGameBtn = new Button("Load Game");
        Button exitBtn = new Button("Exit");

        // Button actions
        newGameBtn.setOnAction(e -> mainApp.showInitialAdoption());
        loadGameBtn.setOnAction(e -> System.out.println("Load Game coming soon!"));
        exitBtn.setOnAction(e -> mainApp.exitGame());

        layout.getChildren().addAll(title, newGameBtn, loadGameBtn, exitBtn);
    }

    public VBox getLayout() {
        return layout;
    }
}
