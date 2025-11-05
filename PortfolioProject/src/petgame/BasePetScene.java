// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

/**
 * BasePetScene
 * Shared parent class for all pet interaction environments (Home, Beach, Park, etc.).
 * Handles:
 *  - Background and pet rendering
 *  - Shared UI structure (stats panel, hearts, navigation)
 *  - Stat updates and decay timeline
 *  - Calls abstract methods for scene-specific customization
 */
public abstract class BasePetScene {

    protected MainApp mainApp;
    protected Pet activePet; // primarily used for relationship hearts
    protected BorderPane layout;

    // UI Elements
    protected ProgressBar happinessBar;
    protected ProgressBar hungerBar;
    protected ProgressBar energyBar;
    protected HBox heartsBox;

    // Heart images
    protected Image fullHeart;
    protected Image emptyHeart;

    // Stat decay timeline
    protected Timeline environmentTimeline;

    // ---------- Constructor ----------
    public BasePetScene(MainApp mainApp, Pet activePet) {
        this.mainApp = mainApp;
        this.activePet = activePet;

        loadHeartImages();
        createLayout();
        startEnvironmentTimeline();
    }

    // ---------- Abstract Methods ----------
    /** Path to this scene's background image. */
    protected abstract String getBackgroundPath();

    // Called every few seconds for each pet (handles hunger, energy)
    protected abstract void onEnvironmentTick(Pet pet);

    /** Builds the care button panel — subclasses decide which actions appear. */
    protected abstract HBox buildCareButtons();

    // ---------- Shared Layout Setup ----------
    private void createLayout() {
        layout = new BorderPane();
        layout.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
        layout.setPadding(new Insets(20));

        layout.setTop(buildTopBar());
        layout.setCenter(buildCenterPane());
        layout.setLeft(buildStatsPanel());
        layout.setBottom(buildCareButtons());
    }

    // ---------- Top Bar ----------
    private BorderPane buildTopBar() {
        BorderPane topPane = new BorderPane();

        Label titleLabel = new Label(getSceneTitle());
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        topPane.setCenter(titleLabel);

        Button backBtn = new Button("Go Somewhere");
        backBtn.setStyle("-fx-font-size: 16px;");
        backBtn.setOnAction(e -> {
            stopEnvironmentTimeline();
            mainApp.showMapScene(activePet);
        });

        BorderPane.setMargin(backBtn, new Insets(0, 20, 0, 0));
        topPane.setRight(backBtn);
        BorderPane.setAlignment(backBtn, Pos.CENTER_RIGHT);

        return topPane;
    }

    // ---------- Title Helper ----------
    protected String getSceneTitle() {
        // Default title — can override in subclasses if needed
        return "Your Pets";
    }

    // ---------- Center (Background + Pet Sprites) ----------
    private StackPane buildCenterPane() {
        StackPane centerPane = new StackPane();

        // Load background
        Image background = AssetCache.getImage(getBackgroundPath());
        if (background == null) {
            background = AssetCache.getImage("/petgame/assets/placeholder.png");
        }

        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);
        centerPane.getChildren().add(backgroundView);

        // Load pets
        List<Pet> pets = mainApp.getAdoptedPets();
        if (pets.isEmpty()) return centerPane;

        double spacing = 800.0 / (pets.size() + 1);
        for (int i = 0; i < pets.size(); i++) {
            Pet p = pets.get(i);
            PetSprite sprite = new PetSprite(p);
            ImageView view = sprite.getView();

            StackPane.setAlignment(view, Pos.BOTTOM_CENTER);
            view.setTranslateX((i - (pets.size() / 2.0)) * spacing);
            view.setTranslateY(-20);

            centerPane.getChildren().add(view);
        }

