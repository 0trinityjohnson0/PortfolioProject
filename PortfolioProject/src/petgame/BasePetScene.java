// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project
//
// BasePetScene -- Shared parent class for all pet interaction environments (Home, Beach, Park)
// Handles:
//  - Background and pet rendering
//  - Shared UI structure (top bar, pet cards, care buttons)
//  - Stat updates and decay timeline
//  - Automatic breeding when two pets reach 5 hearts
//  - Walk-in animation on scene load

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

import java.util.*;

public abstract class BasePetScene {

    protected MainApp mainApp;
    protected Pet activePet;
    protected BorderPane layout;

    // Heart images
    protected Image fullHeart;
    protected Image emptyHeart;

    // Stat decay timeline
    protected Timeline environmentTimeline;

    // (optional) store sprites if needed later
    protected final List<PetSprite> sceneSprites = new ArrayList<>();

    // UI references per pet
    private final Map<Pet, ProgressBar> hungerBars = new HashMap<>();
    private final Map<Pet, ProgressBar> happinessBars = new HashMap<>();
    private final Map<Pet, ProgressBar> energyBars = new HashMap<>();
    private final Map<Pet, HBox> heartRows = new HashMap<>();

    // ---------- Constructor ----------
    public BasePetScene(MainApp mainApp, Pet activePet) {
        this.mainApp = mainApp;
        this.activePet = activePet;

        loadHeartImages();
        createLayout();
        startEnvironmentTimeline();
    }

    // ---------- Abstract methods ----------
    protected abstract String getBackgroundPath();
    protected abstract void onEnvironmentTick(Pet pet);
    protected abstract Pane buildCareButtons();

    // ---------- Layout Setup ----------
    private void createLayout() {
        layout = new BorderPane();
        layout.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);

        layout.setTop(buildTopBar());
        layout.setCenter(buildCenterPane());

