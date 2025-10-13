// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// MainApp class -- This is the main start point for the game. 
// It manages switching between different screens like the main menu, 
// adoption center, and home scenes, and keeps track of the adopted pet.

package petgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private Pet adoptedPet; // holds adopted pet

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainMenu();
    }

    // ---------------- Main Menu ----------------
    public void showMainMenu() {
        VBox menuLayout = new VBox(20);
        menuLayout.setStyle("-fx-padding: 40; -fx-alignment: center;");

        Button newGameBtn = new Button("New Game");
        Button continueBtn = new Button("Continue (not implemented)");
        Button exitBtn = new Button("Exit");

        newGameBtn.setOnAction(e -> showAdoptionCenter());
        continueBtn.setOnAction(e -> {
            // For now, just print a message
            System.out.println("Continue is not implemented yet.");
        });
        exitBtn.setOnAction(e -> primaryStage.close());

        menuLayout.getChildren().addAll(newGameBtn, continueBtn, exitBtn);

        Scene scene = new Scene(menuLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Game - Main Menu");
        primaryStage.show();
    }

    // ---------------- Adoption Center ----------------
    public void showAdoptionCenter() {
        AdoptionCenterScene adoptionScene = new AdoptionCenterScene(this);
        Scene scene = new Scene(adoptionScene.getLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Game - Adoption Center");
    }

    // ---------------- Start Game with Adopted Pet ----------------
    public void startGameWithPet(Pet pet) {
        this.adoptedPet = pet;
        HomeScene homeScene = new HomeScene(this, pet);
        Scene scene = new Scene(homeScene.getLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Home - " + pet.getName());
    }

    // Getter for the adopted pet (optional, for later use)
    public Pet getAdoptedPet() {
        return adoptedPet;
    }
    
    
    
}