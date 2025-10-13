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