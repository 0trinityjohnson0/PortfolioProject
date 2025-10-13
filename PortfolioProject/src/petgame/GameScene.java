package petgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GameScene {

    private MainApp mainApp;
    private Pet currentPet;
    private VBox layout;

    // UI elements for pet stats
    private Label nameLabel;
    private Label breedLabel;
    private Label speciesLabel;

    private ProgressBar hungerBar;
    private ProgressBar happinessBar;
    private ProgressBar energyBar;

    private Label feedbackLabel;

    public GameScene(MainApp mainApp, Pet pet) {
        this.mainApp = mainApp;
        this.currentPet = pet;
        createLayout();
    }

    private void createLayout() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // --- PET INFO ---
        nameLabel = new Label(currentPet.getName());
        nameLabel.setFont(new Font(28));

        speciesLabel = new Label("Species: " + currentPet.getSpecies());
        breedLabel = new Label("Breed: " + currentPet.getBreed());

        // --- STAT BARS ---
        Label hungerLabel = new Label("Hunger");
        hungerBar = new ProgressBar(currentPet.getHunger() / 100.0);
        hungerBar.setPrefWidth(200);

        Label happinessLabel = new Label("Happiness");
        happinessBar = new ProgressBar(currentPet.getHappiness() / 100.0);
        happinessBar.setPrefWidth(200);

        Label energyLabel = new Label("Energy");
        energyBar = new ProgressBar(currentPet.getEnergy() / 100.0);
        energyBar.setPrefWidth(200);

        VBox statsBox = new VBox(10, 
                new HBox(10, hungerLabel, hungerBar),
                new HBox(10, happinessLabel, happinessBar),
                new HBox(10, energyLabel, energyBar)
        );
        statsBox.setAlignment(Pos.CENTER);

        // --- BUTTONS ---
        Button feedButton = new Button("Feed");
        Button playButton = new Button("Play");
        Button petButton = new Button("Pet");

        HBox buttonBox = new HBox(15, feedButton, playButton, petButton);
        buttonBox.setAlignment(Pos.CENTER);

        // --- FEEDBACK ---
        feedbackLabel = new Label("Welcome home, " + currentPet.getName() + "!");
        feedbackLabel.setFont(new Font(16));

        // --- EVENT HANDLERS ---
        feedButton.setOnAction(e -> {
            currentPet.feed(new Food("Kibble"));
            updateStats();
            feedbackLabel.setText(currentPet.getName() + " enjoyed the meal!");
        });

        playButton.setOnAction(e -> {
            currentPet.play(new Toy("Ball"));
            updateStats();
            feedbackLabel.setText(currentPet.getName() + " had fun playing!");
        });

        petButton.setOnAction(e -> {
            currentPet.pet();
            updateStats();
            feedbackLabel.setText(currentPet.getName() + " looks so happy!");
        });

        // --- BACK BUTTON ---
        Button exitButton = new Button("Back to Main Menu");
        exitButton.setOnAction(e -> mainApp.showMainMenu());

        layout.getChildren().addAll(nameLabel, speciesLabel, breedLabel, statsBox, buttonBox, feedbackLabel, exitButton);
    }

    private void updateStats() {
        hungerBar.setProgress(currentPet.getHunger() / 100.0);
        happinessBar.setProgress(currentPet.getHappiness() / 100.0);
        energyBar.setProgress(currentPet.getEnergy() / 100.0);
    }

    public VBox getLayout() {
        return layout;
    }
}