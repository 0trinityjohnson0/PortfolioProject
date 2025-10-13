// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// HomeScene class -- This is the main gameplay screen where the player interacts
// with their adopted pet. The pet’s animated sprite is shown here, along with
// status bars for happiness, hunger, and energy. Buttons let the player feed,
// play, or groom their pet, which affects its stats and animations.

package petgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.InputStream;

public class HomeScene {

    private BorderPane layout;
    private MainApp mainApp;
    private Pet pet;

    // UI Elements
    private ProgressBar happinessBar;
    private ProgressBar hungerBar;
    private ProgressBar energyBar;

    public HomeScene(MainApp mainApp, Pet pet) {
        this.mainApp = mainApp;
        this.pet = pet;
        createLayout();
    }

    private void createLayout() {
        layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // ---------- Top: Pet info ----------
        Label petNameLabel = new Label(pet.getName());
        petNameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        layout.setTop(petNameLabel);
        BorderPane.setAlignment(petNameLabel, Pos.CENTER);

        // ---------- Center: Pet Sprite ----------
        StackPane centerPane = new StackPane();
        String imagePath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/Idle.png";

        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            System.out.println("Image not found: " + imagePath);
            is = getClass().getResourceAsStream("/petgame/assets/placeholder.png");
        }

        Image spriteSheet = new Image(is);
        ImageView petImageView = new ImageView(spriteSheet);

        int numFrames = 4;
        int frameHeight = 48;
        int frameWidth = (int) spriteSheet.getWidth() / numFrames;

        petImageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        petImageView.setFitWidth(200);
        petImageView.setPreserveRatio(true);

        // Animate idle
        final int[] currentFrame = {0};
        Timeline idleAnim = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            currentFrame[0] = (currentFrame[0] + 1) % numFrames;
            petImageView.setViewport(new Rectangle2D(currentFrame[0] * frameWidth, 0, frameWidth, frameHeight));
        }));
        idleAnim.setCycleCount(Timeline.INDEFINITE);
        idleAnim.play();

        centerPane.getChildren().add(petImageView);
        layout.setCenter(centerPane);

        // ---------- Left: Stats ----------
        VBox statsBox = new VBox(12);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        Label hungerLabel = new Label("Hunger:");
        hungerBar = new ProgressBar(pet.getHunger() / 100.0);
        hungerBar.setPrefWidth(150);

        Label happinessLabel = new Label("Happiness:");
        happinessBar = new ProgressBar(pet.getHappiness() / 100.0);
        happinessBar.setPrefWidth(150);

        Label energyLabel = new Label("Energy:");
        energyBar = new ProgressBar(pet.getEnergy() / 100.0);
        energyBar.setPrefWidth(150);

        statsBox.getChildren().addAll(hungerLabel, hungerBar, happinessLabel, happinessBar, energyLabel, energyBar);
        layout.setLeft(statsBox);

        // ---------- Bottom: Buttons ----------
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button feedBtn = new Button("Feed");
        Button playBtn = new Button("Play");
        Button groomBtn = new Button("Groom");

        feedBtn.setOnAction(e -> {
            pet.increaseHunger(10);
            updateStats();
            System.out.println(pet.getName() + " has been fed!");
        });

        playBtn.setOnAction(e -> {
            playActionAnimation(petImageView, spriteSheet);
            pet.increaseHappiness(10);
            updateStats();
            System.out.println(pet.getName() + " played and is happier!");
        });

        groomBtn.setOnAction(e -> {
            layDownAnimation(petImageView, spriteSheet);
            pet.increaseHappiness(5);
            updateStats();
            System.out.println(pet.getName() + " has been groomed!");
        });

        buttonBox.getChildren().addAll(feedBtn, playBtn, groomBtn);
        layout.setBottom(buttonBox);
        
        // ---------- Timeline: Decay + Energy ----------
        Timeline homeEnergyTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            // Decay
            pet.decreaseHunger(2);
            pet.decreaseHappiness(1);

            // Gain energy at home
            pet.increaseEnergy(2);

            // Update bars
            updateStats();
        }));
        homeEnergyTimeline.setCycleCount(Timeline.INDEFINITE);
        homeEnergyTimeline.play();
    }

    private void playActionAnimation(ImageView petImageView, Image spriteSheet) {
        String actionPath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/";
        actionPath += pet.getSpecies().equals("Dog") ? "Bark.png" : "Meow.png";

        InputStream isAction = getClass().getResourceAsStream(actionPath);
        if (isAction != null) {
            Image actionImage = new Image(isAction);
            petImageView.setImage(actionImage);

            Timeline revertIdle = new Timeline(new KeyFrame(Duration.seconds(0.8), ev -> {
                petImageView.setImage(spriteSheet);
            }));
            revertIdle.play();
        }
    }

    private void layDownAnimation(ImageView petImageView, Image spriteSheet) {
        String layPath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/Lay.png";
        InputStream isLay = getClass().getResourceAsStream(layPath);
        if (isLay != null) {
            Image layImage = new Image(isLay);
            petImageView.setImage(layImage);

            Timeline revertIdle = new Timeline(new KeyFrame(Duration.seconds(1.2), ev -> {
                petImageView.setImage(spriteSheet);
            }));
            revertIdle.play();
        }
    }

    private void updateStats() {
        hungerBar.setProgress(pet.getHunger() / 100.0);
        happinessBar.setProgress(pet.getHappiness() / 100.0);
        energyBar.setProgress(pet.getEnergy() / 100.0);
    }

    public BorderPane getLayout() {
        return layout;
    }
}