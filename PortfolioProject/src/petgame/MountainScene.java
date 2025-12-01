// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project
//
// MountainScene -- Environment for pets to explore.

package petgame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MountainScene extends BasePetScene {

    public MountainScene(MainApp mainApp, Pet pet) {
        super(mainApp, pet);
    }

    @Override
    protected String getBackgroundPath() {
        return "/petgame/assets/backgrounds/mountain_background.png";
    }

    @Override
    protected String getSceneTitle() {
        return "Mountains";
    }

    @Override
    protected Pane buildCareButtons() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);

        Button exploreBtn = styledButton("Explore Trail");
        Button sniffBtn = styledButton("Sniff Around");
        Button restBtn = styledButton("Rest on Rock");

        // EXPLORE
        exploreBtn.setOnAction(e -> {
            activePet.decreaseEnergy(6);     
            activePet.increaseHappiness(8); 
            activePet.increaseHunger(3);
            updateStatsAndHearts();
        });

        // SNIFF
        sniffBtn.setOnAction(e -> {
            activePet.increaseHappiness(4);
            activePet.decreaseEnergy(2);
            updateStatsAndHearts();
        });

        // REST
        restBtn.setOnAction(e -> {
            activePet.increaseEnergy(10);
            activePet.increaseHappiness(2);
            updateStatsAndHearts();
        });

        box.getChildren().addAll(exploreBtn, sniffBtn, restBtn);
        return box;
    }

    @Override
    protected void onEnvironmentTick(Pet p) {
        p.decreaseEnergy(2); 		// Regular energy used
        p.decreaseHunger(2);		// Walking around
        p.increaseHappiness(1);		// Being outside makes you happy

        updateStatsAndHearts();
    }
}
