// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// HomeScene class -- Displays the player's adopted pet and its stats. 
// Provides buttons for interactions like feeding, playing, and grooming. 
// Shows animations and updates the pet's happiness and hunger stats.

package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.InputStream;

public class HomeScene {

    private BorderPane layout;
    private MainApp mainApp;
    private Pet pet;

    // Labels for stats
    private Label happinessLabel;
    private Label hungerLabel;

    public HomeScene(MainApp mainApp, Pet pet) {
        this.mainApp = mainApp;
        this.pet = pet;
        createLayout();
    }

    private void createLayout() {
        layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // ---------- Top section: Pet info ----------
        Label petNameLabel = new Label(pet.getName() + " the " + pet.getBreed() + " " + pet.getSpecies());
        petNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        layout.setTop(petNameLabel);
        BorderPane.setAlignment(petNameLabel, Pos.CENTER);

        // ---------- Center section: Pet sprite ----------
        StackPane centerPane = new StackPane();
        
        String imagePath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/Idle.png";
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            System.out.println("Image not found: " + imagePath);
            is = getClass().getResourceAsStream("/petgame/assets/placeholder.png");
        }
        Image spriteSheet = new Image(is);
        
        ImageView petImageView = new ImageView(spriteSheet);
        
        // ---- Animation Settings ---
        int numFrames = 4;     // number of frames in the sheet
        int frameHeight = 48;  // height of a single frame
        int frameWidth = (int) spriteSheet.getWidth() / numFrames; // auto-calculate from sheet
        //int frameWidth = 4;   // width of a single frame
        petImageView.setViewport(new javafx.geometry.Rectangle2D(0, 0, frameWidth, frameHeight));
        
        petImageView.setViewport(new javafx.geometry.Rectangle2D(0, 0, frameWidth, frameHeight));
        petImageView.setFitWidth(200);
        petImageView.setFitHeight(200);
        petImageView.setPreserveRatio(true);
        
     // Timeline to animate frames
        final int[] currentFrame = {0}; // use array to modify inside lambda
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            currentFrame[0] = (currentFrame[0] + 1) % numFrames;
            petImageView.setViewport(new javafx.geometry.Rectangle2D(
                    currentFrame[0] * frameWidth, 0, frameWidth, frameHeight
            ));
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        
        
        centerPane.getChildren().add(petImageView);
        layout.setCenter(centerPane);

        // ---------- Bottom section: Buttons ----------
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button feedBtn = new Button("Feed");
        Button playBtn = new Button("Play");
        Button groomBtn = new Button("Groom");

        // --- FEED ---
        // Button actions (for now, just update labels)
        feedBtn.setOnAction(e -> {
            pet.increaseHunger(10); // assume Pet has this method
            updateStatLabels();
            System.out.println(pet.getName() + " has been fed!");
        });

        // --- PLAY ---
        playBtn.setOnAction(e -> {
            // Determine the path for bark/meow sprite
            String actionPath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/";
            actionPath += pet.getSpecies().equals("Dog") ? "Bark.png" : "Meow.png";

            InputStream isAction = getClass().getResourceAsStream(actionPath);
            if (isAction != null) {
                Image actionImage = new Image(isAction);
                petImageView.setImage(actionImage);

                // Return to idle after a short delay (like 0.5 seconds)
                Timeline revertIdle = new Timeline(new KeyFrame(Duration.seconds(0.5), ev -> {
                    petImageView.setImage(spriteSheet);
                }));
                revertIdle.play();
            }

            // Increase happiness
            pet.increaseHappiness(10); // same as before
            updateStatLabels();
            System.out.println(pet.getName() + " played and is happier!");
        });
        
        // --- GROOM ---
        groomBtn.setOnAction(e -> {
            pet.increaseHappiness(5);
            updateStatLabels();
            System.out.println(pet.getName() + " has been groomed!");
        });

        buttonBox.getChildren().addAll(feedBtn, playBtn, groomBtn);
        layout.setBottom(buttonBox);

        // ---------- Left or Right: Stat labels ----------
        VBox statsBox = new VBox(10);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        happinessLabel = new Label();
        hungerLabel = new Label();
        updateStatLabels();

        statsBox.getChildren().addAll(happinessLabel, hungerLabel);
        layout.setLeft(statsBox);
    }

    private void updateStatLabels() {
        happinessLabel.setText("Happiness: " + pet.getHappiness());
        hungerLabel.setText("Hunger: " + pet.getHunger());
    }

    public BorderPane getLayout() {
        return layout;
    }
}