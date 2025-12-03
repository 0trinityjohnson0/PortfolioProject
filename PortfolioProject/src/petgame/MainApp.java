// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private Pet adoptedPet;

    // Track all pets the player has adopted
    public List<Pet> adoptedPets = new ArrayList<>();

    // Fixed window size
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 760;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);

        // --- SET APPLICATION ICON ---
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/petgame/assets/Logo/PetHavenIcon.png")
        ));

        showSplashScreen();
    }

    // ---------- Scene switching methods ----------
    public void showMainMenu() {
        MainMenu menu = new MainMenu(this);
        Scene scene = new Scene(menu.getLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Main Menu");
        primaryStage.show();
    }
    
    public void showSplashScreen() {
        SplashScreen splash = new SplashScreen(this);
        primaryStage.setScene(splash.getScene());
        primaryStage.setTitle("Pet Haven");
        primaryStage.show();
    }


    public void showAdoptionCenter() {
        // Later adoptions
        AdoptionCenterScene adoptionScene = new AdoptionCenterScene(this, false);
        Scene scene = new Scene(adoptionScene.getLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Adoption Center");
    }
    
    public void showInitialAdoption() {
        AdoptionCenterScene adoptionScene = new AdoptionCenterScene(this, true);
        Scene scene = new Scene(adoptionScene.getLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Choose Your First Pet");
    }

    public void startGameWithPet(Pet pet) {
        this.adoptedPet = pet;

        // Add to adopted pets if not already present
        if (!adoptedPets.contains(pet)) {
            adoptedPets.add(pet);
        }

        HomeScene homeScene = new HomeScene(this, pet);
        Scene scene = new Scene(homeScene.getLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - " + pet.getName());
    }

    
    // --- SCENE ---
    public void showMapScene(Pet pet) {
        MapScene mapScene = new MapScene(this, pet);
        Scene scene = new Scene(mapScene.getLayout(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - Map");
    }

    public void showHomeScene(Pet pet) {
        HomeScene sceneObj = new HomeScene(this, pet);
        primaryStage.setScene(sceneObj.getScene());
        primaryStage.setTitle("Pet Haven - Home");
    }

    public void showBeachScene(Pet pet) {
        BeachScene sceneObj = new BeachScene(this, pet);
        primaryStage.setScene(sceneObj.getScene());
        primaryStage.setTitle("Pet Haven - Beach");
    }

    public void showParkScene(Pet pet) {
        ParkScene sceneObj = new ParkScene(this, pet);
        primaryStage.setScene(sceneObj.getScene());
        primaryStage.setTitle("Pet Haven - Park");
    }

    public void showMountainScene(Pet pet) { 
        MountainScene sceneObj = new MountainScene(this, pet);
        primaryStage.setScene(sceneObj.getScene());
        primaryStage.setTitle("Pet Haven - Mountains");
    }

    
    public void showBreedingScene(Pet parent1, Pet parent2) {
        BreedingScene breedScene = new BreedingScene(this, parent1, parent2);
        Scene scene = breedScene.getScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Haven - New Baby!");
    }
    

    public void exitGame() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
    
    public void saveGame() {
        SaveManager.saveGame(adoptedPets);
    }

    public void loadGame() {
        List<Pet> loaded = SaveManager.loadGame();
        if (loaded != null) {
            adoptedPets = loaded;
            System.out.println("Game loaded successfully!");
            showHomeScene(adoptedPets.get(0));  // go home with first adopted pet
        } else {
            System.out.println("No save found!");
        }
    }

    
    public void resetGame() {
        adoptedPets.clear();
        SaveManager.deleteSave();
    }

    

    // --- ADOPTION LOGIC ---
    public boolean canAdoptAnotherPet() {
        // Can't adopt if no pets yet
        if (adoptedPets.isEmpty()) return false;

        // Must have 5 hearts with every pet
        for (Pet pet : adoptedPets) {
            if (pet.getRelationshipHearts() < 5) {
                return false;
            }
        }
        return true;
    }

    public Pet getAdoptedPet() {
        return adoptedPet;
    }
    
    public List<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
}