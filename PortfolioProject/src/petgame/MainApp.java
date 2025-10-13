package petgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private Pet adoptedPet;

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
        MainMenu menu = new MainMenu(this);
        Scene scene = new Scene(menu.getLayout(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Main Menu");
        primaryStage.show();
    }

    // ---------------- Adoption Center ----------------
    public void showAdoptionCenter() {
        AdoptionCenterScene adoptionScene = new AdoptionCenterScene(this);
        Scene scene = new Scene(adoptionScene.getLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Adoption Center");
    }

    // ---------------- Start Game with Adopted Pet ----------------
    public void startGameWithPet(Pet pet) {
        this.adoptedPet = pet;
        HomeScene homeScene = new HomeScene(this, pet);
        Scene scene = new Scene(homeScene.getLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - " + pet.getName());
    }

    // ---------------- Game Control Helpers ----------------
    public void saveGame() {
        // TODO: Save pet data to file (future feature)
        System.out.println("Saving game... (feature coming soon)");
    }

    public void loadGame() {
        // TODO: Load pet data from file (future feature)
        System.out.println("Loading game... (feature coming soon)");
    }

    public void endGame() {
        this.adoptedPet = null;
        showMainMenu();
    }

    public Pet getAdoptedPet() {
        return adoptedPet;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}