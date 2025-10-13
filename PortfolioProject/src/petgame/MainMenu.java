// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// MainMenu class -- Creates the main menu screen for the game.
// Provides buttons for starting a new game, loading a saved game, or exiting the application.
// The menu layout includes a title and vertically arranged buttons, and it delegates actions
// like starting a new game to the MainApp class.

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {

    private VBox layout;
    private MainApp mainApp;

    public MainMenu(MainApp mainApp) {
        this.mainApp = mainApp;
        createLayout();
    }

    private void createLayout() {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text title = new Text("🐾 Welcome to the Digital Pet Game 🐾");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button newGameBtn = new Button("New Game");
        Button loadGameBtn = new Button("Load Game");
        Button exitBtn = new Button("Exit");

        newGameBtn.setOnAction(e -> startNewGame());
        loadGameBtn.setOnAction(e -> System.out.println("Load Game Coming Soon!"));
        exitBtn.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(title, newGameBtn, loadGameBtn, exitBtn);
    }
    
    public void startNewGame() {
        mainApp.showAdoptionCenter(); // delegates to MainApp
    }

    public VBox getLayout() {
        return layout;
    }
}