        VBox leftPanel = new VBox(20);
        leftPanel.setStyle("""
            -fx-border-color: #d4b483;
            -fx-border-width: 0 2px 0 0;
        """);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(10));
        leftPanel.getChildren().add(buildCareButtons());
        layout.setLeft(leftPanel);

        HBox cards = buildPetCards();
        cards.setStyle("""
            -fx-border-color: #d4b483;
            -fx-border-width: 2 0 0 0;
        """);
        layout.setBottom(cards);
    }

    // ---------- Top bar ----------
    private BorderPane buildTopBar() {
        BorderPane topPane = new BorderPane();
        topPane.setPadding(new Insets(8, 10, 8, 10));
        topPane.setStyle("""
            -fx-border-color: #d4b483;
            -fx-border-width: 0 0 2 0;
        """);

        // Center title
        Label titleLabel = new Label(getSceneTitle());
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        StackPane titleStack = new StackPane(titleLabel);
        topPane.setCenter(titleStack);

        // Back button
        Button backBtn = styledButton("Map");
        backBtn.setOnAction(e -> {
            stopEnvironmentTimeline();
            mainApp.showMapScene(activePet);
        });

        BorderPane.setMargin(backBtn, new Insets(8, 20, 8, 0));
        topPane.setRight(backBtn);

        return topPane;
    }

    protected String getSceneTitle() {
        return "Your Pets";
    }

    // ---------- Center area (Background + Pets with walk-in) ----------
    private StackPane buildCenterPane() {
        StackPane centerPane = new StackPane();

        Image background = AssetCache.getImage(getBackgroundPath());
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);
        centerPane.getChildren().add(backgroundView);

        List<Pet> pets = mainApp.getAdoptedPets();
        if (pets.isEmpty()) return centerPane;

        // Fixed spacing looks good for your window; tweak if needed
        double spacing = 220.0;
        double centerIndex = (pets.size() - 1) / 2.0; // so 1 pet -> index 0, offset 0

        for (int i = 0; i < pets.size(); i++) {
            Pet p = pets.get(i);

            PetSprite sprite = new PetSprite(p);
            sprite.stopAI(); // disable random behavior during intro
            sceneSprites.add(sprite);

            ImageView view = sprite.getView();
            StackPane.setAlignment(view, Pos.BOTTOM_CENTER);

            // final on-screen position (centered nicely)
            double finalX = (i - centerIndex) * spacing;
            double finalY = -20;
            view.setTranslateY(finalY);

            // randomly choose side to walk in from
            boolean fromLeft = Math.random() < 0.5;
            double startX = fromLeft ? -500 : 500;
            view.setTranslateX(startX);

            // walking animation facing the right way
            boolean faceLeft = !fromLeft; // if entering from right, face left
            sprite.playWalkCycle(faceLeft);

            // movement timeline: slide from startX to finalX in small steps
            double distance = Math.abs(finalX - startX);
            double step = 6;                  // pixels per tick
            int steps = (int) Math.ceil(distance / step);

            Timeline move = new Timeline(new KeyFrame(Duration.millis(70), e -> {
                double currentX = view.getTranslateX();
                double nextX = currentX + (fromLeft ? step : -step);
                view.setTranslateX(nextX);
            }));

            move.setCycleCount(steps);
            move.setOnFinished(e -> {
                // snap exactly to final position
                view.setTranslateX(finalX);
                sprite.setBaseX(finalX);

                // switch to idle + normal AI
                sprite.playIdle();
                sprite.startAI();
            });

            move.play();

            centerPane.getChildren().add(view);
        }

        return centerPane;
    }

    // ---------- Pet cards ----------
    private HBox buildPetCards() {
        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 20, 0));

        hungerBars.clear();
        happinessBars.clear();
        energyBars.clear();
        heartRows.clear();

        for (Pet pet : mainApp.getAdoptedPets()) {
            VBox card = new VBox(8);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(10));
            card.setPrefWidth(180);
            card.setStyle("""
                -fx-background-color: #fff8f0;
                -fx-border-color: #d4b483;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
            """);

            Label nameLabel = new Label(pet.getName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            String spritePath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/Idle.png";
            Image spriteImg = AssetCache.getImage(spritePath);
            ImageView spriteView = new ImageView(spriteImg);
            spriteView.setFitWidth(70);
            spriteView.setPreserveRatio(true);

            // Hearts row
            HBox heartsRow = new HBox(3);
            heartsRow.setAlignment(Pos.CENTER);
            heartRows.put(pet, heartsRow);
            updateHeartsRow(pet, heartsRow);

            // Stats bars
            ProgressBar hungerBar = new ProgressBar(pet.getHunger() / 100.0);
            ProgressBar happinessBar = new ProgressBar(pet.getHappiness() / 100.0);
            ProgressBar energyBar = new ProgressBar(pet.getEnergy() / 100.0);

            hungerBar.setPrefWidth(120);
            happinessBar.setPrefWidth(120);
            energyBar.setPrefWidth(120);

            hungerBar.setStyle("-fx-accent: #ffb3b3;");
            happinessBar.setStyle("-fx-accent: #ffc8a2;");
            energyBar.setStyle("-fx-accent: #a3d8f4;");

            hungerBars.put(pet, hungerBar);
            happinessBars.put(pet, happinessBar);
            energyBars.put(pet, energyBar);

            VBox stats = new VBox(3,
                new HBox(5, new Label("🍖"), hungerBar),
                new HBox(5, new Label("😊"), happinessBar),
                new HBox(5, new Label("💤"), energyBar)
            );
            stats.setAlignment(Pos.CENTER);

            card.getChildren().addAll(nameLabel, spriteView, heartsRow, stats);
            box.getChildren().add(card);
        }

        return box;
    }

    // ---------- Timeline logic ----------
    private void startEnvironmentTimeline() {
        environmentTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            for (Pet p : mainApp.getAdoptedPets()) {
                onEnvironmentTick(p);
            }
            checkForBreeding();
            updatePetCards();
        }));
        environmentTimeline.setCycleCount(Timeline.INDEFINITE);
        environmentTimeline.play();
    }

    protected void stopEnvironmentTimeline() {
        if (environmentTimeline != null) environmentTimeline.stop();
    }

    // ---------- Stats Refresh ----------
    private void updatePetCards() {
        for (Pet pet : mainApp.getAdoptedPets()) {
            ProgressBar hungerBar = hungerBars.get(pet);
            ProgressBar happinessBar = happinessBars.get(pet);
            ProgressBar energyBar = energyBars.get(pet);
            HBox heartsRow = heartRows.get(pet);

            if (hungerBar != null) hungerBar.setProgress(pet.getHunger() / 100.0);
            if (happinessBar != null) happinessBar.setProgress(pet.getHappiness() / 100.0);
            if (energyBar != null) energyBar.setProgress(pet.getEnergy() / 100.0);
            if (heartsRow != null) updateHeartsRow(pet, heartsRow);
        }
    }

    protected void updateStatsAndHearts() {
        updatePetCards();
    }

    private void updateHeartsRow(Pet pet, HBox row) {
        row.getChildren().clear();
        int hearts = pet.getRelationshipHearts();
        for (int i = 0; i < 5; i++) {
            Image heart = (i < hearts) ? fullHeart : emptyHeart;
            ImageView hv = new ImageView(heart);
            hv.setFitWidth(18);
            hv.setFitHeight(18);
            row.getChildren().add(hv);
        }
    }

    // ---------- Breeding ----------
    private void checkForBreeding() {
        var pets = mainApp.getAdoptedPets();
        if (pets.size() < 2) return;

        for (int i = 0; i < pets.size(); i++) {
            for (int j = i + 1; j < pets.size(); j++) {
                Pet p1 = pets.get(i);
                Pet p2 = pets.get(j);

                boolean sameSpecies = p1.getSpecies().equals(p2.getSpecies());
                boolean fullHearts = p1.getRelationshipHearts() >= 5 && p2.getRelationshipHearts() >= 5;

                if (sameSpecies && fullHearts && mainApp.canAdoptAnotherPet()) {
                    resetRelationship(p1);
                    resetRelationship(p2);
                    mainApp.showBreedingScene(p1, p2);
                    return;
                }
            }
        }
    }

    private void resetRelationship(Pet pet) {
        try {
            var field = Pet.class.getDeclaredField("relationship");
            field.setAccessible(true);
            field.set(pet, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- Button styling ----------
    protected Button styledButton(String text) {
        Button b = new Button(text);
        b.setPrefWidth(140);
        b.setStyle("""
            -fx-font-size: 15px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """);

        b.setOnMouseEntered(e -> b.setStyle("""
            -fx-font-size: 15px;
            -fx-background-color: #ffe2a8;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        b.setOnMouseExited(e -> b.setStyle("""
            -fx-font-size: 15px;
            -fx-background-color: #ffd27f;
            -fx-background-radius: 10;
            -fx-padding: 8 18;
            -fx-border-radius: 10;
            -fx-border-color: #d4b483;
            -fx-border-width: 2;
        """));

        return b;
    }

    // ---------- Assets ----------
    private void loadHeartImages() {
        fullHeart = AssetCache.getImage("/petgame/assets/displayIcons/heart_full.png");
        emptyHeart = AssetCache.getImage("/petgame/assets/displayIcons/heart_empty.png");
    }

    // ---------- Layout getters ----------
    public BorderPane getLayout() { return layout; }

    public Scene getScene() {
        return new Scene(layout, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
    }
}
