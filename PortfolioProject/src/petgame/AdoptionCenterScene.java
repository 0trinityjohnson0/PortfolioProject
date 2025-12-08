// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AdoptionCenterScene {

    private final MainApp mainApp;
    private final boolean isInitialAdoption;
    private final BorderPane layout;
    private final Random random = new Random();

    private final List<Pet> petOptions = new ArrayList<>();

    public AdoptionCenterScene(MainApp mainApp, boolean isInitialAdoption) {
        this.mainApp = mainApp;
        this.isInitialAdoption = isInitialAdoption;
        layout = new BorderPane();

        generatePetOptions();
        createLayout();
    }

    // ---------- GENERATE PET OPTIONS ----------
    private void generatePetOptions() {
        if (isInitialAdoption) {
            // First-time choice — 3 pre-chosen pets
            petOptions.add(new Pet("", "Cat", "Tuxedo", "Male"));
            petOptions.add(new Pet("", "Dog", "Doberman", "Male"));
            petOptions.add(new Pet("", "Dog", "Shiba Inu", "Female"));
        } else {
            // Later adoptions — 3 randomly generated pets
            String[] species = {"Dog", "Cat"};
            String[][] breeds = {
                    {"Doberman", "Shiba Inu"},
                    {"OrangeTabby", "Tuxedo"}
            };
            String[] genders = {"Male", "Female"};

            for (int i = 0; i < 3; i++) {
                int spIndex = random.nextInt(species.length);
                String s = species[spIndex];
                String b = breeds[spIndex][random.nextInt(breeds[spIndex].length)];
                String g = genders[random.nextInt(genders.length)];
                petOptions.add(new Pet("???", s, b, g)); // placeholder name??
            }
        }
    }

    // ---------- CREATE LAYOUT ----------
    private void createLayout() {
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #f4f1ea;");

        Label title = new Label("Adoption Center");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        layout.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        HBox petBox = new HBox(40);
        petBox.setAlignment(Pos.CENTER);

        for (Pet pet : petOptions) {
            VBox petCard = createPetCard(pet);
            petBox.getChildren().add(petCard);
        }

        layout.setCenter(petBox);

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 16px;");
        backBtn.setOnAction(e -> {
            if (isInitialAdoption) {
                mainApp.showMainMenu();
            } else {
                mainApp.showHomeScene(mainApp.getAdoptedPets().get(0));
            }
        });

        layout.setBottom(backBtn);
        BorderPane.setAlignment(backBtn, Pos.CENTER);
        BorderPane.setMargin(backBtn, new Insets(20, 0, 0, 0));
    }

    // ---------- CREATE PET CARDS ----------
    private VBox createPetCard(Pet pet) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #fff8f0; -fx-border-color: #d4b483; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(220);

        // Load pet image 
        String imagePath = "/petgame/assets/" + pet.getSpecies() + "/" + pet.getBreed() + "/Idle.png";
        InputStream is = getClass().getResourceAsStream(imagePath);
        ImageView petView = new ImageView();

        if (is != null) {
            Image spriteSheet = new Image(is);

            // Showing the sprite's movement
            int frameCount = 4; // same as home scene
            int frameHeight = 70; // match sprite frame height
            int frameWidth = (int) spriteSheet.getWidth() / frameCount;
            double frameDuration = 1500; // better movement

            petView.setImage(spriteSheet);
            petView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
            petView.setFitWidth(150);
            petView.setPreserveRatio(true);

            // Idle animation
            playIdleAnimation(petView, frameCount, frameWidth, frameHeight, frameDuration);
        } else {
            System.err.println("WARNING: Pet image not found: " + imagePath);
        }

        Label nameLabel = new Label(pet.getBreed());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label genderLabel = new Label("Gender: " + pet.getGender());
        genderLabel.setStyle("-fx-font-size: 14px;");

        Button adoptBtn = new Button("Choose Me!");
        adoptBtn.setStyle("-fx-font-size: 16px; -fx-background-color: #ffd27f;");
        adoptBtn.setOnAction(e -> promptNameAndAdopt(pet));

        card.getChildren().addAll(petView, nameLabel, genderLabel, adoptBtn);
        return card;
    }

    // ---------- SPRITE ANIMATION HELPER ----------
    private void playIdleAnimation(ImageView imageView, int frameCount, int frameWidth, int frameHeight, double duration) {
        Timeline animation = new Timeline();

        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            animation.getKeyFrames().add(
                new KeyFrame(Duration.millis(i * (duration / frameCount)), e -> {
                    imageView.setViewport(new Rectangle2D(frameIndex * frameWidth, 0, frameWidth, frameHeight));
                })
            );
        }

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    // --- ADOPTION PROCESS ---
    private void promptNameAndAdopt(Pet chosenPet) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name Your Pet");
        dialog.setHeaderText("What would you like to name your new friend?");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            chosenPet.setName(name);
            mainApp.startGameWithPet(chosenPet);
        });
    }

    public BorderPane getLayout() {
        return layout;
    }
}