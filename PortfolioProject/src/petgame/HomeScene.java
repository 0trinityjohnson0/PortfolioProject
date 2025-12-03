// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project
// 
// Home Scene -- 

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
        p.decreaseHunger(2);
        p.decreaseHappiness(1);
        p.increaseEnergy(2);
    }

    @Override
    protected Pane buildCareButtons() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);

        Button feedBtn = styledButton("Feed");
        Button playBtn = styledButton("Play");
        Button groomBtn = styledButton("Groom");

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

        box.getChildren().addAll(feedBtn, playBtn, groomBtn);
        return box;
    }
}
