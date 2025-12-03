// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {

    private VBox layout;

    public MainMenu(MainApp mainApp) {
        layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40, 20, 40, 20));
        layout.setStyle("-fx-background-color: #fff8f0;");

        // ---------- LOGO ----------
        Image logo = new Image(
                getClass().getResourceAsStream("/petgame/assets/Logo/PetGameLogo.png"),
                260,      // width (adjust as needed)
                0,        // auto height
                true,     // preserve ratio
                false     // no smoothing → crisp pixel art
        );
        ImageView logoView = new ImageView(logo);

        // ---------- TITLE ----------
        
        // ---------- BUTTONS ----------
        Button newGameBtn = styledButton("New Game");
        newGameBtn.setOnAction(e -> {
            mainApp.resetGame();        // clears everything
            mainApp.showInitialAdoption();
        });
        
        Button loadGameBtn = styledButton("Load Game");
        loadGameBtn.setOnAction(e -> mainApp.loadGame());
        
        Button exitBtn = styledButton("Exit");
        exitBtn.setOnAction(e -> mainApp.exitGame());

        // ---------- ADD ELEMENTS ----------
        layout.getChildren().addAll(
                logoView,
                newGameBtn,
                loadGameBtn,
                exitBtn
        );
    }

    public VBox getLayout() {
        return layout;
    }

    // ---------- Unified Button Style (same as BasePetScene) ----------
    private Button styledButton(String text) {
        Button b = new Button(text);

        b.setStyle("""
            -fx-font-size: 18px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-border-radius: 12;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """);

        b.setOnMouseEntered(e -> b.setStyle("""
            -fx-font-size: 18px;
            -fx-background-color: #ffe2a8;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-border-radius: 12;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        b.setOnMouseExited(e -> b.setStyle("""
            -fx-font-size: 18px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-border-radius: 12;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        return b;
    }
}
