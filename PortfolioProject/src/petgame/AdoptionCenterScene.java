// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

// AdoptionCenterScene class -- Displays a screen where the player can adopt a pet. 
// Shows a selection of randomly generated pets with their species, breed, and gender, 
// and allows the player to name and adopt one.

package petgame;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdoptionCenterScene {

    private VBox layout;
    private MainApp mainApp;
    private List<Pet> availablePets;

    public AdoptionCenterScene(MainApp mainApp) {
        this.mainApp = mainApp;
        this.availablePets = generateRandomPets();
        createLayout();
    }

    private void createLayout() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome to the Pet Adoption Center!");
        title.setFont(new Font(26));

        Label instruction = new Label("Choose your first baby pet to adopt:");
        instruction.setFont(new Font(16));

        HBox petsBox = new HBox(20);
        petsBox.setAlignment(Pos.CENTER);

        for (Pet pet : availablePets) {
            VBox petCard = createPetCard(pet);
            petsBox.getChildren().add(petCard);
        }

        Button backBtn = new Button("Back to Main Menu");
        backBtn.setOnAction(e -> mainApp.showMainMenu());

        layout.getChildren().addAll(title, instruction, petsBox, backBtn);
    }

    private VBox createPetCard(Pet pet) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-border-color: #333; -fx-border-width: 2px; -fx-background-color: #f5f5f5; "
                + "-fx-border-radius: 10px; -fx-background-radius: 10px;");
        card.setPrefWidth(180);

        // ---------- Add Image ----------
        String speciesFolder = pet.getSpecies(); // "Dog" or "Cat"
        String breedFolder = pet.getBreed();     // "Doberman" or "Tuxedo"
        String imagePath = "/petgame/assets/" + speciesFolder + "/" + breedFolder + "/Idle.png";

        Image petImage;
        try {
            petImage = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
            petImage = new Image(getClass().getResourceAsStream("/assets/Idle.png"));
        }

        ImageView petImageView = new ImageView(petImage);
        petImageView.setFitWidth(120);
        petImageView.setFitHeight(120);
        petImageView.setPreserveRatio(true);

        // ---------- Labels ----------
        Label speciesLabel = new Label(pet.getSpecies());
        speciesLabel.setFont(new Font(18));

        Label breedLabel = new Label("Breed: " + pet.getBreed());
        Label genderLabel = new Label("Gender: " + pet.getGender());
        //Label ageLabel = new Label("Age: Baby");

        // ---------- Adopt Button ----------
        Button adoptBtn = new Button("Adopt Me!");
        adoptBtn.setOnAction(e -> adoptPet(pet));

        card.getChildren().addAll(petImageView, speciesLabel, breedLabel, genderLabel, adoptBtn);
        return card;
    }

    private void adoptPet(Pet pet) {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Name Your Pet");
        nameDialog.setHeaderText("You chose a baby " + pet.getBreed() + " " + pet.getSpecies().toLowerCase() + "!");
        nameDialog.setContentText("Enter your pet's name:");

        nameDialog.showAndWait().ifPresent(name -> {
            pet.setName(name.trim());
            mainApp.startGameWithPet(pet);
        });
    }

    private List<Pet> generateRandomPets() {
        List<Pet> pets = new ArrayList<>();
        Random rand = new Random();

        String[] dogBreeds = {"Doberman"};
        String[] catBreeds = {"Tuxedo"};
        String[] genders = {"Male", "Female"};

        for (int i = 0; i < 3; i++) {
            boolean isDog = rand.nextBoolean();
            String species = isDog ? "Dog" : "Cat";
            String breed = isDog
                    ? dogBreeds[rand.nextInt(dogBreeds.length)]
                    : catBreeds[rand.nextInt(catBreeds.length)];
            String gender = genders[rand.nextInt(genders.length)];

            Pet pet = new Pet("Unnamed", species, breed, gender);
            //pet.setAge(0); // newborn
            pets.add(pet);
        }

        return pets;
    }

    public VBox getLayout() {
        return layout;
    }
}