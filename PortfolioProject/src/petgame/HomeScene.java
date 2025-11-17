// --- Pet Haven --- 
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class HomeScene extends BasePetScene {

    public HomeScene(MainApp mainApp, Pet pet) {
        super(mainApp, pet);
    }

    @Override
    protected String getBackgroundPath() {
        return "/petgame/assets/backgrounds/home_background.jpeg";
    }
    
    @Override
    protected String getSceneTitle() {
        return "Home";
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        // Home is a resting environment
        p.decreaseHunger(2);
        p.decreaseHappiness(1);
        p.increaseEnergy(2);
    }

    @Override
    protected HBox buildCareButtons() {
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        Button feedBtn = styledButton("Feed");
        Button playBtn = new Button("Play");
        Button groomBtn = new Button("Groom");

        feedBtn.setOnAction(e -> {
            for (Pet pet : mainApp.getAdoptedPets()) pet.feed();
            updateStatsAndHearts();
        });

        playBtn.setOnAction(e -> {
            for (Pet pet : mainApp.getAdoptedPets()) pet.play();
            updateStatsAndHearts();
        });

        groomBtn.setOnAction(e -> {
            for (Pet pet : mainApp.getAdoptedPets()) pet.groom();
            updateStatsAndHearts();
        });

        buttonBox.getChildren().addAll(feedBtn, playBtn, groomBtn);
        return buttonBox;
    }
}