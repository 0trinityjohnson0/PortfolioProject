// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Random;

public class BreedingScene {

    private final MainApp mainApp;
    private final Pet parent1;
    private final Pet parent2;
    private final Pet baby;
    private final BorderPane layout;

    public BreedingScene(MainApp mainApp, Pet parent1, Pet parent2) {
        this.mainApp = mainApp;
        this.parent1 = parent1;
        this.parent2 = parent2;

        this.baby = createBaby(parent1, parent2);
        this.layout = buildLayout();
    }

    private Pet createBaby(Pet p1, Pet p2) {
        Random random = new Random();

        String gender = random.nextBoolean() ? "Male" : "Female";
        String breed = random.nextBoolean() ? p1.getBreed() : p2.getBreed();

        // Temporary placeholder name until the player names it
        String tempName = "Unnamed Baby";

        return new Pet(tempName, p1.getSpecies(), breed, gender);
    }

    private BorderPane buildLayout() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(30));

        // Title message
        Label title = new Label(parent1.getName() + " and " + parent2.getName() + " had a baby!");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        pane.setTop(title);

        // Center: Baby image and input
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        // Baby sprite image
        String spritePath = "/petgame/assets/" + baby.getSpecies() + "/" + baby.getBreed() + "/Idle.png";
        Image babyImg = AssetCache.getImage(spritePath);
        ImageView babyView = new ImageView(babyImg);
        babyView.setFitWidth(180);
        babyView.setPreserveRatio(true);

        Label nameLabel = new Label("What will you name the baby?");
        nameLabel.setStyle("-fx-font-size: 18px;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter baby name...");
        nameField.setMaxWidth(200);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px;");

        confirmBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) return;

            baby.setName(name);

            // Add the new baby to the player's pets
            mainApp.getAdoptedPets().add(baby);

            // Return to home scene with the new baby visible
            mainApp.showHomeScene(baby);
        });

        centerBox.getChildren().addAll(babyView, nameLabel, nameField, confirmBtn);
        pane.setCenter(centerBox);

        return pane;
    }

    public Scene getScene() {
        return new Scene(layout, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
    }
}