        return centerPane;
    }

    // ---------- Left (Stats + Hearts) ----------
    private VBox buildStatsPanel() {
        VBox statsBox = new VBox(12);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        heartsBox = new HBox(5);
        heartsBox.setAlignment(Pos.CENTER_LEFT);
        updateHearts();

        Label hungerLabel = new Label("Hunger:");
        hungerBar = new ProgressBar(activePet.getHunger() / 100.0);
        hungerBar.setPrefWidth(150);

        Label happinessLabel = new Label("Happiness:");
        happinessBar = new ProgressBar(activePet.getHappiness() / 100.0);
        happinessBar.setPrefWidth(150);

        Label energyLabel = new Label("Energy:");
        energyBar = new ProgressBar(activePet.getEnergy() / 100.0);
        energyBar.setPrefWidth(150);

        statsBox.getChildren().addAll(
                heartsBox,
                hungerLabel, hungerBar,
                happinessLabel, happinessBar,
                energyLabel, energyBar
        );

        return statsBox;
    }

    // ---------- Heart Image Loading ----------
    private void loadHeartImages() {
        fullHeart = AssetCache.getImage("/petgame/assets/displayIcons/heart_full.png");
        emptyHeart = AssetCache.getImage("/petgame/assets/displayIcons/heart_empty.png");
    }

    // ---------- Timeline Logic ----------
    private void startEnvironmentTimeline() {
        environmentTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            for (Pet p : mainApp.getAdoptedPets()) {
                onEnvironmentTick(p);
            }
            checkForBreeding();
            updateStatsAndHearts();
        }));
        environmentTimeline.setCycleCount(Timeline.INDEFINITE);
        environmentTimeline.play();
    }

    protected void stopEnvironmentTimeline() {
        if (environmentTimeline != null) environmentTimeline.stop();
    }
    
    private void checkForBreeding() {
        var pets = mainApp.getAdoptedPets();
        if (pets.size() < 2) return;

        for (int i = 0; i < pets.size(); i++) {
            for (int j = i + 1; j < pets.size(); j++) {
                Pet p1 = pets.get(i);
                Pet p2 = pets.get(j);

                boolean sameSpecies = p1.getSpecies().equals(p2.getSpecies());
                boolean fullHearts = p1.getRelationshipHearts() >= 5 && p2.getRelationshipHearts() >= 5;

                // only same species required now
                if (sameSpecies && fullHearts && mainApp.canAdoptAnotherPet()) {

                    // reset hearts after breeding
                    p1.decreaseHappiness(p1.getHappiness()); // sets happiness to 0
                    p2.decreaseHappiness(p2.getHappiness());
                    p1.decreaseEnergy(p1.getEnergy()); // reset energy lol
                    p2.decreaseEnergy(p2.getEnergy());
                    
                    // reset relationship completely
                    resetRelationship(p1);
                    resetRelationship(p2);

                    mainApp.showBreedingScene(p1, p2);
                    return; // stop after one successful pair
                }
            }
        }
    }

    private void resetRelationship(Pet pet) {
        
        try {
            var relationshipField = Pet.class.getDeclaredField("relationship");
            relationshipField.setAccessible(true);
            relationshipField.set(pet, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- UI Update Utilities ----------
    protected void updateStatsAndHearts() {
        updateStats();
        updateHearts();
    }

    private void updateStats() {
        List<Pet> pets = mainApp.getAdoptedPets();
        if (pets.isEmpty()) return;

        double avgHunger = pets.stream().mapToInt(Pet::getHunger).average().orElse(0);
        double avgHappiness = pets.stream().mapToInt(Pet::getHappiness).average().orElse(0);
        double avgEnergy = pets.stream().mapToInt(Pet::getEnergy).average().orElse(0);

        hungerBar.setProgress(avgHunger / 100.0);
        happinessBar.setProgress(avgHappiness / 100.0);
        energyBar.setProgress(avgEnergy / 100.0);
    }

    private void updateHearts() {
        heartsBox.getChildren().clear();
        int hearts = activePet.getRelationshipHearts();
        for (int i = 0; i < 5; i++) {
            ImageView heartView = new ImageView(i < hearts ? fullHeart : emptyHeart);
            heartView.setFitWidth(30);
            heartView.setFitHeight(30);
            heartsBox.getChildren().add(heartView);
        }
    }

    // ---------- Layout Getter ----------
    public BorderPane getLayout() {
        return layout;
    }

    // ---------- Scene Getter (optional convenience) ----------
    public Scene getScene() {
        return new Scene(layout, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
    }
}